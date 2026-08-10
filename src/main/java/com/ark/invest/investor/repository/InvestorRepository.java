package com.ark.invest.investor.repository;

import com.ark.invest.investor.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor, Long> {

    Optional<Investor> findByEmail(String email);

    boolean existsByEmail(String email);
}