package com.deepakraj.accounts.service;


import com.deepakraj.accounts.dto.CustomerServiceDto;

public interface ICustomerService {
    CustomerServiceDto fetchAccount(String mobileNumber);
}
