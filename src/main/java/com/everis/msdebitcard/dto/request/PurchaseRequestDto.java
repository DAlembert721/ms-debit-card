package com.everis.msdebitcard.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequestDto {
    private String debitCardNumber;
    private Double amount;
}
