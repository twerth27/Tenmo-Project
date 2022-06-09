package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("accounts")
//@PreAuthorize("isAuthenticated()")
public class AccountController {
    AccountDao accountDao;
    UserDao userDao;

    @Autowired
    public AccountController(AccountDao accountDao, UserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping("{id}")
    public Account getAccountByUserId(@PathVariable Long id, Principal principal){
        Account username = accountDao.findByUsername(principal.getName());
        int checkId = userDao.findIdByUsername(principal.getName());

        if (checkId == id){
            return accountDao.findById(id);
        }

        return null;
    }

    @GetMapping()
    public List<Account> getAllAccounts(){
        return accountDao.list();
    }

}
