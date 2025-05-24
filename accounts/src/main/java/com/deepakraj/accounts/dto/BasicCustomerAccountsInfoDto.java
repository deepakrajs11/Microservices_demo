package com.deepakraj.accounts.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(
        name = "Basic Customer Accounts",
        description = "Schema to hold the basic account and customer detail"
)
public class BasicCustomerAccountsInfoDto {
    @Schema(
            description = "Basic Customer Detail"
    )
    @Valid
    CustomerDto customerDto;
    @Schema(
            description = "Basic Accounts Detail"
    )
    @Valid
    AccountsDto accountsDto;
}
