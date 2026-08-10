package com.ark.invest.fund.entity;

import com.ark.invest.investor.entity.Investor;
import jakarta.persistence.*;

@Entity
@Table(
        name = "fund_investors",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_fund_investor",
                columnNames = {"fund_id", "investor_id"}
        )
)
public class FundInvestor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fund_id", nullable = false)
    private Fund fund;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

    protected FundInvestor() {
    }

    public FundInvestor(Fund fund, Investor investor) {
        this.fund = fund;
        this.investor = investor;
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
}