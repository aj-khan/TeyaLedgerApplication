package com.teya.ledger.service;

import com.teya.ledger.exception.InsufficientFundsException;
import com.teya.ledger.exception.InvalidFundsException;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerServiceTest {

    @Test
    public void testRecordDepositTransaction() {
        LedgerService ledgerService = new LedgerService();
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(100);

        ledgerService.recordTransaction(transaction);

        assertEquals(100, ledgerService.getCurrentBalance());
        assertFalse(ledgerService.getTransactionHistory().isEmpty());
    }

    @Test
    public void testRecordWithdrawalTransaction() {
        LedgerService ledgerService = new LedgerService();
        Transaction deposit = new Transaction();
        deposit.setType(TransactionType.DEPOSIT);
        deposit.setAmount(200);
        ledgerService.recordTransaction(deposit);

        Transaction withdrawal = new Transaction();
        withdrawal.setType(TransactionType.WITHDRAWAL);
        withdrawal.setAmount(100);

        ledgerService.recordTransaction(withdrawal);

        assertEquals(100, ledgerService.getCurrentBalance());
        assertEquals(2, ledgerService.getTransactionHistory().size());
    }

    @Test
    public void testInsufficientFundsException() {
        LedgerService ledgerService = new LedgerService();
        Transaction withdrawal = new Transaction();
        withdrawal.setType(TransactionType.WITHDRAWAL);
        withdrawal.setAmount(50);

        assertThrows(InsufficientFundsException.class, () -> {
            ledgerService.recordTransaction(withdrawal);
        });
    }

    @Test
    public void testInvalidFundsException() {
        LedgerService ledgerService = new LedgerService();
        Transaction invalidTransaction = new Transaction();
        invalidTransaction.setType(TransactionType.DEPOSIT);
        invalidTransaction.setAmount(-100);

        assertThrows(InvalidFundsException.class, () -> {
            ledgerService.recordTransaction(invalidTransaction);
        });
    }
}
