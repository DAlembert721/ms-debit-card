package com.everis.msdebitcard.domain.service;

import com.everis.msdebitcard.domain.model.BankAccount;
import reactor.core.publisher.Mono;

public interface TransferWebClientService {
    Mono<BankAccount> findBankAccountByAccountNumber(String accountNumber);
}
