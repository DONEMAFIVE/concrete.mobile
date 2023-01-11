package ru.zzbo.concretemobile.gui.fragments.factory_config;

import static ru.zzbo.concretemobile.utils.Constants.answer;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;


import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class OtherFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.other_preferences, rootKey);

        SwitchPreferenceCompat autoQueue = findPreference("auto_queue");
        SeekBarPreference dk = findPreference("dk");
        SeekBarPreference water = findPreference("water");
        SeekBarPreference chemy = findPreference("chemy");
        SeekBarPreference cement = findPreference("cement");
        Preference saveBtn = findPreference("saveBtn");
        ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();

        //Установка максимального значения
        dk.setMax(3);
        water.setMax(3);
        chemy.setMax(3);
        cement.setMax(3);

        //Установка значений по умолчанию
        dk.setValue(0);
        water.setValue(0);
        chemy.setValue(0);
        cement.setValue(0);

        new Thread(() -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    if (answer.get(66).getIntValueIf() == 1) autoQueue.setChecked(true);
                    dk.setValue(answer.get(39).getIntValueIf());
                    chemy.setValue(answer.get(40).getIntValueIf());
                    water.setValue(answer.get(41).getIntValueIf());
                    cement.setValue(answer.get(42).getIntValueIf());

                } catch (NullPointerException ex) {
                    Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();

        saveBtn.setOnPreferenceClickListener(e-> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory)findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);
                    if (autoQueue.isChecked()) {
                        new CommandDispatcher(tagListManual.get(107)).writeSingleRegisterWithValue(true);
                        Thread.sleep(100);

                        int queueDKManual = dk.getValue();
                        int queueDChManual = chemy.getValue();
                        int queueDCManual = cement.getValue();
                        int queueDWManual = water.getValue();

                        Tag comboTag = tagListOptions.get(84);
                        comboTag.setIntValueIf(queueDKManual);
                        new CommandDispatcher(comboTag).writeSingleRegisterWithLock();
                        Thread.sleep(100);

                        comboTag = tagListOptions.get(85);
                        comboTag.setIntValueIf(queueDChManual);
                        new CommandDispatcher(comboTag).writeSingleRegisterWithLock();
                        Thread.sleep(100);

                        comboTag = tagListOptions.get(86);
                        comboTag.setIntValueIf(queueDWManual);
                        new CommandDispatcher(comboTag).writeSingleRegisterWithLock();
                        Thread.sleep(100);

                        comboTag = tagListOptions.get(87);
                        comboTag.setIntValueIf(queueDCManual);
                        new CommandDispatcher(comboTag).writeSingleRegisterWithLock();
                        Thread.sleep(100);

                    }
                    if (!autoQueue.isChecked()) {
                        new CommandDispatcher(tagListManual.get(107)).writeSingleRegisterWithValue(false);
                        Thread.sleep(100);
                    }

                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(getContext(), "Общее - Сохранено", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }
            }).start();
            return true;
        });
    }

}