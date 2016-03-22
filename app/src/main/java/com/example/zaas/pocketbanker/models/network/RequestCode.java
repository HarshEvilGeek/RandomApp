package com.example.zaas.pocketbanker.models.network;

/**
 * Created by zaraahmed on 3/21/16.
 */
public class RequestCode
{

    private String code;

    private String description;

    private String message;

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public String toString()
    {
        return "RequestCode{" + "code='" + code + '\'' + ", description='" + description + '\'' + ", message='"
                + message + '\'' + '}';
    }
}
