package com.example.providers;

import com.example.models.Credentials;
import com.example.models.User;
import io.qameta.allure.Step;

import java.util.Random;

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
    @Step("Credentials for random user")
    public static Credentials getRandom() {
        String loginSymbols = "abcdefghijklmnopqrstuvwxyz";
        String passwordSymbols = "abcdefghijklmnopqrstuvwxyz012356789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            sb.append(loginSymbols.charAt(random.nextInt(loginSymbols.length()-1)));
        }
        String login = sb.toString()+"@"+"domain.com";
        for (int i = 0; i < passwordSymbols.length(); i++) {
            sb1.append(passwordSymbols.charAt(random.nextInt(passwordSymbols.length()-1)));
        }
        String pwd = sb1.toString();
        return new Credentials(login,pwd);
        }
}
