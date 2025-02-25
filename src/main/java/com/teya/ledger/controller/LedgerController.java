package com.teya.ledger.controller;

import com.teya.ledger.exception.InsufficientFundsException;
import com.teya.ledger.exception.InvalidFundsException;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionRequest;
import com.teya.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ledger")
public class LedgerController {
    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/transactions")
    public ResponseEntity<String> recordTransaction(@RequestBody TransactionRequest transaction) {
        try {
            ledgerService.recordTransaction(transaction);
            return ResponseEntity.ok("Transaction recorded successfully");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (InvalidFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public double getCurrentBalance(@RequestParam(name = "accountNo") int accountNo ){

        return ledgerService.getCurrentBalance(accountNo);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(@RequestParam(name = "accountNo") int accountNo ) {
        List<Transaction> transactions = ledgerService.getTransactionHistory(accountNo);
        if (transactions.isEmpty()) {
            return ResponseEntity.ok("No transactions to display");
        }
        return ResponseEntity.ok(transactions);
    }
}
