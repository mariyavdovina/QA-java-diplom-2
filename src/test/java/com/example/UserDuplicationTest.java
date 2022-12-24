package com.example;

import com.example.clients.UserClient;
import com.example.models.User;
import com.example.providers.UserProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;

public class UserDuplicationTest {
    private UserClient userClient;
    private static UserClient userClientPredefined;
    private static User userPredefined;
    private static String preToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userClientPredefined = new UserClient();

    }
    @After
    public void tearDown() throws InterruptedException {
        userClientPredefined.delete(preToken);
        Thread.sleep(300); //In order to avoid 429 Error (too many requests)
    }
    @Test
    public void userCanNotBeDuplicated(){
        userPredefined = UserProvider.getRandom();
        ValidatableResponse responsePredefined = userClientPredefined.create(userPredefined);
        preToken = responsePredefined.extract().path("accessToken").toString().substring(6).trim();
        ValidatableResponse responseDuplicate = userClient.create(userPredefined);
        assertEquals(SC_FORBIDDEN, responseDuplicate.extract().statusCode());
    }
}
