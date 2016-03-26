package com.example.zaas.pocketbanker.sync;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.example.zaas.pocketbanker.models.network.AccountSummary;
import com.example.zaas.pocketbanker.models.network.AuthToken;
import com.example.zaas.pocketbanker.models.network.BehaviorScore;
import com.example.zaas.pocketbanker.models.network.BranchAtmLocations;
import com.example.zaas.pocketbanker.models.network.CardAccDetailsResponse;
import com.example.zaas.pocketbanker.models.network.FundTransfer;
import com.example.zaas.pocketbanker.models.network.LoanAccountSummary;
import com.example.zaas.pocketbanker.models.network.LoanEMIDetails;
import com.example.zaas.pocketbanker.models.network.LoanTransactionDetails;
import com.example.zaas.pocketbanker.models.network.RegisteredPayees;
import com.example.zaas.pocketbanker.models.network.Transactions;
import com.example.zaas.pocketbanker.models.network.WalletBalanceBody;
import com.example.zaas.pocketbanker.models.network.WalletBalanceResponse;
import com.example.zaas.pocketbanker.models.network.WalletCreation;
import com.example.zaas.pocketbanker.models.network.WalletCreditDebitBody;
import com.example.zaas.pocketbanker.models.network.WalletCreditDebitResponse;
import com.example.zaas.pocketbanker.models.network.WalletStatementBody;
import com.example.zaas.pocketbanker.models.network.WalletStatementResponse;

/**
 * Created by zaraahmed on 3/18/16.
 */
public interface IUCWAAPIInterface
{

    @GET ("/{tokenUrl}")
    AuthToken getToken(@Path ("tokenUrl") String tokenUrl);

    @GET ("/{balanceEnquiryUrl}")
    Response getBalanceEnquiry(@Path (value = "balanceEnquiryUrl", encode = false) String balanceEnquiryUrl);

    @GET ("/{accountSummaryUrl}")
    Response getAccountSummary(@Path (value = "accountSummaryUrl", encode = false) String accountSummaryUrl);

    @GET ("/{transactionsUrl}")
    Response getTransactions(@Path (value = "transactionsUrl", encode = false) String transactionsUrl);

    @GET ("/{behaviorScoreUrl}")
    Response getBehaviorScore(@Path (value = "behaviorScoreUrl", encode = false) String behaviorScoreUrl);

    @GET ("/{registeredPayeesUrl}")
    Response getRegisteredPayees(@Path (value = "registeredPayeesUrl", encode = false) String registeredPayeesUrl);

    @GET("/{makeTransactionUrl}")
    Response makeTransaction(@Path (value = "makeTransactionUrl", encode = false) String makeTransactionUrl);

    @GET ("/{branchAtmLocationUrl}")
    Response getBranchAtmLocations(@Path (value = "branchAtmLocationUrl", encode = false) String branchAtmLocationUrl);

    @GET ("/{loanAccountSummaryUrl}")
    LoanAccountSummary getLoanAccountSummary(@Path (value = "loanAccountSummaryUrl", encode = false) String loanAccountSummaryUrl);

    @GET ("/{loanEmiDetailsUrl}")
    LoanEMIDetails getLoanEMIDetails(@Path (value = "loanEmiDetailsUrl", encode = false) String loanEmiDetailsUrl);

    @GET ("/{loanTransactionDetails}")
    LoanTransactionDetails getLoanTransactionDetails(@Path (value = "loanTransactionDetails", encode = false) String loanTransactionDetails);

    @GET ("/{cardDetailsUrl}")
    CardAccDetailsResponse getCardDetails(@Path (value = "cardDetailsUrl", encode = false) String cardDetailsUrl);

    @GET ("/{createWalletUrl}")
    WalletCreation createWallet(@Path (value = "createWalletUrl", encode = false) String createWalletUrl);

    @POST ("/{walletBalanceUrl}")
    WalletBalanceResponse getWalletBalance(@Path (value = "walletBalanceUrl", encode = false) String walletBalanceUrl, @Body WalletBalanceBody walletBalanceBody);

    @POST ("/{walletCreditUrl}")
    WalletCreditDebitResponse creditWallet(@Path (value = "walletCreditUrl", encode = false) String walletCreditUrl, @Body WalletCreditDebitBody walletCreditDebitBody);

    @POST ("/{walletDebitUrl}")
    WalletCreditDebitResponse debitWallet(@Path (value = "walletDebitUrl", encode = false) String walletDebitUrl, @Body WalletCreditDebitBody walletCreditDebitBody);

    @POST ("/{walletStatementUrl}")
    Response getWalletStatement(@Path (value = "walletStatementUrl", encode = false) String walletStatementUrl, @Body WalletStatementBody walletStatementBody);

}
