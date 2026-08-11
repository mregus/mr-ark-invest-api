package com.ark.invest.transaction.dto;

import com.ark.transaction.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(

        @NotNull
        Long fundId,

        @NotNull
        Long investorId,

        @NotNull
        TransactionType type,

        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        LocalDate transactionDate,

        String description
) {
}