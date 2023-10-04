package ru.zzbo.concretemobile.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static ru.zzbo.concretemobile.db.DBConstants.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.zzbo.concretemobile.db.dbStructures.DBInitializer;
import ru.zzbo.concretemobile.db.models.ParameterDB;
import ru.zzbo.concretemobile.models.Current;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Parameter;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.models.Requisites;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.models.Users;

public class DBUtilGet {

    private DBInitializer dbInitializer;
    private SQLiteDatabase sqLiteDatabase;

    public DBUtilGet(Context context) {
        dbInitializer = new DBInitializer(context);
    }

    public void openDbConfig() {
        sqLiteDatabase = dbInitializer.getWritableDatabase();
    }

    public void closeSession() {
        dbInitializer.close();
        sqLiteDatabase.close();
    }

    public boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    //GETTERS+
    @SuppressLint("Range")
    public Current getCurrent() {
        openDbConfig();
        try {
            Current result = null;
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME_CURRENT, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int orderId = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CURRENT_ORDER_ID));
                int recipeId = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CURRENT_RECEPIE_ID));
                String state = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CURRENT_STATE));
                result = new Current(orderId, recipeId, state);
            }
            cursor.close();
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Parameter> getFromParameterTable(String table) {
        openDbConfig();
        try {
            List<Parameter> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query(table, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                String parameter = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PARAMETER));
                String value = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_VALUE));
                result.add(new Parameter(id, parameter, value));
            }
            cursor.close();
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Users> getUsers() {
        openDbConfig();
        try {
            List<Users> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USERS, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_USERS_ID));
                String userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERS_USERNAME));
                String dateCreation = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERS_DATECREATION));
                String login = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERS_LOGIN));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERS_PASSWORD));
                int accessLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_USERS_ACCESSLEVEL));
                result.add(new Users(id, userName, dateCreation, login, password, accessLevel));
            }
            cursor.close();
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Mix> getMixList() {
        openDbConfig();
        try {
            List<Mix> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query("mixes", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nameOrder = cursor.getString(cursor.getColumnIndex("nameOrder"));
                int numberOrder = cursor.getInt(cursor.getColumnIndex("numberOrder"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String organization = cursor.getString(cursor.getColumnIndex("organization"));
                int organizationID = cursor.getInt(cursor.getColumnIndex("organizationID"));
                String transporter = cursor.getString(cursor.getColumnIndex("transporter"));
                int transporterID = cursor.getInt(cursor.getColumnIndex("transporterID"));
                String recipe = cursor.getString(cursor.getColumnIndex("recepie"));
                int recipeID = cursor.getInt(cursor.getColumnIndex("recepieID"));
                int mixCounter = cursor.getInt(cursor.getColumnIndex("mixCounter"));
                float completeCapacity = cursor.getFloat(cursor.getColumnIndex("completeCapacity"));
                float totalCapacity = cursor.getFloat(cursor.getColumnIndex("totalCapacity"));
                float silos1 = cursor.getFloat(cursor.getColumnIndex("silos1"));
                float silos2 = cursor.getFloat(cursor.getColumnIndex("silos2"));
                float bunker11 = cursor.getFloat(cursor.getColumnIndex("bunker11"));
                float bunker12 = cursor.getFloat(cursor.getColumnIndex("bunker12"));
                float bunker21 = cursor.getFloat(cursor.getColumnIndex("bunker21"));
                float bunker22 = cursor.getFloat(cursor.getColumnIndex("bunker22"));
                float bunker31 = cursor.getFloat(cursor.getColumnIndex("bunker31"));
                float bunker32 = cursor.getFloat(cursor.getColumnIndex("bunker32"));
                float bunker41 = cursor.getFloat(cursor.getColumnIndex("bunker41"));
                float bunker42 = cursor.getFloat(cursor.getColumnIndex("bunker42"));
                float water1 = cursor.getFloat(cursor.getColumnIndex("water1"));
                float water2 = cursor.getFloat(cursor.getColumnIndex("water2"));
                float dwpl = cursor.getFloat(cursor.getColumnIndex("dwpl"));
                float chemy1 = cursor.getFloat(cursor.getColumnIndex("chemy1"));
                float chemy2 = cursor.getFloat(cursor.getColumnIndex("chemy2"));
                String uploadAddress = cursor.getString(cursor.getColumnIndex("uploadAddress"));
                float amountConcrete = cursor.getFloat(cursor.getColumnIndex("amountConcrete"));
                String paymentOption = cursor.getString(cursor.getColumnIndex("paymentOption"));
                String operator = cursor.getString(cursor.getColumnIndex("operator"));
                String loadingTime = cursor.getString(cursor.getColumnIndex("loadingTime"));
                result.add(new Mix(
                        id,
                        nameOrder,
                        numberOrder,
                        date,
                        time,
                        uploadAddress,
                        amountConcrete,
                        paymentOption,
                        operator,
                        organization,
                        organizationID,
                        transporter,
                        transporterID,
                        recipe,
                        recipeID,
                        mixCounter,
                        completeCapacity,
                        totalCapacity,
                        bunker11,
                        bunker12,
                        bunker21,
                        bunker22,
                        bunker31,
                        bunker32,
                        bunker41,
                        bunker42,
                        silos1,
                        silos2,
                        water1,
                        water2,
                        dwpl,
                        chemy1,
                        chemy2,
                        loadingTime
                ));
            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Order> getOrdersByRangeDate(List<String> dates, boolean filter) {
        openDbConfig();
        try {
            List<Order> result = new ArrayList<>();
            int orderState = 0;
            if (filter) orderState = 1;

            for (int i = 0; i < dates.size(); i++) {
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orders WHERE date = '" + dates.get(i) + "' AND state = " + orderState, null);
                while (cursor.moveToNext()) {
                    result.add(new Order(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("nameOrder")),
                            cursor.getInt(cursor.getColumnIndex("numberOrder")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("completionDate")),
                            cursor.getString(cursor.getColumnIndex("organizationName")),
                            cursor.getInt(cursor.getColumnIndex("organizationID")),
                            cursor.getString(cursor.getColumnIndex("transporter")),
                            cursor.getInt(cursor.getColumnIndex("transporterID")),
                            cursor.getString(cursor.getColumnIndex("recepie")),
                            cursor.getInt(cursor.getColumnIndex("recepieID")),
                            +cursor.getFloat(cursor.getColumnIndex("totalCapacity")),
                            cursor.getFloat(cursor.getColumnIndex("maxMixCapacity")),
                            cursor.getInt(cursor.getColumnIndex("totalMixCounter")),
                            cursor.getString(cursor.getColumnIndex("markConcrete")),
                            cursor.getString(cursor.getColumnIndex("classConcrete")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker11")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker12")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker21")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker22")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker31")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker32")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker41")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker42")),
                            cursor.getFloat(cursor.getColumnIndex("pieChemy1")),
                            cursor.getFloat(cursor.getColumnIndex("pieChemy2")),
                            cursor.getFloat(cursor.getColumnIndex("pieWater1")),
                            cursor.getFloat(cursor.getColumnIndex("pieWater2")),
                            cursor.getFloat(cursor.getColumnIndex("pieSilos1")),
                            cursor.getFloat(cursor.getColumnIndex("pieSilos2")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker11")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker12")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker21")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker22")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker31")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker32")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker41")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker42")),
                            cursor.getFloat(cursor.getColumnIndex("shortageChemy1")),
                            cursor.getFloat(cursor.getColumnIndex("shortageChemy2")),
                            cursor.getFloat(cursor.getColumnIndex("shortageWater1")),
                            cursor.getFloat(cursor.getColumnIndex("shortageWater2")),
                            cursor.getFloat(cursor.getColumnIndex("shortageSilos1")),
                            cursor.getFloat(cursor.getColumnIndex("shortageSilos2")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker11")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker12")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker21")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker22")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker31")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker32")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker41")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker42")),
                            cursor.getFloat(cursor.getColumnIndex("countChemy1")),
                            cursor.getFloat(cursor.getColumnIndex("countChemy2")),
                            cursor.getFloat(cursor.getColumnIndex("countWater1")),
                            cursor.getFloat(cursor.getColumnIndex("countWater2")),
                            cursor.getFloat(cursor.getColumnIndex("countSilos1")),
                            cursor.getFloat(cursor.getColumnIndex("countSilos2")),
                            cursor.getInt(cursor.getColumnIndex("state")),
                            cursor.getInt(cursor.getColumnIndex("currentMixCount")),
                            cursor.getString(cursor.getColumnIndex("uploadAddress")),
                            cursor.getString(cursor.getColumnIndex("amountConcrete")),
                            cursor.getString(cursor.getColumnIndex("paymentOption")),
                            cursor.getString(cursor.getColumnIndex("operator")),
                            cursor.getString(cursor.getColumnIndex("comment"))
                    ));
                }
            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Order> getOrders() {
        openDbConfig();
        try {
            List<Order> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query("orders", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                result.add(new Order(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nameOrder")),
                        cursor.getInt(cursor.getColumnIndex("numberOrder")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("completionDate")),
                        cursor.getString(cursor.getColumnIndex("organizationName")),
                        cursor.getInt(cursor.getColumnIndex("organizationID")),
                        cursor.getString(cursor.getColumnIndex("transporter")),
                        cursor.getInt(cursor.getColumnIndex("transporterID")),
                        cursor.getString(cursor.getColumnIndex("recepie")),
                        cursor.getInt(cursor.getColumnIndex("recepieID")),
                        +cursor.getFloat(cursor.getColumnIndex("totalCapacity")),
                        cursor.getFloat(cursor.getColumnIndex("maxMixCapacity")),
                        cursor.getInt(cursor.getColumnIndex("totalMixCounter")),
                        cursor.getString(cursor.getColumnIndex("markConcrete")),
                        cursor.getString(cursor.getColumnIndex("classConcrete")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker11")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker12")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker21")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker22")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker31")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker32")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker41")),
                        cursor.getFloat(cursor.getColumnIndex("pieBuncker42")),
                        cursor.getFloat(cursor.getColumnIndex("pieChemy1")),
                        cursor.getFloat(cursor.getColumnIndex("pieChemy2")),
                        cursor.getFloat(cursor.getColumnIndex("pieWater1")),
                        cursor.getFloat(cursor.getColumnIndex("pieWater2")),
                        cursor.getFloat(cursor.getColumnIndex("pieSilos1")),
                        cursor.getFloat(cursor.getColumnIndex("pieSilos2")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker11")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker12")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker21")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker22")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker31")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker32")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker41")),
                        cursor.getFloat(cursor.getColumnIndex("shortageBuncker42")),
                        cursor.getFloat(cursor.getColumnIndex("shortageChemy1")),
                        cursor.getFloat(cursor.getColumnIndex("shortageChemy2")),
                        cursor.getFloat(cursor.getColumnIndex("shortageWater1")),
                        cursor.getFloat(cursor.getColumnIndex("shortageWater2")),
                        cursor.getFloat(cursor.getColumnIndex("shortageSilos1")),
                        cursor.getFloat(cursor.getColumnIndex("shortageSilos2")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker11")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker12")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker21")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker22")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker31")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker32")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker41")),
                        cursor.getFloat(cursor.getColumnIndex("countBuncker42")),
                        cursor.getFloat(cursor.getColumnIndex("countChemy1")),
                        cursor.getFloat(cursor.getColumnIndex("countChemy2")),
                        cursor.getFloat(cursor.getColumnIndex("countWater1")),
                        cursor.getFloat(cursor.getColumnIndex("countWater2")),
                        cursor.getFloat(cursor.getColumnIndex("countSilos1")),
                        cursor.getFloat(cursor.getColumnIndex("countSilos2")),
                        cursor.getInt(cursor.getColumnIndex("state")),
                        cursor.getInt(cursor.getColumnIndex("currentMixCount")),
                        cursor.getString(cursor.getColumnIndex("uploadAddress")),
                        cursor.getString(cursor.getColumnIndex("amountConcrete")),
                        cursor.getString(cursor.getColumnIndex("paymentOption")),
                        cursor.getString(cursor.getColumnIndex("operator")),
                        cursor.getString(cursor.getColumnIndex("comment"))
                ));
            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Mix> getMixListForDate(String date) {
        openDbConfig();
        try {
            List<Mix> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query("mixes", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("date")).equals(date)) {
                    result.add(new Mix(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("nameOrder")),
                            cursor.getInt(cursor.getColumnIndex("numberOrder")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("time")),
                            cursor.getString(cursor.getColumnIndex("uploadAddress")),
                            cursor.getFloat(cursor.getColumnIndex("amountConcrete")),
                            cursor.getString(cursor.getColumnIndex("paymentOption")),
                            cursor.getString(cursor.getColumnIndex("operator")),
                            cursor.getString(cursor.getColumnIndex("organization")),
                            cursor.getInt(cursor.getColumnIndex("organizationID")),
                            cursor.getString(cursor.getColumnIndex("transporter")),
                            cursor.getInt(cursor.getColumnIndex("transporterID")),
                            cursor.getString(cursor.getColumnIndex("recepie")),
                            cursor.getInt(cursor.getColumnIndex("recepieID")),
                            cursor.getInt(cursor.getColumnIndex("mixCounter")),
                            cursor.getFloat(cursor.getColumnIndex("completeCapacity")),
                            cursor.getFloat(cursor.getColumnIndex("totalCapacity")),
                            cursor.getFloat(cursor.getColumnIndex("bunker11")),
                            cursor.getFloat(cursor.getColumnIndex("bunker12")),
                            cursor.getFloat(cursor.getColumnIndex("bunker21")),
                            cursor.getFloat(cursor.getColumnIndex("bunker22")),
                            cursor.getFloat(cursor.getColumnIndex("bunker31")),
                            cursor.getFloat(cursor.getColumnIndex("bunker32")),
                            cursor.getFloat(cursor.getColumnIndex("bunker41")),
                            cursor.getFloat(cursor.getColumnIndex("bunker42")),
                            cursor.getFloat(cursor.getColumnIndex("silos1")),
                            cursor.getFloat(cursor.getColumnIndex("silos2")),
                            cursor.getFloat(cursor.getColumnIndex("water1")),
                            cursor.getFloat(cursor.getColumnIndex("water2")),
                            cursor.getFloat(cursor.getColumnIndex("dwpl")),
                            cursor.getFloat(cursor.getColumnIndex("chemy1")),
                            cursor.getFloat(cursor.getColumnIndex("chemy2")),
                            cursor.getString(cursor.getColumnIndex("loadingTime"))
                    ));
                }
            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Recepie> getRecipes() {
        openDbConfig();
        try {
            List<Recepie> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query("recepies", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                result.add(new Recepie(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("mark")),
                        cursor.getString(cursor.getColumnIndex("classPie")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie11")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie12")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie21")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie22")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie31")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie32")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie41")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerRecepie42")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage11")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage12")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage21")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage22")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage31")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage32")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage41")),
                        cursor.getFloat(cursor.getColumnIndex("bunckerShortage42")),
                        cursor.getFloat(cursor.getColumnIndex("chemyRecepie1")),
                        cursor.getFloat(cursor.getColumnIndex("chemyShortage1")),
                        cursor.getFloat(cursor.getColumnIndex("chemyShortage2")),
                        cursor.getFloat(cursor.getColumnIndex("water1Recepie")),
                        cursor.getFloat(cursor.getColumnIndex("water2Recepie")),
                        cursor.getFloat(cursor.getColumnIndex("water1Shortage")),
                        cursor.getFloat(cursor.getColumnIndex("water2Shortage")),
                        cursor.getFloat(cursor.getColumnIndex("silosRecepie1")),
                        cursor.getFloat(cursor.getColumnIndex("silosRecepie2")),
                        cursor.getFloat(cursor.getColumnIndex("silosShortage1")),
                        cursor.getFloat(cursor.getColumnIndex("silosShortage2")),
                        cursor.getFloat(cursor.getColumnIndex("humidity11")),
                        cursor.getFloat(cursor.getColumnIndex("humidity12")),
                        cursor.getFloat(cursor.getColumnIndex("humidity21")),
                        cursor.getFloat(cursor.getColumnIndex("humidity22")),
                        cursor.getFloat(cursor.getColumnIndex("humidity31")),
                        cursor.getFloat(cursor.getColumnIndex("humidity32")),
                        cursor.getFloat(cursor.getColumnIndex("humidity41")),
                        cursor.getFloat(cursor.getColumnIndex("humidity42")),
                        cursor.getString(cursor.getColumnIndex("uniNumber")),
                        cursor.getInt(cursor.getColumnIndex("timeMix")),
                        cursor.getFloat(cursor.getColumnIndex("chemy2Recepie")),
                        cursor.getFloat(cursor.getColumnIndex("chemy3Recepie")),
                        cursor.getFloat(cursor.getColumnIndex("chemy2Shortage")),
                        cursor.getFloat(cursor.getColumnIndex("chemy3Shortage")),
                        cursor.getInt(cursor.getColumnIndex("pathToHumidity")),
                        cursor.getInt(cursor.getColumnIndex("preDosingWaterPercent")))
                );
            }
//            if (result.isEmpty()) {
//                result.add(new Recepie(-1, "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0, 0, 0, 0, 0, 0));
//            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public Recepie getRecipeForID(int searchID) {
        openDbConfig();
        try {
            Cursor cursor = sqLiteDatabase.query("recepies", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                if (id == searchID) {
                    return new Recepie(
                            id,
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("time")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("mark")),
                            cursor.getString(cursor.getColumnIndex("classPie")),
                            cursor.getString(cursor.getColumnIndex("description")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie11")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie12")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie21")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie22")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie31")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie32")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie41")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerRecepie42")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage11")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage12")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage21")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage22")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage31")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage32")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage41")),
                            cursor.getFloat(cursor.getColumnIndex("bunckerShortage42")),
                            cursor.getFloat(cursor.getColumnIndex("chemyRecepie1")),
                            cursor.getFloat(cursor.getColumnIndex("chemyShortage1")),
                            cursor.getFloat(cursor.getColumnIndex("chemyShortage2")),
                            cursor.getFloat(cursor.getColumnIndex("water1Recepie")),
                            cursor.getFloat(cursor.getColumnIndex("water2Recepie")),
                            cursor.getFloat(cursor.getColumnIndex("water1Shortage")),
                            cursor.getFloat(cursor.getColumnIndex("water2Shortage")),
                            cursor.getFloat(cursor.getColumnIndex("silosRecepie1")),
                            cursor.getFloat(cursor.getColumnIndex("silosRecepie2")),
                            cursor.getFloat(cursor.getColumnIndex("silosShortage1")),
                            cursor.getFloat(cursor.getColumnIndex("silosShortage2")),
                            cursor.getFloat(cursor.getColumnIndex("humidity11")),
                            cursor.getFloat(cursor.getColumnIndex("humidity12")),
                            cursor.getFloat(cursor.getColumnIndex("humidity21")),
                            cursor.getFloat(cursor.getColumnIndex("humidity22")),
                            cursor.getFloat(cursor.getColumnIndex("humidity31")),
                            cursor.getFloat(cursor.getColumnIndex("humidity32")),
                            cursor.getFloat(cursor.getColumnIndex("humidity41")),
                            cursor.getFloat(cursor.getColumnIndex("humidity42")),
                            cursor.getString(cursor.getColumnIndex("uniNumber")),
                            cursor.getInt(cursor.getColumnIndex("timeMix")),
                            cursor.getFloat(cursor.getColumnIndex("chemy2Recepie")),
                            cursor.getFloat(cursor.getColumnIndex("chemy3Recepie")),
                            cursor.getFloat(cursor.getColumnIndex("chemy2Shortage")),
                            cursor.getFloat(cursor.getColumnIndex("chemy3Shortage")),
                            cursor.getInt(cursor.getColumnIndex("pathToHumidity")),
                            cursor.getInt(cursor.getColumnIndex("preDosingWaterPercent"))
                    );
                } else continue;
            }
        } finally {
            closeSession();
        }
        return new Recepie(-1, "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0, 0, 0, 0, 0, 0);
    }

    @SuppressLint("Range")
    public List<Organization> getOrgs() {
        openDbConfig();
        try {
            List<Organization> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query("organizations", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                result.add(new Organization(
                                cursor.getInt(cursor.getColumnIndex("id")),
                                cursor.getString(cursor.getColumnIndex("name")),
                                cursor.getString(cursor.getColumnIndex("fullname")),
                                cursor.getInt(cursor.getColumnIndex("persona")),
                                cursor.getString(cursor.getColumnIndex("inn")),
                                cursor.getString(cursor.getColumnIndex("kpp")),
                                cursor.getString(cursor.getColumnIndex("okpo")),
                                cursor.getString(cursor.getColumnIndex("phone")),
                                cursor.getString(cursor.getColumnIndex("address")),
                                cursor.getString(cursor.getColumnIndex("comment")),
                                cursor.getString(cursor.getColumnIndex("contactName")),
                                cursor.getString(cursor.getColumnIndex("contactPhone"))
                        )
                );
            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public List<Transporter> getTrans() {
        openDbConfig();
        try {
            List<Transporter> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.query("transporters", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                result.add(new Transporter(
                                cursor.getInt(cursor.getColumnIndex("id")),
                                cursor.getString(cursor.getColumnIndex("regNumberAuto")),
                                cursor.getString(cursor.getColumnIndex("organizationName")),
                                cursor.getInt(cursor.getColumnIndex("persona")),
                                cursor.getString(cursor.getColumnIndex("inn")),
                                cursor.getString(cursor.getColumnIndex("driverName")),
                                cursor.getString(cursor.getColumnIndex("markAuto")),
                                cursor.getString(cursor.getColumnIndex("phone")),
                                cursor.getString(cursor.getColumnIndex("address")),
                                cursor.getString(cursor.getColumnIndex("comment"))
                        )
                );
            }
            return result;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public Set<String> getDistinctRecipesMixes(List<String> dates) {
        openDbConfig();
        try {
            Set<String> recipes = new HashSet<>();
            for (int i = 0; i < dates.size(); i++) {
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT DISTINCT recepie FROM mixes WHERE date = '" + dates.get(i) + "'", null);
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    recipes.add(cursor.getString(cursor.getColumnIndex("recepie")));
                    cursor.moveToNext();
                }
            }

            return recipes;
        } finally {
            closeSession();
        }
    }

    @SuppressLint("Range")
    public Order getOrderForID(int searchID) {
        openDbConfig();
        try {
            Cursor cursor = sqLiteDatabase.query("orders", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                if (id == searchID) {
                    return new Order(
                            id,
                            cursor.getString(cursor.getColumnIndex("nameOrder")),
                            cursor.getInt(cursor.getColumnIndex("numberOrder")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("completionDate")),
                            cursor.getString(cursor.getColumnIndex("organizationName")),
                            cursor.getInt(cursor.getColumnIndex("organizationID")),
                            cursor.getString(cursor.getColumnIndex("transporter")),
                            cursor.getInt(cursor.getColumnIndex("transporterID")),
                            cursor.getString(cursor.getColumnIndex("recepie")),
                            cursor.getInt(cursor.getColumnIndex("recepieID")),
                            +cursor.getFloat(cursor.getColumnIndex("totalCapacity")),
                            cursor.getFloat(cursor.getColumnIndex("maxMixCapacity")),
                            cursor.getInt(cursor.getColumnIndex("totalMixCounter")),
                            cursor.getString(cursor.getColumnIndex("markConcrete")),
                            cursor.getString(cursor.getColumnIndex("classConcrete")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker11")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker12")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker21")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker22")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker31")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker32")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker41")),
                            cursor.getFloat(cursor.getColumnIndex("pieBuncker42")),
                            cursor.getFloat(cursor.getColumnIndex("pieChemy1")),
                            cursor.getFloat(cursor.getColumnIndex("pieChemy2")),
                            cursor.getFloat(cursor.getColumnIndex("pieWater1")),
                            cursor.getFloat(cursor.getColumnIndex("pieWater2")),
                            cursor.getFloat(cursor.getColumnIndex("pieSilos1")),
                            cursor.getFloat(cursor.getColumnIndex("pieSilos2")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker11")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker12")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker21")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker22")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker31")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker32")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker41")),
                            cursor.getFloat(cursor.getColumnIndex("shortageBuncker42")),
                            cursor.getFloat(cursor.getColumnIndex("shortageChemy1")),
                            cursor.getFloat(cursor.getColumnIndex("shortageChemy2")),
                            cursor.getFloat(cursor.getColumnIndex("shortageWater1")),
                            cursor.getFloat(cursor.getColumnIndex("shortageWater2")),
                            cursor.getFloat(cursor.getColumnIndex("shortageSilos1")),
                            cursor.getFloat(cursor.getColumnIndex("shortageSilos2")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker11")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker12")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker21")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker22")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker31")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker32")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker41")),
                            cursor.getFloat(cursor.getColumnIndex("countBuncker42")),
                            cursor.getFloat(cursor.getColumnIndex("countChemy1")),
                            cursor.getFloat(cursor.getColumnIndex("countChemy2")),
                            cursor.getFloat(cursor.getColumnIndex("countWater1")),
                            cursor.getFloat(cursor.getColumnIndex("countWater2")),
                            cursor.getFloat(cursor.getColumnIndex("countSilos1")),
                            cursor.getFloat(cursor.getColumnIndex("countSilos2")),
                            cursor.getInt(cursor.getColumnIndex("state")),
                            cursor.getInt(cursor.getColumnIndex("currentMixCount")),
                            cursor.getString(cursor.getColumnIndex("uploadAddress")),
                            cursor.getString(cursor.getColumnIndex("amountConcrete")),
                            cursor.getString(cursor.getColumnIndex("paymentOption")),
                            cursor.getString(cursor.getColumnIndex("operator")),
                            cursor.getString(cursor.getColumnIndex("comment"))
                    );
                } else continue;
            }
        } finally {
            closeSession();
        }
        return null;
    }

    @SuppressLint("Range")
    public Requisites getRequisites() {
        openDbConfig();
        try {
            Cursor cursor = sqLiteDatabase.query("requisites", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                return new Requisites(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("organizationType")),
                        cursor.getString(cursor.getColumnIndex("organizationName")),
                        cursor.getString(cursor.getColumnIndex("inn")),
                        cursor.getString(cursor.getColumnIndex("address")),
                        cursor.getString(cursor.getColumnIndex("headName")),
                        cursor.getString(cursor.getColumnIndex("phone")),
                        cursor.getString(cursor.getColumnIndex("fax")),
                        cursor.getString(cursor.getColumnIndex("site")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("comment")),
                        cursor.getString(cursor.getColumnIndex("loadAddress")),
                        cursor.getString(cursor.getColumnIndex("dispatcherName"))
                );
            }
        } finally {
            closeSession();
        }
        return null;
    }

    public List<String> getListTableDB() {
        openDbConfig();
        try {
            List<String> result = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            while (cursor.moveToNext()) {
                result.add(cursor.getString(0));
            }
            return result;
        }finally {
            closeSession();
        }
    }

    public List<String> getListColumnTable(String table) {
        openDbConfig();
        try {
        String sql = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'source'" +
                "AND table_name = '" + table + "'";
        List<String> columns = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);


            while (cursor.moveToNext()) {
                columns.add(cursor.getString(1));
            }
            return columns;

        } finally {
            closeSession();
        }
    }

    public List<ParameterDB> getParametersDB(String tableName) {
        openDbConfig();
        try {
            List<ParameterDB> shortParam = new ArrayList<>();

            Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                ParameterDB current = new ParameterDB();
                current.setId(cursor.getInt(0));
                current.setParameter(cursor.getString(1));
                current.setValue(cursor.getString(2));
                shortParam.add(current);
            }

            return shortParam;

        } finally {
            closeSession();
        }
    }
}
