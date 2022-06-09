package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao
{

    JdbcTemplate jdbctemplate;


    public JdbcTransferDao(DataSource ds) {
        this.jdbctemplate = new JdbcTemplate(ds);
    }
    @Override
    public List<Transfer> findTransferByAccountId(int id) {

        List<Transfer> transfers = new ArrayList<>();

        String sql = "SELECT transfer_id" +
                ", transfer_type_id" +
                ", transfer_status_id" +
                ", account_from" +
                ", account_to" +
                ", amount " +
                " FROM transfer" +
                " WHERE account_to = ? OR account_from = ? ;"
                ;

        SqlRowSet row = jdbctemplate.queryForRowSet(sql, id, id);

        while(row.next())
        {
          Transfer transfer = mapRowToTransfer(row);
          transfers.add(transfer);
        }

        return transfers;



    }
    @Transactional
    @Override
    public Transfer createTransfer(Transfer transfer) throws Exception {
        //System.out.println(transfer);
        String sql = " INSERT INTO transfer (transfer_type_id" +
                ", transfer_status_id" +
                ", account_from" +
                ", account_to" +
                ", amount)" +
                " VALUES(?, ? , ? , ? , ?);" +
                "" +
                " UPDATE account SET balance = balance - ?" +
                " WHERE account_id = ?;" +
                "" +
                " UPDATE account SET balance = balance + ?" +
                " WHERE account_id = ?;";

        JdbcAccountDao accountDao = new JdbcAccountDao(jdbctemplate.getDataSource());
        Account account = accountDao.findByAccountId((long) transfer.getSender());
        BigDecimal senderBalance = account.getBalance();

        if (senderBalance.subtract(transfer.getTransferAmount()).doubleValue() < 0) {
            throw new Exception("Insufficient Funds");
        }

        if (transfer.getTransferAmount().doubleValue() <= 0) {
            throw new Exception("Can't send negative or zero bucks");
        }

        jdbctemplate.update(sql, transfer.getTransferTypeId(),
                transfer.getTransferStatusId(),
                transfer.getSender(),
                transfer.getRecipient(),
                transfer.getTransferAmount(),
                transfer.getTransferAmount(),
                transfer.getSender(),
                transfer.getTransferAmount(),
                transfer.getRecipient());

        return checkLastTransfer();
    }

//        String sqlRemove = "UPDATE account SET balance = balance - ?\n" +
//                            " WHERE account_id = ?;";
//
//
//        jdbctemplate.update(sqlRemove, transfer.getTransferAmount(), transfer.getSender());
//
//        String sqlAdd = "UPDATE account SET balance = balance + ?\n" +
//                        " WHERE account_id = ?;";
//
//        jdbctemplate.update(sqlAdd, transfer.getTransferAmount(), transfer.getRecipient());



    public Transfer checkLastTransfer()
    {
        Transfer transfer = null;
        String sql = "SELECT transfer_id\n" +
                "\t, transfer_type_id\n" +
                "\t, transfer_status_id\n" +
                "\t, account_from \n" +
                "\t, account_to\n" +
                "\t, amount\n" +
                "FROM transfer\n" +
                "ORDER BY transfer_id DESC \n" +
                "LIMIT 1;\n ";

        SqlRowSet row = jdbctemplate.queryForRowSet(sql);

        while(row.next())
        {
          transfer = mapRowToTransfer(row);

        }
        System.out.println(transfer);
        return transfer;
    }


    public Transfer mapRowToTransfer(SqlRowSet row)
    {
        Transfer transfer = new Transfer(row.getBigDecimal("amount"),row.getInt("account_to")
                                                        , row.getInt("account_from")
                                                        , row.getInt("transfer_id")
                                                        , row.getInt("transfer_type_id")
                                                        , row.getInt("transfer_status_id"));
        return transfer;
    }


}
