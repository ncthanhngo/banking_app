package org.thanhngo.bankingapp.service.impl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thanhngo.bankingapp.dto.AccountDto;
import org.thanhngo.bankingapp.entity.Account;
import org.thanhngo.bankingapp.mapper.AccountMapper;
import org.thanhngo.bankingapp.repository.AccountRepository;
import org.thanhngo.bankingapp.service.AccountService;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
       Account account = accountRepository.findById(id)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
       return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
       double total = account.getBalance() + amount;
       account.setBalance(total);
       Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account  account = accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found"));
        if (amount > account.getBalance()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }else if (amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot be negative");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto transfer(Long fromId, Long toId, Double amount) {
        Optional<Account> fromAccount = accountRepository.findById(fromId);
        Optional<Account> toAccount = accountRepository.findById(toId);
        if (!fromAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        if (!toAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        if (amount > fromAccount.get().getBalance()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }else if (amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot be negative");
        }
        double total = fromAccount.get().getBalance() - amount;
        double total2 = toAccount.get().getBalance() + amount;
        fromAccount.get().setBalance(total);
        toAccount.get().setBalance(total2);
        Account savedFromAccount = accountRepository.save(fromAccount.get());
        Account savedToAccount = accountRepository.save(toAccount.get());
        return AccountMapper.mapToAccountDto(savedFromAccount);
    }

    @Override
    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Iterable<AccountDto> getAllAccounts() {
        Iterable<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList<>();

        for (Account account : accounts) {
            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
            accountDtos.add(accountDto);
        }
        return accountDtos;
    }
}
