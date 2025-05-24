package com.deepakraj.accounts.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deepakraj.accounts.entity.Accounts;

import java.util.Optional;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts,Long> {
    Optional<Accounts> findByCustomerId(Long customerId);
}
