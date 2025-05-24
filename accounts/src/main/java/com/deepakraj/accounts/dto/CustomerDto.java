package com.deepakraj.accounts.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold the Customer Details"
)
public class CustomerDto {
    @Schema(
            description = "Name of the Customer",example = "Alex J"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 2,max = 30,message = "Invalid, The name length should be in the range 2 to 30")
    @Column(name = "name")
    private String name;
    @Schema(
            description = "Email of the Customer",example = "example123@gmail.com"
    )
    @NotEmpty(message = "Email Id can not be a null or empty")
    @Email(message = "Invalid email id")
    @Column(name = "email")
    private String email;
    @Schema(
            description = "Mobile Number of the Customer",example = "1234567890"
    )
    @NotEmpty(message = "Mobile number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Column(name = "mobile_number")
    private String mobileNumber;

}
