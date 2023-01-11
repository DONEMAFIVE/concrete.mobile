package ru.zzbo.concretemobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ru.zzbo.concretemobile.db.dbStructures.DBInitializer;

public class DBUtilDelete {

    private Context context;
    private DBInitializer dbInitializer;
    private SQLiteDatabase sqLiteDatabase;

    public DBUtilDelete(Context context) {
        this.context = context;
        dbInitializer = new DBInitializer(context);
    }

    public void openDbConfig(){
        sqLiteDatabase = dbInitializer.getWritableDatabase();
    }

    public void closeSession(){
        dbInitializer.close();
        sqLiteDatabase.close();
    }

    public void deleteRecepie(int id) {

        openDbConfig();
        try {
            sqLiteDatabase.delete("recepies","id=?" ,new String[]{String.valueOf(id)});
        } finally {
            closeSession();
        }

    }

    public void deleteOrder(int id) {

        openDbConfig();
        try {
            sqLiteDatabase.delete("orders","id=?" ,new String[]{String.valueOf(id)});
        } finally {
            closeSession();
        }

    }

    public void deleteOrg(int id) {

        openDbConfig();
        try {
            sqLiteDatabase.delete("organizations","id=?" ,new String[]{String.valueOf(id)});
        } finally {
            closeSession();
        }

    }

    public void deleteTrans(int id) {

        openDbConfig();
        try {
            sqLiteDatabase.delete("transporters","id=?" ,new String[]{String.valueOf(id)});
        } finally {
            closeSession();
        }

    }

}
