package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.configList;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.utils.FileUtil;

public class ReportHMIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);

        ListView mListView = findViewById(R.id.reportListView);

        ArrayList<String> reportHmiList = new ArrayList<>();

        File f = new File(Environment.getExternalStorageDirectory() + "/IP_"+configList.getHmiIP()+"/datalog/");
        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length != 0) {
            for (int i=0; i<files.length; i++) reportHmiList.add(files[i].getName());
        }
        mListView.setOnItemClickListener((adapterView, view, i, l) -> {
            new FileUtil().openFileXlsx("IP_"+configList.getHmiIP()+"/datalog/"+reportHmiList.get(i));
        });

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1, reportHmiList);
        mListView.setAdapter(mAdapter);

    }

}
