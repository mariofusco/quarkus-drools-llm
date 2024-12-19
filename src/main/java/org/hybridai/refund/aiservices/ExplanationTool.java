package org.hybridai.refund.aiservices;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hybridai.refund.SessionCache;
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
        String explanation = sessionData.hasExplanation() ?
                "The explanation for the refund is: " + sessionData.getExplanation() :
                "You're not entitled of a refund because your delay was less than 1 hour.";
        LOG.info(explanation);
        return explanation;
    }
}