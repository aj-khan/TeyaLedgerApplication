package com.teya.ledger.model;

public class TransactionRequest {
    private TransactionType type;
    private double amount;
    private int source;
    private int target;
    private boolean isSelfTransaction;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public boolean isSelfTransaction() {
        return isSelfTransaction;
    }

    public void setSelfTransaction(boolean selfTransaction) {
        isSelfTransaction = selfTransaction;
    }

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

    public int getSource() {
        return source;
    }

    public void setSource(int acountNo) {
        this.source = acountNo;
    }
}
