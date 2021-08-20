package com.everis.msdebitcard.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavingAccount extends BankAccount{
    private Integer limitTransactions;

    private Integer freeTransactions;

    private Double commissionTransactions;

    private Double minAverageVip;
}
