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

public class CementFragment extends PreferenceFragmentCompat {
    EditTextPreference cementCount, minWeightDc, timeDischargeDc, timeVibroDc, pauseVibroDc, delayDischargeDc;
    SwitchPreferenceCompat permissionStartVibroDc;
    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dc_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }

    private void initFirst() {
        cementCount = findPreference("cement_count");
        minWeightDc = findPreference("min_weight_dc");
        timeDischargeDc = findPreference("time_discharge_dc");
        timeVibroDc = findPreference("time_vibro");
        pauseVibroDc = findPreference("pause_vibro");
        delayDischargeDc = findPreference("delay_discharge_dc");
        permissionStartVibroDc = findPreference("permission_start_vibro");
        saveBtn = findPreference("saveBtn");

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
    }
    private void initThread() {
        new Thread(() -> {
            try {
                optionsController.initTags(getContext());
                optionsController.readValues();

                new Handler(Looper.getMainLooper()).post(() -> {
                    cementCount.setText(String.valueOf(optionsController.getSilosCountDC1()));

                    minWeightDc.setText(String.valueOf(optionsController.getWeightCountDownDC()));

                    timeDischargeDc.setText(String.valueOf((float) optionsController.getTimeRunAfterMixWeightDC() / 1000));

                    timeVibroDc.setText(String.valueOf((float) optionsController.getTimeWorkVibroDC() / 1000));

                    pauseVibroDc.setText(String.valueOf((float) optionsController.getTimwWaitVibroDC() / 1000));

                    delayDischargeDc.setText(String.valueOf((float) optionsController.getTimeoutDropDC() / 1000));

//                    if (answer.get(16).getIntValueIf() == 1) permissionStartVibroDc.setChecked(true);

                });

            } catch (NullPointerException ex) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), "ДЦ - Ошибка загрузки", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
    private void initActions() {
        cementCount.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        minWeightDc.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeDischargeDc.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeVibroDc.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        pauseVibroDc.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        delayDischargeDc.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

                    float delayDropDC = Float.parseFloat(delayDischargeDc.getText());
                    float timeWorkVibroDC = Float.parseFloat(timeVibroDc.getText());
                    float timePauseVibroDC = Float.parseFloat(pauseVibroDc.getText());
                    float timeDischargeDC = Float.parseFloat(timeDischargeDc.getText());
                    float minWeightDC = Float.parseFloat(minWeightDc.getText());
                    int countSilosDispensersDC = Integer.parseInt(cementCount.getText());

                    //кол-во силосов на дозатор
                    Tag tag = tagListOptions.get(19);
                    tag.setIntValueIf(countSilosDispensersDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Мин вес.
                    tag = tagListOptions.get(39);
                    tag.setRealValueIf((long) minWeightDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время разгрузки
                    tag = tagListOptions.get(43);
                    timeDischargeDC *= 1000;
                    tag.setDIntValueIf((long) timeDischargeDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время работы вибратора ДЦ
                    tag = tagListOptions.get(78);
                    timeWorkVibroDC *= 1000;
                    tag.setDIntValueIf((long) timeWorkVibroDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время паузы вибратора ДЦ
                    tag = tagListOptions.get(79);
                    timePauseVibroDC *= 1000;
                    tag.setDIntValueIf((long) timePauseVibroDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    // Задержка на сброс
                    tag = tagListOptions.get(88);
                    delayDropDC *= 1000;
                    tag.setDIntValueIf((long) delayDropDC);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Включение вибратора
                    tag = tagListOptions.get(123);
                    new CommandDispatcher(tag).writeSingleRegisterWithValue((permissionStartVibroDc.isChecked()));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                findPreference("saveCategory").setVisible(true);
            }).start();
            new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "silosCounter", cementCount.getText());
            Toast.makeText(getContext(), "ДЦ - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}