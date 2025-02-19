package com.teya.ledger.service;

import com.teya.ledger.exception.InsufficientFundsException;
import com.teya.ledger.exception.InvalidFundsException;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LedgerService {
    private List<Transaction> transactions = new ArrayList<>();
    private double currentBalance = 0;

    public void recordTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.WITHDRAWAL && transaction.getAmount() > currentBalance) {
            throw new InsufficientFundsException("Insufficient balance for the withdrawal");
        }
        if (transaction.getAmount() <= 0) {
            throw new InvalidFundsException("Invalid amount for the transaction");
        }


        if (transaction.getType() == TransactionType.DEPOSIT) {
            currentBalance += transaction.getAmount();
        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
            currentBalance -= transaction.getAmount();
        }
        Transaction newTransaction = new Transaction(UUID.randomUUID().toString(), transaction.getType(), transaction.getAmount());
        transactions.add(newTransaction);
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactions;
    }
}
