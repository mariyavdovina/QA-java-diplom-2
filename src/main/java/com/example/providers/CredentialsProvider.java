package com.example.providers;

import com.example.models.Credentials;
import io.qameta.allure.Step;

public class CredentialsProvider {
    @Step("Credentials of pre-defined user on server side")
    public static Credentials getDefault() {
        return new Credentials("loginDefault@domain.com", "1234");//user with given credentials already exists on server
    }

    @Step("Creds with invalid password")
    public static Credentials getWithInvalidPassword() {
        return new Credentials("loginDefault@domain.com", "");
    }

    @Step("Creds with invalid login")
    public static Credentials getWithInvalidLogin() {
        return new Credentials("", "1234");
    }

    @Step("Empty creds")
    public static Credentials getWithEmptyCreds() {
        return new Credentials();
    }
}
