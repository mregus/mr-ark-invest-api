package com.ark.invest.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        Long fundId,
        String fundName,
        Long investorId,
        String investorName,
        TransactionType type,
        TransactionEffect effect,
        BigDecimal amount,
        LocalDate transactionDate,
        String description
) {
}