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
        if (transactionRequest.getType() == TransactionType.WITHDRAWAL && currentBalance.get(transactionRequest.getAcountNo()) <transactionRequest.getAmount() ) {
            throw new InsufficientFundsException("Insufficient balance for the withdrawal");
        }
        if (transactionRequest.getAmount() <= 0) {
            throw new InvalidFundsException("Invalid amount for the transaction");
        }


        if (transactionRequest.getType() == TransactionType.DEPOSIT) {
            double current= currentBalance.get(transactionRequest.getAcountNo()) +transactionRequest.getAmount();
            currentBalance.put(transactionRequest.getAcountNo(),current);

        } else if (transactionRequest.getType() == TransactionType.WITHDRAWAL) {
            double current= currentBalance.get(transactionRequest.getAcountNo()) - transactionRequest.getAmount();
            currentBalance.put(transactionRequest.getAcountNo(),current);
        }
        Transaction newTransaction = new Transaction(UUID.randomUUID().toString(), transactionRequest.getType(), transactionRequest.getAmount());
        List<Transaction> list=transactions.get(transactionRequest.getAcountNo());
        list.add(newTransaction);
        transactions.put(transactionRequest.getAcountNo(),list);
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
