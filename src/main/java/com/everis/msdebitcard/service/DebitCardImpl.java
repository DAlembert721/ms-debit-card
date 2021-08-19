package com.everis.msdebitcard.service;

import com.everis.msdebitcard.domain.model.DebitCard;
import com.everis.msdebitcard.domain.repository.DebitCardRepository;
import com.everis.msdebitcard.domain.service.DebitCardService;
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
    public Optional<Flux<DebitCard>> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Mono<DebitCard>> findById(String id) {
        return Optional.empty();
    }
}
