package com.everis.msdebitcard.service;

import com.everis.msdebitcard.domain.model.*;
import com.everis.msdebitcard.domain.repository.DebitCardRepository;
import com.everis.msdebitcard.domain.service.DebitCardService;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class DebitCardImpl implements DebitCardService {


    WebClient webClientCurrent = WebClient.create("http://gateway:8090/api/ms-current-account/currentAccount");

    WebClient webClientFixed = WebClient.create("http://gateway:8090/api/ms-fixed-term/fixedTerm");

    WebClient webClientSaving = WebClient.create("http://gateway:8090/api/ms-saving-account/savingAccount");


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
        return null;
    }

    @Override
    public Mono<Optional<BankAccount>> findBankAccount(String accountNumber) {
        return webClientCurrent.get().uri("/findByAccountNumber/{id}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CurrentAccount.class)
                .map(currentAccount -> {
                    System.out.println("CurrentAccount Founded > " + currentAccount.getId());
                    return Optional.of((BankAccount)currentAccount);})
                .switchIfEmpty(webClientFixed.get().uri("/findByAccountNumber/{id}", accountNumber)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(FixedTerm.class)
                        .map(fixedTerm -> {
                            System.out.println("FixedTerm Founded > " + fixedTerm.getId());
                            return Optional.of((BankAccount)fixedTerm);
                        })
                        .switchIfEmpty(webClientSaving.get().uri("/findByAccountNumber/{id}", accountNumber)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(SavingAccount.class)
                                .map(savingAccount -> {
                                    System.out.println("SavingAccount Founded > " + savingAccount.getId());
                                    return Optional.of((BankAccount)savingAccount);
                                }))
                        .defaultIfEmpty(Optional.empty())
                );
    }
}
