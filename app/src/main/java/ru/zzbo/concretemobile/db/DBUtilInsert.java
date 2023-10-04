package ru.zzbo.concretemobile.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.zzbo.concretemobile.db.builders.StorageMillageBuilder;
import ru.zzbo.concretemobile.db.dbStructures.DBInitializer;
import ru.zzbo.concretemobile.db.models.StorageMillage;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.models.Users;

public class DBUtilInsert {

    private DBInitializer dbInitializer;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;
    public DBUtilInsert(Context context) {
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

    public void insertIntoTrans(Transporter trans) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();

            cv.put("regNumberAuto", trans.getRegNumberAuto());
            cv.put("organizationName", trans.getOrganizationName());
            cv.put("persona", trans.getPersona());
            cv.put("inn", trans.getInn());
            cv.put("driverName", trans.getDriverName());
            cv.put("markAuto", trans.getMarkAuto());
            cv.put("phone", trans.getPhone());
            cv.put("address", trans.getAddress());
            cv.put("comment", trans.getComment());
            sqLiteDatabase.insert("transporters", null, cv);
        } finally {
            closeSession();
        }
    }

    public void insertIntoOrgs(Organization org) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();

            cv.put("name", org.getOrganizationName());
            cv.put("fullname", org.getOrganizationHeadName());
            cv.put("persona", org.getPersona());
            cv.put("inn", org.getInn());
            cv.put("kpp", org.getKpp());
            cv.put("okpo", org.getOkpo());
            cv.put("phone", org.getPhone());
            cv.put("address", org.getAddress());
            cv.put("comment", org.getComment());
            cv.put("contactName", org.getContactName());
            cv.put("contactPhone", org.getContactPhone());
            sqLiteDatabase.insert("organizations", null, cv);
        } finally {
            closeSession();
        }
    }

    public void insertIntoRecipe(Recepie recepie) {
        openDbConfig();

        try {
            ContentValues cv = new ContentValues();
            cv.put("date", recepie.getDate());
            cv.put("time", recepie.getTime());
            cv.put("name", recepie.getName());
            cv.put("mark", recepie.getMark());
            cv.put("classPie", recepie.getClassPie());
            cv.put("description", recepie.getDescription());
            cv.put("bunckerRecepie11", recepie.getBunckerRecepie11());
            cv.put("bunckerRecepie12", recepie.getBunckerRecepie12());
            cv.put("bunckerRecepie21", recepie.getBunckerRecepie21());
            cv.put("bunckerRecepie22", recepie.getBunckerRecepie22());
            cv.put("bunckerRecepie31", recepie.getBunckerRecepie31());
            cv.put("bunckerRecepie32", recepie.getBunckerRecepie32());
            cv.put("bunckerRecepie41", recepie.getBunckerRecepie41());
            cv.put("bunckerRecepie42", recepie.getBunckerRecepie42());
            cv.put("bunckerShortage11", recepie.getBunckerShortage11());
            cv.put("bunckerShortage12", recepie.getBunckerShortage12());
            cv.put("bunckerShortage21", recepie.getBunckerShortage21());
            cv.put("bunckerShortage22", recepie.getBunckerShortage22());
            cv.put("bunckerShortage31", recepie.getBunckerShortage31());
            cv.put("bunckerShortage32", recepie.getBunckerShortage32());
            cv.put("bunckerShortage41", recepie.getBunckerShortage41());
            cv.put("bunckerShortage42", recepie.getBunckerShortage42());
            cv.put("chemyRecepie1", recepie.getChemyRecepie1());
            cv.put("chemyShortage1", recepie.getChemyShortage1());
            cv.put("chemyShortage2", recepie.getChemyShortage2());
            cv.put("water1Recepie", recepie.getWater1Recepie());
            cv.put("water2Recepie", recepie.getWater2Recepie());
            cv.put("water1Shortage", recepie.getWater1Shortage());
            cv.put("water2Shortage", recepie.getWater2Shortage());
            cv.put("silosRecepie1", recepie.getSilosRecepie1());
            cv.put("silosRecepie2", recepie.getSilosRecepie2());
            cv.put("silosShortage1", recepie.getSilosShortage1());
            cv.put("silosShortage2", recepie.getSilosShortage2());
            cv.put("humidity11", recepie.getHumidity11());
            cv.put("humidity12", recepie.getHumidity12());
            cv.put("humidity21", recepie.getHumidity21());
            cv.put("humidity22", recepie.getHumidity22());
            cv.put("humidity31", recepie.getHumidity31());
            cv.put("humidity32", recepie.getHumidity32());
            cv.put("humidity41", recepie.getHumidity41());
            cv.put("humidity42", recepie.getHumidity42());
            cv.put("uniNumber", recepie.getUniNumber());
            cv.put("timeMix", recepie.getTimeMix());
            cv.put("chemy2Recepie", recepie.getChemy2Recepie());
            cv.put("chemy3Recepie", recepie.getChemy3Recepie());
            cv.put("chemy2Shortage", recepie.getChemy2Shortage());
            cv.put("chemy3Shortage", recepie.getChemy3Shortage());
            cv.put("pathToHumidity", recepie.getPathToHumidity());
            cv.put("preDosingWaterPercent", recepie.getPreDosingWaterPercent());

            sqLiteDatabase.insert("recepies", null, cv);
        } finally {
            closeSession();
        }
    }

    public void insertIntoUser(Users user) {
        openDbConfig();

        try {
            ContentValues cv = new ContentValues();
            cv.put("dateCreation", user.getDateCreation());
            cv.put("userName", user.getUserName());
            cv.put("login", user.getLogin());
            cv.put("password", user.getPassword());
            cv.put("accessLevel", user.getAccessLevel());
            sqLiteDatabase.insert("users", null, cv);
        } finally {
            closeSession();
        }
    }

    public void insertIntoOrder(Order order) {
        openDbConfig();

        try {
            ContentValues cv = new ContentValues();
            cv.put("nameOrder", order.getNameOrder());
            cv.put("numberOrder", order.getNumberOrder());
            cv.put("date", order.getDate());
            cv.put("completionDate", order.getCompletionDate());
            cv.put("organizationName", order.getOrganizationName());
            cv.put("organizationID", order.getOrganizationID());
            cv.put("transporter", order.getTransporter());
            cv.put("transporterID", order.getTransporterID());
            cv.put("recepie", order.getRecepie());
            cv.put("recepieID", order.getRecepieID());
            cv.put("totalCapacity", order.getTotalCapacity());
            cv.put("maxMixCapacity", order.getMaxMixCapacity());
            cv.put("totalMixCounter", order.getTotalMixCounter());
            cv.put("markConcrete", order.getMarkConcrete());
            cv.put("classConcrete", order.getClassConcrete());
            cv.put("pieBuncker11", order.getPieBuncker11());
            cv.put("pieBuncker12", order.getPieBuncker12());
            cv.put("pieBuncker21", order.getPieBuncker21());
            cv.put("pieBuncker22", order.getPieBuncker22());
            cv.put("pieBuncker31", order.getPieBuncker31());
            cv.put("pieBuncker32", order.getPieBuncker32());
            cv.put("pieBuncker41", order.getPieBuncker41());
            cv.put("pieBuncker42", order.getPieBuncker42());
            cv.put("pieChemy1", order.getPieChemy1());
            cv.put("pieChemy2", order.getPieChemy2());
            cv.put("pieWater1", order.getPieWater1());
            cv.put("pieWater2", order.getPieWater2());
            cv.put("pieSilos1", order.getPieSilos1());
            cv.put("pieSilos2", order.getPieSilos2());
            cv.put("shortageBuncker11", order.getShortageBuncker11());
            cv.put("shortageBuncker12", order.getShortageBuncker12());
            cv.put("shortageBuncker21", order.getShortageBuncker21());
            cv.put("shortageBuncker22", order.getShortageBuncker22());
            cv.put("shortageBuncker31", order.getShortageBuncker31());
            cv.put("shortageBuncker32", order.getShortageBuncker32());
            cv.put("shortageBuncker41", order.getShortageBuncker41());
            cv.put("shortageBuncker42", order.getShortageBuncker42());
            cv.put("shortageChemy1", order.getShortageChemy1());
            cv.put("shortageChemy2", order.getShortageChemy2());
            cv.put("shortageWater1", order.getShortageWater1());
            cv.put("shortageWater2", order.getShortageWater2());
            cv.put("shortageSilos1", order.getShortageSilos1());
            cv.put("shortageSilos2", order.getShortageSilos2());
            cv.put("countBuncker11", order.getCountBuncker11());
            cv.put("countBuncker12", order.getCountBuncker12());
            cv.put("countBuncker21", order.getCountBuncker21());
            cv.put("countBuncker22", order.getCountBuncker22());
            cv.put("countBuncker31", order.getCountBuncker31());
            cv.put("countBuncker32", order.getCountBuncker32());
            cv.put("countBuncker41", order.getCountBuncker41());
            cv.put("countBuncker42", order.getCountBuncker42());
            cv.put("countChemy1", order.getCountChemy1());
            cv.put("countChemy2", order.getCountChemy2());
            cv.put("countWater1", order.getCountWater1());
            cv.put("countWater2", order.getCountWater2());
            cv.put("countSilos1", order.getCountSilos1());
            cv.put("countSilos2", order.getCountSilos2());
            cv.put("state", order.getState());
            cv.put("currentMixCount", order.getCurrentMixCount());
            cv.put("uploadAddress", order.getUploadAddress());
            cv.put("amountConcrete", order.getAmountConcrete());
            cv.put("paymentOption", order.getPaymentOption());
            cv.put("operator", order.getOperator());
            cv.put("comment", order.getComment());

            sqLiteDatabase.insert("orders", null, cv);
        } finally {
            closeSession();
        }
    }

    //вставка новой записи в таблицу mixes
    @SuppressLint("Range")
    public void insertIntoMix(Mix mix) {
        openDbConfig();

        try {
            //todo: добавить проверку на пустую таблицу
            Mix lastMix;
            String sqlStr = "SELECT * FROM mixes ORDER BY id DESC LIMIT 1";
            Cursor cursor = sqLiteDatabase.rawQuery(sqlStr, null);
            cursor.moveToLast();

            lastMix = new Mix(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nameOrder")),
                    cursor.getInt(cursor.getColumnIndex("numberOrder")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("uploadAddress")),
                    cursor.getFloat(cursor.getColumnIndex("amountConcrete")),
                    cursor.getString(cursor.getColumnIndex("operator")),
                    cursor.getString(cursor.getColumnIndex("paymentOption")),
                    cursor.getString(cursor.getColumnIndex("organization")),
                    cursor.getInt(cursor.getColumnIndex("organizationID")),
                    cursor.getString(cursor.getColumnIndex("transporter")),
                    cursor.getInt(cursor.getColumnIndex("transporterID")),
                    cursor.getString(cursor.getColumnIndex("recepie")),
                    cursor.getInt(cursor.getColumnIndex("recepieID")),
                    cursor.getInt(cursor.getColumnIndex("mixCounter")),
                    cursor.getFloat(cursor.getColumnIndex("completeCapacity")),
                    cursor.getFloat(cursor.getColumnIndex("totalCapacity")),
                    cursor.getFloat(cursor.getColumnIndex("silos1")),
                    cursor.getFloat(cursor.getColumnIndex("silos2")),
                    cursor.getFloat(cursor.getColumnIndex("bunker11")),
                    cursor.getFloat(cursor.getColumnIndex("bunker12")),
                    cursor.getFloat(cursor.getColumnIndex("bunker21")),
                    cursor.getFloat(cursor.getColumnIndex("bunker22")),
                    cursor.getFloat(cursor.getColumnIndex("bunker31")),
                    cursor.getFloat(cursor.getColumnIndex("bunker32")),
                    cursor.getFloat(cursor.getColumnIndex("bunker41")),
                    cursor.getFloat(cursor.getColumnIndex("bunker42")),
                    cursor.getFloat(cursor.getColumnIndex("water1")),
                    cursor.getFloat(cursor.getColumnIndex("water2")),
                    cursor.getFloat(cursor.getColumnIndex("dwpl")),
                    cursor.getFloat(cursor.getColumnIndex("chemy1")),
                    cursor.getFloat(cursor.getColumnIndex("chemy2")),
                    cursor.getString(cursor.getColumnIndex("loadingTime"))
            );

            if ((lastMix.getBuncker11() == mix.getBuncker11()) &&
                    (lastMix.getBuncker21() == mix.getBuncker21()) &&
                    (lastMix.getBuncker31() == mix.getBuncker31()) &&
                    (lastMix.getBuncker41() == mix.getBuncker31()) &&
                    (lastMix.getWater1() == mix.getWater1()) &&
                    (lastMix.getSilos1() == mix.getSilos1()) &&
                    (lastMix.getChemy1() == mix.getChemy1())
            ) {
                Log.e(null, ">Find duplicate! Data shall not recording");
                return;
            }

            if ((lastMix.getBuncker11() == mix.getBuncker11()) &&
                    (lastMix.getBuncker21() == mix.getBuncker21()) &&
                    (lastMix.getBuncker31() == mix.getBuncker31()) &&
                    (lastMix.getBuncker41() == mix.getBuncker31()) &&
                    (lastMix.getWater1() == mix.getWater1()) &&
                    (lastMix.getSilos1() == 0) &&
                    (lastMix.getChemy1() == mix.getChemy1())
            ) {
                Log.e(null, ">Find duplicate! Data shall not recording");
                return;
            }

            if ((mix.getBuncker11() < 10) && (mix.getBuncker12() < 10) &&
                    (mix.getBuncker21() < 10) && (mix.getBuncker22() < 10) &&
                    (mix.getBuncker31() < 10) && (mix.getBuncker32() < 10) &&
                    (mix.getSilos1() < 5) && (mix.getSilos2() < 5) &&
                    (mix.getWater1() < 0.5) && (mix.getChemy1() < 0.5)) return;

            System.err.println(">Doses recording...");

            //вставка
            ContentValues cv = new ContentValues();
            cv.put("nameOrder", mix.getNameOrder());
            cv.put("numberOrder", mix.getNumberOrder());
            cv.put("date", mix.getDate());
            cv.put("time", mix.getTime());
            cv.put("organization", mix.getOrganization());
            cv.put("organizationID", mix.getOrganizationID());
            cv.put("transporter", mix.getTransporter());
            cv.put("transporterID", mix.getTransporterID());
            cv.put("recepie", mix.getRecepie());
            cv.put("recepieID", mix.getRecepieID());
            cv.put("mixCounter", mix.getMixCounter());
            cv.put("completeCapacity", mix.getCompleteCapacity());
            cv.put("totalCapacity", mix.getTotalCapacity());
            cv.put("silos1", mix.getSilos1());
            cv.put("silos2", mix.getSilos2());
            cv.put("bunker11", mix.getBuncker11());
            cv.put("bunker12", mix.getBuncker12());
            cv.put("bunker21", mix.getBuncker21());
            cv.put("bunker22", mix.getBuncker22());
            cv.put("bunker31", mix.getBuncker31());
            cv.put("bunker32", mix.getBuncker32());
            cv.put("bunker41", mix.getBuncker41());
            cv.put("bunker42", mix.getBuncker42());
            cv.put("water1", mix.getWater1());
            cv.put("water2", mix.getWater2());
            cv.put("dwpl", mix.getDwpl());
            cv.put("chemy1", mix.getChemy1());
            cv.put("chemy2", mix.getChemy2());
            cv.put("uploadAddress", mix.getUploadAddress());
            cv.put("amountConcrete", mix.getAmountConcrete());
            cv.put("paymentOption", mix.getPaymentOption());
            cv.put("operator", mix.getOperator());
            cv.put("loadingTime", mix.getLoadingTime());

            sqLiteDatabase.insert("mixes", null, cv);
            calcMillage(mix);

        } finally {
            closeSession();
        }
    }

    private void calcMillage(Mix mix){
        StorageMillage storageMillage = new StorageMillageBuilder().getValues(context);
        //к пробегу прибавить данные из замеса
        storageMillage.setBunckerMillage1(storageMillage.getBunckerMillage1() + mix.getBuncker11());
        storageMillage.setBunckerMillage1(storageMillage.getBunckerMillage1() + mix.getBuncker12());
        storageMillage.setBunckerMillage2(storageMillage.getBunckerMillage2() + mix.getBuncker21());
        storageMillage.setBunckerMillage2(storageMillage.getBunckerMillage2() + mix.getBuncker22());
        storageMillage.setBunckerMillage3(storageMillage.getBunckerMillage3() + mix.getBuncker31());
        storageMillage.setBunckerMillage3(storageMillage.getBunckerMillage3() + mix.getBuncker32());
        storageMillage.setBunckerMillage4(storageMillage.getBunckerMillage4() + mix.getBuncker41());
        storageMillage.setBunckerMillage4(storageMillage.getBunckerMillage4() + mix.getBuncker42());
        storageMillage.setWaterMillage(storageMillage.getWaterMillage() + mix.getWater1());
        storageMillage.setChemy1Millage(storageMillage.getChemy1Millage() + mix.getChemy1());
        storageMillage.setChemy2Millage(storageMillage.getChemy2Millage() + mix.getChemy2());
//        storageMillage.setChemy3Millage(storageMillage.getChemy3Millage() + mix.getChemy3());
        storageMillage.setSilos1Millage(storageMillage.getSilos1Millage() + mix.getSilos1());
        storageMillage.setSilos2Millage(storageMillage.getSilos2Millage() + mix.getSilos2());
//        storageMillage.setSilos3Millage(storageMillage.getSilos3Millage() + mix.getSilos3());
//        storageMillage.setSilos4Millage(storageMillage.getSilos4Millage() + mix.getSilos4());

        //для каждого склада вычесть данные из замеса, привязанные к бункеру
        switch (storageMillage.getSetStorageBuncker1()){
            case 0:{
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker11());
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker12());
                break;
            }
            case 1:{
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker11());
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker12());
                break;
            }
            case 2:{
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker11());
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker12());
                break;
            }
            case 3:{
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker11());
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker12());
                break;
            }
        }
        switch (storageMillage.getSetStorageBuncker2()){
            case 0:{
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker21());
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker22());
                break;
            }
            case 1:{
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker21());
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker22());
                break;
            }
            case 2:{
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker21());
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker22());
                break;
            }
            case 3:{
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker21());
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker22());
                break;
            }
        }
        switch (storageMillage.getSetStorageBuncker3()){
            case 0:{
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker31());
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker32());
                break;
            }
            case 1:{
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker31());
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker32());
                break;
            }
            case 2:{
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker31());
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker32());
                break;
            }
            case 3:{
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker31());
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker32());
                break;
            }
        }
        switch (storageMillage.getSetStorageBuncker4()){
            case 0:{
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker41());
                storageMillage.setInertStorage1(storageMillage.getInertStorage1() - mix.getBuncker42());
                break;
            }
            case 1:{
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker41());
                storageMillage.setInertStorage2(storageMillage.getInertStorage2() - mix.getBuncker42());
                break;
            }
            case 2:{
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker41());
                storageMillage.setInertStorage3(storageMillage.getInertStorage3() - mix.getBuncker42());
                break;
            }
            case 3:{
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker41());
                storageMillage.setInertStorage4(storageMillage.getInertStorage1() - mix.getBuncker42());
                break;
            }
        }

        new DBUtilUpdate(context).updateStorageMillageValues(storageMillage);

    }

    public void addColumnTable(String table, String column, String type, String afterColumn) {
        String sqlStr = "ALTER TABLE `" + table + "` ADD COLUMN `" + column + "` " + type + " NOT NULL AFTER `" + afterColumn + "`;";
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(sqlStr, null);
        } finally {
            closeSession();
        }
    }

    public void insertParameter(String table, String param, String value) {
        String sql = "INSERT INTO `"+table+"` (`parameter`, `value`) VALUES ('" + param + "', '" + value + "');";
        try {


        } finally {
            closeSession();
        }
    }
}
