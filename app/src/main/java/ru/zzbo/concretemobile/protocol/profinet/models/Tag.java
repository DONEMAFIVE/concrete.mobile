package ru.zzbo.concretemobile.protocol.profinet.models;

public class Tag {

    private int id;
    private int area;
    private int dbNumber;
    private int start;
    private int bit;
    private String typeTag;
    private boolean boolValueIf;
    private int intValueIf;
    private long DIntValueIf;
    private float realValueIf;
    private String description;

    private int isAlarm;

    public Tag(){}

    public Tag(int id, int area, int dbNumber, int start, int bit, String typeTag, boolean boolValueIf, int intValueIf, long DIntValueIf, float realValueIf, String description, int isAlarm) {
        this.id = id;
        this.area = area;
        this.dbNumber = dbNumber;
        this.start = start;
        this.bit = bit;
        this.typeTag = typeTag;
        this.boolValueIf = boolValueIf;
        this.intValueIf = intValueIf;
        this.DIntValueIf = DIntValueIf;
        this.realValueIf = realValueIf;
        this.description = description;
        this.isAlarm = isAlarm;
    }

    public Tag(int area, int dbNumber, int start, int bit, String typeTag, boolean boolValueIf, int intValueIf, long DIntValueIf, float realValueIf, String description, int isAlarm) {
        this.area = area;
        this.dbNumber = dbNumber;
        this.start = start;
        this.bit = bit;
        this.typeTag = typeTag;
        this.boolValueIf = boolValueIf;
        this.intValueIf = intValueIf;
        this.DIntValueIf = DIntValueIf;
        this.realValueIf = realValueIf;
        this.description = description;
        this.isAlarm = isAlarm;
    }

    public int getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public int getDbNumber() {
        return dbNumber;
    }

    public int getStart() {
        return start;
    }

    public int getBit() {
        return bit;
    }

    public String getTypeTag() {
        return typeTag;
    }

    public boolean isBoolValueIf() {
        return boolValueIf;
    }

    public int getIntValueIf() {
        return intValueIf;
    }

    public long getDIntValueIf() {
        return DIntValueIf;
    }

    public float getRealValueIf() {
        return realValueIf;
    }

    public String getDescription() {
        return description;
    }

    public int getIsAlarm() {
        return isAlarm;
    }

    public void setBoolValueIf(boolean boolValueIf) {
        this.boolValueIf = boolValueIf;
    }

    public void setIntValueIf(int intValueIf) {
        this.intValueIf = intValueIf;
    }

    public void setDIntValueIf(long DIntValueIf) {
        this.DIntValueIf = DIntValueIf;
    }

    public void setRealValueIf(float realValueIf) {
        this.realValueIf = realValueIf;
    }

    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setDbNumber(int dbNumber) {
        this.dbNumber = dbNumber;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public void setIsAlarm(int isAlarm) {
        this.isAlarm = isAlarm;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", area=" + area +
                ", dbNumber=" + dbNumber +
                ", start=" + start +
                ", bit=" + bit +
                ", typeTag='" + typeTag + '\'' +
                ", boolValueIf=" + boolValueIf +
                ", intValueIf=" + intValueIf +
                ", DIntValueIf=" + DIntValueIf +
                ", realValueIf=" + realValueIf +
                ", description='" + description + '\'' +
                ", isAlarm=" + isAlarm +
                '}';
    }
}
