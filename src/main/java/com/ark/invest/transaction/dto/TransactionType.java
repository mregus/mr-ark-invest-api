package com.ark.invest.transaction.dto;

public enum TransactionType {

    CONTRIBUTION(TransactionEffect.CREDIT),
    INTEREST_INCOME(TransactionEffect.CREDIT),
    DISTRIBUTION(TransactionEffect.DEBIT),
    GENERAL_EXPENSE(TransactionEffect.DEBIT),
    MANAGEMENT_FEE(TransactionEffect.DEBIT);

    private final TransactionEffect effect;

    TransactionType(TransactionEffect effect) {
        this.effect = effect;
    }

    public TransactionEffect getEffect() {
        return effect;
    }
}
