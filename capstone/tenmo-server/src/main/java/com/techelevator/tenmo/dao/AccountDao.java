package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao
{
     List<Account> list();

     Account findById(Long userId);

     Account findByUsername(String userName);

      Account findByAccountId(Long accountId);

}
