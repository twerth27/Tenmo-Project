package com.techelevator.tenmo.model.dto;

import java.math.BigDecimal;

public class TransferDTO {
    private int transferId;
    private String username;
    private String transferTypeDesc;
    private BigDecimal transferAmount;
    private int accountTo;
    private int accountFrom;
    private String whoGaveIt;


    public TransferDTO() {
    }

    public TransferDTO(int transferId, String username, String transferTypeDesc, BigDecimal transferAmount, int accountTo, int accountFrom, String whoGaveIt) {
        this.transferId = transferId;
        this.username = username;
        this.transferTypeDesc = transferTypeDesc;
        this.transferAmount = transferAmount;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
        this.whoGaveIt = whoGaveIt;
    }

    public String getWhoGaveIt() {
        return whoGaveIt;
    }

    public void setWhoGaveIt(String whoGaveIt) {
        this.whoGaveIt = whoGaveIt;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "transferId=" + transferId +
                ", username='" + username + '\'' +
                ", transferTypeDesc='" + transferTypeDesc + '\'' +
                ", transferAmount=" + transferAmount +
                ", accountTo=" + accountTo +
                ", accountFrom=" + accountFrom +
                '}';
    }
}
