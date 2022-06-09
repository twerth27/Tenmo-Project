package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account
{
    private Long userId;
    private Long accountId;
    private BigDecimal balance;

    public Account(Long userId, Long accountId, BigDecimal balance) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getBalanceByAccountId(Long accountId)
    {

        return balance;

    }

}
