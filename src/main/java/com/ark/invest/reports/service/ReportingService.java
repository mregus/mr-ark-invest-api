package com.ark.invest.reports.service;

import com.ark.invest.fund.entity.Fund;
import com.ark.invest.fund.repository.FundInvestorRepository;
import com.ark.invest.fund.service.FundService;
import com.ark.invest.investor.entity.Investor;
import com.ark.invest.investor.service.InvestorService;
import com.ark.invest.reports.dto.FundSummaryResponse;
import com.ark.invest.reports.dto.InvestorSummaryResponse;
import com.ark.invest.transaction.entity.InvestmentTransaction;
import com.ark.invest.transaction.repository.InvestmentTransactionRepository;
import com.ark.invest.transaction.dto.TransactionEffect;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportingService {

    private final InvestmentTransactionRepository transactionRepository;
    private final FundInvestorRepository fundInvestorRepository;
    private final FundService fundService;
    private final InvestorService investorService;

    public ReportingService(
            InvestmentTransactionRepository transactionRepository,
            FundInvestorRepository fundInvestorRepository,
            FundService fundService,
            InvestorService investorService
    ) {
        this.transactionRepository = transactionRepository;
        this.fundInvestorRepository = fundInvestorRepository;
        this.fundService = fundService;
        this.investorService = investorService;
    }

    @Transactional(readOnly = true)
    public FundSummaryResponse getFundSummary(Long fundId) {

        Fund fund = fundService.getEntity(fundId);

        List<InvestmentTransaction> transactions =
                transactionRepository.findByFundId(fundId);

        BigDecimal totalCredits =
                sumByEffect(transactions, TransactionEffect.CREDIT);

        BigDecimal totalDebits =
                sumByEffect(transactions, TransactionEffect.DEBIT);

        BigDecimal netBalance =
                totalCredits.subtract(totalDebits);

        long investorCount =
                fundInvestorRepository.countByFundId(fundId);

        return new FundSummaryResponse(
                fund.getId(),
                fund.getCode(),
                fund.getName(),
                totalCredits,
                totalDebits,
                netBalance,
                investorCount,
                transactions.size()
        );
    }

    @Transactional(readOnly = true)
    public InvestorSummaryResponse getInvestorSummary(Long investorId) {

        Investor investor = investorService.getEntity(investorId);

        List<InvestmentTransaction> transactions =
                transactionRepository.findByInvestorId(investorId);

        BigDecimal totalCredits =
                sumByEffect(transactions, TransactionEffect.CREDIT);

        BigDecimal totalDebits =
                sumByEffect(transactions, TransactionEffect.DEBIT);

        BigDecimal netPosition =
                totalCredits.subtract(totalDebits);

        long fundCount =
                fundInvestorRepository.countByInvestorId(investorId);

        return new InvestorSummaryResponse(
                investor.getId(),
                investor.getName(),
                totalCredits,
                totalDebits,
                netPosition,
                fundCount,
                transactions.size()
        );
    }

    private BigDecimal sumByEffect(
            List<InvestmentTransaction> transactions,
            TransactionEffect effect
    ) {
        return transactions.stream()
                .filter(transaction ->
                        transaction.getType().getEffect() == effect
                )
                .map(InvestmentTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}