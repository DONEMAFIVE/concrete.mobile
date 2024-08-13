package ru.zzbo.concretemobile.gui.fragments.set_points;

import static ru.zzbo.concretemobile.utils.Constants.optionsController;
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
    SwitchPreferenceCompat autoQueue;
    SeekBarPreference dk, water, chemy, cement;
    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.other_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }

    private void initActions() {
        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
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
                    ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }
            }).start();
            return true;
        });
    }

    private void initThread() {
        new Thread(() -> {
            optionsController.initTags(getContext());
            optionsController.readValues();

            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    if (optionsController.getUseAutoQueueUploadSourcesOption() == 1)
                        autoQueue.setChecked(true);
                    dk.setValue(optionsController.getQueueDK());
                    chemy.setValue(optionsController.getQueueDCh());
                    water.setValue(optionsController.getQueueDW());
                    cement.setValue(optionsController.getQueueDC());
                } catch (NullPointerException ex) {
                    Toast.makeText(getActivity(), "Ошибка загрузки, попробуйте снова.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void initFirst() {
        autoQueue = findPreference("auto_queue");
        dk = findPreference("dk");
        water = findPreference("water");
        chemy = findPreference("chemy");
        cement = findPreference("cement");
        saveBtn = findPreference("saveBtn");
        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();

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

    }

}