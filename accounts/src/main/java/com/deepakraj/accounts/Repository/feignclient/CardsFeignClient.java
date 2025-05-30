package com.deepakraj.accounts.Repository.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("cards")
public interface CardsFeignClient {
}
