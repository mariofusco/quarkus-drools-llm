package org.hybridai.refund;

public class SessionData {

    private Customer customer;

    private Flight flight;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean isComplete() {
        return customer != null && flight != null;
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "customer=" + customer +
                ", flight=" + flight +
                '}';
    }
}
