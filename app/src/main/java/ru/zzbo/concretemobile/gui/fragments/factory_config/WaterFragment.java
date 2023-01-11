package ru.zzbo.concretemobile.gui.fragments.factory_config;

import static ru.zzbo.concretemobile.utils.Constants.answer;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.db.builders.FactoryComplectationBuilder;
import ru.zzbo.concretemobile.models.Configs;
import ru.zzbo.concretemobile.models.MasterFactoryComplectation;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class WaterFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dw_preferences, rootKey);

        SwitchPreferenceCompat autoResetDw = findPreference("auto_reset_dw");
        SwitchPreferenceCompat water2 = findPreference("water2");
        SwitchPreferenceCompat humidityMixerSensor = findPreference("humidity_mixer_sensor");
        ListPreference typeDw = findPreference("type_dw");
        EditTextPreference minWeight = findPreference("min_weight");
        EditTextPreference timeDischarge = findPreference("time_discharge");
        EditTextPreference delayOnPumpDischarge = findPreference("delay_on_pump_discharge");
        EditTextPreference delayOffValveDischarge = findPreference("delay_off_valve_discharge");
        EditTextPreference delayOnPumpPouring = findPreference("delay_on_pump_pouring");
        EditTextPreference delayOffValvePouring = findPreference("delay_off_valve_pouring");
        EditTextPreference delayDischarge = findPreference("delay_discharge");
        Preference saveBtn = findPreference("saveBtn");
        ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();

        new Thread(() -> {
            try {
                new Handler(Looper.getMainLooper()).post(() -> {
                    FactoryComplectationBuilder configBuilder = new FactoryComplectationBuilder();
                    MasterFactoryComplectation factoryComplectation = configBuilder.parseList(
                            new DBUtilGet(getActivity()).getFromParameterTable("factory_complectation"));

                    if (answer.get(69).getIntValueIf() == 1) autoResetDw.setChecked(true);

                    water2.setChecked(factoryComplectation.isWater2());

                    humidityMixerSensor.setChecked(factoryComplectation.isHumidityMixerSensor());

                    typeDw.setValueIndex(answer.get(4).getIntValueIf());

                    minWeight.setText(String.valueOf(answer.get(119).getRealValueIf()));

                    float current = answer.get(81).getDIntValueIf();
                    timeDischarge.setText(String.valueOf(current / 1000));

                    current = answer.get(84).getDIntValueIf();
                    delayOnPumpDischarge.setText(String.valueOf(current / 1000));

                    current = answer.get(85).getDIntValueIf();
                    delayOffValveDischarge.setText(String.valueOf(current / 1000));

                    current = answer.get(99).getDIntValueIf();
                    delayDischarge.setText(String.valueOf(current / 1000));

                    current = answer.get(101).getDIntValueIf();
                    delayOnPumpPouring.setText(String.valueOf(current / 1000));

                    current = answer.get(102).getDIntValueIf();
                    delayOffValvePouring.setText(String.valueOf(current / 1000));

                });

            } catch (NullPointerException ex) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), "ДВ - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();

        saveBtn.setOnPreferenceClickListener(e -> {
            //TODO собрать и записать в PLC
            new Thread(() -> {
                try {
                    ((PreferenceCategory)findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

                    new CommandDispatcher(tagListManual.get(104)).writeSingleRegisterWithValue(autoResetDw.isChecked());

                   MasterFactoryComplectation factoryOptionList = new FactoryComplectationBuilder()
                            .parseList(new DBUtilGet(getActivity())
                                    .getFromParameterTable("factory_complectation"));

                    //TODO water2
                    water2.setChecked(factoryOptionList.isWater2());

                    //TODO humidityMixerSensor
                    humidityMixerSensor.setChecked(factoryOptionList.isHumidityMixerSensor());

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
                    ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }

            }).start();

            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "water2", water2.isChecked() + "");
            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "humidityMixerSensor", humidityMixerSensor.isChecked() + "");
            Toast.makeText(getContext(), "ДВ - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });

    }
}