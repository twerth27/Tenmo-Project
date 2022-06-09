package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDTODao implements TransferDTODao {

    JdbcTemplate jdbctemplate;

    public JdbcTransferDTODao(DataSource ds) {
        this.jdbctemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<TransferDTO> findAllTransferDTOByAccountID(int id) {

        List<TransferDTO> transfers = new ArrayList<>();

        String sql = "SELECT t.transfer_id\n" +
                "                , tenmo_user.username\n" +
                "                , tt.transfer_type_desc\n" +
                "                , t.amount\n" +
                "                , t.account_to\n" +
                "                , t.account_from\n" +
                "\t\t\t\t,'RECEIVED' AS who_gave_it\n" +
                "\t\t\t\tFROM tenmo_user\n" +
                "JOIN account AS acc\n" +
                "ON acc.user_id = tenmo_user.user_id\n" +
                "JOIN transfer AS t\n" +
                "ON t.account_from = acc.account_id\n" +
                "JOIN transfer_type AS tt\n" +
                "\tON t.transfer_type_id = tt.transfer_type_id\n" +
                "WHERE t.account_to = ?\n" +
                "\n" +
                "UNION\n" +
                "\n" +
                "SELECT t.transfer_id\n" +
                "                , tenmo_user.username\n" +
                "                , tt.transfer_type_desc\n" +
                "                , t.amount\n" +
                "                , t.account_to\n" +
                "                , t.account_from\n" +
                "\t\t\t\t,'SENT' AS who_gave_it FROM tenmo_user \n" +
                "JOIN account AS acc\n" +
                "ON acc.user_id = tenmo_user.user_id\n" +
                "JOIN transfer AS t\n" +
                "ON t.account_to = acc.account_id\n" +
                "JOIN transfer_type AS tt\n" +
                "\tON t.transfer_type_id = tt.transfer_type_id\n" +
                "WHERE t.account_from = ?\n" +
                "ORDER BY transfer_id;\n";

        SqlRowSet row = jdbctemplate.queryForRowSet(sql, id, id);

        while(row.next())
        {
            TransferDTO transfer = mapRowToTransferDTO(row);
            transfers.add(transfer);
        }

        return transfers;
    }

    public TransferDTO mapRowToTransferDTO(SqlRowSet row)
    {
        TransferDTO transfer = new TransferDTO(row.getInt("transfer_id"),
                row.getString("username"),
                row.getString("transfer_type_desc"),
                row.getBigDecimal("amount"),
                row.getInt("account_to"),
                row.getInt("account_from"),
                row.getString("who_gave_it"));
        return transfer;
    }



//    "SELECT t.transfer_id\n" +
//            "\t, tu.username\n" +
//            "\t, tt.transfer_type_desc\n" +
//            "\t, t.amount\n" +
//            "\t, t.account_to\n" +
//            "\t, t.account_from\n" +
//            "FROM transfer AS t\n" +
//            "INNER JOIN transfer_type AS tt\n" +
//            "\tON t.transfer_type_id = tt.transfer_type_id\n" +
//            "INNER JOIN account AS afrom\n" +
//            "\tON t.account_from = afrom.account_id\n" +
//            "INNER JOIN account AS ato\n" +
//            "\tON t.account_to = ato.account_id\n" +
//            "INNER JOIN tenmo_user AS tu\n" +
//            "\tON afrom.user_id = tu.user_id\n" +
//            "WHERE account_to = ? OR account_from = ?\n" +
//            ";"
}

