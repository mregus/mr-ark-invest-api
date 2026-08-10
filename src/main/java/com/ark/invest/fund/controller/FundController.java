package com.ark.invest.fund.controller;

import com.ark.invest.fund.dto.FundInvestorResponse;
import com.ark.invest.fund.dto.FundRequest;
import com.ark.invest.fund.dto.FundResponse;
import com.ark.invest.fund.service.FundInvestorService;
import com.ark.invest.fund.service.FundService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funds")
public class FundController {

    private final FundService fundService;

    private final FundInvestorService fundInvestorService;

    public FundController(
            FundService fundService,
            FundInvestorService fundInvestorService
    ) {
        this.fundService = fundService;
        this.fundInvestorService = fundInvestorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FundResponse create(
            @Valid @RequestBody FundRequest request
    ) {
        return fundService.create(request);
    }

    @GetMapping
    public List<FundResponse> findAll() {
        return fundService.findAll();
    }

    @GetMapping("/{id}")
    public FundResponse findById(@PathVariable Long id) {
        return fundService.findById(id);
    }

    @PutMapping("/{id}")
    public FundResponse update(
            @PathVariable Long id,
            @Valid @RequestBody FundRequest request
    ) {
        return fundService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fundService.delete(id);
    }

    @PostMapping("/{fundId}/investors/{investorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FundInvestorResponse addInvestor(
            @PathVariable Long fundId,
            @PathVariable Long investorId
    ) {
        return fundInvestorService
                .addInvestorToFund(fundId, investorId);
    }

    @GetMapping("/{fundId}/investors")
    public List<FundInvestorResponse> getInvestors(
            @PathVariable Long fundId
    ) {
        return fundInvestorService
                .getInvestorsForFund(fundId);
    }

    @DeleteMapping("/{fundId}/investors/{investorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvestor(
            @PathVariable Long fundId,
            @PathVariable Long investorId
    ) {
        fundInvestorService
                .removeInvestorFromFund(fundId, investorId);
    }
}