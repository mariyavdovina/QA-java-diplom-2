package com.example;

import com.example.clients.OrderClient;
import com.example.models.Order;
import com.example.providers.OrderProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderAuthTest {
    private Order order;
    private int statusCode;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    public OrderAuthTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        //List.of("")
        return new Object[][]{
                {OrderProvider.getDefault(), SC_OK},
                {OrderProvider.getEmpty(), SC_BAD_REQUEST},
                {OrderProvider.getWithInvalidHash(), SC_INTERNAL_SERVER_ERROR}
        };
    }

    @Test
    public void orderCanBeCreatedAuth() {
        ValidatableResponse responseCreateAuth = orderClient.createWithAuth(order);
        int actualCodeAuth = responseCreateAuth.extract().statusCode();
        assertEquals(statusCode,actualCodeAuth);
    }
}