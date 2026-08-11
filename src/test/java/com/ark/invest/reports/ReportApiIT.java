package com.ark.invest.reports;

import com.ark.invest.BaseIntegrationTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportApiIT extends BaseIntegrationTest {

    @Test
    void shouldCalculateFundSummary() throws Exception {

        // create fund + investor + relationship first

        MvcResult fundResult = mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "FUND-100",
                                  "name": "Income Fund"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult investorResult = mockMvc.perform(post("/api/investors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "John Doe",
                                  "email": "john@example.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        long fundId = extractId(fundResult);
        long investorId = extractId(investorResult);

        mockMvc.perform(post(
                        "/api/funds/{fundId}/investors/{investorId}",
                        fundId,
                        investorId
                ))
                .andExpect(status().isCreated());

        createTransaction(fundId, investorId, "CONTRIBUTION", "100000.00");
        createTransaction(fundId, investorId, "INTEREST_INCOME", "5000.00");
        createTransaction(fundId, investorId, "DISTRIBUTION", "15000.00");
        createTransaction(fundId, investorId, "MANAGEMENT_FEE", "2500.00");

        mockMvc.perform(
                        get("/api/reports/funds/{fundId}/summary", fundId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCredits").value(105000.00))
                .andExpect(jsonPath("$.totalDebits").value(17500.00))
                .andExpect(jsonPath("$.netBalance").value(87500.00))
                .andExpect(jsonPath("$.transactionCount").value(4))
                .andExpect(jsonPath("$.investorCount").value(1));
    }

    private void createTransaction(long fundId, long investorId, String investmentType, String amount) throws Exception {

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fundId": %d,
                                  "investorId": %d,
                                  "type": "%s",
                                  "amount": %s,
                                  "transactionDate": "2026-08-10",
                                  "description": "Initial contribution"
                                }
                                """.formatted(fundId, investorId, investmentType, amount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value(investmentType));
    }

    private long extractId(MvcResult result) throws Exception {
        JsonNode node = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        return node.get("id").asLong();
    }
}
