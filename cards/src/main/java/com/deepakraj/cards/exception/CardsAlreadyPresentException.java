package com.deepakraj.cards.exception;

public class CardsAlreadyPresentException extends RuntimeException{
    public CardsAlreadyPresentException(String message){
        super(message);
    }
}
