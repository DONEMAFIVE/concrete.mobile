package ru.zzbo.concretemobile.db.dbStructures;

import static ru.zzbo.concretemobile.db.DBConstants.DATABASE_NAME;
import static ru.zzbo.concretemobile.db.DBConstants.DATABASE_VERSION;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.zzbo.concretemobile.db.DBUtilCreate;

public class DBInitializer extends SQLiteOpenHelper {
    Context context;
    public DBInitializer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            DBUtilCreate dbUtilCreate = new DBUtilCreate(context);
            dbUtilCreate.executeSqlFile("db_init.sql");
            dbUtilCreate.executeSqlFile("tag_additional_options.sql");
            dbUtilCreate.executeSqlFile("tag_main.sql");
            dbUtilCreate.executeSqlFile("tag_manual.sql");
            dbUtilCreate.executeSqlFile("tag_options.sql");
        }
        // Добавляйте дополнительные условия для последующих версий
    }

}
