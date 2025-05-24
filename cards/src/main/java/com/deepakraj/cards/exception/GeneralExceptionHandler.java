package com.deepakraj.cards.exception;

import com.deepakraj.cards.Dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> mappedErrors=new HashMap<>();
        List<ObjectError> founderrors= ex.getBindingResult().getAllErrors();
        for(ObjectError founderror:founderrors){
            String fieldName=((FieldError)founderror).getField();
            String fieldValue=founderror.getDefaultMessage();
            mappedErrors.put(fieldName,fieldValue);
        }
        return new ResponseEntity<>(mappedErrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOtherGenericException(Exception exception,WebRequest webRequest){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,webRequest.getDescription(false),LocalDateTime.now()));
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundError(Exception exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND,webRequest.getDescription(false), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }

    @ExceptionHandler(CardsAlreadyPresentException.class)
    public ResponseEntity<ErrorResponseDto> handleCardAlreadyPresentException(Exception exception,WebRequest webRequest){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(exception.getMessage(),HttpStatus.BAD_REQUEST, webRequest.getDescription(false),LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

}
