package com.nemal.BankAPI.repo;

import com.nemal.BankAPI.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByNicNumberAndAccountType(String nicNumber, String accountType);
}
