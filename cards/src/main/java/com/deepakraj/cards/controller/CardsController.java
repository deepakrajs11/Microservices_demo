package com.deepakraj.cards.controller;
import com.deepakraj.cards.Dto.CardsContactInfoDto;
import com.deepakraj.cards.Dto.CardsDto;
import com.deepakraj.cards.Dto.ErrorResponseDto;
import com.deepakraj.cards.Dto.ResponseDto;
import com.deepakraj.cards.constants.CardsConstants;
import com.deepakraj.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Cards",
        description = "APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CardsController {
    ICardsService iCardsService;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card"
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
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber){
       iCardsService.createCards(mobileNumber);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
   }
    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
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
    })
   @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCards(@RequestParam String mobileNumber){
       CardsDto cardsDto=iCardsService.fetchCards(mobileNumber);
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(cardsDto);
   }
    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
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
    })
   @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardsDto cardsDto){
       boolean isUpdated=iCardsService.updateCard(cardsDto);
       if(isUpdated)
       return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
       else
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
   }
    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
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
    })
   @DeleteMapping("/remove")
    public ResponseEntity<ResponseDto> removeCard(@RequestParam String mobileNumber){
       boolean isdeleted=iCardsService.deleteCard(mobileNumber);
       if(!isdeleted){
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
       }
       return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
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
   @GetMapping("/contact-info")
   public ResponseEntity<CardsContactInfoDto> getContactInfo() {
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(cardsContactInfoDto);
   }


}
