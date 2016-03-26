package com.example.zaas.pocketbanker.models.local;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class AccountSummaryDetailHeaderUIITem
{

    // Bank account, loan account, card
    private String headerType;

    private String accountNo;

    private double balance;

    // savings etc
    private String accountType;

    private String accountHolderName;

    // for card and loan
    private double accountDebt;

    private double rateOfInterest;

    // credit card
    private double availableLimit;

    private String status;

    private String delinquency;

    // expiry for card, max date for loan, current for bank account
    private long time;

    public void setHeaderDataForBankAccount(String accountNumber, double balance, String accountType, long time, String headerType)
    {
        this.accountNo = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.time = time;
        this.headerType = headerType;
    }

    public void setHeaderDataForLoanAccount(String accountNumber, String customerName, double outstandingBalance,
            String accountType, long time, double roi, String headerType, String delinquency)
    {
        this.accountNo = accountNumber;
        this.accountHolderName = customerName;
        this.accountDebt = outstandingBalance;
        this.accountType = accountType;
        this.time = time;
        this.rateOfInterest = roi;
        this.headerType = headerType;
        this.delinquency = delinquency;
    }

    public void setHeaderDataForCard(String cardNumber, double debtAmount, double availableLimit, long expiryDate,
            String accountType, String status, String headerType)
    {
        this.accountNo = cardNumber;
        this.accountDebt = debtAmount;
        this.availableLimit = availableLimit;
        this.time = expiryDate;
        this.accountType = accountType;
        this.status = status;
        this.headerType = headerType;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getAccountHolderName()
    {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName)
    {
        this.accountHolderName = accountHolderName;
    }

    public double getAccountDebt()
    {
        return accountDebt;
    }

    public void setAccountDebt(double accountDebt)
    {
        this.accountDebt = accountDebt;
    }

    public double getRateOfInterest()
    {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest)
    {
        this.rateOfInterest = rateOfInterest;
    }

    public double getAvailableLimit()
    {
        return availableLimit;
    }

    public void setAvailableLimit(double availableLimit)
    {
        this.availableLimit = availableLimit;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getHeaderType()
    {
        return headerType;
    }

    public void setHeaderType(String headerType)
    {
        this.headerType = headerType;
    }

    public String getDelinquency() {
        return delinquency;
    }

    public void setDelinquency(String delinquency) {
        this.delinquency = delinquency;
    }
}
