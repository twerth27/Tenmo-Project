package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer
{
    private BigDecimal transferAmount;

    private int recipient;

    private int sender;

    private int transferId;

    private int transferTypeId;

    private int transferStatusId;


    public Transfer(BigDecimal transferAmount, int recipient, int sender, int transferId, int transferTypeId, int transferStatusId) {
        this.transferAmount = transferAmount;
        this.recipient = recipient;
        this.sender = sender;
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferAmount=" + transferAmount +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", transferId=" + transferId +
                ", transferTypeId=" + transferTypeId +
                ", transferStatusId=" + transferStatusId +
                '}';
    }
}
