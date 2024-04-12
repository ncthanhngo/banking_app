package org.thanhngo.bankingapp.service;

import org.thanhngo.bankingapp.dto.AccountDto;
import org.thanhngo.bankingapp.entity.Account;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id, Double amount);
    AccountDto withdraw(Long id, Double amount);
    AccountDto transfer(Long fromId, Long toId, Double amount);
    void deleteAccountById(Long id);
    //Get all accounts
    Iterable<AccountDto> getAllAccounts();
}
