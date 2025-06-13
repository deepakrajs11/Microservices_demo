package com.deepakraj.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AccountsFallBackController {
    @GetMapping("/accounts/fallback")
    public Mono<String> accountfallback(){
        return Mono.just("THe account service is not working properly, please try after some time or contact help desk");
    }
}
