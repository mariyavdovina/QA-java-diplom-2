package com.example.clients;

import com.example.models.Credentials;
import com.example.models.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String PATH = "api/auth/";

    @Step("User creation")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH + "register/")
                .then();
    }

    @Step("User deletion")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("authorization", "bearer " + accessToken)
                .spec(getSpec())
                .when()
                .delete(PATH + "user/")
                .then();
    }

    @Step("Authorized user modify")
    public ValidatableResponse authorizedModify(String accessToken, User newUser) {
        return given()
                .header("authorization", "bearer " + accessToken)
                .spec(getSpec())
                .body(newUser)
                .when()
                .patch(PATH + "user/")
                .then();
    }

    @Step("Unauthorized user modify")
    public ValidatableResponse unAuthorizedModify(User newUser) {
        return given()
                .spec(getSpec())
                .body(newUser)
                .when()
                .patch(PATH + "user/")
                .then();
    }

    @Step("User login")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH + "login/")
                .then();
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
}
