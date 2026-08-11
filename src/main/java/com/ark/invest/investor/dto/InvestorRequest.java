package com.ark.invest.investor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InvestorRequest(

        @NotBlank
        @Schema(example = "Jane Smith")
        String name,

        @NotBlank
        @Email
        @Schema(example = "jane.smith@example.com")
        String email
) {
}