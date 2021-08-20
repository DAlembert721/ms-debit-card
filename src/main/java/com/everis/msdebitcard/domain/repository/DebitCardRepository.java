package com.everis.msdebitcard.domain.repository;

import com.everis.msdebitcard.domain.model.DebitCard;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DebitCardRepository extends BaseRepository<DebitCard>{
    Mono<DebitCard> findByCardNumber(String cardNumber);
}
