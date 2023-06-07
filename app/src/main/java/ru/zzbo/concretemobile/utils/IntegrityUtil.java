package ru.zzbo.concretemobile.utils;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.db.DBUtilCreate;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.db.models.ParameterDB;
import ru.zzbo.concretemobile.db.models.TableModel;

public class IntegrityUtil {
    private Context context;                                                                                                //Путь к папке с sql - файлами
    private String path;                                                                                                //Путь к папке с sql - файлами
    private List<String> files;
    private List<String> tables;

    private List<TableModel> tablesDB;                                                                                  //Список таблиц с колонками
    private List<TableModel> tablesFiles;

    public IntegrityUtil(String path, Context context) {
        this.context = context;
        this.path = path;
        chkFileTable();
        chkFieldsTable();
        chkTableParameters();
    }

    //Инициализация файлов и таблиц
    public void initTables() {
        files = new FileUtil().getListFilesFolder(path);                                                               //Список файлов в папке
        tables = new DBUtilGet(context).getListTableDB(); //todo
        tablesDB = new ArrayList<>();
        tablesFiles = new ArrayList<>();

        //Пройти по БД добавить таблицы с колонками
        for (int i = 0; i < tables.size(); i++) {
            tablesDB.add(new TableModel(tables.get(i), new DBUtilGet(context).getListColumnTable(tables.get(i)))); //todo
        }
        //Пройти по файлам в папке и добавить их содержимое
        for (int i = 0; i < files.size(); i++) {
            tablesFiles.add(new TableModel(files.get(i), new FileUtil().readFile(path + "\\" + files.get(i))));
        }

    }

    //Проверка актуальности таблиц (Если SQL-файлов больше чем таблиц в БД)
    public void chkFileTable() {
        initTables();
        if (tablesFiles.size() > tablesDB.size()) {
            for (int t = 0; t < tablesFiles.size(); t++) {
                int countTable = 0;
                if (tablesDB.size() == 0) {
//                    executeSqlFile(path + tablesFiles.get(t).getTableName());
                    new DBUtilCreate(context).executeSqlFile(path + tablesFiles.get(t).getTableName());
                } else {
                    for (int r = 0; r < tablesDB.size(); r++) {
                        if (!tablesDB.get(r).getTableName().equals(tablesFiles.get(t).getTableName().replaceAll(".sql", ""))) {
                            countTable++;
                            if (countTable == tablesDB.size()) {
//                                executeSqlFile(path + tablesFiles.get(t).getTableName());
                                new DBUtilCreate(context).executeSqlFile((path + tablesFiles.get(t).getTableName()));
                                countTable = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    //Проверка полей в таблицах
    public void chkFieldsTable() {
        initTables();
        for (int j = 0; j < files.size(); j++) {
            //Проверка, если названия файлов соответствуют названиям таблиц из БД
            try {
                if (files.get(j).replaceAll(".sql", "").equals(tablesDB.get(j).getTableName())) {
                    System.out.println("================= " + tablesDB.get(j).getTableName() + " - " + files.get(j) + " ================== ");

                    //Проходим по полям в таблице
                    for (int i = 0; i < tablesFiles.get(j).getColumnNames().size(); i++) {
                        //Здесь проверяем на наличие полей
                        if (tablesFiles.get(j).getColumnNames().get(i).contains("  `")) {
                            String column = parseName(tablesFiles.get(j).getColumnNames().get(i));
                            String type = parseType(tablesFiles.get(j).getColumnNames().get(i));
                            int matchCount = 0;
                            for (int t = 0; t < tablesDB.get(j).getColumnNames().size(); t++) {
                                if (!tablesDB.get(j).getColumnNames().get(t).equals(column)) {
                                    matchCount++;
                                    if (matchCount == tablesDB.get(j).getColumnNames().size()) {
                                        String tableName = tablesDB.get(j).getTableName();
                                        String afterColumn = parseName(tablesFiles.get(j).getColumnNames().get(i - 1));

                                        new DBUtilInsert(context).addColumnTable(tableName, column, type, afterColumn); //todo
                                        matchCount = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }

            //Обновление таблиц с тегами
            try {
                if (tablesFiles.get(j).getTableName().contains("tags_")) {
                    new DBUtilCreate(context).executeSqlFile(path + tablesFiles.get(j).getTableName());
//                    executeSqlFile(path + tablesFiles.get(j).getTableName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void chkTableParameters() {
        initTables();
        for (int j = 0; j < files.size(); j++) {
            //Проверка, если названия файлов соответствуют названиям таблиц из БД
            try {
                if (files.get(j).replaceAll(".sql", "").equals("configs")) {
                    List<ParameterDB> factoryShortList = new DBUtilGet(context).getParametersDB("configs"); //todo

                    int id = 0;
                    for (int i = 0; i < tablesFiles.get(j).getColumnNames().size(); i++) {
                        //Здесь проверяем на наличие полей
                        if (tablesFiles.get(j).getColumnNames().get(i).contains("\t(")) {
                            String name = tablesFiles.get(j).getColumnNames().get(i);
                            name.substring(3, name.lastIndexOf("')"));
                            id ++;
                            if (id > factoryShortList.size()) {
                                String[] names = name.substring(0,name.length()-2) .split(",");
                                String param = names[1].replaceAll("'", "");
                                String value = names[2].replaceAll("'", "");
                                new DBUtilInsert(context).insertParameter("configs", param.trim(), value.trim()); //todo
                            }
                        }
                    }
                }

                if (files.get(j).replaceAll(".sql", "").equals("factory_complectation")) {
                    System.out.println("================= " + tablesDB.get(j).getTableName() + " - " + files.get(j) + " ================== ");
                    List<ParameterDB> factoryShortList = new DBUtilGet(context).getParametersDB("factory_complectation"); //todo

                    int id = 0;
                    for (int i = 0; i < tablesFiles.get(j).getColumnNames().size(); i++) {
                        //TODO: Здесь проверяем на наличие полей
                        if (tablesFiles.get(j).getColumnNames().get(i).contains("\t(")) {
                            String name = tablesFiles.get(j).getColumnNames().get(i);
                            name.substring(3, name.lastIndexOf("')"));
                            id ++;
                            if (id > factoryShortList.size()) {
                                String[] names = name.substring(0,name.length()-2) .split(",");
                                String param = names[1].replaceAll("'", "");
                                String value = names[2].replaceAll("'", "");
                                new DBUtilInsert(context).insertParameter("factory_complectation", param.trim(), value.trim()); //todo
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static String parseType(String type) {
        String types = type.substring(type.indexOf("` "), type.lastIndexOf(" "));
        types = types.replaceAll("`", "")
                .replaceAll("NOT", "")
                .replaceAll("NULL", "")
                .replaceAll("DEFAULT", "")
                .trim();
        return types;
    }

    public static String parseName(String name) {
        return name.substring(3, name.lastIndexOf("` "));
    }

}
