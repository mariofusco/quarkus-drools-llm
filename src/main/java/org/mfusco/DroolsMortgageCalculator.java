package org.mfusco;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Singleton
public class DroolsMortgageCalculator {

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    public String grantMortgage(Person person) {
        KieSession kieSession = runtimeBuilder.newKieSession();
        List<String> answers = new ArrayList<>();
        kieSession.setGlobal("answers", answers);
        kieSession.insert(person);
        kieSession.fireAllRules();

        if (answers.isEmpty()) {
            return "Yes, mortgage can be granted to " + person.getFullName();
        }
        return "Mortgage cannot be granted to " + person.getFullName() + " because " + answers;
    }
}
