package com.deepakraj.accounts.Repository.feignclient;

import com.deepakraj.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFeignClientFallBack implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
