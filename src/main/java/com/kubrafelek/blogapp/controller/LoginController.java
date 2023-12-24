package com.kubrafelek.blogapp.controller;

import com.kubrafelek.blogapp.model.Account;
import com.kubrafelek.blogapp.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String getRegisterPage(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "login";
    }

    @PostMapping("/login")
    public String createUser(@ModelAttribute Account account){
        return "redirect:/";
    }
}
