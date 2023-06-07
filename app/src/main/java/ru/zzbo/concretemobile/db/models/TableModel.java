package ru.zzbo.concretemobile.db.models;

import java.util.List;

public class TableModel {
    String nameTable;
    List<String> namesColumn;

    public TableModel(String nameTable, List<String> namesColumn) {
        this.nameTable = nameTable;
        this.namesColumn = namesColumn;
    }

    public String getTableName() {
        return nameTable;
    }

    public List<String> getColumnNames() {
        return namesColumn;
    }
}
