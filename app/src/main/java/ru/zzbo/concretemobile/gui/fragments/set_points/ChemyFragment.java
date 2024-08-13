package ru.zzbo.concretemobile.gui.fragments.set_points;

import static ru.zzbo.concretemobile.utils.Constants.optionsController;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
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
    EditTextPreference chemyCount, minWeightDch, timeDischargeDch, delayOnPumpDischarge, delayOffValveDischarge;
    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dch_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }

    private void initFirst() {
        chemyCount = findPreference("chemy_count");
        minWeightDch = findPreference("min_weight_dch");
        timeDischargeDch = findPreference("time_discharge_dch");
        delayOnPumpDischarge = findPreference("delay_on_pump_discharge");
        delayOffValveDischarge = findPreference("delay_off_valve_discharge");
        saveBtn = findPreference("saveBtn");

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
    }

    private void initThread() {
        new Thread(() -> {
            try {
                optionsController.initTags(getContext());
                optionsController.readValues();

                new Handler(Looper.getMainLooper()).post(() -> {
                    chemyCount.setText(String.valueOf(optionsController.getCountBunckerDCh1()));
                    minWeightDch.setText(String.valueOf(optionsController.getWeightCountDownDCh()));
                    timeDischargeDch.setText(String.valueOf((float) optionsController.getTimeRunAfterMixWeightDCh() / 1000));
                    delayOnPumpDischarge.setText(String.valueOf((float) optionsController.getTimeoutPumpCh() / 1000));
                    delayOffValveDischarge.setText(String.valueOf((float) optionsController.getTimeoutValveCh() / 1000));
                });

            } catch (NullPointerException ex) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), "ДХ - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void initActions() {
        chemyCount.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        minWeightDch.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeDischargeDch.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayOnPumpDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayOffValveDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

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
                ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                findPreference("saveCategory").setVisible(true);
            }).start();
            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "chemyCounter", chemyCount.getText());
            Toast.makeText(getContext(), "ДХ - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}