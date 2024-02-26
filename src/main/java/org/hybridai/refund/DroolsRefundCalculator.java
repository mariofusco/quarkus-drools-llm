package org.hybridai.refund;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Singleton
public class DroolsRefundCalculator {

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    public String checkRefund(SessionData sessionData) {
        KieSession kieSession = runtimeBuilder.newKieSession("refund");
        kieSession.insert(sessionData.getCustomer());
        kieSession.insert(sessionData.getFlight());
        kieSession.fireAllRules();

        var refunds = kieSession.getObjects(new ClassObjectFilter(RefundAmount.class));

        if (refunds.isEmpty()) {
            return "Sorry " + sessionData.getCustomer().getFullName() + ", but you are not eligible for any refund.";
        }
        RefundAmount refund = (RefundAmount) refunds.iterator().next();
        return "Good news " + sessionData.getCustomer().getFullName() + ", you are eligible for a refund of $" + refund.getAmount();
    }
}
