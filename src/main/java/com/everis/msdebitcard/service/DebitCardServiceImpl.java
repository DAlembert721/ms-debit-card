package com.everis.msdebitcard.service;

import com.everis.msdebitcard.domain.model.*;
import com.everis.msdebitcard.domain.repository.DebitCardRepository;
import com.everis.msdebitcard.domain.service.DebitCardService;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import com.everis.msdebitcard.exception.NotFoundedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DebitCardServiceImpl implements DebitCardService {


    @Autowired
    private DebitCardRepository debitCardRepository;

    @Autowired
    private TransferWebClientServiceImpl transferWebClientService;


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
        return transferWebClientService.findBankAccountByAccountNumber(accountNumber)
                .flatMap(bankAccount -> {
                    DebitCard debitCard = DebitCard.generateBankAccount(bankAccount);
                    return debitCardRepository.save(debitCard);
                })
                .map(DebitCardResponseDto::entityToResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<DebitCardResponseDto> updateAccounts(String cardNumber, String accountNumber) {
        return transferWebClientService.findBankAccountByAccountNumber(accountNumber)
                .flatMap(bankAccount ->
                        debitCardRepository.findByCardNumber(cardNumber)
                                .flatMap(debitCard -> {
                                            DebitCard updatedCard = DebitCard.updateAccounts(bankAccount, debitCard);
                                            return debitCardRepository.save(updatedCard);
                                        }
                                ).map(DebitCardResponseDto::entityToResponse)
                                .switchIfEmpty(Mono.error(new NotFoundedException("Debit card", cardNumber)))
                ).switchIfEmpty(Mono.error(new NotFoundedException("Account", accountNumber)))
                .onErrorResume(Mono::error);
    }

}
