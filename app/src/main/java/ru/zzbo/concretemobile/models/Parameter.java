package ru.zzbo.concretemobile.models;

public class Parameter {

    private int id;
    private String parameter;
    private String value;

    public Parameter(int id, String parameter, String value) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getParameter() {
        return parameter;
    }

    public String getValue() {
        return value;
    }
}
