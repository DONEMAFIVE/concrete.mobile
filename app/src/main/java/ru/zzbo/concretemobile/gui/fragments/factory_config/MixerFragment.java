package ru.zzbo.concretemobile.gui.fragments.factory_config;

import static ru.zzbo.concretemobile.utils.Constants.answer;
import static ru.zzbo.concretemobile.utils.Constants.tagListMain;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class MixerFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.mixer_preferences, rootKey);

//        EditTextPreference mixerCapacity = findPreference("mixer_capacity");   //
        SwitchPreferenceCompat dischargeConveyor = findPreference("discharge_conveyor");        //Конвейер выгрузки
        EditTextPreference timeDischargeConveyor = findPreference("time_discharge_conveyor");   //Время выгрузки к.

        EditTextPreference openShiber = findPreference("open_shiber");                          //Время открытия шибера

        SwitchPreferenceCompat impulseDischarge = findPreference("impulse_discharge");          //Импульсная разгрузка смесителя
        EditTextPreference closeShiber = findPreference("close_shiber");                        //Время закрытия шибера
        EditTextPreference countOpenShiber = findPreference("count_open_shiber");               //Кол-во открытий шибера

        EditTextPreference operAutoGreaser = findPreference("oper_auto_greaser");               //Время работы авто смазчика
        EditTextPreference pauseGreaser = findPreference("pause_greaser");                      //Время паузы авто смазчика

        EditTextPreference waitDischarge = findPreference("wait_discharge");                    //Время до аварийного предупреждения ожидания разгрузки, сек

        SwitchPreferenceCompat hydroGate = findPreference("hydro_gate");                        //2-х катушкчный привод

        SwitchPreferenceCompat openMiddleShiber = findPreference("open_middle_shiber");                 //Откр. смесителя до середины
        EditTextPreference timeOpenMiddleShiber = findPreference("time_open_middle_shiber");            //Время откр
        EditTextPreference timePauseOpenMiddleShiber = findPreference("time_pause_open_middle_shiber"); //Время паузы

        Preference saveBtn = findPreference("saveBtn");

        ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();

        new Thread(() -> {
            try {
                new Handler(Looper.getMainLooper()).post(() -> {

//                    mixerCapacity.setText(String.valueOf(answer.get(113).getRealValueIf()));

                    if (answer.get(9).getIntValueIf() == 1) dischargeConveyor.setChecked(true);
                    float current = answer.get(100).getDIntValueIf();
                    timeDischargeConveyor.setText(String.valueOf(current / 1000));

                    current = answer.get(75).getDIntValueIf();
                    openShiber.setText(String.valueOf(current / 1000));

                    if (answer.get(65).getIntValueIf() == 1) impulseDischarge.setChecked(true);

                    if (answer.get(65).getIntValueIf() == 1) {
                        current = answer.get(90).getDIntValueIf();
                        openShiber.setText(String.valueOf(current / 1000));
                    }
                    current = answer.get(91).getDIntValueIf();
                    closeShiber.setText(String.valueOf(current / 1000));

                    countOpenShiber.setText(String.valueOf(answer.get(52).getIntValueIf()));

                    current = answer.get(96).getDIntValueIf();
                    operAutoGreaser.setText(String.valueOf(current / 1000));

                    current = answer.get(97).getDIntValueIf();
                    pauseGreaser.setText(String.valueOf(current / 1000));

                    current = answer.get(105).getDIntValueIf();
                    waitDischarge.setText(String.valueOf(current / 1000));

                    if (answer.get(10).getIntValueIf() == 1) hydroGate.setChecked(true);

                    if (answer.get(11).getIntValueIf() == 1)  openMiddleShiber.setChecked(true);

                    current = answer.get(103).getDIntValueIf();
                    timeOpenMiddleShiber.setText(String.valueOf(current / 1000));

                    current = answer.get(104).getDIntValueIf();
                    timePauseOpenMiddleShiber.setText(String.valueOf(current / 1000));

                });
            } catch (Exception ex3) {
                ex3.printStackTrace();
            }
        }).start();

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory)findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

//                    Tag tag = tagListOptions.get(0);
//                    tag.setRealValueIf(Float.parseFloat(mixerCapacity.getText()));
//                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Импульсная разгрузка смесителя
                    new CommandDispatcher(tagListMain.get(135)).writeSingleRegisterWithValue(impulseDischarge.isChecked());

                    //Конвейер выгрузки
                    new CommandDispatcher(tagListOptions.get(107)).writeSingleRegisterWithValue(dischargeConveyor.isChecked());

                    float timeDropMixer = Float.parseFloat(openShiber.getText());
                    float timeDropConveyor = Float.parseFloat(timeDischargeConveyor.getText());
                    float timeDropMixerImpulse = Float.parseFloat(openShiber.getText());
                    float timeImpulseCloseDrop = Float.parseFloat(closeShiber.getText());
                    float countOpenMixer = Float.parseFloat(countOpenShiber.getText());
                    float timeWorkOiler = Float.parseFloat(operAutoGreaser.getText());
                    float timeWaitOiler = Float.parseFloat(pauseGreaser.getText());
                    float timeWaitDischargeMixer = Float.parseFloat(waitDischarge.getText());
                    float timeOpenMiddleMixer = Float.parseFloat(timeOpenMiddleShiber.getText());
                    float timePauseInMiddleMixer = Float.parseFloat(timePauseOpenMiddleShiber.getText());

                    //Вр. выгрузки конвейера
                    Tag tag = tagListOptions.get(110);
                    timeDropConveyor *= 1000;
                    tag.setDIntValueIf((long) timeDropConveyor);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время открытия шибера
                    tag = tagListOptions.get(35);
                    timeDropMixer *= 1000;
                    tag.setDIntValueIf((long) timeDropMixer);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время открытия шибера при имп выгр бетона
                    tag = tagListOptions.get(76);
                    timeDropMixerImpulse *= 1000;
                    tag.setDIntValueIf((long) timeDropMixerImpulse);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время закр затвора при имп выгр бетона
                    tag = tagListOptions.get(77);
                    timeImpulseCloseDrop *= 1000;
                    tag.setDIntValueIf((long) timeImpulseCloseDrop);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Количество открытий шибера
                    tag = tagListOptions.get(109);
                    tag.setIntValueIf((int) countOpenMixer);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время работы автосмазчика
                    tag = tagListOptions.get(82);
                    timeWorkOiler *= 1000;
                    tag.setDIntValueIf((int) timeWorkOiler);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время паузы автосмазчика
                    tag = tagListOptions.get(83);
                    timeWaitOiler *= 1000;
                    tag.setDIntValueIf((int) timeWaitOiler);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время ожидания разгрузки смесителя
                    tag = tagListOptions.get(116);
                    timeWaitDischargeMixer *= 1000;
                    tag.setDIntValueIf((int) timeWaitDischargeMixer);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Гидрозатвор
                    new CommandDispatcher(tagListOptions.get(108)).writeSingleRegisterWithValue(hydroGate.isChecked());

                    //Откр. смесителя до середины
                    new CommandDispatcher(tagListOptions.get(113)).writeSingleRegisterWithValue(openMiddleShiber.isChecked());

                    //Время откр. до середины
                    tag = tagListOptions.get(114);
                    timeOpenMiddleMixer *= 1000;
                    tag.setDIntValueIf((int) timeOpenMiddleMixer);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //Время паузы на середине
                    tag = tagListOptions.get(115);
                    timePauseInMiddleMixer *= 1000;
                    tag.setDIntValueIf((int) timePauseInMiddleMixer);
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "hydroGate", String.valueOf(hydroGate.isChecked()));
                    new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "dropConveyor", String.valueOf(dischargeConveyor.isChecked()));

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ((PreferenceCategory)findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }

            }).start();
            Toast.makeText(getContext(), "Смеситель - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}