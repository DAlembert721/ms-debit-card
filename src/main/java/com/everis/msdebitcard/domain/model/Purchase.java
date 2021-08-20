package com.everis.msdebitcard.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Document("Purchase")
public class Purchase {
    @Id
    private String id;

    @NotNull
    private Double amount;

    @NotNull
    private DebitCard debitCard;
}
