package ru.zzbo.concretemobile.db.helpers;

import android.content.Context;

import java.util.List;

import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.models.ParameterDB;
import ru.zzbo.concretemobile.db.models.StorageMillage;

public class StorageMillageBuilder {

    public StorageMillage getValues(Context context){

        List<ParameterDB> inertBalanceList = new DBUtilGet(context).getParametersDB("inert_balance");

        StorageMillage storageMillage = new StorageMillage();

        for (ParameterDB current : inertBalanceList){

            if (current.getParameter().equals("buncker1_millage")) storageMillage.setBunckerMillage1(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("buncker2_millage")) storageMillage.setBunckerMillage2(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("buncker3_millage")) storageMillage.setBunckerMillage3(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("buncker4_millage")) storageMillage.setBunckerMillage4(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("water_millage")) storageMillage.setWaterMillage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("chemy1_millage")) storageMillage.setChemy1Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("chemy2_millage")) storageMillage.setChemy2Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("chemy3_millage")) storageMillage.setChemy3Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("silos1_millage")) storageMillage.setSilos1Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("silos2_millage")) storageMillage.setSilos2Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("silos3_millage")) storageMillage.setSilos3Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("silos4_millage")) storageMillage.setSilos4Millage(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("inert_storage1")) storageMillage.setInertStorage1(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("inert_storage2")) storageMillage.setInertStorage2(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("inert_storage3")) storageMillage.setInertStorage3(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("inert_storage4")) storageMillage.setInertStorage4(Float.parseFloat(current.getValue()));
            if (current.getParameter().equals("set_storage_buncker1")) storageMillage.setSetStorageBuncker1(Integer.parseInt(current.getValue()));
            if (current.getParameter().equals("set_storage_buncker2")) storageMillage.setSetStorageBuncker2(Integer.parseInt(current.getValue()));
            if (current.getParameter().equals("set_storage_buncker3")) storageMillage.setSetStorageBuncker3(Integer.parseInt(current.getValue()));
            if (current.getParameter().equals("set_storage_buncker4")) storageMillage.setSetStorageBuncker4(Integer.parseInt(current.getValue()));

        }

        return storageMillage;

    }

}
