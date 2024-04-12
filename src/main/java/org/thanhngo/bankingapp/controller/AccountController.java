package org.thanhngo.bankingapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thanhngo.bankingapp.dto.AccountDto;
import org.thanhngo.bankingapp.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    //Add Account REST API
    /*public ResponseEntity<AccountDto> AddAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }*/
    @PostMapping("/add")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
    //Get a Accounts by ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }
    //Deposit  REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String,Double> request) {
        Double amount =request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }
    //Withdraw REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                              @RequestBody Map<String,Double> request) {
        Double amount =request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }
    //tranfer REST API
    @PutMapping("/{fromId}/{toId}/transfer")
    public ResponseEntity<AccountDto> transfer(@PathVariable Long fromId,@PathVariable Long toId,
                                              @RequestBody Map<String,Double> request) {
        Double amount =request.get("amount");
        AccountDto accountDto = accountService.transfer(fromId, toId, amount);
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }
    //Get All Accounts REST API
    @GetMapping("")
    public ResponseEntity<Iterable<AccountDto>> getAllAccounts() {
        Iterable<AccountDto> accountDtos = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(accountDtos);
    }
    //delete Account by ID REST API
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<AccountDto> deleteAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        accountService.deleteAccountById(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }
}
