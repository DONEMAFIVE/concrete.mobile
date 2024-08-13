package ru.zzbo.concretemobile.gui;


import static ru.zzbo.concretemobile.utils.Constants.configList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.helpers.ConfigBuilder;

public class NameBunckerActivity extends AppCompatActivity {

    private EditText buncker11Field;
    private EditText buncker12Field;
    private EditText buncker21Field;
    private EditText buncker22Field;
    private EditText buncker31Field;
    private EditText buncker32Field;
    private EditText buncker41Field;
    private EditText buncker42Field;
    private EditText chemy1Field;
    private EditText chemy2Field;
    private EditText chemy3Field;
    private EditText silos1Field;
    private EditText silos2Field;
    private EditText silos3Field;
    private EditText silos4Field;
    private EditText water1Field;
    private EditText water2Field;
    private Button saveBtn;
    private Button closeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_bunker_activity);

        initialUI();
        startListeners();
    }

    private void initialUI() {
        buncker11Field = findViewById(R.id.buncker11Field);
        buncker12Field = findViewById(R.id.buncker12Field);
        buncker21Field = findViewById(R.id.buncker21Field);
        buncker22Field = findViewById(R.id.buncker22Field);
        buncker31Field = findViewById(R.id.buncker31Field);
        buncker32Field = findViewById(R.id.buncker32Field);
        buncker41Field = findViewById(R.id.buncker41Field);
        buncker42Field = findViewById(R.id.buncker42Field);

        chemy1Field = findViewById(R.id.chemy1Field);
        chemy2Field = findViewById(R.id.chemy2Field);
        chemy3Field = findViewById(R.id.chemy3Field);

        silos1Field = findViewById(R.id.silos1Field);
        silos2Field = findViewById(R.id.silos2Field);
        silos3Field = findViewById(R.id.silos3Field);
        silos4Field = findViewById(R.id.silos4Field);

        water1Field = findViewById(R.id.water1Field);
        water2Field = findViewById(R.id.water2Field);

        saveBtn = findViewById(R.id.saveOrg);
        closeBtn = findViewById(R.id.closeOrg);

        new Handler(Looper.getMainLooper()).post(() -> {
            buncker11Field.setText(configList.getBuncker11());
            buncker12Field.setText(configList.getBuncker12());
            buncker21Field.setText(configList.getBuncker21());
            buncker22Field.setText(configList.getBuncker22());
            buncker31Field.setText(configList.getBuncker31());
            buncker32Field.setText(configList.getBuncker32());
            buncker41Field.setText(configList.getBuncker41());
            buncker42Field.setText(configList.getBuncker42());

            chemy1Field.setText(configList.getChemy1());
            chemy2Field.setText(configList.getChemy2());
            chemy3Field.setText(configList.getChemy3());

            silos1Field.setText(configList.getSilos1());
            silos2Field.setText(configList.getSilos2());
            silos3Field.setText(configList.getSilos3());
            silos4Field.setText(configList.getSilos4());

            water1Field.setText(configList.getWater1());
            water2Field.setText(configList.getWater2());
        });
    }

    private void startListeners() {
        saveBtn.setOnClickListener(view -> {
            try {
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker11", buncker11Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker12", buncker12Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker21", buncker21Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker22", buncker22Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker31", buncker31Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker32", buncker32Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker41", buncker41Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "buncker42", buncker42Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "chemy1", chemy1Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "chemy2", chemy2Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "chemy3", chemy3Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "silos1", silos1Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "silos2", silos2Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "silos3", silos3Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "silos4", silos4Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "water1", water1Field.getText().toString());
                new DBUtilUpdate(getApplicationContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "water2", water2Field.getText().toString());

                configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getApplicationContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

                Toast.makeText(getApplicationContext(), "Сохранено!", Toast.LENGTH_LONG).show();
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        closeBtn.setOnClickListener(view -> super.onBackPressed());
    }

}
