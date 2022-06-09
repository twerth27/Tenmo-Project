package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.util.List;

public interface TransferDao
{
    Transfer createTransfer(Transfer transfer) throws Exception;

    List<Transfer> findTransferByAccountId(int id);

}
