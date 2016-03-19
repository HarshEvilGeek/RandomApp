package com.example.zaas.pocketbanker.sync;

import retrofit.RequestInterceptor;

/**
 * Created by zaraahmed on 3/18/16.
 */
public class UCWARequestInterceptor implements RequestInterceptor {

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT_TYPE = "Accept";
    public static final String HEADER_VALUE_APPLICATION_JSON = "application/json";

    private String contentType = HEADER_VALUE_APPLICATION_JSON;

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    @Override
    public void intercept(RequestFacade request)
    {
        request.addHeader(HEADER_CONTENT_TYPE, contentType);
        request.addHeader(HEADER_ACCEPT_TYPE, HEADER_VALUE_APPLICATION_JSON);
    }
}
