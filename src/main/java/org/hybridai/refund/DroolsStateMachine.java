package org.hybridai.refund;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hybridai.refund.model.ChatState;
import org.hybridai.refund.model.SessionData;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Singleton
public class DroolsStateMachine {
    @Inject
    KieRuntimeBuilder runtimeBuilder;

    public ChatState nextState(SessionData sessionData) {
        KieSession kieSession = runtimeBuilder.newKieSession("refundState");
        kieSession.insert(sessionData);
        kieSession.fireAllRules();

        return (ChatState) kieSession.getObjects(new ClassObjectFilter(ChatState.class)).iterator().next();
    }
}
