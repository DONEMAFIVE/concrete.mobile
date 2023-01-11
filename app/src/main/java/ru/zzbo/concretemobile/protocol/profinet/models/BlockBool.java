package ru.zzbo.concretemobile.protocol.profinet.models;

public class BlockBool {
    private int dbArea;
    private int number;
    private int start;
    private int step;
    private int startBit;
    private int length;

    public BlockBool(int dbArea, int number, int start, int step, int startBit, int length) {
        this.dbArea = dbArea;
        this.number = number;
        this.start = start;
        this.step = step;
        this.startBit = startBit;
        this.length = length;
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

    public int getStep() {
        return step;
    }

    public int getStartBit() {
        return startBit;
    }

    public int getLength() {
        return length;
    }
}
