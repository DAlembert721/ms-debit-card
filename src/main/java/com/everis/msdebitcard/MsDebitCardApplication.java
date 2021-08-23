package com.everis.msdebitcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsDebitCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDebitCardApplication.class, args);
    }

}
