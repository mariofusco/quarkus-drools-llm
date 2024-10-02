package org.hybridai.refund;

import java.util.Optional;
import java.util.function.Function;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.ConcatenatingChatMemory;
import org.hybridai.refund.aiservices.AirlineChatService;
import org.hybridai.refund.aiservices.CustomerChatService;
import org.hybridai.refund.aiservices.CustomerExtractor;
import org.hybridai.refund.aiservices.FlightChatService;
import org.hybridai.refund.aiservices.FlightExtractor;
import org.hybridai.refund.model.ChatState;
import org.hybridai.refund.model.SessionData;
import org.hybridai.refund.model.Validated;
import org.jboss.logging.Logger;

@Singleton
public class StateManager {

    private static final Logger LOG = Logger.getLogger(StateManager.class);

    @Inject
    DroolsStateMachine stateMachine;

    @Inject
    AirlineChatService airlineChatService;

    @Inject
    CustomerChatService customerChatService;

    @Inject
    FlightChatService flightChatService;

    @Inject
    CustomerExtractor customerExtractor;

    @Inject
    FlightExtractor flightExtractor;

    @Inject
    ConcatenatingChatMemory extractorsMemory;

    public ChatbotState getState(SessionData sessionData) {
        var nextState = stateMachine.nextState(sessionData);
        return new ChatbotState(nextState, sessionData);
    }

    private <T extends Validated> Optional<T> readDomainObject(SessionData sessionData, String message, String kind, Function<String, T> extractor) {
        try {
            if (sessionData.getFlight() == null) {
                LOG.info("Extracting " + kind + " from " + message);
                String sessionId = sessionData.getSessionId() + kind;
                T flight = extractor.apply(extractorsMemory.append(sessionId, message));
                if (flight != null && flight.isValid()) {
                    LOG.info("Extracted: " + flight);
                    extractorsMemory.clear(sessionId);
                    return Optional.of(flight);
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return Optional.empty();
    }

    public class ChatbotState {
        private final ChatState chatState;
        private final SessionData sessionData;

        public ChatbotState(ChatState chatState, SessionData sessionData) {
            this.chatState = chatState;
            this.sessionData = sessionData;
        }

        public void extractData(String message) {
            switch (chatState) {
                case EXTRACT_CUSTOMER:
                    readDomainObject(sessionData, message, "customer", customerExtractor::extractData).ifPresent(sessionData::setCustomer);
                    break;
                case EXTRACT_FLIGHT:
                    readDomainObject(sessionData, message, "flight", flightExtractor::extractData).ifPresent(sessionData::setFlight);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        public String chat(String message) {
            return switch (chatState) {
                case EXTRACT_CUSTOMER -> customerChatService.chat(sessionData.getSessionId(), message);
                case EXTRACT_FLIGHT -> flightChatService.chat(sessionData.getSessionId(), message);
                default -> airlineChatService.chat(sessionData.getSessionId(), message);
            };
        }
    }
}
