package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.accessLevel;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.operatorLogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Spinner loginSpinner;
    private EditText passwdField;
    private Button okBtn;
    private CheckBox rememberLogin;
    private Spinner connection;
    private SharedPreferences settings;
    private List<Users> userList;
    private DrawerLayout progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.settings = getSharedPreferences("setting", MODE_PRIVATE);

        initFieldUI();
        chkFirstRun();
        initLoginList();
        chkRememberLoginPasswd();
        initActions();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выход");
        builder.setMessage("Вы действительно хотите завершить работу");

        builder.setPositiveButton("Да", (dialog, id) -> {
            try {
                finishAffinity();
            } catch (Exception ex) {
                Log.e("EXIT", ex.getMessage());
            }
        });
        builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initLoginList() {
        ArrayList<String> login = new ArrayList<>();
        ArrayAdapter<String> adapter;
        userList = dbUtilGet.getUsers();
        for (Users user: userList) login.add(user.getLogin());
        adapter = new ArrayAdapter<>(this, R.layout.spinner, login);
        loginSpinner.setAdapter(adapter);
    }

    private void initFieldUI() {
        progressLoading = findViewById(R.id.progress_loading);
        info = findViewById(R.id.info);
        loginSpinner = findViewById(R.id.loginField);
        passwdField = findViewById(R.id.passwdField);
        okBtn = findViewById(R.id.okBtn);
        rememberLogin = findViewById(R.id.rememberLogin);
        connection = findViewById(R.id.connectType);

        connection.setSelection(getRememberConType());
        dbUtilCreate = new DBUtilCreate(this);
        dbUtilGet = new DBUtilGet(this);

        String versionName = BuildConfig.VERSION_NAME;

        SimpleDateFormat year = new SimpleDateFormat("YYYY");
        info.setText(versionName + "v. Златоустовский завод бетоносмесительного оборудования. " + year.format(new Date()) + "г."); //2022. Златоустовский завод бетоносмесительного оборудования (1.0v. Златоустовский завод бетоносмесительного оборудования. 2023г)
    }

    private void chkRememberLoginPasswd() {
        rememberLogin.setChecked(isRememberLogin());
        if (rememberLogin.isChecked()) {
            loginSpinner.setSelection(settings.getInt("login", 0));
            passwdField.setText(settings.getString("passwd", ""));
        }
    }

    private void chkFirstRun() {
        //Если базы нет, создаем
        if (!dbUtilGet.doesDatabaseExist(this)) {
            dbUtilCreate.createAllTables();
            Toast.makeText(getApplicationContext(), "БД создана", Toast.LENGTH_SHORT).show();
        }

        //Получение конфига при первом запуске
        configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getApplicationContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

        //Если первый запуск, открываем окно с настройками.
        if (configList.getFirstRun().equals("true")) {
            Toast.makeText(getApplicationContext(), "Первый запуск", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SystemConfigActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    private void initActions() {
        okBtn.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    runOnUiThread(()->progressLoading.setVisibility(View.VISIBLE));
                    break;
                }
            }
            return false;
        });
        okBtn.setOnClickListener(view -> {


            //Проверки на подключение
            String device = null;
            if (!ConnectionUtil.isWifiConnected(this)) device = "WIFI";
            else {
                configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getApplicationContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

                exchangeLevel = connection.getSelectedItemPosition();
                switch (exchangeLevel) {
                    case 0: if (!ConnectionUtil.isIpConnected(configList.getPlcIP())) device = "PLC"; break;
                    case 1: if (!ConnectionUtil.isIpConnected(configList.getScadaIP())) device = "PC"; break;
                }
            }

            if (device != null) {
                String finalDevice = device;
                runOnUiThread(() -> {
                    progressLoading.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Подключение").setMessage("Не удается подключиться к " + finalDevice);
                    builder.setIcon(R.drawable.warning);
                    builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
                    builder.show();
                });
            } else {
                //TODO: Проверка лицензии
                new Thread(() -> {
                    if (exchangeLevel == 0) {
                        if (!LicenseUtil.chkLicense(this)) {
                            runOnUiThread(() -> {
                                progressLoading.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Лицензия").setMessage("Отсутсвует лицензия");
                                builder.setPositiveButton("ОК", (dialog, id) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), SystemConfigActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                });
                                builder.show();
                            });
                            return;
                        }
                    }
                    if (exchangeLevel == 1) {
                        try {
                            if (!LicenseUtil.chkPCLicense(this)) {
                                runOnUiThread(() -> {
                                    progressLoading.setVisibility(View.GONE);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setTitle("Лицензия").setMessage("Отсутсвует лицензия");
                                    builder.setPositiveButton("ОК", (dialog, id) -> {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), SystemConfigActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                    });
                                    builder.show();
                                });
                                return;
                            }
                        } catch (NullPointerException exc) {
                            exc.printStackTrace();
                            runOnUiThread(()->{
                                progressLoading.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Подключение к устройству").setMessage("Не удается подключиться к ПК");
                                builder.setIcon(R.drawable.warning);
                                builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
                                builder.show();
                            });
                            return;
                        }
                    }

                    String user = loginSpinner.getSelectedItem().toString().trim();
                    String pass = passwdField.getText().toString();
                    login(user, pass);
                }).start();
            }
        });
    }

    private void login(String login, String pass) {
        if (login.equals("") || pass.equals("")) {
            runOnUiThread(()-> {
                progressLoading.setVisibility(View.GONE);
                Toast.makeText(this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
            });
            return;
        }


        int accessLvl = -1;
        for (Users user : userList) {
            String decryptPass = new CryptoUtil(user.getPassword()).decrypt();
            if ((login.equals(user.getLogin())) && (pass.equals(decryptPass.trim()))) {
                accessLvl = user.getAccessLevel();
                operatorLogin = user.getUserName();
            }
        }

        if (accessLvl != -1) {
            runOnUiThread(()-> progressLoading.setVisibility(View.GONE));
            saveRememberLogin(rememberLogin);
            saveConnectionType();

            if (rememberLogin.isChecked()) saveLoginPasswd(loginSpinner, passwdField);
            accessLevel = accessLvl;
            switch (accessLevel) {
                case 2: {   //Пуско-наладка
                    Intent intent = new Intent(getApplicationContext(), CommissioningActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                    return;
                }
                case 0:     //Оператор
                case 1:     //Диспетчер
                case 3: {   //Администратор
                    Intent intent = new Intent(getApplicationContext(), OperatorViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                    return;
                }
            }

        } else {
            runOnUiThread(()->{
                progressLoading.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Уведомление").setMessage("Не корректное имя пользователя или пароль. Проверьте правильность ввода");
                builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
                builder.show();
            });
            return;
        }
    }

    private void saveLoginPasswd(Spinner loginSpinner, EditText passwdField) {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putInt("login", loginSpinner.getSelectedItemPosition());
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