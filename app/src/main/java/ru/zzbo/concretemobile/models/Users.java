package ru.zzbo.concretemobile.models;

public class Users {

    private int id;
    private String userName;
    private String dateCreation;
    private String login;
    private String password;
    private int accessLevel;

    public Users(int id, String userName, String dateCreation, String login, String password, int accessLevel) {
        this.id = id;
        this.userName = userName;
        this.dateCreation = dateCreation;
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }
}
