package ru.zzbo.concretemobile.protocol.profinet.models;

import java.util.List;

public class TagBoolShorts {
    private int dbArea;
    int number;
    int start;
    List<Integer> bits;

    public TagBoolShorts(int dbArea, int number, int start, List<Integer> bits) {
        this.dbArea = dbArea;
        this.number = number;
        this.start = start;
        this.bits = bits;
    }

    public int getDbArea() {
        return dbArea;
    }

    public int getNumber() {
        return number;
    }

    public int getStart() {
        return start;
    }

    public List<Integer> getBits() {
        return bits;
    }

    @Override
    public String toString() {

        String bits = "";
        for (int i = 0; i < this.bits.size(); i++) {
            bits += this.bits.get(i) + ", ";
        }

        return "TagShort{" +
                "dbArea='" + dbArea + '\'' +
                ", nunber=" + number +
                ", start=" + start +
                ", bits=" + bits +
                '}';
    }
}
