package orders;

import com.example.clients.OrderClient;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OrderListTest {
    private OrderClient orderClient;
    @Before
    public void setUp()  {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Checking that list of orders returns for authorized user")
    public void getListOfOrdersAuth(){
        ValidatableResponse responseCreateAuth = orderClient.listOfOrdersAuth();
        responseCreateAuth.assertThat().statusCode(SC_OK);
        assertNotNull(responseCreateAuth.extract().path("orders"));
    }
    @Test
    @Description("Checking that list of orders returns without being authorized")
    public void getListOfOrders(){
        ValidatableResponse responseCreateAuth = orderClient.listOfOrdersNonAuth();
        responseCreateAuth.assertThat().statusCode(SC_UNAUTHORIZED);
        assertNull(responseCreateAuth.extract().path("orders"));
    }

}