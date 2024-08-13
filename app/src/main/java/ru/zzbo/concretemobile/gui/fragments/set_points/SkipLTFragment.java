package ru.zzbo.concretemobile.gui.fragments.set_points;

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
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class SkipLTFragment extends PreferenceFragmentCompat {
    SwitchPreferenceCompat noLiftWater, noLiftCement, optionAlarmSensorSkipUp;
    ListPreference liftAfter;
    EditTextPreference delayLift, dischargeSkip, alarmStopSkip, dischargeLT;
    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.skip_lt_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }

    private void initFirst() {

        noLiftWater = findPreference("no_lift_water");
        noLiftCement = findPreference("no_lift_cement");
        optionAlarmSensorSkipUp = findPreference("option_alarm_sensor_skip_up");

        liftAfter = findPreference("lift_after");

        delayLift = findPreference("delay_lift");            //Время задержки подъема
        dischargeSkip = findPreference("discharge_skip");    //Время выгрузки скипа
        alarmStopSkip = findPreference("alarm_stop_skip");   //Время до аварийной остановки
        dischargeLT = findPreference("discharge_lt");        //Время выгрузки ЛТ

        saveBtn = findPreference("saveBtn");

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
    }

    private void initThread() {
        new Thread(() -> {
            try {
                optionsController.initTags(getContext());
                optionsController.readValues();

                new Handler(Looper.getMainLooper()).post(() -> {
                    if (optionsController.getWaitForDropMixerClimbSkip() == 1)
                        liftAfter.setValueIndex(0);
                    if (optionsController.getWaitForBeginDropMixerClimbSkip() == 1)
                        liftAfter.setValueIndex(1);

                    dischargeSkip.setText(String.valueOf((float) optionsController.getTimeDropSkip() / 1000));

                    if (optionsController.getTypeTransporterDK() == 12) {
                        dischargeLT.setVisible(true);
                        //Время выгрузки ЛТ
                        dischargeLT.setText(String.valueOf((float) optionsController.getTimeWorkVerticalConveyor() / 1000));
                    } else dischargeLT.setVisible(false);

                    delayLift.setText(String.valueOf((float) optionsController.getTimeoutStartSkipToClimb() / 1000));

                    noLiftCement.setChecked(optionsController.getWaitForDCLoadClimbSkip() == 1);
                    noLiftWater.setChecked(optionsController.getWaitForDWLoadClimbSkip() == 1);

                    optionAlarmSensorSkipUp.setChecked(optionsController.getSensorAlarmUpSkip() == 1);
                    alarmStopSkip.setText(String.valueOf((float) optionsController.getAlarmTimeClimbSkip() / 1000));

                });
            } catch (Exception e12) {
                e12.printStackTrace();
            }
        }).start();
    }

    private void initActions() {
        delayLift.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        dischargeSkip.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        alarmStopSkip.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        dischargeLT.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));


        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);
                    float timeSkipDrop = Float.parseFloat(dischargeSkip.getText());
                    float timeDelaySkipUp = Float.parseFloat(delayLift.getText());

                    //Время выгрузки скипа

                    Tag tag = tagListOptions.get(52);
                    timeSkipDrop *= 1000;
                    tag.setDIntValueIf((long) timeSkipDrop);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    switch (liftAfter.findIndexOfValue(liftAfter.getValue())) {
                        case 0: {
                            new CommandDispatcher(tagListOptions.get(120)).writeSingleRegisterWithValue(true);
                            break;
                        }
                        case 1: {
                            new CommandDispatcher(tagListOptions.get(121)).writeSingleRegisterWithValue(true);
                            break;
                        }
                    }

                    if (optionsController.getTypeTransporterDK() == 12) {
                        float timeDropLT = Float.parseFloat(dischargeLT.getText());
                        tag = tagListOptions.get(125);
                        timeDropLT *= 1000;
                        tag.setDIntValueIf((long) timeDropLT);
                        new CommandDispatcher(tag).writeSingleRegisterWithLock();
                    }

                    tag = tagListOptions.get(117);
                    timeDelaySkipUp *= 1000;
                    tag.setDIntValueIf((long) timeDelaySkipUp);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();


                    new CommandDispatcher(tagListOptions.get(118)).writeSingleRegisterWithValue(noLiftCement.isChecked());
                    new CommandDispatcher(tagListOptions.get(119)).writeSingleRegisterWithValue(noLiftWater.isChecked());

                    //время аварийной остановки скипа
                    tag = tagListOptions.get(122);
                    float timeSkipAlarm = Float.parseFloat(alarmStopSkip.getText());
                    timeSkipAlarm *= 1000;
                    tag.setDIntValueIf((long) timeSkipAlarm);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    new CommandDispatcher(tagListManual.get(188)).writeSingleRegisterWithValue(optionAlarmSensorSkipUp.isChecked());

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }
            }).start();
            Toast.makeText(getContext(), "Скип/Лента - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}