package com.example;

import com.example.clients.UserClient;
import com.example.models.Credentials;
import com.example.models.User;
import com.example.providers.CredentialsProvider;
import com.example.providers.UserProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserCreationNegativeTest {
    private User user;
    private String message;
    private int statusCode;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300); //In order to avoid 429 Error (too many requests)
    }
    public UserCreationNegativeTest(User user, int statusCode, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.message = message;
    }
    //test data
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        //List.of("")
        return new Object[][]{
                {UserProvider.getWithoutEmail(), SC_FORBIDDEN, "Email, password and name are required fields"},
                {UserProvider.getWithoutPassword(), SC_FORBIDDEN, "Email, password and name are required fields"},
                {UserProvider.getWithoutName(), SC_FORBIDDEN, "Email, password and name are required fields"},
                {UserProvider.getEmpty(), SC_FORBIDDEN, "Email, password and name are required fields"},
                //{new User(CredentialsProvider.getDefault().getEmail(), CredentialsProvider.getDefault().getPassword(), "Bob"), SC_FORBIDDEN, "User already exists"}
        };
    }

    @Test
    public void userCanBeCreated(){
        ValidatableResponse responseCreate = userClient.create(user);
        String actualMessage = responseCreate.extract().path("message").toString();
        int actualStatusCode = responseCreate.extract().statusCode();
        assertEquals(statusCode, actualStatusCode);
        assertEquals(message, actualMessage);
        if (actualStatusCode == SC_OK){
            accessToken = responseCreate.extract().path("accessToken").toString().substring(6).trim();
            userClient.delete(accessToken);
        }
    }
}
