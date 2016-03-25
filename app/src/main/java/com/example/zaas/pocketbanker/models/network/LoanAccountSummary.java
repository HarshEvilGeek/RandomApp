package com.example.zaas.pocketbanker.models.network;

import java.util.Arrays;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class LoanAccountSummary
{

    private String errorCode;

    private String errorDescripttion;

    private LoanDetails[] loanDetails;

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorDescripttion()
    {
        return errorDescripttion;
    }

    public void setErrorDescripttion(String errorDescripttion)
    {
        this.errorDescripttion = errorDescripttion;
    }

    public LoanDetails[] getLoanDetails()
    {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails[] loanDetails)
    {
        this.loanDetails = loanDetails;
    }

    @Override
    public String toString()
    {
        return "LoanAccountSummary{" + "errorCode='" + errorCode + '\'' + ", errorDescripttion='" + errorDescripttion
                + '\'' + ", loanDetails=" + Arrays.toString(loanDetails) + '}';
    }
}
