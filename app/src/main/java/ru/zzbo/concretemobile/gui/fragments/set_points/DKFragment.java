package ru.zzbo.concretemobile.gui.fragments.set_points;

import static ru.zzbo.concretemobile.utils.Constants.optionsController;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class DKFragment extends PreferenceFragmentCompat {
    EditTextPreference impulseDose11, impulseDose12, impulseDose21, impulseDose22, impulseDose31,
            impulseDose32, impulseDose41, impulseDose42, dkCount, flapPulse, flapPause, minWeightDk,
            timeDischargeDk, timeVibroOper, timeVibroPause, timeBeltOperDk, timeBeltPauseDk;
    ListPreference transporterType;

    SwitchPreference reverseConveyor, hopper1, hopper2, hopper3, hopper4;

    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dk_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }
    private void initFirst() {

        impulseDose11 = findPreference("impulse_dose11");
        impulseDose12 = findPreference("impulse_dose12");
        impulseDose21 = findPreference("impulse_dose21");
        impulseDose22 = findPreference("impulse_dose22");
        impulseDose31 = findPreference("impulse_dose31");
        impulseDose32 = findPreference("impulse_dose32");
        impulseDose41 = findPreference("impulse_dose41");
        impulseDose42 = findPreference("impulse_dose42");

        transporterType = findPreference("transporter_type_lp");
        dkCount = findPreference("dk_count");
        flapPulse = findPreference("flap_pulse");
        flapPause = findPreference("flap_pause");
        minWeightDk = findPreference("min_weight_dk");
        timeDischargeDk = findPreference("time_discharge_dk");
        timeVibroOper = findPreference("time_vibro_oper");
        timeVibroPause = findPreference("time_vibro_pause");
        timeBeltOperDk = findPreference("time_belt_oper_dk");
        timeBeltPauseDk = findPreference("time_belt_pause_dk");

        reverseConveyor = findPreference("reverse_conveyor");

        hopper1 = findPreference("hopper1");
        hopper2 = findPreference("hopper2");
        hopper3 = findPreference("hopper3");
        hopper4 = findPreference("hopper4");
        saveBtn = findPreference("saveBtn");

        if (optionsController.getTypeTransporterDK() != 12) {
            timeBeltOperDk.setVisible(false);
            timeBeltPauseDk.setVisible(false);
        }

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
    }
    private void initThread() {
        new Thread(() -> {
            optionsController.initTags(getContext());
            optionsController.readValues();

            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    int current = optionsController.getTypeTransporterDK();
                    switch (current) {
                        case 0:
                            transporterType.setValueIndex(0);
                            break;
                        case 11:
                            transporterType.setValueIndex(2);
                            break;
                        case 12:
                            transporterType.setValueIndex(1);
                            break;
                    }
                    impulseDose11.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser11()));
                    impulseDose12.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser12()));
                    impulseDose21.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser21()));
                    impulseDose22.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser22()));
                    impulseDose31.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser31()));
                    impulseDose32.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser32()));
                    impulseDose41.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser41()));
                    impulseDose42.setText(String.valueOf(optionsController.getPercentSwitchToImpulseModeDoser42()));

                    dkCount.setText(String.valueOf(optionsController.getBunckerCountDK()));
                    flapPulse.setText(String.valueOf((float) optionsController.getTimeOpenImpulseDK() / 1000));
                    flapPause.setText(String.valueOf((float) optionsController.getTimeCloseImpulseDK() / 1000));
                    minWeightDk.setText(String.valueOf(optionsController.getWeightCountDownDK()));
                    timeDischargeDk.setText(String.valueOf((float) optionsController.getTimeRunAfterMixWeightDK() / 1000));
                    timeVibroOper.setText(String.valueOf((float) optionsController.getTimeWorkVibroDK() / 1000));
                    timeVibroPause.setText(String.valueOf((float) optionsController.getTimeWaitVibroDK() / 1000));
                    timeBeltOperDk.setText(String.valueOf((float) optionsController.getTimeWorkHorConvQBOnly() / 1000));
                    timeBeltPauseDk.setText(String.valueOf((float) optionsController.getTimeWaitHorConvQBOnly() / 1000));

                    if (optionsController.getSplitBuncker1() == 0) hopper1.setChecked(true);
                    if (optionsController.getSplitBuncker2() == 0) hopper2.setChecked(true);
                    if (optionsController.getSplitBuncker3() == 0) hopper3.setChecked(true);
                    if (optionsController.getSplitBuncker4() == 0) hopper4.setChecked(true);

                } catch (NullPointerException ex) {
                    Toast.makeText(getContext(), "ДК - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();

    }
    private void initActions() {
        impulseDose11.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose12.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose21.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose22.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose31.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose32.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose41.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        impulseDose42.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        dkCount.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        flapPulse.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        flapPause.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        minWeightDk.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeDischargeDk.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeVibroOper.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeVibroPause.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeBeltOperDk.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeBeltPauseDk.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));

        if (transporterType != null) {
            transporterType.setOnPreferenceChangeListener((preference, newValue) -> {
                int index = transporterType.findIndexOfValue(newValue.toString());
                if (index == 1) {
                    timeBeltOperDk.setVisible(true);
                    timeBeltPauseDk.setVisible(true);
                } else {
                    timeBeltOperDk.setVisible(false);
                    timeBeltPauseDk.setVisible(false);
                }
                Toast.makeText(getContext(), "Выбранный индекс: " + index, Toast.LENGTH_SHORT).show();
                return true;
            });
        }

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

        saveBtn.setOnPreferenceClickListener(e -> {
            //TODO собрать
            //TODO проверить на нули
            //TODO записать в PLC
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

                    //Тип транспортера инертных
                    int transportType = 0;
                    switch (transporterType.findIndexOfValue(transporterType.getValue())) {
                        case 0:
                            transportType = 0;
                            break;
                        case 1:
                            transportType = 12;
                            break;
                        case 2:
                            transportType = 11;
                            break;
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

                    int impulse11 = Integer.parseInt(impulseDose11.getText());
                    int impulse12 = Integer.parseInt(impulseDose12.getText());
                    int impulse21 = Integer.parseInt(impulseDose21.getText());
                    int impulse22 = Integer.parseInt(impulseDose22.getText());
                    int impulse31 = Integer.parseInt(impulseDose31.getText());
                    int impulse32 = Integer.parseInt(impulseDose32.getText());
                    int impulse41 = Integer.parseInt(impulseDose41.getText());
                    int impulse42 = Integer.parseInt(impulseDose42.getText());

                    Tag tag = tagListOptions.get(91);
                    tag.setIntValueIf(impulse11);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(92);
                    tag.setIntValueIf(impulse12);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(93);
                    tag.setIntValueIf(impulse21);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(94);
                    tag.setIntValueIf(impulse22);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(95);
                    tag.setIntValueIf(impulse31);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(96);
                    tag.setIntValueIf(impulse32);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(97);
                    tag.setIntValueIf(impulse41);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    tag = tagListOptions.get(98);
                    tag.setIntValueIf(impulse42);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Импульс, сек
                    tag = tagListOptions.get(30);
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

                    if (optionsController.getTypeTransporterDK() == 12) {
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
                    }


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

                    ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();

            DBUtilUpdate dbUtilUpdate = new DBUtilUpdate(getContext());
            dbUtilUpdate.updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "inertBunckerCounter", dkCount.getText());
            dbUtilUpdate.updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "transporterType", convertTransporterType(transporterType.findIndexOfValue(transporterType.getValue())) + "");

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putBoolean("reverseConveyor", reverseConveyor.isChecked());
            prefEditor.putBoolean("hopper1", hopper1.isChecked());
            prefEditor.putBoolean("hopper2", hopper2.isChecked());
            prefEditor.putBoolean("hopper3", hopper3.isChecked());
            prefEditor.putBoolean("hopper4", hopper4.isChecked());
            prefEditor.apply();

            return true;
        });

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
    }

    public int convertTransporterType(int val) {
        switch (val) {
            case 1: return 12;
            case 2: return 11;
        }
        return val;
    }
}
