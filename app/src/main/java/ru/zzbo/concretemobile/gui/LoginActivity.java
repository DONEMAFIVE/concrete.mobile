package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.accessLevel;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
        info.setText(versionName + "v. Златоустовский завод бетоносмесительного оборудования. " + year.format(new Date()) + "г."); //2022. Златоустовский завод бетоносмесительного оборудования (1.0v. Златоустовский завод бетоносмесительного оборудования. 2023г)
    }

    private void chkRememberLoginPasswd() {
        rememberLogin.setChecked(isRememberLogin());
        if (rememberLogin.isChecked()) {
            loginField.setText(settings.getString("login", "operator"));
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
        okBtn.setOnClickListener(view -> {
            //Проверки на подключение
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
                    builder.setTitle("Уведомление").setMessage("Отсутсвует подключение к " + finalDevice);
                    builder.setPositiveButton("ОК", (dialog, id) -> {
                        dialog.dismiss();
                    });
                    builder.show();
                });
            } else {
                //TODO: Проверка лицензии
                if (true) {
                    //если подключаемся к ПК, то отправить запрос на прохождение авторизации
                    //если подключаемся на прямую, то используем базу sqlite
                    String user = loginField.getText().toString();
                    String pass = passwdField.getText().toString();
                    login(user, pass);
                }
            }
        });
    }

    private void login(String login, String pass) {
        if (login.equals("") || pass.equals("")) {
            Toast.makeText(this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
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
                case 3: {   //уровень доступа оператор
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
            builder.setTitle("Уведомление").setMessage("Не корректное имя пользователя или пароль. Проверьте правильность ввода");
            builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
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