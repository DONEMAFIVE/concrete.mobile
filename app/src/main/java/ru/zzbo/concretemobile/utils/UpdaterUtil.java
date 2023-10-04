package ru.zzbo.concretemobile.utils;


import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import ru.zzbo.concretemobile.BuildConfig;

public class UpdaterUtil {
    private static final String APP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";

    private static void install(Context context, String fileName) {
        File file = new File(APP_DIR + fileName);

        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String type = "application/vnd.android.package-archive";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri downloadedApk = FileProvider.getUriForFile(Objects.requireNonNull(context), BuildConfig.APPLICATION_ID + ".provider", file);
                intent.setDataAndType(downloadedApk, type);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            } else {
                intent.setDataAndType(Uri.fromFile(file), type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Файл не найден!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void downloadInstall(String apkurl, Context context) {
        try {
            URL url = new URL(apkurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Загрузка завершена!", Toast.LENGTH_SHORT).show();
            });


            install(context, "app.apk");
        } catch (IOException e) {
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Ошибка загрузки!", Toast.LENGTH_LONG).show();
            });
        }
    }

}
