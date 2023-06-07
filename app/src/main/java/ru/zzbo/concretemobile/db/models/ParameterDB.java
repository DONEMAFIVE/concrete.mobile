package ru.zzbo.concretemobile.db.models;

/**
 * универсальный объект для передачи параметров в лист из таблиц БД где используется подход "три колонки"
 */
public class ParameterDB {

    private int id;
    private String parameter;
    private String value;

    public int getId() {
        return id;
    }

    public String getParameter() {
        return parameter;
    }

    public String getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
