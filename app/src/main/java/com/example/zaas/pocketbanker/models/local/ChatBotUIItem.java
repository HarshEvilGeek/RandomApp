package com.example.zaas.pocketbanker.models.local;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class ChatBotUIItem
{

    private String type;
    private String data;
    private long time;

    public ChatBotUIItem(String type, String data, long time)
    {
        this.type = type;
        this.data = data;
        this.time = time;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
}
