package com.everis.msdebitcard.router;

import com.everis.msdebitcard.handler.DebitCardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> debitCardRoutes(DebitCardHandler handler) {
        return route(GET("/debit-cards"), handler::findAll);
    }
}
