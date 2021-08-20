package com.everis.msdebitcard.domain.service;

import com.everis.msdebitcard.domain.model.BankAccount;
import com.everis.msdebitcard.domain.model.DebitCard;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface DebitCardService extends BaseService<DebitCardResponseDto>{
    Mono<DebitCardResponseDto> findByCardNumber(String cardNumber);
    Mono<DebitCardResponseDto> createDebitCard(String accountNumber);
    public Mono<Optional<BankAccount>> findBankAccount(String cardNumber);
}
