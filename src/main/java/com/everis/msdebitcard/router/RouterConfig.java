package com.everis.msdebitcard.router;

import com.everis.msdebitcard.handler.DebitCardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> debitCardRoutes(DebitCardHandler handler) {
        return route(GET("/ms-debit-card/debit-cards"), handler::findAllDebitCards)
                .andRoute(GET("/ms-debit-card/debit-cards/{id}"), handler::findDebitCardById)
                .andRoute(POST("/ms-debit-card/debit-cards"), handler::createDebitCard)
                .andRoute(PUT("/ms-debit-card/debit-cards"), handler::updateAccounts);
    }
}
