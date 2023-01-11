package ru.zzbo.concretemobile.protocol.profinet.models;

import java.util.List;

public class TagRealShorts {
    private int id;
    private int dbArea;
    int number;
    List<Integer> starts;

    public TagRealShorts(int dbArea, int number, List<Integer> starts) {
        this.dbArea = dbArea;
        this.number = number;
        this.starts = starts;
    }

    public int getDbArea() {
        return dbArea;
    }

    public int getNumber() {
        return number;
    }

    public List<Integer> getStarts() {
        return starts;
    }
}
