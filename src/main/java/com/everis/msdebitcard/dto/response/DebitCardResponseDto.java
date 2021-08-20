package com.everis.msdebitcard.dto.response;

import com.everis.msdebitcard.domain.model.BankAccount;
import com.everis.msdebitcard.domain.model.Customer;
import com.everis.msdebitcard.domain.model.DebitCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DebitCardResponseDto {
    private String cardNumber;
    private Customer customer;
    private List<BankAccount> registeredAccounts;
    private LocalDate expirationDate;


    static public DebitCardResponseDto entityToResponse(DebitCard debitCard) {
        DebitCardResponseDto responseDto = new DebitCardResponseDto();
        responseDto.setCardNumber(debitCard.getCardNumber());
        responseDto.setCustomer(debitCard.getCustomer());
        responseDto.setExpirationDate(debitCard.getExpirationDate());
        responseDto.setRegisteredAccounts(debitCard.getRegisteredAccounts());
        return responseDto;
    }
}
