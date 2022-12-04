package com.example;

import com.example.clients.UserClient;
import com.example.models.Credentials;
import com.example.models.User;
import com.example.providers.CredentialsProvider;
import com.example.providers.UserProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;


public class UserLoginTest {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private Credentials credentials;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserProvider.getDefault();
        credentials = CredentialsProvider.getDefault();
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }

    @Test
    public void userCanLogin(){
        ValidatableResponse responseLogin = userClient.login(credentials);
        int statusCode = responseLogin.extract().statusCode();
        accessToken = responseLogin.extract().path("accessToken").toString().substring(6).trim();
        Assert.assertEquals(SC_OK, statusCode);
    }

}