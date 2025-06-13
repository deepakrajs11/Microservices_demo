package com.deepakraj.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Customer Service",
        description = "Schema to hold Account, Customer, Card and Loan information"
)
@Data
public class CustomerServiceDto {
    private AccountsDto accountsDto;
    private CustomerDto customerDto;
    private CardsDto cardsDto;
    private LoansDto loansDto;
}
