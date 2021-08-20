package com.everis.msdebitcard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String id;

    private Customer customer;

    private String cardNumber;

    private List<Person> holders;

    private List<Person> signers;

    private Double balance;

    private LocalDateTime date;
}
