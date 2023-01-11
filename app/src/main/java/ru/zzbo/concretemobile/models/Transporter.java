package ru.zzbo.concretemobile.models;

public class Transporter {

    private int id;
    private String regNumberAuto;
    private String organizationName;
    private int persona;
    private String inn;
    private String driverName;
    private String markAuto;
    private String phone;
    private String address;
    private String comment;

    public Transporter(int id, String regNumberAuto, String organizationName, int persona, String inn, String driverName, String markAuto, String phone, String address, String comment) {
        this.id = id;
        this.regNumberAuto = regNumberAuto;
        this.organizationName = organizationName;
        this.persona = persona;
        this.inn = inn;
        this.driverName = driverName;
        this.markAuto = markAuto;
        this.phone = phone;
        this.address = address;
        this.comment = comment;
    }

    public Transporter(String regNumberAuto, String organizationName, int persona, String inn, String driverName, String markAuto, String phone, String address, String comment) {
        this.regNumberAuto = regNumberAuto;
        this.organizationName = organizationName;
        this.persona = persona;
        this.inn = inn;
        this.driverName = driverName;
        this.markAuto = markAuto;
        this.phone = phone;
        this.address = address;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getRegNumberAuto() {
        return regNumberAuto;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public int getPersona() {
        return persona;
    }

    public String getInn() {
        return inn;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getMarkAuto() {
        return markAuto;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getComment() {
        return comment;
    }

}
