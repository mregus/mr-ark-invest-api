package com.ark.invest.investor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InvestorRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email
) {
}