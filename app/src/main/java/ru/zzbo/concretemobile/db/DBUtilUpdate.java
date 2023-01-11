package ru.zzbo.concretemobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ru.zzbo.concretemobile.db.dbStructures.DBInitializer;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recipe;
import ru.zzbo.concretemobile.models.Transporter;

public class DBUtilUpdate {

    private DBInitializer dbInitializer;
    private SQLiteDatabase sqLiteDatabase;

    public DBUtilUpdate(Context context) {
        dbInitializer = new DBInitializer(context);
    }

    public void openDbConfig() {
        sqLiteDatabase = dbInitializer.getWritableDatabase();
    }

    public void closeSession() {
        dbInitializer.close();
        sqLiteDatabase.close();
    }

    //обновляется value значение по параметру parameter, только для структуры таблиц c тремя колонками id-parameter-value
    public void updateParameterTypeTable(String table, String parameter, String value) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.COLUMN_NAME_PARAMETER, parameter);
            cv.put(DBConstants.COLUMN_NAME_VALUE, value);
            sqLiteDatabase.update(table, cv, DBConstants.COLUMN_NAME_PARAMETER + "= ?", new String[]{parameter});
        } finally {
            closeSession();
        }
    }

    //обновить 1 шт параметр в колонке, в этой таблице всего одна строка
    public void updCurrentTable(String parameter, String value) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();
            cv.put(parameter, value);
            sqLiteDatabase.update("current", cv, null, null);
        } finally {
            closeSession();
        }
    }

    public boolean updateRecepie(Recipe recipe) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();
            cv.put("id", recipe.getId());
            cv.put("date", recipe.getDate());
            cv.put("time", recipe.getTime());
            cv.put("name", recipe.getName());
            cv.put("mark", recipe.getMark());
            cv.put("classPie", recipe.getClassPie());
            cv.put("description", recipe.getDescription());
            cv.put("bunckerRecepie11", recipe.getBunckerRecepie11());
            cv.put("bunckerRecepie12", recipe.getBunckerRecepie12());
            cv.put("bunckerRecepie21", recipe.getBunckerRecepie21());
            cv.put("bunckerRecepie22", recipe.getBunckerRecepie22());
            cv.put("bunckerRecepie31", recipe.getBunckerRecepie31());
            cv.put("bunckerRecepie32", recipe.getBunckerRecepie32());
            cv.put("bunckerRecepie41", recipe.getBunckerRecepie41());
            cv.put("bunckerRecepie42", recipe.getBunckerRecepie42());
            cv.put("bunckerShortage11", recipe.getBunckerShortage11());
            cv.put("bunckerShortage12", recipe.getBunckerShortage12());
            cv.put("bunckerShortage21", recipe.getBunckerShortage21());
            cv.put("bunckerShortage22", recipe.getBunckerShortage22());
            cv.put("bunckerShortage31", recipe.getBunckerShortage31());
            cv.put("bunckerShortage32", recipe.getBunckerShortage32());
            cv.put("bunckerShortage41", recipe.getBunckerShortage41());
            cv.put("bunckerShortage42", recipe.getBunckerShortage42());
            cv.put("chemyRecepie1", recipe.getChemyRecepie1());
            cv.put("chemyShortage1", recipe.getChemyShortage1());
            cv.put("chemyShortage2", recipe.getChemyShortage2());
            cv.put("water1Recepie", recipe.getWater1Recepie());
            cv.put("water2Recepie", recipe.getWater2Recepie());
            cv.put("water1Shortage", recipe.getWater1Shortage());
            cv.put("water2Shortage", recipe.getWater2Shortage());
            cv.put("silosRecepie1", recipe.getSilosRecepie1());
            cv.put("silosRecepie2", recipe.getSilosRecepie2());
            cv.put("silosShortage1", recipe.getSilosShortage1());
            cv.put("silosShortage2", recipe.getSilosShortage2());
            cv.put("humidity11", recipe.getHumidity11());
            cv.put("humidity12", recipe.getHumidity12());
            cv.put("humidity21", recipe.getHumidity21());
            cv.put("humidity22", recipe.getHumidity22());
            cv.put("humidity31", recipe.getHumidity31());
            cv.put("humidity32", recipe.getHumidity32());
            cv.put("humidity41", recipe.getHumidity41());
            cv.put("humidity42", recipe.getHumidity42());
            cv.put("uniNumber", recipe.getUniNumber());
            cv.put("timeMix", recipe.getTimeMix());
            cv.put("chemy2Recepie", recipe.getChemy2Recepie());
            cv.put("chemy3Recepie", recipe.getChemy3Recepie());
            cv.put("chemy2Shortage", recipe.getChemy2Shortage());
            cv.put("chemy3Shortage", recipe.getChemy3Shortage());
            cv.put("pathToHumidity", recipe.getPathToHumidity());
            cv.put("preDosingWaterPercent", recipe.getPreDosingWaterPercent());
            sqLiteDatabase.update("recepies", cv, "id" + "= ?", new String[]{String.valueOf(recipe.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSession();
        }
    }

    public boolean updateOrder(Order order) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();
            cv.put("id", order.getId());
            cv.put("nameOrder", order.getNameOrder());
            cv.put("numberOrder", order.getNumberOrder());
            cv.put("date", order.getDate());
            cv.put("completionDate", order.getCompletionDate());
            cv.put("organizationName", order.getOrganizationName());
            cv.put("organizationID", order.getOrganizationID());
            cv.put("transporter", order.getTransporter());
            cv.put("transporterID", order.getTransporterID());
            cv.put("recepie", order.getRecipe());
            cv.put("recepieID", order.getRecipeID());
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
            sqLiteDatabase.update("orders", cv, "id" + "= ?", new String[]{String.valueOf(order.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSession();
        }
    }

    public void updateOrganization(Organization org) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();
            cv.put("id", org.getId());
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
            sqLiteDatabase.update("organizations", cv, "id" + "= ?", new String[]{String.valueOf(org.getId())});
        } finally {
            closeSession();
        }
    }

    public void updateTransporter(Transporter trans) {
        openDbConfig();
        try {
            ContentValues cv = new ContentValues();
            cv.put("id", trans.getId());
            cv.put("regNumberAuto", trans.getRegNumberAuto());
            cv.put("organizationName", trans.getOrganizationName());
            cv.put("persona", trans.getPersona());
            cv.put("inn", trans.getInn());
            cv.put("driverName", trans.getDriverName());
            cv.put("markAuto", trans.getMarkAuto());
            cv.put("phone", trans.getPhone());
            cv.put("address", trans.getAddress());
            cv.put("comment", trans.getComment());
            sqLiteDatabase.update("transporters", cv, "id" + "= ?", new String[]{String.valueOf(trans.getId())});
        } finally {
            closeSession();
        }
    }

}
