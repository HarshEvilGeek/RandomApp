package com.example.zaas.pocketbanker.models.network;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class RegisteredPayees
{

    private String creationdate;

    private String payeename;

    private String payeeaccountno;

    private String custid;

    private String payeeid;

    private String shortname;

    public String getCreationdate()
    {
        return creationdate;
    }

    public void setCreationdate(String creationdate)
    {
        this.creationdate = creationdate;
    }

    public String getPayeename()
    {
        return payeename;
    }

    public void setPayeename(String payeename)
    {
        this.payeename = payeename;
    }

    public String getPayeeaccountno()
    {
        return payeeaccountno;
    }

    public void setPayeeaccountno(String payeeaccountno)
    {
        this.payeeaccountno = payeeaccountno;
    }

    public String getCustid()
    {
        return custid;
    }

    public void setCustid(String custid)
    {
        this.custid = custid;
    }

    public String getPayeeid()
    {
        return payeeid;
    }

    public void setPayeeid(String payeeid)
    {
        this.payeeid = payeeid;
    }

    public String getShortname()
    {
        return shortname;
    }

    public void setShortname(String shortname)
    {
        this.shortname = shortname;
    }

    @Override
    public String toString()
    {
        return "RegisteredPayees{" + "creationdate='" + creationdate + '\'' + ", payeename='" + payeename + '\''
                + ", payeeaccountno='" + payeeaccountno + '\'' + ", custid='" + custid + '\'' + ", payeeid='" + payeeid
                + '\'' + ", shortname='" + shortname + '\'' + '}';
    }
}
