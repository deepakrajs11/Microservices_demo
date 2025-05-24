package com.deepakraj.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Schema(
        name = "Error Response",
        description = "Schema to hold the error response"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {
    @Schema(
            description = "The response error code", example = "404"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "The api path invoked",example = "localhost:8080/api/create"
    )
    private String apiPath;
    @Schema(
            description = "The error Message from the api",
            example = "Internal Server Error"
    )
    private String  errorMessage;
    @Schema(
            description = "The error occurrence time",
            example = "12.25"
    )
    private LocalDateTime errorTime;
}
