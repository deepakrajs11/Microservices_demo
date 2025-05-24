package com.deepakraj.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String ResourceName, String KeyName, String Value){
        super(ResourceName+" with the "+KeyName+" : "+Value+" not found!");
    }
}
