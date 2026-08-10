package com.ark.invest.investor.service;

import com.ark.invest.common.ResourceNotFoundException;
import com.ark.invest.investor.dto.InvestorRequest;
import com.ark.invest.investor.dto.InvestorResponse;
import com.ark.invest.investor.entity.Investor;
import com.ark.invest.investor.repository.InvestorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Transactional
    public InvestorResponse create(InvestorRequest request) {
        if (investorRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Investor email already exists: " + request.email()
            );
        }

        Investor investor = new Investor(
                request.name(),
                request.email()
        );

        return toResponse(investorRepository.save(investor));
    }

    @Transactional(readOnly = true)
    public List<InvestorResponse> findAll() {
        return investorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public InvestorResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public InvestorResponse update(Long id, InvestorRequest request) {
        Investor investor = getEntity(id);

        investor.setName(request.name());
        investor.setEmail(request.email());

        return toResponse(investor);
    }

    @Transactional
    public void delete(Long id) {
        Investor investor = getEntity(id);
        investorRepository.delete(investor);
    }

    public Investor getEntity(Long id) {
        return investorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Investor not found: " + id
                        ));
    }

    private InvestorResponse toResponse(Investor investor) {
        return new InvestorResponse(
                investor.getId(),
                investor.getName(),
                investor.getEmail()
        );
    }
}