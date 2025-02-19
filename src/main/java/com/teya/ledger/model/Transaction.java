    package com.teya.ledger.model;

    import java.time.LocalDateTime;

    public class Transaction {
        private String id;
        private TransactionType type;
        private double amount;
        private LocalDateTime transactionTime;

        public Transaction(){

        }

        public Transaction(String id, TransactionType type, double amount) {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.transactionTime = LocalDateTime.now();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public LocalDateTime getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(LocalDateTime transactionTime) {
            this.transactionTime = transactionTime;
        }
    }
