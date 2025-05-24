package com.deepakraj.accounts.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold the account details"
)
public class AccountsDto {
    @Schema(
            description = "Account Number of the customer",
            example = "9123456780"
    )
    @NotNull(message = "Account Number cannot be empty")
    @Min(value = 1000000000L, message = "AccountNumber must be at least 10 digits")
    @Max(value = 9999999999L, message = "AccountNumber must be at most 10 digits")
    private Long accountNumber;
    @Schema(
            description = "Account TYpe of the customer",
            example = "SAVINGS"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;
    @Schema(
            description = "Branch Address of the Customer",
            example = "11,West Anna Nagar, Chennai"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
