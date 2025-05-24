package com.deepakraj.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
@Schema(
        name = "Response",
        description = "Response form Api information"
)
@Data @AllArgsConstructor
public class ResponseDto {
    @Schema(
            description = "The Html status code"
    )
    private String statusCode;
    @Schema(
            description = "THe actual result status message"
    )
    private String statusMsg;
}
