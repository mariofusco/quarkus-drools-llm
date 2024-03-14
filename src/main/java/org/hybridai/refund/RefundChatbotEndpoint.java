package org.hybridai.refund;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.hybridai.refund.model.SessionData;
import org.jboss.logging.Logger;

@Path("/hybridai")
public class RefundChatbotEndpoint {

    private static final Logger LOG = Logger.getLogger(RefundChatbotEndpoint.class);

    @Inject
    SessionCache sessionCache;

    @Inject
    StateManager stateManager;

    @Inject
    DroolsRefundCalculator refundCalculator;

    @POST
    @Path("chatbot/{sessionId}/refund")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String chat(String sessionId, String message) {
        LOG.info("message = " + message);
        LOG.info("sessionId = " + sessionId);
        SessionData sessionData = sessionCache.getSessionData(sessionId);
        LOG.info("sessionData = " + sessionData);

        stateManager.getState(sessionData).extractData(message);

        return sessionData.isComplete() ?
                refundCalculator.checkRefund(sessionData) :
                stateManager.getState(sessionData).chat(message);
    }
}
