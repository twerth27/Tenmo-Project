package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDTO;

import java.util.List;

public interface TransferDTODao {

    List<TransferDTO> findAllTransferDTOByAccountID(int id);
}
