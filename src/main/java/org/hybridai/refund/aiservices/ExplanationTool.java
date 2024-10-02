package org.hybridai.refund.aiservices;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hybridai.refund.SessionCache;
import org.hybridai.refund.model.RefundAmount;
import org.hybridai.refund.model.SessionData;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ExplanationTool {

    private static final Logger LOG = Logger.getLogger(ExplanationTool.class);

    @Inject
    SessionCache sessionCache;

    @Tool
    public String getRefundExplanation(String sessionId) {
        LOG.info("ExplanationTool called for session: " + sessionId);
        SessionData sessionData = sessionCache.getSessionData(sessionId);
        RefundAmount refundAmount = sessionData.getRefundAmount();
        String explanation = refundAmount != null ? "The explanation for the refund is: " + refundAmount.getExplanation() : "At the moment there is no explanation available.";
        LOG.info(explanation);
        return explanation;
    }
}