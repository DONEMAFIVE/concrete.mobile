package ru.zzbo.concretemobile.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {

    static List<String> importFromJSON(Context context) {

        try (FileInputStream fileInputStream = context.openFileInput("FILE_NAME");
             InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return dataItems.getUsers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItems {
        private List<String> users;

        List<String> getUsers() {
            return users;
        }

        void setUsers(List<String> users) {
            this.users = users;
        }
    }
}
