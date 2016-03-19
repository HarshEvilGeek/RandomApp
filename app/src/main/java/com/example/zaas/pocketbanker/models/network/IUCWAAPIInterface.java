package com.example.zaas.pocketbanker.models.network;

import retrofit.http.GET;
import retrofit.http.Path;

import com.example.zaas.pocketbanker.models.local.AuthToken;
import com.example.zaas.pocketbanker.models.local.BalanceEnquiry;

/**
 * Created by zaraahmed on 3/18/16.
 */
public interface IUCWAAPIInterface
{

    @GET ("/{tokenUrl}")
    AuthToken getToken(@Path ("tokenUrl") String tokenUrl);

    @GET ("/{balanceEnquiryUrl}")
    BalanceEnquiry getBalanceEnquiry(@Path (value = "balanceEnquiryUrl", encode = false) String balanceEnquiryUrl);


}
