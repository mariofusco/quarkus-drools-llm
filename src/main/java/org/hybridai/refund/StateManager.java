package org.hybridai.refund;

import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hybridai.refund.model.ChatState;
import org.hybridai.refund.model.Customer;
import org.hybridai.refund.model.Flight;
import org.hybridai.refund.model.SessionData;
import org.jboss.logging.Logger;

@Singleton
public class StateManager {

    private static final Logger LOG = Logger.getLogger(StateManager.class);

    @Inject
    DroolsStateMachine stateMachine;

    @Inject
    CustomerChatService customerChatService;

    @Inject
    FlightChatService flightChatService;

    @Inject
    CustomerExtractor customerExtractor;

    @Inject
    FlightExtractor flightExtractor;

    public ChatbotState getState(SessionData sessionData) {
        var nextState = stateMachine.nextState(sessionData);
        return new ChatbotState(nextState, sessionData);
    }

    private Optional<Customer> readCustomer(SessionData sessionData, String message) {
        try {
            if (sessionData.getCustomer() == null) {
                LOG.info("Extracting customer from " + message);
                Customer customer = customerExtractor.extractData(message);
                if (customer != null && customer.isValid()) {
                    LOG.info("Extracted: " + customer);
                    return Optional.of(customer);
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return Optional.empty();
    }

    private Optional<Flight> readFlight(SessionData sessionData, String message) {
        try {
            if (sessionData.getFlight() == null) {
                LOG.info("Extracting flight from " + message);
                Flight flight = flightExtractor.extractData(message);
                if (flight != null && flight.isValid()) {
                    LOG.info("Extracted: " + flight);
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
                    readCustomer(sessionData, message).ifPresent(sessionData::setCustomer);
                    break;
                case EXTRACT_FLIGHT:
                    readFlight(sessionData, message).ifPresent(sessionData::setFlight);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        public String chat(String message) {
            return switch (chatState) {
                case EXTRACT_CUSTOMER -> customerChatService.chat(sessionData.getSessionId(), message);
                case EXTRACT_FLIGHT -> flightChatService.chat(sessionData.getSessionId(), message);
                default -> throw new IllegalStateException();
            };
        }
    }
}
