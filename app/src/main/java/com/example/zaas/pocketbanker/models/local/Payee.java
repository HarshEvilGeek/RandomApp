package com.example.zaas.pocketbanker.models.local;

/**
 * Model for Payee Created by adugar on 3/19/16.
 */
public class Payee
{
    private String payeeId;
    private String name;
    private String accountNo;
    private String shortName;
    private long creationDate;
    private String customerId;

    public Payee()
    {

    }

    public Payee(String payeeId, String name, String accountNo, String shortName, long creationDate, String customerId)
    {
        this.payeeId = payeeId;
        this.name = name;
        this.accountNo = accountNo;
        this.shortName = shortName;
        this.creationDate = creationDate;
        this.customerId = customerId;
    }

    public String getPayeeId()
    {
        return payeeId;
    }

    public void setPayeeId(String payeeId)
    {
        this.payeeId = payeeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(long creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
}