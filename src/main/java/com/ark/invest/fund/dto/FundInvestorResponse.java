package com.ark.invest.fund.dto;

public record FundInvestorResponse(
        Long fundId,
        Long investorId,
        String investorName,
        String investorEmail
) {
}