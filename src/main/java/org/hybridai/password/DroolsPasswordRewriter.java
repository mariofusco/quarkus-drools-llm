package org.hybridai.password;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Singleton
public class DroolsPasswordRewriter {

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    public String rewritePassword(String s) {
        KieSession kieSession = runtimeBuilder.newKieSession("password");
        kieSession.insert(s);
        kieSession.fireAllRules();
        return kieSession.getObjects().iterator().next().toString();
    }

}
