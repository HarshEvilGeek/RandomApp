package com.example.zaas.pocketbanker.models.local;

/**
 * Created by akhil on 3/25/16.
 */
public class PocketAccount {

    String authToken;
    String firstName;
    String lastName;
    String emailAddress;
    String phoneNumber;
    String merchantId;
    String scope;
    Gender gender;
    long birthday;
    double balance;

    public PocketAccount()
    {

    }

    public PocketAccount(String firstName, String lastName, String email, long dob, String phNo, String merchantId,
            Gender gender, String authToken, String scope)
    {
        this.authToken = authToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = email;
        this.phoneNumber = phNo;
        this.merchantId = merchantId;
        this.scope = scope;
        this.gender = gender;
        this.birthday = dob;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public enum Gender {
        MALE, FEMALE;
    }
}
