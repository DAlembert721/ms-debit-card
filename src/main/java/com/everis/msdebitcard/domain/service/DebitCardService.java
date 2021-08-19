package com.everis.msdebitcard.domain.service;

import com.everis.msdebitcard.domain.model.DebitCard;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import reactor.core.publisher.Mono;

public interface DebitCardService extends BaseService<DebitCardResponseDto>{
    Mono<DebitCardResponseDto> findByCardNumber(String cardNumber);
}
