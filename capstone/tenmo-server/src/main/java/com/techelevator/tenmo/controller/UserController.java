package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("users")
//@PreAuthorize("isAuthenticated()")
public class UserController {

    UserDao userDao;

    @Autowired

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public User getUserByUsername(Principal principal)
    {
        String username = principal.getName();

        return userDao.findByUsername(username);
    }

}
