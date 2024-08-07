package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account){
        return accountRepository.save(account);
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    public Account findAccountByAccountId(Integer accountId) {
        return accountRepository.findByAccountId(accountId);
    }
}
