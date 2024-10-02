package org.hybridai.refund;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hybridai.refund.model.RefundAmount;
import org.hybridai.refund.model.SessionData;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Singleton
public class DroolsRefundCalculator {

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    public String checkRefund(SessionData sessionData) {
        KieSession kieSession = runtimeBuilder.newKieSession("refundCalc");
        kieSession.insert(sessionData.getCustomer());
        kieSession.insert(sessionData.getFlight());
        kieSession.fireAllRules();

        var refunds = kieSession.getInstancesOf(RefundAmount.class);
        sessionData.setRefundAmount(refunds.isEmpty() ? RefundAmount.NO_REFUND : refunds.iterator().next());
        return sessionData.getOutcome();
    }
}
