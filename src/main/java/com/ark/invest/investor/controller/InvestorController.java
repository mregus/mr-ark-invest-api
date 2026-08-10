package com.ark.invest.investor.controller;

import com.ark.invest.investor.dto.InvestorRequest;
import com.ark.invest.investor.dto.InvestorResponse;
import com.ark.invest.investor.service.InvestorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {

    private final InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvestorResponse create(
            @Valid @RequestBody InvestorRequest request
    ) {
        return investorService.create(request);
    }

    @GetMapping
    public List<InvestorResponse> findAll() {
        return investorService.findAll();
    }

    @GetMapping("/{id}")
    public InvestorResponse findById(@PathVariable Long id) {
        return investorService.findById(id);
    }

    @PutMapping("/{id}")
    public InvestorResponse update(
            @PathVariable Long id,
            @Valid @RequestBody InvestorRequest request
    ) {
        return investorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        investorService.delete(id);
    }
}