package org.hybridai.refund.model;

public class SessionData {

    private final String sessionId;

    private Customer customer;

    private Flight flight;

    private RefundAmount refundAmount;

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

    public RefundAmount getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(RefundAmount refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getOutcome() {
        return refundAmount.getAmount() > 0 ?
                "Good news " + customer.getFullName() + ", you are eligible for a refund of $" + refundAmount.getAmount() :
                "Sorry " + customer.getFullName() + ", but you are not eligible for any refund.";
    }

    public boolean isComplete() {
        return customer != null && flight != null;
    }

    public boolean calculateRefund() {
        return isComplete() && refundAmount == null;
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "customer=" + customer +
                ", flight=" + flight +
                '}';
    }
}
