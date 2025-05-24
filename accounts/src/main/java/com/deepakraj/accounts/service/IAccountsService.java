package com.deepakraj.accounts.service;
import com.deepakraj.accounts.dto.CustomerDto;
import com.deepakraj.accounts.dto.BasicCustomerAccountsInfoDto;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);
    BasicCustomerAccountsInfoDto fetchAccount(String mobileNumber);
    boolean updateAccount(BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto);
    BasicCustomerAccountsInfoDto deleteAccount(String MobileNumber);
}
