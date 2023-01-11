package ru.zzbo.concretemobile.protocol.profinet.models;

public class BlockMultiple {
    private int dbArea;
    private int number;
    private int startValue;
    private int step;
    private int length;
    private Object value;

    public BlockMultiple(int dbArea, int number, int startValue, int step, int length, Object value) {
        this.dbArea = dbArea;
        this.number = number;
        this.startValue = startValue;
        this.step = step;
        this.length = length;
        this.value = value;
    }

    public int getDbArea() {
        return dbArea;
    }

    public int getNumber() {
        return number;
    }

    public int getStartValue() {
        return startValue;
    }

    public int getStep() {
        return step;
    }

    public int getLength() {
        return length;
    }

    public Object getValue() {
        return value;
    }
}
