package com.teya.ledger.model;

import java.time.LocalDateTime;

public class TransactionRequest {
    private TransactionType type;
    private double amount;
    private int acountNo;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAcountNo() {
        return acountNo;
    }

    public void setAcountNo(int acountNo) {
        this.acountNo = acountNo;
    }
}
