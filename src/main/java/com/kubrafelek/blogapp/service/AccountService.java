package com.kubrafelek.blogapp.service;

import com.kubrafelek.blogapp.model.Account;
import com.kubrafelek.blogapp.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> findByEmail(String mail) {
        return accountRepository.findOneByEmail(mail);
    }

    public Optional<Account> findOneByEmail(String authUsername) {
        return accountRepository.findOneByEmail(authUsername);
    }
}
