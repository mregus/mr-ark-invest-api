package com.ark.invest.reports.dto;

import java.math.BigDecimal;

public record InvestorSummaryResponse(
        Long investorId,
        String investorName,
        BigDecimal totalCredits,
        BigDecimal totalDebits,
        BigDecimal netPosition,
        long fundCount,
        long transactionCount
) {
}