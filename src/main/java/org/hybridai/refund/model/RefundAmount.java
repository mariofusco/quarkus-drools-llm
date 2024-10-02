package org.hybridai.refund.model;

import java.util.ArrayList;
import java.util.List;

public class RefundAmount {

    public static final RefundAmount NO_REFUND = new RefundAmount(0).addExplanation("You're not entitled of a refund because your delay was less than 1 hour.");

    private double amount;

    private final List<String> explanations = new ArrayList<>();

    public RefundAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RefundAmount addExplanation(String explanation) {
        explanations.add(explanation);
        return this;
    }

    public String getExplanation() {
        return String.join(" ", explanations);
    }
}
