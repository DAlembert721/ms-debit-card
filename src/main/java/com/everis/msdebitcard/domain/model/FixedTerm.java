package com.everis.msdebitcard.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FixedTerm extends BankAccount{
    private Integer limitDeposits;

    private Integer limitDraft;

    private LocalDate allowDateTransaction;

    private Integer freeTransactions;

    private Double commissionTransactions;
}
