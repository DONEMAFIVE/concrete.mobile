package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.androidID;
import static ru.zzbo.concretemobile.utils.Constants.configList;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.CryptoUtil;


public class SystemConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_config);
//        String ANDROID_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

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
        private EditTextPreference idDeviceETP;
        private EditTextPreference plcIpETP;
        private EditTextPreference scadaIpETP;
        private EditTextPreference restServerIpETP;
        private EditTextPreference serverUpdateIpETP;
        private EditTextPreference productionNumberETP;
        private EditTextPreference hardkeyETP;
        private Preference getLicence;
        private Preference saveBtn;
        private Tag readedTag;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.system_config_preferences, rootKey);
            configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

            new Thread(()->{
                Tag current = new Tag(S7.S7AreaDB, 76, 0, 0, "String", false, 0, 0, 0, "", "", 0);
                readedTag = new CommandDispatcher(current).readSingleRegister();
            }).start();

            Dialog dialogQR = new Dialog(getContext());
            dialogQR.setContentView(R.layout.custom_get_licence);
            ImageView imageQR = dialogQR.findViewById(R.id.image);

            idDeviceETP = findPreference("device_id");
            plcIpETP = findPreference("plc_ip");
            scadaIpETP = findPreference("scada_ip");
            restServerIpETP = findPreference("rest_server_ip");
            serverUpdateIpETP = findPreference("server_update");
            productionNumberETP = findPreference("production_number");
            hardkeyETP = findPreference("hardkey");
            getLicence = findPreference("getLicence");
            saveBtn = findPreference("saveBtn");

            idDeviceETP.setText(androidID);
            plcIpETP.setText(configList.getPlcIP());

            scadaIpETP.setText(configList.getScadaIP());
            restServerIpETP.setText(configList.getRestServerIP());
            serverUpdateIpETP.setText(configList.getServerUpdate());
            productionNumberETP.setText(configList.getProductionNumber());
            hardkeyETP.setText(configList.getHardKey());

            if (getLicence != null) {
                getLicence.setOnPreferenceClickListener(e -> {
                    boolean isLicence;
                    try {
                        isLicence = new CryptoUtil(hardkeyETP.getText()).decrypt().equals(readedTag.getStringValueIf().trim());
                    } catch (Exception ex) {
                        isLicence = false;
                    }
                    if (isLicence) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Проверка лицензии");
                        builder.setMessage("Лицензия активирована!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            String fromGetKey = new CryptoUtil(new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ";" + readedTag.getStringValueIf()).encrypt();
                            imageQR.setImageBitmap(generateQrCode(fromGetKey));
                            dialogQR.show();
                        });
                    }
                    return true;
                });
            }


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

    public static Bitmap generateQrCode(String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Bitmap bitmap = null;
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text,
                    BarcodeFormat.QR_CODE,
                    300, 300);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}