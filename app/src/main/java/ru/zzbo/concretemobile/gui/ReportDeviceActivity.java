package ru.zzbo.concretemobile.gui;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.utils.FileUtil;

public class ReportDeviceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);

        ListView mListView = findViewById(R.id.reportListView);

        ArrayList<String> reportDeviceList = new ArrayList<>();

        File f = new File(Environment.getExternalStorageDirectory() + "/reports/");
        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length != 0) {
            for (int i=0; i<files.length; i++) reportDeviceList.add(files[i].getName());
        }
        mListView.setOnItemClickListener((adapterView, view, i, l) -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                new FileUtil().openFileXlsx("reports/"+reportDeviceList.get(i));
            });
        });

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1, reportDeviceList);
        mListView.setAdapter(mAdapter);

    }

}
