package com.example;

import com.example.clients.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class OrderListTest {
    private OrderClient orderClient;
    @Before
    public void setUp()  {
        orderClient = new OrderClient();
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
    @Test
    //Checking that list of orders returns for authorized user
    public void getListOfOrdersAuth(){
        ValidatableResponse responseCreateAuth = orderClient.listOfOrdersAuth();
        responseCreateAuth.assertThat().statusCode(SC_OK);
        assertTrue(responseCreateAuth.extract().path("success"));
        responseCreateAuth.assertThat().body("total", instanceOf(Integer.class));
    }
    @Test
    //Checking that list of orders returns without being authorized
    public void getListOfOrders(){
        ValidatableResponse responseCreateAuth = orderClient.listOfOrdersNonAuth();
        responseCreateAuth.assertThat().statusCode(SC_UNAUTHORIZED);
        assertNull(responseCreateAuth.extract().path("orders"));
    }
}