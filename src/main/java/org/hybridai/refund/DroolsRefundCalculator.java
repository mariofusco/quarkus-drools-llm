package org.hybridai.refund;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hybridai.refund.model.RefundAmount;
import org.hybridai.refund.model.SessionData;
import org.jboss.logging.Logger;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Singleton
public class DroolsRefundCalculator {

    private static final Logger LOG = Logger.getLogger(DroolsRefundCalculator.class);

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    public String checkRefund(SessionData sessionData) {
        KieSession kieSession = runtimeBuilder.newKieSession("refundCalc");
        kieSession.addEventListener(new RecordingRulesListener(sessionData));

        kieSession.insert(sessionData.getCustomer());
        kieSession.insert(sessionData.getFlight());
        kieSession.fireAllRules();

        var refunds = kieSession.getInstancesOf(RefundAmount.class);
        sessionData.setRefundAmount(refunds.isEmpty() ? RefundAmount.NO_REFUND : refunds.iterator().next());
        return sessionData.getOutcome();
    }

    private static class RecordingRulesListener extends DefaultAgendaEventListener {

        private final SessionData sessionData;

        private RecordingRulesListener(SessionData sessionData) {
            this.sessionData = sessionData;
        }

        @Override
        public void afterMatchFired(AfterMatchFiredEvent event) {
            String ruleName = event.getMatch().getRule().getName();
            LOG.info("Rule fired: " + ruleName);
            sessionData.addExplanation(ruleName);
        }
    }
}
