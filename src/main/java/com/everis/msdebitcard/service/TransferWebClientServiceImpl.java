package com.everis.msdebitcard.service;

import com.everis.msdebitcard.domain.model.BankAccount;
import com.everis.msdebitcard.domain.service.TransferWebClientService;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TransferWebClientServiceImpl implements TransferWebClientService {

    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    WebClient webClientTransfer = WebClient.create("http://localhost:8090/api/ms-transfer-bank/transfer");


    public TransferWebClientServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("ms-transfer");
    }


    @Override
    public Mono<BankAccount> findBankAccountByAccountNumber(String accountNumber) {
        return reactiveCircuitBreaker.run(webClientTransfer
                .get().uri("/findAccount/{accountNumber}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BankAccount.class), Mono::error);
    }
}
