package com.teya.ledger.service;

import com.teya.ledger.exception.InsufficientFundsException;
import com.teya.ledger.exception.InvalidFundsException;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionRequest;
import com.teya.ledger.model.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class LedgerService {
    private static Map<Integer,List<Transaction>> transactions = new HashMap<>();
    private static Map<Integer,Double> currentBalance = new HashMap<>();

    public void recordTransaction(TransactionRequest transactionRequest) {
        System.out.println("this is");
        if (transactionRequest.getType() == TransactionType.WITHDRAWAL && currentBalance.getOrDefault(transactionRequest.getSource(),0.0) <transactionRequest.getAmount() ) {
            throw new InsufficientFundsException("Insufficient balance for the withdrawal");
        }
        if (!transactionRequest.isSelfTransaction() && transactionRequest.getType() == TransactionType.DEPOSIT && currentBalance.getOrDefault(transactionRequest.getSource(),0.0) <transactionRequest.getAmount() ) {
            throw new InsufficientFundsException("Insufficient balance for the withdrawal");
        }
        if (transactionRequest.getAmount() <= 0) {
            throw new InvalidFundsException("Invalid amount for the transaction");
        }
        List<Transaction> list=transactions.getOrDefault(transactionRequest.getSource(),new ArrayList<>());
        List<Transaction> toList=transactions.getOrDefault(transactionRequest.getTarget(),new ArrayList<>());
        if(transactionRequest.isSelfTransaction()) {
            if (transactionRequest.getType() == TransactionType.DEPOSIT) {
                double current = currentBalance.getOrDefault(transactionRequest.getSource(), 0.0) + transactionRequest.getAmount();
                currentBalance.put(transactionRequest.getSource(), current);

            } else if (transactionRequest.getType() == TransactionType.WITHDRAWAL) {
                double current = currentBalance.getOrDefault(transactionRequest.getSource(), 0.0) - transactionRequest.getAmount();
                currentBalance.put(transactionRequest.getSource(), current);
            }
            Transaction newTransaction = new Transaction(UUID.randomUUID().toString(), transactionRequest.getType(), transactionRequest.getAmount());
            list.add(newTransaction);
        }else {
            if (transactionRequest.getType() == TransactionType.DEPOSIT) {
                double source = currentBalance.getOrDefault(transactionRequest.getSource(), 0.0) - transactionRequest.getAmount();
                currentBalance.put(transactionRequest.getSource(), source);
                Transaction src = new Transaction(UUID.randomUUID().toString(), TransactionType.WITHDRAWAL, transactionRequest.getAmount());
                list.add(src);
                double target = currentBalance.getOrDefault(transactionRequest.getTarget(), 0.0) + transactionRequest.getAmount();
                currentBalance.put(transactionRequest.getTarget(), target);
                Transaction tgt = new Transaction(UUID.randomUUID().toString(), TransactionType.DEPOSIT, transactionRequest.getAmount());
                toList.add(tgt);

            } else if (transactionRequest.getType() == TransactionType.WITHDRAWAL) {
                double source = currentBalance.getOrDefault(transactionRequest.getSource(), 0.0) + transactionRequest.getAmount();
                currentBalance.put(transactionRequest.getSource(), source);
                Transaction src = new Transaction(UUID.randomUUID().toString(), TransactionType.DEPOSIT, transactionRequest.getAmount());
                list.add(src);
                double target = currentBalance.getOrDefault(transactionRequest.getTarget(), 0.0) - transactionRequest.getAmount();
                currentBalance.put(transactionRequest.getTarget(), target);
                Transaction tgt = new Transaction(UUID.randomUUID().toString(), TransactionType.WITHDRAWAL, transactionRequest.getAmount());
                toList.add(tgt);
            }
            transactions.put(transactionRequest.getTarget(),toList);
        }

        transactions.put(transactionRequest.getSource(),list);
    }

    public double getCurrentBalance(int accountNo) {
        return currentBalance.getOrDefault(accountNo,0.0);
    }

    public List<Transaction> getTransactionHistory(int accountNo) {
        if(ObjectUtils.isEmpty(transactions) )
            return Arrays.asList(new Transaction());
        return transactions.get(accountNo);
    }
}
