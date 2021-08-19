package com.everis.msdebitcard.service;

import com.everis.msdebitcard.domain.model.DebitCard;
import com.everis.msdebitcard.domain.repository.DebitCardRepository;
import com.everis.msdebitcard.domain.service.DebitCardService;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class DebitCardImpl implements DebitCardService {

    @Autowired
    private DebitCardRepository debitCardRepository;


    @Override
    public Flux<DebitCardResponseDto> findAll() {
        return debitCardRepository.findAll()
                .map(DebitCardResponseDto::entityToResponse)
                .onErrorResume(throwable -> Mono.error(new Exception("Error on get all DebitCards")));
    }

    @Override
    public Mono<DebitCardResponseDto> findById(String id) {
        return Mono.empty();
    }
}
