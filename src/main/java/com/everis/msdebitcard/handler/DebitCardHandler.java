package com.everis.msdebitcard.handler;

import com.everis.msdebitcard.domain.model.DebitCard;
import com.everis.msdebitcard.domain.service.DebitCardService;
import com.everis.msdebitcard.dto.response.DebitCardResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(debitCardService.findAll(), DebitCardResponseDto.class);
    }


}
