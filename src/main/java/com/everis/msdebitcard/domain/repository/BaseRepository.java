package com.everis.msdebitcard.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BaseRepository<T> extends ReactiveMongoRepository<T, String> {
}
