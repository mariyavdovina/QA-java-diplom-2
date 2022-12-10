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
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class UserModificationTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserProvider.getRandom();
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
    @Test
    public void modifyWithoutAuthNotPossible(){
        ValidatableResponse responseCreate = userClient.create(user);
        responseCreate.assertThat().statusCode(SC_OK);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        responseLogin.assertThat().statusCode(SC_OK);
        accessToken = responseLogin.extract().path("accessToken").toString().substring(6).trim();
        ValidatableResponse responseUnAuthorizedChange = userClient.unAuthorizedModify(new User(user.getEmail()+"1",user.getPassword()+"1", user.getName()+"1" ));
        boolean isSuccess = responseUnAuthorizedChange.extract().path("success").equals(true);
        Assert.assertEquals(SC_UNAUTHORIZED, responseUnAuthorizedChange.extract().statusCode());
        Assert.assertFalse(isSuccess);
        Assert.assertEquals(responseUnAuthorizedChange.extract().path("message"), "You should be authorised");
        userClient.delete(accessToken);
    }
}
