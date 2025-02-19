package com.teya.ledger.controller;

import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionType;
import com.teya.ledger.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LedgerController.class)
public class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LedgerService ledgerService;

    @Test
    public void testRecordTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(100);

        mockMvc.perform(post("/v1/ledger/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"DEPOSIT\", \"amount\": 100}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction recorded successfully"));
    }

    @Test
    public void testGetCurrentBalance() throws Exception {
        when(ledgerService.getCurrentBalance()).thenReturn(100.0);

        mockMvc.perform(get("/v1/ledger/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));
    }

    @Test
    public void testGetTransactionHistory_NoTransactions() throws Exception {
        when(ledgerService.getTransactionHistory()).thenReturn(List.of());

        mockMvc.perform(get("/v1/ledger/history"))
                .andExpect(status().isOk())
                .andExpect(content().string("No transactions to display"));
    }

    @Test
    public void testGetTransactionHistory_WithTransactions() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(100);
        when(ledgerService.getTransactionHistory()).thenReturn(List.of(transaction));

        mockMvc.perform(get("/v1/ledger/history"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"type\": \"DEPOSIT\", \"amount\": 100.0}]"));
    }
}
