package ru.zzbo.concretemobile.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class InstallationUtil {
    /**
     * https://russianblogs.com/article/10291353405/
     *
     Генерация и получение идентификатора установки аналогичны доступу UUID к SP

     Взаимодействие SharedPreferences и UUID:
     Получить uuid из SharedPreferences, если есть прямой возврат, если не сгенерирован
     String uuid = UUID.randomUUID().toString();
     Затем верните uuid и сохраните созданный на этот раз uuid в SharedPreferences.
     */
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

}
