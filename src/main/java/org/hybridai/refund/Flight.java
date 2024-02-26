package org.hybridai.refund;

public record Flight(String number, int delayInMinutes)  {

    @Override
    public String toString() {
        return "Flight {" +
                " number = \"" + number + "\"" +
                ", delayInMinutes = " + delayInMinutes +
                " }";
    }

    public boolean isValid() {
        return number != null && !number.isEmpty() && delayInMinutes > 0;
    }
}
