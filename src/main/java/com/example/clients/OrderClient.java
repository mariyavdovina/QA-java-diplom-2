package com.example.clients;

import com.example.models.Order;
import com.example.providers.CredentialsProvider;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH = "api/orders/";
    private UserClient userClient = new UserClient();
    private ValidatableResponse responseLogin = userClient.login(CredentialsProvider.getDefault());
    private String accessToken = responseLogin.extract().path("accessToken").toString().substring(6).trim();

    @Step("Order creation without token")
    public ValidatableResponse createWithoutAuth(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();

    }
    @Step("Order creation with token")
    public ValidatableResponse createWithAuth(Order order) {
        return given()
                .header("authorization", "bearer " + accessToken)
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("List of orders without token")
    public ValidatableResponse listOfOrdersNonAuth() {
        return given()
                .spec(getSpec())
                .body("")
                .when()
                .get(PATH)
                .then();
    }
    @Step("List of orders with token")
    public ValidatableResponse listOfOrdersAuth() {
        return given()
                .header("authorization", "bearer " + accessToken)
                .spec(getSpec())
                .body("")
                .when()
                .get(PATH)
                .then();
    }
}
