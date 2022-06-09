package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDTODao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("transferdto")
@PreAuthorize("isAuthenticated()")
public class TransferDTOController {

        JdbcUserDao userDao;
        JdbcTransferDao transferDao;
        JdbcAccountDao accountDao;
        JdbcTransferDTODao transferDTODao;

        @Autowired
        public TransferDTOController(JdbcUserDao userDao, JdbcTransferDao transferDao, JdbcAccountDao accountDao, JdbcTransferDTODao transferDTODao) {
            this.userDao = userDao;
            this.transferDao = transferDao;
            this.accountDao = accountDao;
            this.transferDTODao = transferDTODao;
        }

        @RequestMapping(path = "", method = RequestMethod.GET)
        public List<TransferDTO> listTransfersByAccount(Principal principal) {
            String username = principal.getName();

            int userId = userDao.findIdByUsername(username);
            Account account = accountDao.findById(Long.valueOf(userId));

            Long accountId = account.getAccountId();
            List<TransferDTO> listOfTransfers = transferDTODao.findAllTransferDTOByAccountID(Math.toIntExact(accountId));

            return listOfTransfers;
        }
}
