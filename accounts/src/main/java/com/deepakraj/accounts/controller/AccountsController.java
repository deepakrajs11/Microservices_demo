package com.deepakraj.accounts.controller;
import com.deepakraj.accounts.constants.AccountsConstants;
import com.deepakraj.accounts.dto.*;
import com.deepakraj.accounts.service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE} )
@Validated
@Tag(
        name = "Accounts Api Services",
        description = "CRUD operation in Account API"
)
public class AccountsController {
    private IAccountsService iAccountsService;
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
    public AccountsController(IAccountsService iAccountsService){
        this.iAccountsService=iAccountsService;
    }
    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(
            summary = "Create Account REST API",
            description = "RestAPI to create customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account REST API",
            description = "RestAPI to get customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<BasicCustomerAccountsInfoDto> fetchAccount(@RequestParam
                                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                                      String mobileNumber){
        BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto=iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(basicCustomerAccountsInfoDto);
    }

    @Operation(
            summary = "Update Account REST API",
            description = "RestAPI to update customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccounts(@Valid @RequestBody BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto){
        boolean isUpdated=iAccountsService.updateAccount(basicCustomerAccountsInfoDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
    }

    @Operation(
            summary = "Remove Account REST API",
            description = "RestAPI to delete customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDto> deleteAccounts(@RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                  String MobileNumber){
        BasicCustomerAccountsInfoDto basicCustomerAccountsInfoDto=iAccountsService.deleteAccount(MobileNumber);
        if(basicCustomerAccountsInfoDto==null){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @Retry(name = "getcontactInfo",fallbackMethod = "getContactInfoFallBack")
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        logger.debug("getContactInfo triggered");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

    public ResponseEntity<AccountsContactInfoDto> getContactInfoFallBack(Throwable throwable) {
        logger.debug("getContactInfoFallBack triggered");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AccountsContactInfoDto());
    }


    @Value("${java-version}")
    private String version;
    @RateLimiter(name = "javaversion",fallbackMethod = "getjavaversionFallBack")
    @GetMapping("/javaversion")
    public ResponseEntity<String> getjavaverion(){
        return ResponseEntity.status(HttpStatus.OK).body(version);
    }

    public ResponseEntity<String> getjavaversionFallBack(Throwable throwable){return ResponseEntity.status(HttpStatus.OK).body("Too many request per second");}

}
