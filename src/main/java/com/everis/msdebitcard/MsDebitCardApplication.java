package com.everis.msdebitcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsDebitCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDebitCardApplication.class, args);
    }

}
