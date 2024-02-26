package org.hybridai.mortgage;

import java.time.LocalDate;

public record Person(String firstName, String lastName, LocalDate birthDate, int income)  {

    @Override
    public String toString() {
        return "Person {" +
                " firstName = \"" + firstName + "\"" +
                ", lastName = \"" + lastName + "\"" +
                ", birthDate = " + birthDate +
                ", income = " + income +
                " }";
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return birthDate.until(LocalDate.now()).getYears();
    }
}
