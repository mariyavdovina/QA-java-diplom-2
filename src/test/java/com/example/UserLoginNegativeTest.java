package com.example;

import com.example.clients.UserClient;
import com.example.models.Credentials;
import com.example.providers.CredentialsProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserLoginNegativeTest {
    private Credentials credentials;
    private String message;
    private int statusCode;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
    public UserLoginNegativeTest(Credentials credentials, int statusCode, String message) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {CredentialsProvider.getWithInvalidPassword(), SC_UNAUTHORIZED, "email or password are incorrect"},
                //{CredentialsProvider.getWithInvalidLogin(), SC_UNAUTHORIZED, "email or password are incorrect"},
                {CredentialsProvider.getWithEmptyCreds(), SC_UNAUTHORIZED, "email or password are incorrect"},
        };
    }

    @Test
    public void userCanLogin(){
        ValidatableResponse responseLogin = userClient.login(credentials);
        String actualMessage = responseLogin.extract().path("message").toString();
        int actualStatusCode = responseLogin.extract().statusCode();
        assertEquals(statusCode, actualStatusCode);
        assertEquals(message, actualMessage);
    }
}
