package com.example.providers;

import com.example.models.User;
import io.qameta.allure.Step;

public class UserProvider {
    @Step("User with default creds")
    public static User getDefault() {
        return new User("login@domain.com", "1234", "Bob");
    }
    @Step("User without email")
    public static User getWithoutEmail() {
        return new User("", "1234", "Bob");
    }

    @Step("User without password")
    public static User getWithoutPassword() {
        return new User("login@domain.com", "", "Bob");
    }

    @Step("User without name")
    public static User getWithoutName() {
        return new User("login@domain.com", "1234", "");
    }

    @Step("Without creds")
    public static User getEmpty() {
        return new User();
    }
}
