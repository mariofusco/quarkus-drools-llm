package org.hybridai.refund.model;

import java.util.ArrayList;
import java.util.List;

public class RefundAmount {

    public static final RefundAmount NO_REFUND = new RefundAmount();

    private double amount;

    public RefundAmount() {
        this(0.0);
    }

    public RefundAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
