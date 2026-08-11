package com.ark.invest.transaction.service;

import com.ark.invest.common.ResourceNotFoundException;
import com.ark.invest.fund.entity.Fund;
import com.ark.invest.fund.repository.FundInvestorRepository;
import com.ark.invest.fund.service.FundService;
import com.ark.invest.investor.entity.Investor;
import com.ark.invest.investor.service.InvestorService;
import com.ark.invest.transaction.dto.TransactionRequest;
import com.ark.invest.transaction.dto.TransactionResponse;
import com.ark.invest.transaction.entity.InvestmentTransaction;
import com.ark.invest.transaction.repository.InvestmentTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvestmentTransactionService {

    private final InvestmentTransactionRepository transactionRepository;
    private final FundService fundService;
    private final InvestorService investorService;
    private final FundInvestorRepository fundInvestorRepository;

    public InvestmentTransactionService(
            InvestmentTransactionRepository transactionRepository,
            FundService fundService,
            InvestorService investorService,
            FundInvestorRepository fundInvestorRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.fundService = fundService;
        this.investorService = investorService;
        this.fundInvestorRepository = fundInvestorRepository;
    }

    @Transactional
    public TransactionResponse create(TransactionRequest request) {

        Fund fund = fundService.getEntity(request.fundId());
        Investor investor = investorService.getEntity(request.investorId());

        validateFundInvestorRelationship(
                request.fundId(),
                request.investorId()
        );

        InvestmentTransaction transaction =
                new InvestmentTransaction(
                        fund,
                        investor,
                        request.type(),
                        request.amount(),
                        request.transactionDate(),
                        request.description()
                );

        return toResponse(
                transactionRepository.save(transaction)
        );
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TransactionResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findByFund(Long fundId) {
        fundService.getEntity(fundId);

        return transactionRepository.findByFundId(fundId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findByInvestor(Long investorId) {
        investorService.getEntity(investorId);

        return transactionRepository.findByInvestorId(investorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TransactionResponse update(
            Long id,
            TransactionRequest request
    ) {
        InvestmentTransaction transaction = getEntity(id);

        /*
         * For this implementation, we allow the fund/investor relationship
         * to change as part of an update.
         */
        Fund fund = fundService.getEntity(request.fundId());
        Investor investor = investorService.getEntity(request.investorId());

        validateFundInvestorRelationship(
                request.fundId(),
                request.investorId()
        );

        transaction.setFund(fund);
        transaction.setInvestor(investor);

        transaction.update(
                request.type(),
                request.amount(),
                request.transactionDate(),
                request.description()
        );

        return toResponse(transaction);
    }

    @Transactional
    public void delete(Long id) {
        InvestmentTransaction transaction = getEntity(id);
        transactionRepository.delete(transaction);
    }

    private void validateFundInvestorRelationship(
            Long fundId,
            Long investorId
    ) {
        boolean associated =
                fundInvestorRepository.existsByFundIdAndInvestorId(
                        fundId,
                        investorId
                );

        if (!associated) {
            throw new IllegalArgumentException(
                    "Investor " + investorId +
                            " is not associated with fund " + fundId
            );
        }
    }

    private InvestmentTransaction getEntity(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found: " + id
                        ));
    }

    private TransactionResponse toResponse(
            InvestmentTransaction transaction
    ) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getFund().getId(),
                transaction.getFund().getName(),
                transaction.getInvestor().getId(),
                transaction.getInvestor().getName(),
                transaction.getType(),
                transaction.getType().getEffect(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription()
        );
    }
}