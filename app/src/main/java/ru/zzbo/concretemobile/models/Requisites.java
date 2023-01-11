package ru.zzbo.concretemobile.models;

public class Requisites {

    private int id;
    private String organizationType;    //тип организации
    private String organizationName;    //название организации
    private String inn;                 //инн
    private String address;             //юр адрес
    private String headName;            //руководитель
    private String phone;               //телефон
    private String fax;                 //fax
    private String site;                //сайт
    private String email;               //почта
    private String comment;             //комментарий
    private String loadAddress;         //место погрузки
    private String dispatcherName;      //ФИО диспетчера

    public Requisites(int id,String organizationType, String organizationName, String inn, String address, String headName, String phone, String fax, String site, String email, String comment, String loadAddress, String dispatcherName) {
        this.id = id;
        this.organizationType = organizationType;
        this.organizationName = organizationName;
        this.inn = inn;
        this.address = address;
        this.headName = headName;
        this.phone = phone;
        this.fax = fax;
        this.site = site;
        this.email = email;
        this.comment = comment;
        this.loadAddress = loadAddress;
        this.dispatcherName = dispatcherName;
    }

    public Requisites(String organizationType, String organizationName, String inn, String address, String headName, String phone, String fax, String site, String email, String comment, String loadAddress, String dispatcherName) {
        this.organizationType = organizationType;
        this.organizationName = organizationName;
        this.inn = inn;
        this.address = address;
        this.headName = headName;
        this.phone = phone;
        this.fax = fax;
        this.site = site;
        this.email = email;
        this.comment = comment;
        this.loadAddress = loadAddress;
        this.dispatcherName = dispatcherName;
    }

    public int getId() {
        return id;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getInn() {
        return inn;
    }

    public String getAddress() {
        return address;
    }

    public String getHeadName() {
        return headName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getSite() {
        return site;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public String getLoadAddress() {
        return loadAddress;
    }

    public String getDispatcherName() {
        return dispatcherName;
    }
}
