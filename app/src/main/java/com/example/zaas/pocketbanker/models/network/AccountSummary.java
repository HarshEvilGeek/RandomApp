package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class AccountSummary
{

    private String accountno;
    private double balance;

    @SerializedName ("product_desc")
    private String productdesc;

    @SerializedName ("sub_product_type")
    private String subproducttype;

    @SerializedName ("product_type")
    private String producttype;

    private String custid;
    private String accounttype;

    @SerializedName ("account_status")
    private String accountstatus;

    @SerializedName ("mobile_no")
    private String mobileno;

    @SerializedName ("product_category")
    private String productcategory;

    public String getAccountno()
    {
        return accountno;
    }

    public void setAccountno(String accountno)
    {
        this.accountno = accountno;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getProductdesc()
    {
        return productdesc;
    }

    public void setProductdesc(String productdesc)
    {
        this.productdesc = productdesc;
    }

    public String getSubproducttype()
    {
        return subproducttype;
    }

    public void setSubproducttype(String subproducttype)
    {
        this.subproducttype = subproducttype;
    }

    public String getProducttype()
    {
        return producttype;
    }

    public void setProducttype(String producttype)
    {
        this.producttype = producttype;
    }

    public String getCustid()
    {
        return custid;
    }

    public void setCustid(String custid)
    {
        this.custid = custid;
    }

    public String getAccounttype()
    {
        return accounttype;
    }

    public void setAccounttype(String accounttype)
    {
        this.accounttype = accounttype;
    }

    public String getAccountstatus()
    {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus)
    {
        this.accountstatus = accountstatus;
    }

    public String getMobileno()
    {
        return mobileno;
    }

    public void setMobileno(String mobileno)
    {
        this.mobileno = mobileno;
    }

    public String getProductcategory()
    {
        return productcategory;
    }

    public void setProductcategory(String productcategory)
    {
        this.productcategory = productcategory;
    }

    @Override
    public String toString()
    {
        return "AccountSummary{" + "accountno='" + accountno + '\'' + ", balance=" + balance + ", productdesc='"
                + productdesc + '\'' + ", subproducttype='" + subproducttype + '\'' + ", producttype='" + producttype
                + '\'' + ", custid='" + custid + '\'' + ", accounttype='" + accounttype + '\'' + ", accountstatus='"
                + accountstatus + '\'' + ", mobileno='" + mobileno + '\'' + ", productcategory='" + productcategory
                + '\'' + '}';
    }
}
