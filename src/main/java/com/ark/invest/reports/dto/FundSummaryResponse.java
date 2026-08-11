package com.ark.invest.reports.dto;

import java.math.BigDecimal;

public record FundSummaryResponse(
        Long fundId,
        String fundCode,
        String fundName,
        BigDecimal totalCredits,
        BigDecimal totalDebits,
        BigDecimal netBalance,
        long investorCount,
        long transactionCount
) {
}