package com.everis.msdebitcard.handler;

import com.everis.msdebitcard.domain.service.DebitCardService;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import com.everis.msdebitcard.dto.response.ErrorResponse;
import com.everis.msdebitcard.exception.NoParamsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j(topic = "DEBIT_CARD_HANDLER")
public class DebitCardHandler {
    @Autowired
    private DebitCardService debitCardService;

    public Mono<ServerResponse> findAllDebitCards(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(debitCardService.findAll(), DebitCardResponseDto.class);
    }

    public Mono<ServerResponse> findDebitCardById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return debitCardService.findById(id)
                .flatMap(debitCardResponseDto -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(debitCardResponseDto))
                .switchIfEmpty(
                        ErrorResponse.buildBadResponse("The debit card with id: ".concat(id),HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public Mono<ServerResponse> createDebitCard(ServerRequest serverRequest) {
        String accountNumber = serverRequest.queryParam("accountNumber")
                .orElseThrow(() ->  new NoParamsException("Must be include account number params"));
        return debitCardService.createDebitCard(accountNumber)
                .flatMap(debitCardResponseDto -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(debitCardResponseDto))
                .switchIfEmpty(
                        ErrorResponse.buildBadResponse("The account with number: "
                                        .concat(accountNumber).concat("can't be founded"),
                                HttpStatus.BAD_REQUEST))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.BAD_REQUEST));

    }

    public Mono<ServerResponse> updateAccounts(ServerRequest serverRequest) {
        String accountNumber = serverRequest.queryParam("accountNumber")
                .orElseThrow(() -> new NoParamsException("Must be include account number params"));
        String debitCardNumber = serverRequest.queryParam("cardNumber")
                .orElseThrow(() -> new NoParamsException("Must be include card number params"));
        return debitCardService.updateAccounts(debitCardNumber, accountNumber)
                .flatMap(debitCardResponseDto -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(debitCardResponseDto))
                .switchIfEmpty(
                        ErrorResponse
                                .buildBadResponse("The account with number: "
                                                .concat(accountNumber).concat("can't be founded"),
                                HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.BAD_REQUEST));
    }


}
