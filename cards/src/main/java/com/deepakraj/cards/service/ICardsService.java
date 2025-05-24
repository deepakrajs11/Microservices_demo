package com.deepakraj.cards.service;

import com.deepakraj.cards.Dto.CardsDto;

public interface ICardsService {
     void createCards(String mobileNumber);
     CardsDto fetchCards(String mobileNumber);
     boolean updateCard(CardsDto cardsDto);
     boolean deleteCard(String MobileNumber);

}
