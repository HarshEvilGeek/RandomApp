package com.example.zaas.pocketbanker.models.local;

/**
 * Created by akhil on 3/26/16.
 */
public class PocketTransaction {

    String bankTxnId;
    String txnId;
    double amount;
    long transDate;
    String tranType;
    String remarks;
    String walletAcNo;

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTransDate() {
        return transDate;
    }

    public void setTransDate(long transDate) {
        this.transDate = transDate;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWalletAcNo() {
        return walletAcNo;
    }

    public void setWalletAcNo(String walletAcNo) {
        this.walletAcNo = walletAcNo;
    }
}
