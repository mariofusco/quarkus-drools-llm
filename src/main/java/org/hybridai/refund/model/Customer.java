package org.hybridai.refund.model;

public record Customer(String firstName, String lastName, int age)  {

    @Override
    public String toString() {
        return "Customer {" +
                " firstName = \"" + firstName + "\"" +
                ", lastName = \"" + lastName + "\"" +
                ", age = " + age +
                " }";
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isValid() {
        return firstName != null && !firstName.isEmpty() && age > 0;
    }
}
