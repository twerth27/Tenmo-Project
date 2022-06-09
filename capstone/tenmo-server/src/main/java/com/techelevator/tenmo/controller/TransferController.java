package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    JdbcUserDao userDao;
    JdbcTransferDao transferDao;
    JdbcAccountDao accountDao;
    JdbcTransferDTODao transferDTODao;



    @Autowired
    public TransferController(JdbcUserDao userDao, JdbcTransferDao transferDao, JdbcAccountDao accountDao, JdbcTransferDTODao transferDTODao){
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.transferDTODao = transferDTODao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<User> getAllUsers(){

        return userDao.findAll();
    }

    @PostMapping()
    public Transfer createTransfer(@RequestBody Transfer transfer) throws Exception {
        return transferDao.createTransfer(transfer);
    }

    @RequestMapping(path = "/search/{username}", method= RequestMethod.GET)
    public int getAccountIdByUsername(@PathVariable String username)
    {
        Account account = null;
        System.out.println(username);
        int userId = userDao.findIdByUsername(username);
        account = accountDao.findById(Long.valueOf(userId));

        return Math.toIntExact(account.getAccountId());

    }

    @RequestMapping(path = "/check", method = RequestMethod.GET)
    public Transfer checkLastTransfer()
    {
        return transferDao.checkLastTransfer();

    }

}
