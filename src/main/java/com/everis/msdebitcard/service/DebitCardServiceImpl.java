package com.everis.msdebitcard.service;

import com.everis.msdebitcard.domain.model.*;
import com.everis.msdebitcard.domain.repository.DebitCardRepository;
import com.everis.msdebitcard.domain.service.DebitCardService;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class DebitCardServiceImpl implements DebitCardService {

    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    WebClient webClientCurrent = WebClient.create("http://localhost:8090/api/ms-current-account/currentAccount");

    WebClient webClientFixed = WebClient.create("http://localhost:8090/api/ms-fixed-term/fixedTerm");

    WebClient webClientSaving = WebClient.create("http://localhost:8090/api/ms-saving-account/savingAccount");

    WebClient webClientTransfer = WebClient.create("http://localhost:8090/api/ms-transfer-bank/transfer");

    @Autowired
    private DebitCardRepository debitCardRepository;

    public DebitCardServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("accounts");
    }


    @Override
    public Flux<DebitCardResponseDto> findAll() {
        return debitCardRepository.findAll()
                .map(DebitCardResponseDto::entityToResponse)
                .onErrorResume(throwable -> Mono.error(new Exception("Error on get all DebitCards")));
    }

    @Override
    public Mono<DebitCardResponseDto> findById(String id) {
        return debitCardRepository.findById(id)
                .map(DebitCardResponseDto::entityToResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(throwable -> Mono.error(new Exception("Error on getting the data")));
    }

    @Override
    public Mono<DebitCardResponseDto> findByCardNumber(String cardNumber) {
        return debitCardRepository.findByCardNumber(cardNumber)
                .map(DebitCardResponseDto::entityToResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(throwable -> Mono.error(new Exception("Error on getting the data")));
    }

    @Override
    public Mono<DebitCardResponseDto> createDebitCard(String accountNumber) {
        return reactiveCircuitBreaker.run(webClientTransfer
                .get().uri("/findAccount/{accountNumber}", accountNumber)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(BankAccount.class), Mono::error)
                .flatMap(bankAccount -> {
                    DebitCard debitCard = DebitCard.generateBankAccount(bankAccount);
                    return debitCardRepository.save(debitCard);
                })
                .map(DebitCardResponseDto::entityToResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(Mono::error);
    }

}
