package com.ark.invest.transaction.dto;

import com.ark.transaction.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(

        @NotNull
        @Schema(example = "1")
        Long fundId,

        @NotNull
        @Schema(example = "1")
        Long investorId,

        @NotNull
        @Schema(example = "CONTRIBUTION")
        TransactionType type,

        @NotNull
        @Positive
        @Schema(example = "100000.00")
        BigDecimal amount,

        @NotNull
        @Schema(example = "2026-08-10")
        LocalDate transactionDate,

        @Schema(example = "Initial investor contribution")
        String description
) {
}