package org.hybridai.refund;

import jakarta.inject.Singleton;
import org.hybridai.refund.model.RefundAmount;
import org.hybridai.refund.model.SessionData;

@Singleton
public class DroolsRefundCalculator {

    public String checkRefund(SessionData sessionData) {
        sessionData.setRefundAmount(calculateRefundAmount(sessionData));
        return sessionData.getOutcome();
    }

    private static RefundAmount calculateRefundAmount(SessionData sessionData) {
        int delay = sessionData.getFlight().delayInMinutes();
        if (delay < 60) {
            return RefundAmount.NO_REFUND;
        }

        RefundAmount refundAmount = new RefundAmount( 2 * delay);
        refundAmount.addExplanation("You're entitled of $2 of refund for each of the " + delay + " minutes of delay.");

        if (sessionData.getCustomer().age() > 65) {
            refundAmount.setAmount(refundAmount.getAmount() * 1.1 );
            refundAmount.addExplanation("Since you're a senior customer (older than 65) you're also entitled of a 10% increase on your refund.");
        }
        return refundAmount;
    }
}
