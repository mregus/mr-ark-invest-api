package com.ark.invest.fund.service;

import com.ark.invest.fund.dto.FundInvestorResponse;
import com.ark.invest.fund.entity.Fund;
import com.ark.invest.fund.entity.FundInvestor;
import com.ark.invest.fund.repository.FundInvestorRepository;
import com.ark.invest.investor.entity.Investor;
import com.ark.invest.investor.service.InvestorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundInvestorService {

    private final FundInvestorRepository fundInvestorRepository;
    private final FundService fundService;
    private final InvestorService investorService;

    public FundInvestorService(
            FundInvestorRepository fundInvestorRepository,
            FundService fundService,
            InvestorService investorService
    ) {
        this.fundInvestorRepository = fundInvestorRepository;
        this.fundService = fundService;
        this.investorService = investorService;
    }

    @Transactional
    public FundInvestorResponse addInvestorToFund(
            Long fundId,
            Long investorId
    ) {
        if (fundInvestorRepository
                .existsByFundIdAndInvestorId(fundId, investorId)) {
            throw new IllegalArgumentException(
                    "Investor is already associated with this fund"
            );
        }

        Fund fund = fundService.getEntity(fundId);
        Investor investor = investorService.getEntity(investorId);

        FundInvestor relationship =
                new FundInvestor(fund, investor);

        return toResponse(
                fundInvestorRepository.save(relationship)
        );
    }

    @Transactional(readOnly = true)
    public List<FundInvestorResponse> getInvestorsForFund(Long fundId) {
        fundService.getEntity(fundId);

        return fundInvestorRepository.findByFundId(fundId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void removeInvestorFromFund(
            Long fundId,
            Long investorId
    ) {
        if (!fundInvestorRepository
                .existsByFundIdAndInvestorId(fundId, investorId)) {
            throw new IllegalArgumentException(
                    "Investor is not associated with this fund"
            );
        }

        fundInvestorRepository
                .deleteByFundIdAndInvestorId(fundId, investorId);
    }

    private FundInvestorResponse toResponse(
            FundInvestor relationship
    ) {
        Investor investor = relationship.getInvestor();

        return new FundInvestorResponse(
                relationship.getFund().getId(),
                investor.getId(),
                investor.getName(),
                investor.getEmail()
        );
    }
}