package ru.zzbo.concretemobile.gui.fragments.factory_config;

import static ru.zzbo.concretemobile.utils.Constants.answer;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.Toast;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

public class DKFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dk_preferences, rootKey);

        SwitchPreferenceCompat autoZeroDk = findPreference("auto_zero_dk");
        ListPreference transporterType = findPreference("transporter_type_lp");
        EditTextPreference dkCount = findPreference("dk_count");
        EditTextPreference flapPulse = findPreference("flap_pulse");
        EditTextPreference flapPause = findPreference("flap_pause");
        EditTextPreference minWeightDk = findPreference("min_weight_dk");
        EditTextPreference timeDischargeDk = findPreference("time_discharge_dk");
        EditTextPreference timeVibroOper = findPreference("time_vibro_oper");
        EditTextPreference timeVibroPause = findPreference("time_vibro_pause");
        EditTextPreference timeBeltOperDk = findPreference("time_belt_oper_dk");
        EditTextPreference timeBeltPauseDk = findPreference("time_belt_pause_dk");

        SwitchPreference hopper1 = findPreference("hopper1");
        SwitchPreference hopper2 = findPreference("hopper2");
        SwitchPreference hopper3 = findPreference("hopper3");
        SwitchPreference hopper4 = findPreference("hopper4");

        Preference saveBtn = findPreference("saveBtn");

        //Установка формата ввода "NUMBER"
        dkCount.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        flapPulse.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        flapPause.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        minWeightDk.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        timeDischargeDk.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        timeVibroOper.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        timeVibroPause.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        timeBeltOperDk.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));
        timeBeltPauseDk.setOnBindEditTextListener(e -> e.setInputType(InputType.TYPE_CLASS_NUMBER));

        //TODO Прочитать из PLC
        new Thread(() -> {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        if (answer.get(70).getIntValueIf() == 1) autoZeroDk.setChecked(true);
                        float current = answer.get(25).getIntValueIf();
                        switch ((int) current) {
                            case 0: transporterType.setValueIndex(0);break;
                            case 11: transporterType.setValueIndex(2);break;
                            case 12: transporterType.setValueIndex(1);break;
                        }

                        dkCount.setText(String.valueOf(answer.get(26).getIntValueIf()));
                        current = answer.get(71).getDIntValueIf();
                        flapPulse.setText(String.valueOf(current / 1000));
                        current = answer.get(72).getDIntValueIf();
                        flapPause.setText(String.valueOf(current / 1000));
                        current = answer.get(116).getRealValueIf();
                        minWeightDk.setText(String.valueOf(current));
                        current = answer.get(78).getDIntValueIf();
                        timeDischargeDk.setText(String.valueOf(current / 1000));
                        current = answer.get(86).getDIntValueIf();
                        timeVibroOper.setText(String.valueOf(current / 1000));
                        current = answer.get(87).getDIntValueIf();
                        timeVibroPause.setText(String.valueOf(current / 1000));
                        current = answer.get(94).getDIntValueIf();
                        timeBeltOperDk.setText(String.valueOf(current / 1000));
                        current = answer.get(95).getDIntValueIf();
                        timeBeltPauseDk.setText(String.valueOf(current / 1000));

                        if (answer.get(0).getIntValueIf() == 1) hopper1.setChecked(true);
                        if (answer.get(1).getIntValueIf() == 1) hopper2.setChecked(true);
                        if (answer.get(2).getIntValueIf() == 1) hopper3.setChecked(true);
                        if (answer.get(3).getIntValueIf() == 1) hopper4.setChecked(true);

                        Toast.makeText(getContext(), "ДК - Загружено", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException ex) {
                        Toast.makeText(getContext(), "ДК - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                    }
                });
        }).start();

        saveBtn.setOnPreferenceClickListener(e -> {
            //TODO собрать и записать в PLC
            new Thread(() -> {
                try {
                    new CommandDispatcher(tagListManual.get(103)).writeSingleRegisterWithValue(autoZeroDk.isChecked());

                    //Тип транспортера инертных
                    int transportType = 0;
                    switch (transporterType.findIndexOfValue(transporterType.getValue())) {
                        case 0: transportType = 0; break;
                        case 1: transportType = 12; break;
                        case 2: transportType = 11; break;
                    }
                    Tag transportTypeTag = tagListOptions.get(14);
                    transportTypeTag.setIntValueIf(transportType);
                    new CommandDispatcher(transportTypeTag).writeSingleRegisterWithLock();

                    //кол-во бункеров
                    int valueCountBuncker = Integer.valueOf(dkCount.getText());
                    Tag dkCountTag = tagListOptions.get(15);
                    dkCountTag.setIntValueIf(valueCountBuncker);
                    new CommandDispatcher(dkCountTag).writeSingleRegisterWithLock();

                    float timeDischargeDK = Float.parseFloat(timeDischargeDk.getText());
                    float timeVibroDKPowerUp = Float.parseFloat(timeVibroOper.getText());
                    float timeVibroDKPowerDown = Float.parseFloat(timeVibroPause.getText());
                    float minWeightDK = Float.parseFloat(minWeightDk.getText());

                    float timeWorkImpulse = Float.parseFloat(flapPulse.getText());
                    float timePauseImpulse = Float.parseFloat(flapPause.getText());

                    //Импульс, сек
                    Tag tag = tagListOptions.get(30);
                    timeWorkImpulse *= 1000;
                    tag.setDIntValueIf((long) timeWorkImpulse);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Пауза, сек
                    tag = tagListOptions.get(31);
                    timePauseImpulse *= 1000;
                    tag.setDIntValueIf((long) timePauseImpulse);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Мин. вес ДИ
                    tag = tagListOptions.get(38);
                    tag.setRealValueIf((long) minWeightDK);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время включения работы вибратора ДК
                    tag = tagListOptions.get(50);
                    timeVibroDKPowerUp *= 1000;
                    tag.setDIntValueIf((long) timeVibroDKPowerUp);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время паузы вибратора ДК
                    tag = tagListOptions.get(51);
                    timeVibroDKPowerDown *= 1000;
                    tag.setDIntValueIf((long) timeVibroDKPowerDown);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время работы ДК - Лента
                    float current = Float.parseFloat(timeBeltOperDk.getText().trim());
                    current *= 1000;
                    int timeWorkLineDK = (int) current;
                    tag = tagListOptions.get(80);
                    tag.setDIntValueIf(timeWorkLineDK);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время паузы ДК - Лента
                    current = Float.parseFloat(timeBeltPauseDk.getText().trim());
                    current *= 1000;
                    int timeStopLineDK = (int) current;
                    tag = tagListOptions.get(81);
                    tag.setDIntValueIf(timeStopLineDK);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время разгрузки
                    tag = tagListOptions.get(42);
                    timeDischargeDK *= 1000;
                    tag.setDIntValueIf((long) timeDischargeDK);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();


                    //Разделение бункеров
                    tag = tagListOptions.get(6);
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(hopper1.isChecked());

                    tag = tagListOptions.get(7);
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(hopper2.isChecked());

                    tag = tagListOptions.get(8);
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(hopper3.isChecked());

                    tag = tagListOptions.get(9);
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(hopper4.isChecked());

                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getContext(), "ДК - Сохранено", Toast.LENGTH_SHORT).show());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();

            DBUtilUpdate dbUtilUpdate = new DBUtilUpdate(getContext());
            dbUtilUpdate.updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "inertBunckerCounter", dkCount.getText());
            dbUtilUpdate.updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "transporterType", convertTransporterType(transporterType.findIndexOfValue(transporterType.getValue())) + "");

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putBoolean("hopper1", hopper1.isChecked());
            prefEditor.putBoolean("hopper2", hopper2.isChecked());
            prefEditor.putBoolean("hopper3", hopper3.isChecked());
            prefEditor.putBoolean("hopper4", hopper4.isChecked());
            prefEditor.apply();
            return true;
        });

    }

    public int convertTransporterType(int val) {
        switch (val) {
            case 1: val = 12; break;
            case 2: val = 11; break;
        }
        return val;
    }
}