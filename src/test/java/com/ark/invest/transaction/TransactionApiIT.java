package com.ark.invest.transaction;

import com.ark.invest.BaseIntegrationTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionApiIT extends BaseIntegrationTest {

    @Test
    void shouldCreateTransactionForAssociatedInvestor() throws Exception {

        MvcResult fundResult = mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "FUND-101",
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
                                  "email": "john.doe@example.com"
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

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fundId": %d,
                                  "investorId": %d,
                                  "type": "CONTRIBUTION",
                                  "amount": 100000.00,
                                  "transactionDate": "2026-08-10",
                                  "description": "Initial contribution"
                                }
                                """.formatted(fundId, investorId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("CONTRIBUTION"))
                .andExpect(jsonPath("$.effect").value("CREDIT"))
                .andExpect(jsonPath("$.amount").value(100000.00));
    }

    @Test
    void shouldRejectTransactionWhenInvestorIsNotAssociatedWithFund()
            throws Exception {

        MvcResult fundResult = mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "code": "FUND-200",
                              "name": "Private Equity Fund"
                            }
                            """))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult investorResult = mockMvc.perform(post("/api/investors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Alice Johnson",
                              "email": "alice@example.com"
                            }
                            """))
                .andExpect(status().isCreated())
                .andReturn();

        long fundId = extractId(fundResult);
        long investorId = extractId(investorResult);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "fundId": %d,
                              "investorId": %d,
                              "type": "CONTRIBUTION",
                              "amount": 50000.00,
                              "transactionDate": "2026-08-10"
                            }
                            """.formatted(fundId, investorId)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(
                                "Investor " + investorId +
                                        " is not associated with fund " + fundId
                        ));
    }

    private long extractId(MvcResult result) throws Exception {
        JsonNode node = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        return node.get("id").asLong();
    }
}