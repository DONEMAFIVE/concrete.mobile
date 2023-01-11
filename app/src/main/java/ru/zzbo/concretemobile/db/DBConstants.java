package ru.zzbo.concretemobile.db;

public class DBConstants {
    public static String DATABASE_NAME = "source.db";
    public static int DATABASE_VERSION = 1;

    //таблица configs
    public static String TABLE_NAME_CONFIG = "config";
    public static String COLUMN_NAME_ID = "id";
    public static String COLUMN_NAME_PARAMETER = "parameter";
    public static String COLUMN_NAME_VALUE = "value";

    //таблица factory_complectation
    public static String TABLE_NAME_FACTORY_COMPLECTATION = "factory_complectation";

    //таблица current
    public static final String TABLE_NAME_CURRENT = "current";
    public static String COLUMN_NAME_CURRENT_ORDER_ID = "orderId";
    public static String COLUMN_NAME_CURRENT_RECEPIE_ID = "recepieId";
    public static String COLUMN_NAME_CURRENT_STATE = "state";

    //таблица users
    public static final String TABLE_NAME_USERS = "users";
    public static String COLUMN_NAME_USERS_ID = "id";
    public static String COLUMN_NAME_USERS_USERNAME = "userName";
    public static String COLUMN_NAME_USERS_DATECREATION = "dateCreation";
    public static String COLUMN_NAME_USERS_LOGIN = "login";
    public static String COLUMN_NAME_USERS_PASSWORD = "password";
    public static String COLUMN_NAME_USERS_ACCESSLEVEL = "accessLevel";

    //таблица mixes

}
