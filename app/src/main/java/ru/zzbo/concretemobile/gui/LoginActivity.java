package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.db.DBConstants.TABLE_NAME_CONFIG;
import static ru.zzbo.concretemobile.utils.Constants.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.BuildConfig;
import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilCreate;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.helpers.ConfigBuilder;
import ru.zzbo.concretemobile.models.Users;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.ConnectionUtil;
import ru.zzbo.concretemobile.utils.CryptoUtil;
import ru.zzbo.concretemobile.utils.LicenseUtil;
import ru.zzbo.concretemobile.utils.UpdaterUtil;

public class LoginActivity extends AppCompatActivity {

    private TextView info;
    private TextView textInfo;
    private Spinner loginSpinner;
    private EditText passwdField;
    private Button loginBtn;
    private CheckBox rememberLogin;
    private Spinner connection;
    private List<Users> userList;
    private DrawerLayout progressLoading;
    private String serverVersion = BuildConfig.VERSION_NAME;
    private long mLastClickTime = 0;

    private DBUtilGet dbUtilGet;
    private DBUtilCreate dbUtilCreate;

    static {

    }

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * StrictMode позволяет контролировать и выявлять потенциальные проблемы в вашем коде,
         * такие как выполнение сетевых операций в основном потоке.
         * Однако, использование `permitAll()` для разрешения всех операций в основном потоке может привести
         * к проблемам с производительностью и отзывчивостью пользовательского интерфейса.
         */

        //Установка политики StrictMode для разрешения всех операций в основном потоке
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        mSettings = getSharedPreferences("setting", MODE_PRIVATE);
        //Уникальный идентификатор. Он может изменяться при сбросе устройства до заводских настроек.
        androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        chkFirstRun();              //  Проверка при первом запуске
        initFieldUI();              //  Инициализация полей
        initLoginList();            //  Инициализация списка пользователей
        initRememberLoginPasswd();
//        getMacPlc();                //  Читаем MAC из PLC

        initActions();              //  Инициализация событий
        chkUpdate();                //TODO  Проверка обновления
    }

    /**
     * проверить если есть новая версия, то отобразить информацию
     */
    private void chkUpdate() {
        new Thread(() -> {
            if (!ConnectionUtil.isIpConnected("ya.ru")) return;
            if (!ConnectionUtil.isIpConnected(configList.getServerUpdate())) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Нет соединения с сервером обновлений", Toast.LENGTH_LONG).show());
                return;
            }

            try {
                URL url = new URL("http://" + configList.getServerUpdate() + "/boilershop/android/version");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(60000); // timing out in a minute

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                serverVersion = in.readLine();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!serverVersion.equals(BuildConfig.VERSION_NAME)) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Доступно новое обновление! Версия: " + serverVersion, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Рекомендуем установить последнюю версию приложения.\nОбновить приложение?");
                    builder.setTitle("Доступно новое обновление! Версия: " + serverVersion);
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.arrow_down);
                    builder.setPositiveButton("Обновить", (dialog, which) -> {
                        textInfo.setText("Загрузка обновления. Пожалуйста подождите...");
                        progressLoading.setVisibility(View.VISIBLE);
                        new Thread(() -> {
                            UpdaterUtil.downloadInstall("http://188.225.42.106/boilershop/android/app.apk", this);
                            runOnUiThread(() -> progressLoading.setVisibility(View.GONE));
                            //todo загрузить и проверить наличие новых таблиц и полей.
                        }).start();
                    });
                    builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
            }

        }).start();
    }

    private void getMacPlc() {
        if (!ConnectionUtil.isIpConnected(configList.getPlcIP())) return;
        new Thread(() -> {
            Tag current = new Tag(S7.S7AreaDB, 76, 0, 0, "String", false,
                    0, 0, 0, "", "", 0);
            plcMac = new CommandDispatcher(current).readSingleRegister();
            if (plcMac == null) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Отсутствует mac-адрес устройства", Toast.LENGTH_LONG).show();
                });
//
//                runOnUiThread(() -> {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Чтение данных PLC");
//                    builder.setMessage("Отсутствует mac-адрес устройства");
//                    builder.setIcon(R.drawable.warning);
//                    builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
//                    builder.show();
//                });
            }
        }).start();
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
        for (Users user : userList) login.add(user.getLogin());
        adapter = new ArrayAdapter<>(this, R.layout.spinner, login);
        loginSpinner.setAdapter(adapter);
    }

    private void initFieldUI() {
        progressLoading = findViewById(R.id.progress_loading);
        info = findViewById(R.id.info);
        textInfo = findViewById(R.id.text_info);
        loginSpinner = findViewById(R.id.loginField);
        passwdField = findViewById(R.id.passwdField);
        loginBtn = findViewById(R.id.okBtn);
        rememberLogin = findViewById(R.id.rememberLogin);
        connection = findViewById(R.id.connectType);
        connection.setSelection(getRememberConType());

        SimpleDateFormat year = new SimpleDateFormat("YYYY");
        info.setText(serverVersion + " Златоустовский завод бетоносмесительного оборудования. "
                + year.format(new Date()) + "г.");
    }

    private void initRememberLoginPasswd() {
        rememberLogin.setChecked(isRememberLogin());
        if (rememberLogin.isChecked()) {
            loginSpinner.setSelection(mSettings.getInt("login", 0));
            passwdField.setText(mSettings.getString("passwd", ""));
        }
    }

    private void chkFirstRun() {
        dbUtilCreate = new DBUtilCreate(this);
        dbUtilGet = new DBUtilGet(this);

        //Если базы нет, создаем
        if (!dbUtilGet.doesDatabaseExist(this)) {
            dbUtilCreate.executeSqlFile("db_init.sql");
            dbUtilCreate.executeSqlFile("tag_additional_options.sql");
            dbUtilCreate.executeSqlFile("tag_main.sql");
            dbUtilCreate.executeSqlFile("tag_manual.sql");
            dbUtilCreate.executeSqlFile("tag_options.sql");
            Toast.makeText(getApplicationContext(), "БД создана", Toast.LENGTH_SHORT).show();
        }

        //Получение конфига при первом запуске
        configList = new ConfigBuilder().buildScadaParameters(dbUtilGet.getFromParameterTable(TABLE_NAME_CONFIG));

        //Если первый запуск, открываем окно с настройками.
        if (configList.getFirstRun().equals("true")) {
//            Toast.makeText(getApplicationContext(), "Первый запуск", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initActions() {
        loginBtn.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
//                    getMacPlc();
                    runOnUiThread(() -> {
                        textInfo.setText("Подключение...");
                        progressLoading.setVisibility(View.VISIBLE);
                    });
                    break;
                }
            }
            return false;
        });
        loginBtn.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) return;
            mLastClickTime = SystemClock.elapsedRealtime();

            String device = ConnectionUtil.getDeviceDisconnected(this);

            if (device != null) {
                runOnUiThread(() -> {
                    progressLoading.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Подключение").setMessage("Не удается подключиться к " + device + "\nПроверьте адрес устройства");
                    builder.setIcon(R.drawable.warning);
                    builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
                    builder.setNeutralButton("Настройки", (dialog, id) -> startActivity(new Intent(getApplicationContext(), OptionsActivity.class)));
                    builder.show();
                });
                return;
            }

//            new Thread(() -> {
            if (!LicenseUtil.chkLicense(this, exchangeLevel)) {
                runOnUiThread(() -> {
                    progressLoading.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Лицензия").setMessage("Отсутствует лицензия");
                    builder.setNeutralButton("Настройки", (dialog, id) -> startActivity(new Intent(getApplicationContext(), OptionsActivity.class)));
                    builder.setPositiveButton("ОК", (dialog, id) -> {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    });
                    builder.show();
                });
                return;
            }

            String user = loginSpinner.getSelectedItem().toString().trim();
            String pass = passwdField.getText().toString();
            login(user, pass);

//            }).start();

        });
        connection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                exchangeLevel = connection.getSelectedItemPosition();
                if (exchangeLevel == 0) {
                    getMacPlc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void login(String login, String pass) {
        if (login.equals("") || pass.equals("")) {
            runOnUiThread(() -> {
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

        //Авторизация прошла
        if (accessLvl != -1) {
            runOnUiThread(() -> progressLoading.setVisibility(View.GONE));
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
                }
            }

        } else {
            runOnUiThread(() -> {
                progressLoading.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Уведомление").setMessage("Не корректное имя пользователя или пароль. Проверьте правильность ввода");
                builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
                builder.show();
            });
        }
    }

    private void saveLoginPasswd(Spinner loginSpinner, EditText passwdField) {
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putInt("login", loginSpinner.getSelectedItemPosition());
        prefEditor.putString("passwd", String.valueOf(passwdField.getText()));
        prefEditor.apply();
    }

    private void saveRememberLogin(CheckBox rememberLogin) {
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString("rememberLogin", String.valueOf(rememberLogin.isChecked()));
        prefEditor.apply();
    }

    private void saveConnectionType() {
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString("connectionType", String.valueOf(connection.getSelectedItemPosition()));
        prefEditor.apply();
    }

    private boolean isRememberLogin() {
        try {
            return Boolean.parseBoolean(mSettings.getString("rememberLogin", "false"));
        } catch (Exception e) {
            return false;
        }
    }

    private int getRememberConType() {
        try {
            return Integer.parseInt(mSettings.getString("connectionType", "0"));
        } catch (Exception e) {
            return 0;
        }
    }
}