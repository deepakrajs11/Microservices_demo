package com.deepakraj.accounts.Repository.feignclient;

import com.deepakraj.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards",fallback = CardsFeignClientFallBack.class)
public interface CardsFeignClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCards(@RequestParam String mobileNumber);
}
