package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    JdbcTemplate jdbctemplate;

    public JdbcAccountDao(DataSource ds) {
        this.jdbctemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Account> list()
    {
        List<Account> accountList = new ArrayList<>();

        String sql = "SELECT account_id" +
            ", user_id" +
            ", balance" +
        "FROM account;";

        SqlRowSet row = jdbctemplate.queryForRowSet(sql);

        while (row.next())
        {
            Account account = mapRowToAccount(row);
            accountList.add(account);
        }

        return accountList;
    }

    @Override
    public Account findById(Long userId) {

        Account account = null;

        String sql = "SELECT account_id" +
                ", user_id" +
                ", balance" +
                " FROM account" +
                " WHERE user_id = ?;";

        SqlRowSet row = jdbctemplate.queryForRowSet(sql, userId);

        if (row.next())
        {
            account = mapRowToAccount(row);
        }

        return account;

    }

    @Override
    public Account findByAccountId(Long accountId) {

        Account account = null;


        String sql = "SELECT account_id" +
                ", user_id" +
                ", balance" +
                " FROM account" +
                " WHERE account_id = ?;";

        SqlRowSet row = jdbctemplate.queryForRowSet(sql, accountId);

        if (row.next())
        {
            account = mapRowToAccount(row);
        }

        return account;

    }

    @Override
    public Account findByUsername(String userName) {

        Account account = null;

        String sql = "SELECT account_id" +
                ", a.user_id" +
                ", balance" +
                " FROM account AS a" +
                " INNER JOIN tenmo_user AS tu" +
                " ON a.user_id = tu.user_id" +
                " WHERE tu.username = ?;";

        SqlRowSet row = jdbctemplate.queryForRowSet(sql, userName);

        if (row.next())
        {
            account = mapRowToAccount(row);
        }

        return account;

    }


    private Account mapRowToAccount(SqlRowSet row)
    {
        Account account = new Account(row.getLong("user_id"), row.getLong("account_id"), row.getBigDecimal("balance") );

        return account;
    }
}
