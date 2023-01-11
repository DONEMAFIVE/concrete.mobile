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
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class ChemyFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dch_preferences, rootKey);

        SwitchPreferenceCompat autoResetDch = findPreference("auto_reset_dch");
        EditTextPreference chemyCount = findPreference("chemy_count");
        EditTextPreference minWeightDch = findPreference("min_weight_dch");
        EditTextPreference timeDischargeDch = findPreference("time_discharge_dch");
        EditTextPreference delayOnPumpDischarge = findPreference("delay_on_pump_discharge");
        EditTextPreference delayOffValveDischarge = findPreference("delay_off_valve_discharge");
        Preference saveBtn = findPreference("saveBtn");

        ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();

        new Thread(() -> {
            try {

                new Handler(Looper.getMainLooper()).post(() -> {
                    if (answer.get(68).getIntValueIf() == 1) autoResetDch.setChecked(true);

                    float current = answer.get(34).getIntValueIf();
                    chemyCount.setText(String.valueOf((int) current));

                    current = answer.get(118).getRealValueIf();
                    minWeightDch.setText(String.valueOf(current));

                    current = answer.get(80).getDIntValueIf();
                    timeDischargeDch.setText(String.valueOf(current / 1000));

                    current = answer.get(82).getDIntValueIf();
                    delayOnPumpDischarge.setText(String.valueOf(current / 1000));

                    current = answer.get(83).getDIntValueIf();
                    delayOffValveDischarge.setText(String.valueOf(current / 1000));

                });

            } catch (NullPointerException ex) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), "ДХ - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory)findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);
                    new CommandDispatcher(tagListManual.get(105)).writeSingleRegisterWithValue(autoResetDch.isChecked());

                    //Кол-во химии на дозатор
                    Tag tag = tagListOptions.get(24);
                    float current = Float.parseFloat(chemyCount.getText());
                    tag.setIntValueIf((int) current);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Мин вес
                    tag = tagListOptions.get(40);
                    current = Float.parseFloat(minWeightDch.getText());
                    tag.setRealValueIf(current);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время слива
                    tag = tagListOptions.get(44);
                    current = Float.parseFloat(timeDischargeDch.getText());
                    current *= 1000;
                    tag.setDIntValueIf((int) current);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Задержка на вкл. насоса слива
                    tag = tagListOptions.get(46);
                    current = Float.parseFloat(delayOnPumpDischarge.getText());
                    current *= 1000;
                    tag.setDIntValueIf((int) current);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Задержка откл. клапана слива химии
                    tag = tagListOptions.get(47);
                    current = Float.parseFloat(delayOffValveDischarge.getText());
                    current *= 1000;
                    tag.setDIntValueIf((int) current);

                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();
                findPreference("saveCategory").setVisible(true);
            }).start();
            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "chemyCounter", chemyCount.getText());
            Toast.makeText(getContext(), "ДХ - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}