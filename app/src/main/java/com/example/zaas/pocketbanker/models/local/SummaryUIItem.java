package com.example.zaas.pocketbanker.models.local;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class SummaryUIItem
{

    private int itemType;
    private String title;
    private long balance;

    public SummaryUIItem(int itemType, String title, long balance)
    {
        this.itemType = itemType;
        this.title = title;
        this.balance = balance;
    }

    public int getItemType()
    {
        return itemType;
    }

    public void setItemType(int itemType)
    {
        this.itemType = itemType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public long getBalance()
    {
        return balance;
    }

    public void setBalance(long balance)
    {
        this.balance = balance;
    }
}
