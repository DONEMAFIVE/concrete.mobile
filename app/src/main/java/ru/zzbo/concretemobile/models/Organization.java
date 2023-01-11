package ru.zzbo.concretemobile.models;

public class Organization {

    private int id;
    private String organizationHeadName;
    private String organizationName;
    private int persona;
    private String inn;
    private String kpp;
    private String okpo;
    private String phone;
    private String address;
    private String comment;
    private String contactName;
    private String contactPhone;

    public Organization(int id, String organizationHeadName, String organizationName, int persona, String inn, String kpp, String okpo, String phone, String address, String comment, String contactName, String contactPhone) {
        this.id = id;
        this.organizationHeadName = organizationHeadName;
        this.organizationName = organizationName;
        this.persona = persona;
        this.inn = inn;
        this.kpp = kpp;
        this.okpo = okpo;
        this.phone = phone;
        this.address = address;
        this.comment = comment;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public Organization(String organizationHeadName, String organizationName, int persona, String inn, String kpp, String okpo, String phone, String address, String comment, String contactName, String contactPhone) {
        this.organizationHeadName = organizationHeadName;
        this.organizationName = organizationName;
        this.persona = persona;
        this.inn = inn;
        this.kpp = kpp;
        this.okpo = okpo;
        this.phone = phone;
        this.address = address;
        this.comment = comment;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public int getId() {
        return id;
    }

    public String getOrganizationHeadName() {
        return organizationHeadName;
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

    public String getKpp() {
        return kpp;
    }

    public String getOkpo() {
        return okpo;
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

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

}
