package ru.zzbo.concretemobile.gui.fragments.set_points;

import static ru.zzbo.concretemobile.utils.Constants.factoryComplectation;
import static ru.zzbo.concretemobile.utils.Constants.optionsController;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.helpers.FactoryComplectationBuilder;
import ru.zzbo.concretemobile.models.MasterFactoryComplectation;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class WaterFragment extends PreferenceFragmentCompat {
    SwitchPreferenceCompat autoResetDw, water2;
    ListPreference typeDw;

    EditTextPreference minWeight, timeDischarge, delayOnPumpDischarge, delayOffValveDischarge,
            delayOnPumpPouring, delayOffValvePouring, delayDischarge;
    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dw_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }

    private void initActions() {
        minWeight.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayOnPumpDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayOffValveDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayOnPumpPouring.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayOffValvePouring.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));

        saveBtn.setOnPreferenceClickListener(e -> {
            //TODO собрать и записать в PLC
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

                    new CommandDispatcher(tagListManual.get(104)).writeSingleRegisterWithValue(autoResetDw.isChecked());

                    //TODO water2
                    water2.setChecked(factoryComplectation.isWater2());


                    Tag tag = tagListOptions.get(10);
                    boolean dispenserType1DW = false;
                    if (typeDw.findIndexOfValue(typeDw.getValue()) == 1) dispenserType1DW = true;
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(dispenserType1DW);

                    //Мин вес
                    tag = tagListOptions.get(41);
                    tag.setRealValueIf(Float.parseFloat(minWeight.getText()));
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время слива
                    tag = tagListOptions.get(45);
                    float timeDischargeDW = Float.parseFloat(timeDischarge.getText());
                    timeDischargeDW *= 1000;
                    tag.setDIntValueIf((long) timeDischargeDW);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //задержка вкл. насоса слива воды
                    tag = tagListOptions.get(48);
                    float delayOnPumpDischargeDW = Float.parseFloat(delayOnPumpDischarge.getText());
                    delayOnPumpDischargeDW *= 1000;
                    tag.setDIntValueIf((long) delayOnPumpDischargeDW);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //задержка откл. клапана слива воды
                    tag = tagListOptions.get(49);
                    float delayOffFlapDischargeDW = Float.parseFloat(delayOnPumpDischarge.getText());
                    delayOffFlapDischargeDW *= 1000;
                    tag.setDIntValueIf((long) delayOffFlapDischargeDW);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Задержка на сброс
                    tag = tagListOptions.get(89);
                    float timeWaitDropDW = Float.parseFloat(delayDischarge.getText());
                    timeWaitDropDW *= 1000;
                    tag.setDIntValueIf((long) timeWaitDropDW);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //задержка вкл. насоса налива
                    tag = tagListOptions.get(111);
                    float delayOnPumpFillDW = Float.parseFloat(delayOnPumpPouring.getText());
                    delayOnPumpFillDW *= 1000;
                    tag.setDIntValueIf((long) delayOnPumpFillDW);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //задержка откл. клапана налива
                    tag = tagListOptions.get(112);
                    float delayOffPumpFillDW = Float.parseFloat(delayOffValvePouring.getText());
                    delayOffPumpFillDW *= 1000;
                    tag.setDIntValueIf((long) delayOffPumpFillDW);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }

            }).start();

            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "water2", water2.isChecked() + "");
            Toast.makeText(getContext(), "ДВ - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void initThread() {
        new Thread(() -> {
            try {
                optionsController.initTags(getContext());
                optionsController.readValues();

                new Handler(Looper.getMainLooper()).post(() -> {
                    water2.setChecked(factoryComplectation.isWater2());

                    typeDw.setValueIndex(optionsController.getTypeDispenserWater1());
                    minWeight.setText(String.valueOf(optionsController.getWeightCountDownDW()));
                    timeDischarge.setText(String.valueOf((float) optionsController.getTimeRunAfterMixWeightDW() / 1000));
                    delayOnPumpDischarge.setText(String.valueOf((float) optionsController.getTimeoutOnPumpDropW() / 1000));
                    delayOffValveDischarge.setText(String.valueOf((float) optionsController.getTimeoutOffPumpDropW() / 1000));
                    delayDischarge.setText(String.valueOf((float) optionsController.getTimeoutDropDW() / 1000));
                    delayOnPumpPouring.setText(String.valueOf((float) optionsController.getTimeoutStartPumpDW() / 1000));
                    delayOffValvePouring.setText(String.valueOf((float) optionsController.getTimeoutStopPumpDW() / 1000));

                });

            } catch (NullPointerException ex) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), "ДВ - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void initFirst() {
        autoResetDw = findPreference("auto_reset_dw");
        water2 = findPreference("water2");
        typeDw = findPreference("type_dw");
        minWeight = findPreference("min_weight");
        timeDischarge = findPreference("time_discharge");
        delayOnPumpDischarge = findPreference("delay_on_pump_discharge");
        delayOffValveDischarge = findPreference("delay_off_valve_discharge");
        delayOnPumpPouring = findPreference("delay_on_pump_pouring");
        delayOffValvePouring = findPreference("delay_off_valve_pouring");
        delayDischarge = findPreference("delay_discharge");
        saveBtn = findPreference("saveBtn");

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
    }
}