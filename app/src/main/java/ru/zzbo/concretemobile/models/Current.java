package ru.zzbo.concretemobile.models;

public class Current {

    private int orderID;
    private int recipeID;
    private String state;

    public Current(int orderID, int recipeID, String state) {
        this.orderID = orderID;
        this.recipeID = recipeID;
        this.state = state;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getState() {
        return state;
    }
}
