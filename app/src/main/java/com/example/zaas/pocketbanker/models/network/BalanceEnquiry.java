package com.example.zaas.pocketbanker.models.network;

/**
 * Created by zaraahmed on 3/18/16.
 */
public class BalanceEnquiry
{

    private String balance;
    private String accountno;
    private String accounttype;
    private String balancetime;

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public String getAccountno()
    {
        return accountno;
    }

    public void setAccountno(String accountno)
    {
        this.accountno = accountno;
    }

    public String getAccounttype()
    {
        return accounttype;
    }

    public void setAccounttype(String accounttype)
    {
        this.accounttype = accounttype;
    }

    public String getBalancetime()
    {
        return balancetime;
    }

    public void setBalancetime(String balancetime)
    {
        this.balancetime = balancetime;
    }

    @Override
    public String toString()
    {
        return "BalanceEnquiry{" + ", balance='" + balance + '\'' + ", accountno='" + accountno + '\''
                + ", accounttype='" + accounttype + '\'' + ", balancetime='" + balancetime + '\'' + '}';
    }
}
