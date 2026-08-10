package com.ark.invest.fund.service;

import com.ark.invest.common.ResourceNotFoundException;
import com.ark.invest.fund.dto.FundRequest;
import com.ark.invest.fund.dto.FundResponse;
import com.ark.invest.fund.entity.Fund;
import com.ark.invest.fund.repository.FundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundService {

    private final FundRepository fundRepository;

    public FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    @Transactional
    public FundResponse create(FundRequest request) {

        if (fundRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException(
                    "Fund code already exists: " + request.code()
            );
        }

        Fund fund = new Fund(
                request.code(),
                request.name()
        );

        return toResponse(fundRepository.save(fund));
    }

    @Transactional(readOnly = true)
    public List<FundResponse> findAll() {
        return fundRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FundResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public FundResponse update(Long id, FundRequest request) {

        Fund fund = getEntity(id);

        fund.setCode(request.code());
        fund.setName(request.name());

        return toResponse(fund);
    }

    @Transactional
    public void delete(Long id) {
        Fund fund = getEntity(id);
        fundRepository.delete(fund);
    }

    public Fund getEntity(Long id) {
        return fundRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fund not found: " + id
                        ));
    }

    private FundResponse toResponse(Fund fund) {
        return new FundResponse(
                fund.getId(),
                fund.getCode(),
                fund.getName()
        );
    }
}