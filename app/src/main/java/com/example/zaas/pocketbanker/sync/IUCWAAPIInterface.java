package com.example.zaas.pocketbanker.sync;

import retrofit.http.GET;
import retrofit.http.Path;

import com.example.zaas.pocketbanker.models.network.AuthToken;
import com.example.zaas.pocketbanker.models.network.BalanceEnquiry;

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
