package usercreation;

import com.example.clients.UserClient;
import com.example.models.Credentials;
import com.example.models.User;
import com.example.providers.CredentialsProvider;
import com.example.providers.UserProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class UserCreationTest {
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

    @Test
    public void userCanBeCreated(){
        ValidatableResponse responseCreate = userClient.create(user);
        responseCreate.assertThat().statusCode(SC_OK);
        accessToken = responseCreate.extract().path("accessToken").toString().substring(6).trim();
        userClient.delete(accessToken);
    }
}
