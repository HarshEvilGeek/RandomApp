package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class LoanDetails
{

    @SerializedName ("ID")
    private long id;

    @SerializedName ("loanAccounNo")
    private String loanAccNum;

    private String customerName;

    private String pos;

    @SerializedName ("principal_outstanding")
    private double principleOutstanding;

    @SerializedName ("date_of_loan")
    private String dateOfLoan;

    @SerializedName ("type_of_loan")
    private String typeOfLoan;

    private double roi;

    @SerializedName ("month_delinquency")
    private String monthDelinquency;

    private double loanAmount;

    private String custId;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getLoanAccNum()
    {
        return loanAccNum;
    }

    public void setLoanAccNum(String loanAccNum)
    {
        this.loanAccNum = loanAccNum;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getPos()
    {
        return pos;
    }

    public void setPos(String pos)
    {
        this.pos = pos;
    }

    public double getPrincipleOutstanding()
    {
        return principleOutstanding;
    }

    public void setPrincipleOutstanding(double principleOutstanding)
    {
        this.principleOutstanding = principleOutstanding;
    }

    public String getDateOfLoan()
    {
        return dateOfLoan;
    }

    public void setDateOfLoan(String dateOfLoan)
    {
        this.dateOfLoan = dateOfLoan;
    }

    public String getTypeOfLoan()
    {
        return typeOfLoan;
    }

    public void setTypeOfLoan(String typeOfLoan)
    {
        this.typeOfLoan = typeOfLoan;
    }

    public double getRoi()
    {
        return roi;
    }

    public void setRoi(double roi)
    {
        this.roi = roi;
    }

    public String getMonthDelinquency()
    {
        return monthDelinquency;
    }

    public void setMonthDelinquency(String monthDelinquency)
    {
        this.monthDelinquency = monthDelinquency;
    }

    public double getLoanAmount()
    {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount)
    {
        this.loanAmount = loanAmount;
    }

    public String getCustId()
    {
        return custId;
    }

    public void setCustId(String custId)
    {
        this.custId = custId;
    }

    @Override
    public String toString()
    {
        return "LoanDetails{" + "id=" + id + ", loanAccNum='" + loanAccNum + '\'' + ", customerName='" + customerName
                + '\'' + ", pos='" + pos + '\'' + ", principleOutstanding=" + principleOutstanding + ", dateOfLoan='"
                + dateOfLoan + '\'' + ", typeOfLoan='" + typeOfLoan + '\'' + ", roi=" + roi + ", monthDelinquency='"
                + monthDelinquency + '\'' + ", loanAmount=" + loanAmount + ", custId='" + custId + '\'' + '}';
    }
}
