package ru.zzbo.concretemobile.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileDispatcher {

    //просто очистить содержимое указанного файла
    public void cleanFile(String path) {
        try {
            FileWriter fstream1 = new FileWriter(path);// конструктор с одним параметром - для перезаписи
            BufferedWriter out1 = new BufferedWriter(fstream1); // создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
            fstream1.close();
        } catch (FileNotFoundException e) {
            Log.i(null, "Файл или папка не обнаружены. Были созданы новые.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * проверка на отсутствие каталога
     * при отсутствии создать
     * @param nameFolder название каталога
     */
    public void chkExistFolder(String nameFolder){
        File folder = new File(nameFolder);
        if (!folder.exists()) folder.mkdir();
    }

    public void appendFile(String writeString, String path) {
        try {
            File searchFile = new File(path);
            if (searchFile.exists()) {
                FileWriter writer = new FileWriter(path, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(writeString);
                bufferWriter.close();
                writer.close();
            } else { //если файл не найден то создать новый
                String[] rootFolder = path.split("/");
                File folder = new File(rootFolder[0]);
                folder.mkdir();
                searchFile.createNewFile();
                FileWriter writer = new FileWriter(path, false);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(writeString);
                bufferWriter.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * обычное копирование файл из указанного места в указанное рекурсивно
     *
     * @param PATH_FROM
     * @param PATH_TO
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void copyFile(String PATH_FROM, String PATH_TO) {
        try {
            Files.copy(Paths.get(PATH_FROM), Paths.get(PATH_TO), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * просто читает текстовый файл
     * @return и возвращает сожержимое ввиде коллекции List
     */
    public List<String> readFile(String path){

        //сначала проверить наличие файла, если его нет - создать
        File chkFile = new File(path);
        if (!chkFile.exists()) cleanFile(path);

        List<String> fileStrings = new ArrayList<>();
        try{
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String currentString;

            while ((currentString = br.readLine()) != null){
                fileStrings.add(currentString);
            }
            fstream.close();
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (fileStrings.size() > 0) return fileStrings;
        else return null;
    }

}
