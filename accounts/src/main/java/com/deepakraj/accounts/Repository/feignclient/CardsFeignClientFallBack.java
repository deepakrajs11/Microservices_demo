package com.deepakraj.accounts.Repository.feignclient;

import com.deepakraj.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFeignClientFallBack implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCards(String mobileNumber) {
        return null;
    }
}
