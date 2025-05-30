package com.deepakraj.accounts.Repository.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("loans")
public interface LoansFeignClient {
}
