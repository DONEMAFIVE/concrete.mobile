package ru.zzbo.concretemobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import ru.zzbo.concretemobile.db.dbStructures.DBInitializer;

public class DBUtilCreate {

    private Context context;
    private DBInitializer dbInitializer;
    private SQLiteDatabase sqLiteDatabase;

    public DBUtilCreate(Context context) {
        this.context = context;
        dbInitializer = new DBInitializer(context);
    }

    public void openDbConfig() {
        sqLiteDatabase = dbInitializer.getWritableDatabase();
    }

    public void closeSession() {
        dbInitializer.close();
        sqLiteDatabase.close();
    }

    public void createAllTables() {
        try {
            String file = "db_init.sql";
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            openDbConfig();
            String[] requests = new String(buffer).split(";");
            for (String query : requests) sqLiteDatabase.execSQL(query);

        } catch (IOException e) {
            Log.e("EXECUTE SQL-FILE", e.getMessage());
        }finally {
            closeSession();
        }
    }

}
