package ru.zzbo.concretemobile.models;

public class Current {

    private int orderID;
    private int recepieID;
    private String state;

    public Current(int orderID, int recepieID, String state) {
        this.orderID = orderID;
        this.recepieID = recepieID;
        this.state = state;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getRecepieID() {
        return recepieID;
    }

    public String getState() {
        return state;
    }
}
