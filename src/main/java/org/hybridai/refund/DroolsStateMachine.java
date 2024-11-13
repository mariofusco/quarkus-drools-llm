package org.hybridai.refund;

import jakarta.inject.Singleton;
import org.hybridai.refund.model.ChatState;
import org.hybridai.refund.model.SessionData;

@Singleton
public class DroolsStateMachine {

    public ChatState nextState(SessionData sessionData) {
        if (sessionData.getCustomer() == null) {
            return ChatState.EXTRACT_CUSTOMER;
        }
        if (sessionData.getFlight() == null) {
            return ChatState.EXTRACT_FLIGHT;
        }
        return ChatState.CALCULATE_REFUND;
    }
}
