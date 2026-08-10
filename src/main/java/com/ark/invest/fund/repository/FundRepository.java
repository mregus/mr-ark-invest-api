package com.ark.invest.fund.repository;

import com.ark.invest.fund.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundRepository extends JpaRepository<Fund, Long> {

    Optional<Fund> findByCode(String code);

    boolean existsByCode(String code);
}