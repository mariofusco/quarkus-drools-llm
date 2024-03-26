package org.hybridai.refund.model;

public record Flight(String number, int delayInMinutes) implements Validated {

    @Override
    public String toString() {
        return "Flight {" +
                " number = \"" + number + "\"" +
                ", delayInMinutes = " + delayInMinutes +
                " }";
    }

    @Override
    public boolean isValid() {
        return number != null && !number.isEmpty() && delayInMinutes > 0;
    }
}
