package com.ark.invest.transaction.entity;

import com.ark.invest.fund.entity.Fund;
import com.ark.invest.investor.entity.Investor;
import com.ark.transaction.model.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "investment_transactions",
        indexes = {
                @Index(name = "idx_transaction_fund", columnList = "fund_id"),
                @Index(name = "idx_transaction_investor", columnList = "investor_id"),
                @Index(name = "idx_transaction_date", columnList = "transaction_date")
        }
)
public class InvestmentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fund_id", nullable = false)
    private Fund fund;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(length = 500)
    private String description;

    protected InvestmentTransaction() {
    }

    public InvestmentTransaction(
            Fund fund,
            Investor investor,
            TransactionType type,
            BigDecimal amount,
            LocalDate transactionDate,
            String description
    ) {
        this.fund = fund;
        this.investor = investor;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Fund getFund() {
        return fund;
    }

    public Investor getInvestor() {
        return investor;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}