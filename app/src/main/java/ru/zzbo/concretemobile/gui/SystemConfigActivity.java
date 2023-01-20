package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.configList;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.utils.LicenseUtil;

public class SystemConfigActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_config);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.config, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //Фрагмент настроек
    public static class SettingsFragment extends PreferenceFragmentCompat {
        private EditTextPreference plcIpETP;
        private EditTextPreference macPlc;
        private EditTextPreference scadaIpETP;
        private EditTextPreference restServerIpETP;
        private EditTextPreference serverUpdateIpETP;
        private EditTextPreference productionNumberETP;
        private EditTextPreference hardkeyETP;
        private SwitchPreferenceCompat yandexOptionSPC;
        private EditTextPreference timeSyncIpETP;
        private Preference saveBtn;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.system_config_preferences, rootKey);
            configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

            plcIpETP = findPreference("plc_ip");
            macPlc = findPreference("mac_plc");
            scadaIpETP = findPreference("scada_ip");
            restServerIpETP = findPreference("rest_server_ip");
            serverUpdateIpETP = findPreference("server_update");
            productionNumberETP = findPreference("production_number");
            hardkeyETP = findPreference("hardkey");
            yandexOptionSPC = findPreference("yandex_option");
            timeSyncIpETP = findPreference("time_sync");
            saveBtn = findPreference("saveBtn");

            plcIpETP.setText(configList.getPlcIP());
            try {
                macPlc.setText(LicenseUtil.getMacFromIP(configList.getPlcIP()));
            }catch (Exception e){
                macPlc.setText("Нет доступа или отсутсвтует подключение");
            }
            scadaIpETP.setText(configList.getScadaIP());
            restServerIpETP.setText(configList.getRestServerIP());
            serverUpdateIpETP.setText(configList.getServerUpdate());
            productionNumberETP.setText(configList.getProductionNumber());
            hardkeyETP.setText(configList.getHardKey());
            yandexOptionSPC.setChecked(Boolean.parseBoolean(configList.getYandexOption()));
            timeSyncIpETP.setText(configList.getTimeSync());

            if (saveBtn != null) {
                saveBtn.setOnPreferenceClickListener(e -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Сохранение");
                    builder.setMessage("Вы действительно хотите сохранить настройки конфигурации");

                    builder.setPositiveButton("Да", (dialog, id) -> {
                        try {
                            //TODO Проверка введенных данных
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "plc_ip", plcIpETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "server_update", serverUpdateIpETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "hardkey", hardkeyETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "scada_ip", scadaIpETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "rest_server_ip", restServerIpETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "yandex_option", yandexOptionSPC.getKey());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "time_sync", timeSyncIpETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "productionNumber", productionNumberETP.getText());
                            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_CONFIG, "first_run", "false");

                            Toast.makeText(getContext(), "Сохранение выполнено успешно", Toast.LENGTH_SHORT).show();
                            System.exit(0);
                        } catch (Exception ex) {
                            Log.e("SaveConfig", ex.getMessage());
                            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                });
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}