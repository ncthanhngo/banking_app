package org.thanhngo.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thanhngo.bankingapp.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
