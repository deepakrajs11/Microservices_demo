package com.deepakraj.cards.service.Impl;

import com.deepakraj.cards.Dto.CardsDto;
import com.deepakraj.cards.Entity.Cards;
import com.deepakraj.cards.constants.CardsConstants;
import com.deepakraj.cards.exception.ResourceNotFound;
import com.deepakraj.cards.exception.CardsAlreadyPresentException;
import com.deepakraj.cards.mapper.CardsMapper;
import com.deepakraj.cards.repository.CardsRepo;
import com.deepakraj.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
@Service
@AllArgsConstructor
public class CardsService implements ICardsService {
    private CardsRepo cardsRepo;
    @Override
    public void createCards(String mobileNumber) {
       Optional<Cards> alreadyPresent=cardsRepo.findByMobileNumber(mobileNumber);
       if(alreadyPresent.isPresent()){
           throw new CardsAlreadyPresentException("The card with the give mobile number already present");
       }
       cardsRepo.save(CreateNewCard(mobileNumber));
    }

    private Cards CreateNewCard(String mobileNumber){
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(randomCardNumber);
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0L);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCards(String mobileNumber){
        Cards cards=cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () ->  new ResourceNotFound("Cards","Mobile number",mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards,new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto){
        Cards cards=cardsRepo.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                ()-> new ResourceNotFound("Card","Mobile", cardsDto.getMobileNumber().toString())
        );
        cards = CardsMapper.mapToCards(cardsDto,cards);
        cardsRepo.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber){
        Cards cards=cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFound("Cards","Mobile number",mobileNumber)
        );
        cardsRepo.delete(cards);
        return true;
    }



}
