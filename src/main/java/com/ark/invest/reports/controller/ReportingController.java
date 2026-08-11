package com.ark.invest.reports.controller;

import com.ark.invest.reports.dto.FundSummaryResponse;
import com.ark.invest.reports.dto.InvestorSummaryResponse;
import com.ark.invest.reports.service.ReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@Tag(
        name = "Reporting",
        description = "Fund and investor financial reporting"
)
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(
            ReportingService reportingService
    ) {
        this.reportingService = reportingService;
    }

    @GetMapping("/funds/{fundId}/summary")
    @Operation(
            summary = "Get fund financial summary",
            description = """
            Returns aggregated financial information for a fund,
            including credits, debits, net balance, investor count,
            and transaction count.
            """
    )
    public FundSummaryResponse getFundSummary(
            @PathVariable Long fundId
    ) {
        return reportingService.getFundSummary(fundId);
    }

    @GetMapping("/investors/{investorId}/summary")
    @Operation(
            summary = "Get investor financial summary",
            description = """
            Returns aggregated financial information for an investor
            across their associated funds.
            """
    )
    public InvestorSummaryResponse getInvestorSummary(
            @PathVariable Long investorId
    ) {
        return reportingService.getInvestorSummary(investorId);
    }
}