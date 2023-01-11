package ru.zzbo.concretemobile.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.db.dbStructures.DBInitializer;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

public class DBTags {

    private DBInitializer dbInitializer;
    private SQLiteDatabase sqLiteDatabase;

    public DBTags(Context context) {
        dbInitializer = new DBInitializer(context);
    }

    public void openDbConfig() {
        sqLiteDatabase = dbInitializer.getWritableDatabase();
    }

    public void closeSession() {
        dbInitializer.close();
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public List<Tag> getTags(String table) {
        //todo: implement this
        openDbConfig();
        List<Tag> result = new ArrayList<>();

        try {
            Cursor cursor = sqLiteDatabase.query(table, null, null, null, null, null, null);
            Tag current;
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tagName = cursor.getString(cursor.getColumnIndex("tagName"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int start = cursor.getInt(cursor.getColumnIndex("start"));
                int bit = cursor.getInt(cursor.getColumnIndex("bit"));
                String dbAreaGet = cursor.getString(cursor.getColumnIndex("dbArea"));
                String typeGet = cursor.getString(cursor.getColumnIndex("type"));
                String valueGet = cursor.getString(cursor.getColumnIndex("value"));
                int isAlarm = cursor.getInt(cursor.getColumnIndex("isAlarm"));

                int area = 0;
                if (dbAreaGet.equals("DB")) area = S7.S7AreaDB;
                if (dbAreaGet.equals("PE")) area = S7.S7AreaPE;
                if (dbAreaGet.equals("PA")) area = S7.S7AreaPA;
                if (dbAreaGet.equals("MK")) area = S7.S7AreaMK;
                if (dbAreaGet.equals("CT")) area = S7.S7AreaCT;
                if (dbAreaGet.equals("TM")) area = S7.S7AreaTM;

                boolean boolValueIf = false;
                int intValueIf = 0;
                long DIntValueIf = 0;
                float realValueIf = 0;

                if (typeGet.equals("Bool")) {
                    if (valueGet.equals("true")) boolValueIf = true;
                    else boolValueIf = false;
                }
                if (typeGet.equals("Real")) realValueIf = Float.valueOf(valueGet);
                if (typeGet.equals("DInt")) DIntValueIf = Long.valueOf(valueGet);
                if (typeGet.equals("Int")) intValueIf = Integer.parseInt(valueGet);

                current = new Tag(
                        id,
                        area,
                        number,
                        start,
                        bit,
                        typeGet,
                        boolValueIf,
                        intValueIf,
                        DIntValueIf,
                        realValueIf,
                        tagName,
                        isAlarm
                );
                result.add(current);
            }
        } finally {
            closeSession();
        }

        return result;
    }

    @SuppressLint("Range")
    public Tag getTagForNum(int index, String table) {
        openDbConfig();
        try {
            Cursor cursor = sqLiteDatabase.query(table, null, null, null, null, null, null);
            Tag current;
            int i = 0;

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tagName = cursor.getString(cursor.getColumnIndex("tagName"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int start = cursor.getInt(cursor.getColumnIndex("start"));
                int bit = cursor.getInt(cursor.getColumnIndex("bit"));
                String dbAreaGet = cursor.getString(cursor.getColumnIndex("dbArea"));
                String typeGet = cursor.getString(cursor.getColumnIndex("type"));
                String valueGet = cursor.getString(cursor.getColumnIndex("value"));
                int isAlarm = cursor.getInt(cursor.getColumnIndex("isAlarm"));

                int area = 0;
                if (dbAreaGet.equals("DB")) area = S7.S7AreaDB;
                if (dbAreaGet.equals("PE")) area = S7.S7AreaPE;
                if (dbAreaGet.equals("PA")) area = S7.S7AreaPA;
                if (dbAreaGet.equals("MK")) area = S7.S7AreaMK;
                if (dbAreaGet.equals("CT")) area = S7.S7AreaCT;
                if (dbAreaGet.equals("TM")) area = S7.S7AreaTM;

                boolean boolValueIf = false;
                int intValueIf = 0;
                long DIntValueIf = 0;
                float realValueIf = 0;

                current = new Tag(
                        id,
                        area,
                        number,
                        start,
                        bit,
                        typeGet,
                        boolValueIf,
                        intValueIf,
                        DIntValueIf,
                        realValueIf,
                        tagName,
                        isAlarm
                );

                if (index == i) {
                    closeSession();
                    return current;
                }
                i++;
            }
        } finally {
            closeSession();
        }

        return new Tag();
    }

}
