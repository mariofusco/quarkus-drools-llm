package org.hybridai.mortgage;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;

@Singleton
public class DroolsMortgageCalculator {

    public String grantMortgage(Person person) {
        List<String> answers = new ArrayList<>();

        if (person.getAge() < 18) {
            answers.add(person.getFullName() + " is too young");
        }

        if (person.income() < 100000) {
            answers.add(person.getFullName() + "'s income is too low");
        }

        if (answers.isEmpty()) {
            return "Yes, mortgage can be granted to " + person.getFullName();
        }
        return "Mortgage cannot be granted to " + person.getFullName() + " because " + answers;
    }
}
