package com.ark.invest.fund.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FundRequest(

        @NotBlank
        @Size(max = 50)
        @Schema(example = "GROWTH-001")
        String code,

        @NotBlank
        @Size(max = 200)
        @Schema(example = "Ark Growth Fund")
        String name
) {
}