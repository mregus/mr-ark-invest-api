package com.ark.invest.transaction.repository;

import com.ark.invest.transaction.entity.InvestmentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvestmentTransactionRepository
        extends JpaRepository<InvestmentTransaction, Long> {

    List<InvestmentTransaction> findByFundId(Long fundId);

    List<InvestmentTransaction> findByInvestorId(Long investorId);

    List<InvestmentTransaction>
    findByFundIdAndTransactionDateBetween(
            Long fundId,
            LocalDate from,
            LocalDate to
    );

    List<InvestmentTransaction>
    findByInvestorIdAndTransactionDateBetween(
            Long investorId,
            LocalDate from,
            LocalDate to
    );
}