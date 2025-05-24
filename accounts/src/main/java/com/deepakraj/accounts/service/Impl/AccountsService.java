package com.deepakraj.accounts.service.Impl;
import com.deepakraj.accounts.Repository.AccountsRepo;
import com.deepakraj.accounts.Repository.CustomerRepo;
import com.deepakraj.accounts.constants.AccountsConstants;
import com.deepakraj.accounts.dto.AccountsDto;
import com.deepakraj.accounts.dto.BasicCustomerAccountsInfoDto;
import com.deepakraj.accounts.dto.CustomerDto;
import com.deepakraj.accounts.entity.Customer;
import com.deepakraj.accounts.exception.CustomerAlreadyExistsException;
import com.deepakraj.accounts.exception.ResourceNotFoundException;
import com.deepakraj.accounts.mapper.AccountsMapper;
import com.deepakraj.accounts.mapper.CustomerMapper;
import com.deepakraj.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.deepakraj.accounts.entity.Accounts;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService {

    private AccountsRepo accountsRepo;
    private CustomerRepo customerRepo;
    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
      Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
      Optional<Customer> optionalCustomer = customerRepo.findByMobileNumber(customerDto.getMobileNumber());
      if(optionalCustomer.isPresent()){
          throw new CustomerAlreadyExistsException("The Customer Mobile Number"+customer.getMobileNumber()+"Already Exists");
      }
      Customer savedCustomer=customerRepo.save(customer);
      accountsRepo.save(createnewAccount(savedCustomer));
    }

    private Accounts createnewAccount(Customer customer){
      Accounts newaccounts=new Accounts();
      newaccounts.setCustomerId(customer.getCustomerId());
      newaccounts.setAccountType(AccountsConstants.SAVINGS);
      newaccounts.setAccountNumber(1000000000L+new Random().nextInt(900000000));
      newaccounts.setBranchAddress(AccountsConstants.ADDRESS);

      return newaccounts;
    }

    @Override
    public BasicCustomerAccountsInfoDto fetchAccount(String mobileNumber){
        Optional<Customer> optionalCustomer=customerRepo.findByMobileNumber(mobileNumber);
        if(!optionalCustomer.isPresent()){
            throw new ResourceNotFoundException("Customer","Mobile number",mobileNumber);
        }
        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(optionalCustomer.get(),new CustomerDto());
        Accounts accounts=accountsRepo.findByCustomerId(optionalCustomer.get().getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Accounts","Customer ID",optionalCustomer.get().getCustomerId().toString()));
        AccountsDto accountsDto= AccountsMapper.mapToAccountsDto(accounts,new AccountsDto());
        BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto=new BasicCustomerAccountsInfoDto();
        basicCustomerAccountsInfoDto.setAccountsDto(accountsDto);
        basicCustomerAccountsInfoDto.setCustomerDto(customerDto);
        return basicCustomerAccountsInfoDto;
    }

    @Override
    public boolean updateAccount(BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto){
        AccountsDto accountsDto=basicCustomerAccountsInfoDto.getAccountsDto();
        Accounts accounts=accountsRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
        );
        Long customerId=accounts.getCustomerId();
        Customer customer=customerRepo.findById(accounts.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Customer Id",customerId.toString())
        );
        accounts=AccountsMapper.mapToAccounts(basicCustomerAccountsInfoDto.getAccountsDto(),accounts);
        customer=CustomerMapper.mapToCustomer(basicCustomerAccountsInfoDto.getCustomerDto(),customer);
        accounts=accountsRepo.save(accounts);
        customer=customerRepo.save(customer);
        System.out.println(accounts.toString());
        return true;
    }

    @Override
    public BasicCustomerAccountsInfoDto deleteAccount(String MobileNumber){
        BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto=fetchAccount(MobileNumber);
        Customer customer=customerRepo.findByMobileNumber(MobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobile Number",MobileNumber)
        );
        Accounts accounts=accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account","Customer Id", customer.getCustomerId().toString())
        );
            customerRepo.delete(customer);
            accountsRepo.delete(accounts);
            return basicCustomerAccountsInfoDto;

    }
}
