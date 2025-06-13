package com.deepakraj.accounts.service.Impl;

import com.deepakraj.accounts.Repository.AccountsRepo;
import com.deepakraj.accounts.Repository.CustomerRepo;
import com.deepakraj.accounts.Repository.feignclient.CardsFeignClient;
import com.deepakraj.accounts.Repository.feignclient.LoansFeignClient;
import com.deepakraj.accounts.dto.*;
import com.deepakraj.accounts.entity.Accounts;
import com.deepakraj.accounts.entity.Customer;
import com.deepakraj.accounts.exception.ResourceNotFoundException;
import com.deepakraj.accounts.mapper.AccountsMapper;
import com.deepakraj.accounts.mapper.CustomerMapper;
import com.deepakraj.accounts.service.ICustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private AccountsRepo accountsRepo;
    private CustomerRepo customerRepo;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    CustomerServiceImpl(AccountsRepo accountsRepo,CustomerRepo customerRepo,CardsFeignClient cardsFeignClient,LoansFeignClient loansFeignClient){
        this.accountsRepo=accountsRepo;
        this.customerRepo=customerRepo;
        this.cardsFeignClient=cardsFeignClient;
        this.loansFeignClient=loansFeignClient;
    }
    @Override
    public CustomerServiceDto fetchAccount(String mobileNumber) {
        CustomerServiceDto customerServiceDto=new CustomerServiceDto();

        Customer customer=customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () ->     new ResourceNotFoundException("customer","mobile number",mobileNumber)
        );
        Accounts accounts=accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("account","customer id",customer.getCustomerId().toString())
        );
        ResponseEntity<CardsDto> cardsDtoResponseEntity=cardsFeignClient.fetchCards(mobileNumber);
        ResponseEntity<LoansDto> loansDtoResponseEntity=loansFeignClient.fetchLoanDetails(mobileNumber);
        CardsDto cardsDto = new CardsDto();
        LoansDto loansDto = new LoansDto();

        if (cardsDtoResponseEntity != null && cardsDtoResponseEntity.getBody() != null) {
            cardsDto = cardsDtoResponseEntity.getBody();
        }

        if (loansDtoResponseEntity != null && loansDtoResponseEntity.getBody() != null) {
            loansDto = loansDtoResponseEntity.getBody();
        }

        customerServiceDto.setCardsDto(cardsDto);
        customerServiceDto.setLoansDto(loansDto);

        CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        AccountsDto accountsDto= AccountsMapper.mapToAccountsDto(accounts,new AccountsDto());
        customerServiceDto.setCustomerDto(customerDto);
        customerServiceDto.setAccountsDto(accountsDto);
        return customerServiceDto;


    }
}
