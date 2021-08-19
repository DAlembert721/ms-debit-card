package com.everis.msdebitcard.domain.repository;

import com.everis.msdebitcard.domain.model.DebitCard;
import reactor.core.publisher.Mono;

public interface DebitCardRepository extends BaseRepository<DebitCard>{
    Mono<DebitCard> findByCardNumber(String cardNumber);
}
