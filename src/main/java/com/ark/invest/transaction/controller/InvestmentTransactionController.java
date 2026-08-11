package com.ark.invest.transaction.controller;

import com.ark.invest.transaction.dto.TransactionRequest;
import com.ark.invest.transaction.dto.TransactionResponse;
import com.ark.invest.transaction.service.InvestmentTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class InvestmentTransactionController {

    private final InvestmentTransactionService transactionService;

    public InvestmentTransactionController(
            InvestmentTransactionService transactionService
    ) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(
            @Valid @RequestBody TransactionRequest request
    ) {
        return transactionService.create(request);
    }

    @GetMapping
    public List<TransactionResponse> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public TransactionResponse findById(
            @PathVariable Long id
    ) {
        return transactionService.findById(id);
    }

    @GetMapping("/fund/{fundId}")
    public List<TransactionResponse> findByFund(
            @PathVariable Long fundId
    ) {
        return transactionService.findByFund(fundId);
    }

    @GetMapping("/investor/{investorId}")
    public List<TransactionResponse> findByInvestor(
            @PathVariable Long investorId
    ) {
        return transactionService.findByInvestor(investorId);
    }

    @PutMapping("/{id}")
    public TransactionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request
    ) {
        return transactionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        transactionService.delete(id);
    }
}