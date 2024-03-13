package org.hybridai.refund;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/hybridai")
public class RefundChatbotEndpoint {

    private static final Logger LOG = Logger.getLogger(RefundChatbotEndpoint.class);

    @Inject
    ChatService chatService;

    @Inject
    SessionCache sessionCache;

    @Inject
    CustomerExtractor customerExtractor;

    @Inject
    FlightExtractor flightExtractor;

    @Inject
    DroolsRefundCalculator refundCalculator;

    @POST
    @Path("chatbot/{sessionId}/refund")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String chat(String sessionId, String message) {
        LOG.info("sessionId = " + sessionId);
        SessionData sessionData = sessionCache.getSessionData(sessionId);
        LOG.info("sessionData = " + sessionData);

        var customer = CompletableFuture.supplyAsync(() -> readCustomer(sessionData, message));
        var flight = CompletableFuture.supplyAsync(() -> readFlight(sessionData, message));
        var chatResponse = CompletableFuture.supplyAsync(() -> chatService.chat(sessionId, message));

        customer.join().ifPresent(sessionData::setCustomer);
        flight.join().ifPresent(sessionData::setFlight);

        if (sessionData.isComplete()) {
            chatResponse.complete("unnecessary");
            sessionCache.clear(sessionId);
            return refundCalculator.checkRefund(sessionData);
        }

        return chatResponse.join();
    }

    private Optional<Customer> readCustomer(SessionData sessionData, String message) {
        try {
            if (sessionData.getCustomer() == null) {
                LOG.info("Extracting customer from " + message);
                Customer customer = customerExtractor.extractCustomerFrom(message);
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
                Flight flight = flightExtractor.extractFlightFrom(message);
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
}
