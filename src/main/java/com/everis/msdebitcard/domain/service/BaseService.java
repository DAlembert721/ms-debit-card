package com.everis.msdebitcard.domain.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BaseService<T> {
    Optional<Flux<T>> findAll();
    Optional<Mono<T>> findById(String id);
}
