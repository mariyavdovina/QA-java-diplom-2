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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class UserModifyTest {

    private User newUser;
    private int statusCode;
    private String accessToken;
    private static String token;
    private boolean isSuccess;
    private String message;
    @Before
    public void setUp() {
    }
    @After
    public void cleanUp() throws InterruptedException {
        new UserClient().delete(token);
        Thread.sleep(300);
    }

    public UserModifyTest(String accessToken, User newUser,int statusCode,boolean isSuccess, String message){
        this.newUser = newUser;
        this.accessToken = accessToken;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;

    }
    @Parameterized.Parameters
    public static Object[][] getTestData(){
        UserClient userClient = new UserClient();
        //User user = UserProvider.getDefault();
        User user = UserProvider.getRandom();
        String login = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();
        ValidatableResponse responseCreate = userClient.create(user);
        responseCreate.assertThat().statusCode(SC_OK);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        responseLogin.assertThat().statusCode(SC_OK);
        token = responseLogin.extract().path("accessToken").toString().substring(6).trim();

        return new Object[][]{
                {token, new User(user.getEmail()+"1",user.getPassword()+"1", user.getName()+"1" ),SC_OK,true,null},
                {token, new User(login,password+"1", name+"1" ),SC_FORBIDDEN,false,"User with such email already exists"},
                {"token", new User(user.getEmail()+"1",user.getPassword()+"1", user.getName()+"1" ),SC_FORBIDDEN,false,"jwt malformed"}
           };
    }

    @Test
    public void userCanBeModified(){
        ValidatableResponse responseModify = new UserClient().authorizedModify(accessToken,newUser);
        Assert.assertEquals(statusCode, responseModify.extract().statusCode());
        Assert.assertEquals(isSuccess, responseModify.extract().path("success"));
        new UserClient().delete(accessToken);

    }
}
