package com.deepakraj.accounts.controller;

import com.deepakraj.accounts.dto.CustomerServiceDto;
import com.deepakraj.accounts.dto.ErrorResponseDto;
import com.deepakraj.accounts.service.IAccountsService;
import com.deepakraj.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE} )
@Validated
@Tag(
        name = "Accounts Api Services",
        description = "CRUD operation in Account API"
)
public class CustomerServiceController {
    ICustomerService iCustomerService;
    CustomerServiceController(ICustomerService iCustomerService){
        this.iCustomerService=iCustomerService;
    }
    @Operation(
            summary = "Fetch customer services",
            description = "RestAPI to fetch complete accounts, customer, cards, loans"
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
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerServiceDto> fetchCustomerDetails(@RequestParam String mobileNumber){
        CustomerServiceDto customerServiceDto=iCustomerService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerServiceDto);
    }

}
