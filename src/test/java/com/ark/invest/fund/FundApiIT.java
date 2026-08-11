package com.ark.invest.fund;

import com.ark.invest.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FundApiIT extends BaseIntegrationTest {

    @Test
    void shouldCreateFund() throws Exception {
        mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "FUND-001",
                                  "name": "Growth Fund"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value("FUND-001"))
                .andExpect(jsonPath("$.name").value("Growth Fund"));
    }

    @Test
    void shouldRejectInvalidFund() throws Exception {
        mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "",
                                  "name": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors.name").value("must not be blank"));
    }

    @Test
    void shouldReturn404ForMissingFund() throws Exception {
        mockMvc.perform(get("/api/funds/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fund not found: 999"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}