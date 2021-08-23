package com.everis.msdebitcard.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Document("DebitCard")
@Getter
@Setter
@NoArgsConstructor
public class DebitCard {
    @NotNull
    private String id;

    @NotEmpty
    private String cardNumber;

    private Customer customer;

    private List<BankAccount> registeredAccounts = new ArrayList<>();

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private LocalDate expirationDate;

    static public DebitCard generateBankAccount(BankAccount bankAccount) {
        DebitCard debitCard = new DebitCard();
        debitCard.setCardNumber(DebitCard.generateNumber());
        debitCard.setCreationDate(LocalDate.now());
        debitCard.setExpirationDate(LocalDate.from(LocalDate.now()).plusYears(4));
        debitCard.getRegisteredAccounts().add(bankAccount);
        debitCard.setCustomer(bankAccount.getCustomer());
        return debitCard;
    }

    static private String generateNumber() {
        Random random = new Random();
        String card = "";
        for(int i=0; i < 16; i++) {
            card = card.concat(Integer.toString(random.nextInt(10)));
        }
        return card;
    }

    static public DebitCard updateAccounts(BankAccount bankAccount, DebitCard debitCard) {
        if(debitCard.getRegisteredAccounts()
                .stream()
                .anyMatch(bankAccount1 -> bankAccount1.getId().equals(bankAccount.getId()))) {
            int index = 0;
            for (BankAccount value : debitCard.getRegisteredAccounts()) {
                if(value.getId().equals(bankAccount.getId())) {
                    Collections.swap(debitCard.getRegisteredAccounts(), 0, index);
                    break;
                }
                index++;
            }
        } else  {
            debitCard.getRegisteredAccounts().add(bankAccount);
        }
        return debitCard;
    }

}
