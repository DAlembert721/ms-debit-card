package com.everis.msdebitcard.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrentAccount extends BankAccount{
    private Integer freeTransactions;

    private Double commissionTransactions;

    private Double commissionMaintenance;
}
