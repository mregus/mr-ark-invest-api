package com.ark.invest.reports.controller;

import com.ark.invest.reports.dto.FundSummaryResponse;
import com.ark.invest.reports.dto.InvestorSummaryResponse;
import com.ark.invest.reports.service.ReportingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(
            ReportingService reportingService
    ) {
        this.reportingService = reportingService;
    }

    @GetMapping("/funds/{fundId}/summary")
    public FundSummaryResponse getFundSummary(
            @PathVariable Long fundId
    ) {
        return reportingService.getFundSummary(fundId);
    }

    @GetMapping("/investors/{investorId}/summary")
    public InvestorSummaryResponse getInvestorSummary(
            @PathVariable Long investorId
    ) {
        return reportingService.getInvestorSummary(investorId);
    }
}