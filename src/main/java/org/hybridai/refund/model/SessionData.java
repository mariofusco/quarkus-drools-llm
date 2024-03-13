package org.hybridai.refund.model;

public class SessionData {

    private final String sessionId;

    private Customer customer;

    private Flight flight;

    public String getSessionId() {
        return sessionId;
    }

    public SessionData(String sessionId) {
        this.sessionId = sessionId;
    }

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
