package com.ark.invest.fund.repository;

import com.ark.invest.fund.entity.FundInvestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundInvestorRepository extends JpaRepository<FundInvestor, Long> {

    boolean existsByFundIdAndInvestorId(Long fundId, Long investorId);

    List<FundInvestor> findByFundId(Long fundId);

    List<FundInvestor> findByInvestorId(Long investorId);

    void deleteByFundIdAndInvestorId(Long fundId, Long investorId);

    long countByFundId(Long fundId);

    long countByInvestorId(Long investorId);
}