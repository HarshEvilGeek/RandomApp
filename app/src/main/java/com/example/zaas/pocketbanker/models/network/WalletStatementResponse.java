package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletStatementResponse
{

    @SerializedName ("bank_txn_id")
    private long bankTransactionId;

    private WalletStatement[] walletStatement;

    public long getBankTransactionId()
    {
        return bankTransactionId;
    }

    public void setBankTransactionId(long bankTransactionId)
    {
        this.bankTransactionId = bankTransactionId;
    }

    public WalletStatement[] getWalletStatement()
    {
        return walletStatement;
    }

    public void setWalletStatement(WalletStatement[] walletStatement)
    {
        this.walletStatement = walletStatement;
    }

    @Override
    public String toString()
    {
        return "WalletStatementResponse{" + "bankTransactionId=" + bankTransactionId + ", walletStatement="
                + Arrays.toString(walletStatement) + '}';
    }
}
