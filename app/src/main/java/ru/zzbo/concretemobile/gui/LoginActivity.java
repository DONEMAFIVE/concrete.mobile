package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.accessLevel;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.BuildConfig;
import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilCreate;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.models.Users;
import ru.zzbo.concretemobile.utils.ConnectionUtil;
import ru.zzbo.concretemobile.utils.CryptoUtil;
import ru.zzbo.concretemobile.utils.LicenseUtil;

public class LoginActivity extends AppCompatActivity {
    private DBUtilGet dbUtilGet;
    private DBUtilCreate dbUtilCreate;
    private TextView info;
    private EditText loginField;
    private EditText passwdField;
    private Button okBtn;
    private CheckBox rememberLogin;
    private Spinner connection;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.settings = getSharedPreferences("setting", MODE_PRIVATE);

        initFieldUI();
        chkRememberLoginPasswd();
        chkFirstRun();
        initActions();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("??????????");
        builder.setMessage("???? ?????????????????????????? ???????????? ?????????????????? ????????????");

        builder.setPositiveButton("????", (dialog, id) -> {
            try {
                finishAffinity();
            } catch (Exception ex) {
                Log.e("EXIT", ex.getMessage());
            }
        });
        builder.setNegativeButton("??????", (dialog, id) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initFieldUI() {
        info = findViewById(R.id.info);
        loginField = findViewById(R.id.loginField);
        passwdField = findViewById(R.id.passwdField);
        okBtn = findViewById(R.id.okBtn);
        rememberLogin = findViewById(R.id.rememberLogin);
        connection = findViewById(R.id.connectType);

        connection.setSelection(getRememberConType());
        dbUtilCreate = new DBUtilCreate(this);
        dbUtilGet = new DBUtilGet(this);

        String versionName = BuildConfig.VERSION_NAME;

        SimpleDateFormat year = new SimpleDateFormat("YYYY");
        info.setText(versionName + "v. ???????????????????????????? ?????????? ?????????????????????????????????????? ????????????????????????. " + year.format(new Date()) + "??."); //2022. ???????????????????????????? ?????????? ?????????????????????????????????????? ???????????????????????? (1.0v. ???????????????????????????? ?????????? ?????????????????????????????????????? ????????????????????????. 2023??)
    }

    private void chkRememberLoginPasswd() {
        rememberLogin.setChecked(isRememberLogin());
        if (rememberLogin.isChecked()) {
            loginField.setText(settings.getString("login", "operator"));
            passwdField.setText(settings.getString("passwd", ""));
        }
    }

    private void chkFirstRun() {
        //???????? ???????? ??????, ??????????????
        if (!dbUtilGet.doesDatabaseExist(this)) {
            dbUtilCreate.createAllTables();
            Toast.makeText(getApplicationContext(), "???? ??????????????", Toast.LENGTH_SHORT).show();
        }

        //?????????????????? ?????????????? ?????? ???????????? ??????????????
        configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getApplicationContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

        //???????? ???????????? ????????????, ?????????????????? ???????? ?? ??????????????????????.
        if (configList.getFirstRun().equals("true")) {
            Toast.makeText(getApplicationContext(), "???????????? ????????????", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SystemConfigActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    private void initActions() {
        okBtn.setOnClickListener(view -> {
            //???????????????? ???? ??????????????????????
            String device = null;
            if (!ConnectionUtil.isWifiConnected(this)) device = "WIFI";
            else {
                configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getApplicationContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

                exchangeLevel = connection.getSelectedItemPosition();
                switch (exchangeLevel) {
                    case 0:
                        if (!ConnectionUtil.isIpConnected(configList.getPlcIP())) device = "PLC";
                        break;
                    case 1:
                        if (!ConnectionUtil.isIpConnected(configList.getScadaIP())) device = "PC";
                        break;
                }

            }

            if (device != null) {
                String finalDevice = device;
                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("??????????????????????").setMessage("???????????????????? ?????????????????????? ?? " + finalDevice);
                    builder.setPositiveButton("????", (dialog, id) -> {
                        dialog.dismiss();
                    });
                    builder.show();
                });
            } else {
                //TODO: ???????????????? ????????????????
                //???????? ???????????????????????? ?? ????, ???? ?????????????????? ???????????? ???? ?????????????????????? ??????????????????????
                //???????? ???????????????????????? ???? ????????????, ???? ???????????????????? ???????? sqlite

                if (exchangeLevel == 0 && !LicenseUtil.chkLicense(this)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("????????????????").setMessage("???????????????????? ????????????????");
                    builder.setPositiveButton("????", (dialog, id) -> {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), SystemConfigActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    });
                    builder.show();
                    return;
                }

                String user = loginField.getText().toString();
                String pass = passwdField.getText().toString();
                login(user, pass);

            }
        });
    }

    private void login(String login, String pass) {
        if (login.equals("") || pass.equals("")) {
            Toast.makeText(this, "???????????????????? ?????????????????? ?????? ????????", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Users> userList = dbUtilGet.getUsers();
        int accessLvl = -1;
        for (Users user : userList) {
            String decryptPass = new CryptoUtil(user.getPassword()).decrypt();
            if ((login.equals(user.getLogin())) && (pass.equals(decryptPass.trim()))) {
                accessLvl = user.getAccessLevel();
            }
        }

        if (accessLvl != -1) {
            saveRememberLogin(rememberLogin);
            saveConnectionType();

            if (rememberLogin.isChecked()) saveLoginPasswd(loginField, passwdField);

            accessLevel = accessLvl;
            switch (accessLevel) {
                case 3: {   //?????????????? ?????????????? ????????????????
                    Intent intent = new Intent(getApplicationContext(), OperatorViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                    return;
                }
                case 4: {
                    Intent intent = new Intent(getApplicationContext(), CommissioningActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                    return;
                }
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("??????????????????????").setMessage("???? ???????????????????? ?????? ???????????????????????? ?????? ????????????. ?????????????????? ???????????????????????? ??????????");
            builder.setPositiveButton("????", (dialog, id) -> dialog.dismiss());
            builder.show();
            return;
        }
    }

    private void saveLoginPasswd(EditText loginField, EditText passwdField) {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("login", String.valueOf(loginField.getText()));
        prefEditor.putString("passwd", String.valueOf(passwdField.getText()));
        prefEditor.apply();
    }

    private void saveRememberLogin(CheckBox rememberLogin) {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("rememberLogin", String.valueOf(rememberLogin.isChecked()));
        prefEditor.apply();
    }

    private void saveConnectionType() {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("connectionType", String.valueOf(connection.getSelectedItemPosition()));
        prefEditor.apply();
    }

    private boolean isRememberLogin() {
        try {
            return Boolean.parseBoolean(settings.getString("rememberLogin", "false"));
        } catch (Exception e) {
            return false;
        }
    }

    private int getRememberConType() {
        try {
            return Integer.parseInt(settings.getString("connectionType", "0"));
        } catch (Exception e) {
            return 0;
        }
    }
}