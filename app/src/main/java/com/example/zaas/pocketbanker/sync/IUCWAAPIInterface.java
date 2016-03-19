package com.example.zaas.pocketbanker.sync;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.example.zaas.pocketbanker.models.network.AccountSummary;
import com.example.zaas.pocketbanker.models.network.AuthToken;
import com.example.zaas.pocketbanker.models.network.BalanceEnquiry;
import com.example.zaas.pocketbanker.models.network.BehaviorScore;
import com.example.zaas.pocketbanker.models.network.BranchAtmLocations;
import com.example.zaas.pocketbanker.models.network.CardAccDetailsResponse;
import com.example.zaas.pocketbanker.models.network.FundTransfer;
import com.example.zaas.pocketbanker.models.network.LoanAccountSummary;
import com.example.zaas.pocketbanker.models.network.LoanEMIDetails;
import com.example.zaas.pocketbanker.models.network.LoanTransactionDetails;
import com.example.zaas.pocketbanker.models.network.RegisteredPayees;
import com.example.zaas.pocketbanker.models.network.Transactions;

/**
 * Created by zaraahmed on 3/18/16.
 */
public interface IUCWAAPIInterface
{

    @GET ("/{tokenUrl}")
    AuthToken getToken(@Path ("tokenUrl") String tokenUrl);

    @GET ("/{balanceEnquiryUrl}")
    BalanceEnquiry getBalanceEnquiry(@Path (value = "balanceEnquiryUrl", encode = false) String balanceEnquiryUrl);

    @GET ("/{accountSummaryUrl}")
    AccountSummary getAccountSummary(@Path (value = "accountSummaryUrl", encode = false) String accountSummaryUrl);

    @GET ("/{transactionsUrl}")
    Transactions getTransactions(@Path (value = "transactionsUrl", encode = false) String transactionsUrl);

    @GET ("/{behaviorScoreUrl}")
    BehaviorScore getBehaviorScore(@Path (value = "behaviorScoreUrl", encode = false) String behaviorScoreUrl);

    @GET ("/{registeredPayeesUrl}")
    RegisteredPayees getRegisteredPayees(@Path (value = "registeredPayeesUrl", encode = false) String registeredPayeesUrl);

    @POST("/{makeTransactionUrl}")
    FundTransfer makeTransaction(@Path (value = "makeTransactionUrl", encode = false) String makeTransactionUrl);

    @GET ("/{branchAtmLocationUrl}")
    BranchAtmLocations getBranchAtmLocations(@Path (value = "branchAtmLocationUrl", encode = false) String branchAtmLocationUrl);

    @GET ("/{loanAccountSummaryUrl}")
    LoanAccountSummary getLoanAccountSummary(@Path (value = "loanAccountSummaryUrl", encode = false) String loanAccountSummaryUrl);

    @GET ("/{loanEmiDetailsUrl}")
    LoanEMIDetails getLoanEMIDetails(@Path (value = "loanEmiDetailsUrl", encode = false) String loanEmiDetailsUrl);

    @GET ("/{loanTransactionDetails}")
    LoanTransactionDetails getLoanTransactionDetails(@Path (value = "loanTransactionDetails", encode = false) String loanTransactionDetails);

    @GET ("/{cardDetailsUrl}")
    CardAccDetailsResponse getCardDetails(@Path (value = "cardDetailsUrl", encode = false) String cardDetailsUrl);

}
