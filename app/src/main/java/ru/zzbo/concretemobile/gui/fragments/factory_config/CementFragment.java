package ru.zzbo.concretemobile.gui.fragments.factory_config;

import static ru.zzbo.concretemobile.utils.Constants.answer;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.Constants;

public class CementFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dc_preferences, rootKey);

        SwitchPreferenceCompat autoResetDc = findPreference("auto_reset_dc");
        EditTextPreference cementCount = findPreference("cement_count");
        EditTextPreference minWeightDc = findPreference("min_weight_dc");
        EditTextPreference timeDischargeDc = findPreference("time_discharge_dc");
        EditTextPreference timeVibroDc = findPreference("time_vibro");
        EditTextPreference pauseVibroDc = findPreference("pause_vibro");
        EditTextPreference delayDischargeDc = findPreference("delay_discharge_dc");
        SwitchPreferenceCompat permissionStartVibroDc = findPreference("permission_start_vibro");
        Preference saveBtn = findPreference("saveBtn");

        new Thread(() -> {
            try {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (answer.get(67).getIntValueIf() == 1)  autoResetDc.setChecked(true);

                    float current = answer.get(29).getIntValueIf();
                    cementCount.setText(String.valueOf((int) current));

                    current = answer.get(117).getRealValueIf();
                    minWeightDc.setText(String.valueOf(current));

                    current = answer.get(79).getDIntValueIf();
                    timeDischargeDc.setText(String.valueOf(current / 1000));

                    current = answer.get(92).getDIntValueIf();
                    timeVibroDc.setText(String.valueOf(current / 1000));

                    current = answer.get(93).getDIntValueIf();
                    pauseVibroDc.setText(String.valueOf(current / 1000));

                    current = answer.get(98).getDIntValueIf();
                    delayDischargeDc.setText(String.valueOf(current / 1000));

                    if (answer.get(16).getIntValueIf() == 1) permissionStartVibroDc.setChecked(true);

                    Toast.makeText(getContext(), "ДЦ - Загружено", Toast.LENGTH_SHORT).show();
                });

            } catch (NullPointerException ex) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), "ДЦ - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    new CommandDispatcher(tagListManual.get(106)).writeSingleRegisterWithValue(autoResetDc.isChecked());

                    float delayDropDC = Float.parseFloat(delayDischargeDc.getText());
                    float timeWorkVibroDC = Float.parseFloat(timeVibroDc.getText());
                    float timePauseVibroDC = Float.parseFloat(pauseVibroDc.getText());
                    float timeDischargeDC = Float.parseFloat(timeDischargeDc.getText());
                    float minWeightDC = Float.parseFloat(minWeightDc.getText());
                    int countSilosDispensersDC = Integer.parseInt(cementCount.getText());

                    //кол-во силосов на дозатор
                    Tag tag = tagListOptions.get(19);
                    tag.setIntValueIf(countSilosDispensersDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Мин вес.
                    tag = tagListOptions.get(39);
                    tag.setRealValueIf((long) minWeightDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время разгрузки
                    tag = tagListOptions.get(43);
                    timeDischargeDC *= 1000;
                    tag.setDIntValueIf((long) timeDischargeDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время работы вибратора ДЦ
                    tag = tagListOptions.get(78);
                    timeWorkVibroDC *= 1000;
                    tag.setDIntValueIf((long) timeWorkVibroDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время паузы вибратора ДЦ
                    tag = tagListOptions.get(79);
                    timePauseVibroDC *= 1000;
                    tag.setDIntValueIf((long) timePauseVibroDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    // Задержка на сброс
                    tag = tagListOptions.get(88);
                    delayDropDC *= 1000;
                    tag.setDIntValueIf((long) delayDropDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Включение вибратора
                    tag = tagListOptions.get(123);
                    new CommandDispatcher(tag).writeSingleRegisterWithValue((permissionStartVibroDc.isChecked()));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "silosCounter", cementCount.getText());
            Toast.makeText(getContext(), "ДЦ - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}