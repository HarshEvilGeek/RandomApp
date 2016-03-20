package com.example.zaas.pocketbanker.models.local;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class AccountSummaryDetailItem
{

    //header/item
    private int itemType;
    private AccountSummaryDetailHeaderUIITem headerUIITem;
    private TransactionDataUIItem transactionDataUIItem;

    public AccountSummaryDetailItem(int itemType, AccountSummaryDetailHeaderUIITem headerUIITem,
            TransactionDataUIItem transactionDataUIItem)
    {
        this.itemType = itemType;
        this.headerUIITem = headerUIITem;
        this.transactionDataUIItem = transactionDataUIItem;
    }

    public int getItemType()
    {
        return itemType;
    }

    public void setItemType(int itemType)
    {
        this.itemType = itemType;
    }

    public AccountSummaryDetailHeaderUIITem getHeaderUIITem()
    {
        return headerUIITem;
    }

    public void setHeaderUIITem(AccountSummaryDetailHeaderUIITem headerUIITem)
    {
        this.headerUIITem = headerUIITem;
    }

    public TransactionDataUIItem getTransactionDataUIItem()
    {
        return transactionDataUIItem;
    }

    public void setTransactionDataUIItem(TransactionDataUIItem transactionDataUIItem)
    {
        this.transactionDataUIItem = transactionDataUIItem;
    }
}
