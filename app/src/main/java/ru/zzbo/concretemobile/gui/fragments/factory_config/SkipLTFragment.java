package ru.zzbo.concretemobile.gui.fragments.factory_config;

import static ru.zzbo.concretemobile.utils.Constants.answer;
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
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class SkipLTFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.skip_lt_preferences, rootKey);

        SwitchPreferenceCompat noLiftWater = findPreference("no_lift_water");
        SwitchPreferenceCompat noLiftCement = findPreference("no_lift_cement");

        ListPreference liftAfter = findPreference("lift_after");

        EditTextPreference delayLift = findPreference("delay_lift");            //Время задержки подъема
        EditTextPreference dischargeSkip = findPreference("discharge_skip");    //Время выгрузки скипа
        EditTextPreference alarmStopSkip = findPreference("alarm_stop_skip");   //Время до аварийной остановки
        EditTextPreference dischargeLT = findPreference("discharge_lt");        //Время выгрузки ЛТ

        ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();

        Preference saveBtn = findPreference("saveBtn");

        new Thread(() -> {
            try {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (answer.get(14).getIntValueIf() == 1) liftAfter.setValueIndex(0);
                    if (answer.get(15).getIntValueIf() == 1) liftAfter.setValueIndex(1);

                    float current = answer.get(88).getDIntValueIf();
                    dischargeSkip.setText(String.valueOf(current / 1000));

                    current = answer.get(25).getIntValueIf();
                    if ((int) current == 12) {
                        dischargeLT.setVisible(true);
                        //Время выгрузки ЛТ
                        current = answer.get(108).getDIntValueIf();
                        dischargeLT.setText(String.valueOf(current / 1000));
                    } else dischargeLT.setVisible(false);

                    current = answer.get(106).getDIntValueIf();
                    delayLift.setText(String.valueOf(current / 1000));

                    if (answer.get(12).getIntValueIf() == 1) noLiftCement.setChecked(true);
                    if (answer.get(13).getIntValueIf() == 1) noLiftWater.setChecked(true);

                    current = answer.get(107).getDIntValueIf();
                    alarmStopSkip.setText(String.valueOf(current / 1000));

                });
            } catch (Exception e12) {
                e12.printStackTrace();
            }
        }).start();

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory)findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);
                    float timeSkipDrop = Float.parseFloat(dischargeSkip.getText());
                    float timeDelaySkipUp = Float.parseFloat(delayLift.getText());

                    //Время выгрузки скипа

                    Tag tag = tagListOptions.get(52);
                    timeSkipDrop *= 1000;
                    tag.setDIntValueIf((long) timeSkipDrop);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    switch (liftAfter.findIndexOfValue(liftAfter.getValue())) {
                        case 0:{
                            new CommandDispatcher(tagListOptions.get(120)).writeSingleRegisterWithValue(true);
                            break;
                        }
                        case 1:{
                            new CommandDispatcher(tagListOptions.get(121)).writeSingleRegisterWithValue(true);
                            break;
                        }
                    }

                    if (answer.get(25).getIntValueIf() == 12) {
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

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }
            }).start();
            Toast.makeText(getContext(), "Скип/Лента - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}