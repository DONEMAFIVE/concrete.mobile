package ru.zzbo.concretemobile.utils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.zzbo.concretemobile.BuildConfig;

public class FileUtil {
    //Возвращает список файлов в папке
    public List<String> getListFilesFolder(String path) {
        File folder = new File(path);
        List<String> files = new ArrayList<>();
        for (File file : folder.listFiles()) files.add(file.getName());
        return files;
    }

    /**
     * просто читает текстовый файл
     * @return и возвращает сожержимое ввиде коллекции List
     */
    public List<String> readFile(String path) {
        //сначала проверить наличие файла, если его нет - создать
        File chkFile = new File(path);
        if (!chkFile.exists()) cleanFile(path);

        List<String> fileStrings = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String currentString;

            while ((currentString = br.readLine()) != null) {
                fileStrings.add(currentString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileStrings.size() > 0) return fileStrings;
        else return null;
    }

    public String read(Context context, String path){
        try {
            InputStream isr = context.getAssets().open(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(isr));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.err.println(sb.toString());
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "empty file";
    }
    //просто очистить содержимое указанного файла
    public void cleanFile(String path) {
        try {
            FileWriter fstream1 = new FileWriter(path);// конструктор с одним параметром - для перезаписи
            BufferedWriter out1 = new BufferedWriter(fstream1); // создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (FileNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "Файл или папка отчета не обнаружены. Были созданы новые.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Проверка наличия файла
    public void chkExistFile(String pathFile){
        File chkFile = new File(pathFile);
        if (!chkFile.exists()) chkFile.mkdir();
    }

    public void openFileXlsx(String pathFile){
        File file = new File(Environment.getExternalStorageDirectory() +"/"+ pathFile);
        Uri path = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT > 24) path = Uri.parse(file.getPath());
        Intent fileOpenintent = new Intent(Intent.ACTION_VIEW);
        fileOpenintent.setDataAndType(path, "application/vnd.ms-excel");
        startActivity(fileOpenintent);
    }

    public void openFolder(String path){
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + path);
        Uri uri_path = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT > 24) uri_path = Uri.parse(file.getPath());
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setType("*/*");
        intent.setDataAndType(uri_path, "*/*");
        startActivity(intent);
    }
    //Проверка наличия директории
    public void chkExistFolder(String pathFolder) {
        try {
            File folder = new File(pathFolder);
            if (!folder.exists()) Files.createDirectories(Paths.get(pathFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Получение списка sql файлов с сервера
    public List<String> getListFileDB(boolean wOutSql){
        try {
            new FileUtil().chkExistFolder("zzboFolder");
            new FileUtil().chkExistFolder("zzboFolder" + "cacheFolder");
            String pathToDBList = "zzboFolder" + "cacheFolder" + "/db_list";

            BufferedInputStream in = new BufferedInputStream(new URL("http://188.225.42.106/boilershop/expander/db_list").openStream()); //Читаем файл со списком файлов
            FileOutputStream fileOutputStream = new FileOutputStream(pathToDBList);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();
            in.close();

            //TODO: Получение списка файлов
            List<String> urlsList = new ArrayList<>();      //Список sql - файлов
            FileInputStream fstream = new FileInputStream(pathToDBList);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String tmp;

            while ((tmp = br.readLine()) != null) {
                if (wOutSql) urlsList.add(tmp.replaceAll(".sql",""));
                else urlsList.add(tmp);
            }

            return urlsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Загрузка sql файлов с сервера
    public void downloadFilesDB(List<String> urlsList, String pathToCacheDir){
        //TODO: Скачать sql-файлы во временную папку
        for (int j = 0; j < urlsList.size(); j++) {
            try {
                URL url = new URL("http://188.225.42.106/boilershop/expander/db/" + urlsList.get(j));
                BufferedInputStream input = new BufferedInputStream(url.openStream()); //Скачиваем sql-файлы

                //создание папки согласно пути из списка
                String[] currentFilePathArray = urlsList.get(j).split("/");
                String buildPath = pathToCacheDir + "/tables/";
                for (int k = 0; k < currentFilePathArray.length - 1; k++) { //сборка пути до файла из списка
                    buildPath += currentFilePathArray[k] + "/";
                }

                //TODO: Проверить наличие папки
                new FileUtil().chkExistFolder(buildPath);

                FileOutputStream currentOutStream = new FileOutputStream(pathToCacheDir + "/tables/" + urlsList.get(j));
                byte[] currentDataBuffer = new byte[1024];
                int currentBytesRead;
                while ((currentBytesRead = input.read(currentDataBuffer, 0, 1024)) != -1) {
                    currentOutStream.write(currentDataBuffer, 0, currentBytesRead);
                }
                currentOutStream.close();
                input.close();
            } catch (FileNotFoundException exp) {
                System.err.println("Отсутствует файл: " + urlsList.get(j));
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }
    }


}

