package com.deepakraj.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deepakraj.cards.Entity.Cards;

import java.util.Optional;

public interface CardsRepo extends JpaRepository<Cards,Long> {
    Optional<Cards> findByMobileNumber(String MobileNumber);
}
