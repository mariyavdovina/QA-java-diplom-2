package com.example.models;

import java.util.List;

public class Order {
    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    private List<String> ingredients;
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public Order() {
    }
}
