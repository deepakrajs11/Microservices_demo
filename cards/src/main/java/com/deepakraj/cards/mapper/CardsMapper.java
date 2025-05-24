package com.deepakraj.cards.mapper;

import com.deepakraj.cards.Dto.CardsDto;
import com.deepakraj.cards.Entity.Cards;

public class CardsMapper {
    public static Cards mapToCards(CardsDto cardsDto,Cards cards){
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setAmountUsed(cardsDto.getAvailableAmount());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        return cards;
    }
    public static CardsDto mapToCardsDto(Cards cards,CardsDto cardsDto){
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        return cardsDto;
    }
}
