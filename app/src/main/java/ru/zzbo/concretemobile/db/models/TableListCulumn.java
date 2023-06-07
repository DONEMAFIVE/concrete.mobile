package ru.zzbo.concretemobile.db.models;

public class TableListCulumn {
    private int id;
    private String name;

    public TableListCulumn(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
