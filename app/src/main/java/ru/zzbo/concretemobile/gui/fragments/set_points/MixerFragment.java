package ru.zzbo.concretemobile.gui.fragments.set_points;

import static ru.zzbo.concretemobile.utils.Constants.factoryComplectation;
import static ru.zzbo.concretemobile.utils.Constants.optionsController;
import static ru.zzbo.concretemobile.utils.Constants.tagListMain;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
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
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.helpers.FactoryComplectationBuilder;
import ru.zzbo.concretemobile.models.MasterFactoryComplectation;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.LoadingPreference;

public class MixerFragment extends PreferenceFragmentCompat {
    SwitchPreferenceCompat humidityMixerSensor, funnelVibrator, dischargeConveyor, impulseDischarge,
            hydroGate, openMiddleShiber, useSensorMiddleMixer, noSensorShiberCheckerOpen,
            noSensorShiberCheckerClose;
    EditTextPreference timeWorkFv, timePauseFv, timeDischargeConveyor, openShiber, closeShiber,
            countOpenShiber, operAutoGreaser, pauseGreaser, waitDischarge, timeOpenMiddleShiber,
            timePauseOpenMiddleShiber, mixerCapacity;
    Preference saveBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.mixer_preferences, rootKey);
        initFirst();
        initThread();
        initActions();
    }

    private void initFirst() {
        humidityMixerSensor = findPreference("humidity_mixer_sensor");                          //Датчик влажности

        dischargeConveyor = findPreference("discharge_conveyor");                               //Конвейер выгрузки
        timeDischargeConveyor = findPreference("time_discharge_conveyor");                      //Время выгрузки к.

        funnelVibrator = findPreference("funnel_vibrator");                                     //Вибратор воронки
        timeWorkFv = findPreference("time_work_fv");                                            //Время работы
        timePauseFv = findPreference("time_pause_fv");                                          //Время паузы

        impulseDischarge = findPreference("impulse_discharge");                                 //Импульсная разгрузка смесителя
        openShiber = findPreference("open_shiber");                                             //Время открытия шибера
        closeShiber = findPreference("close_shiber");                                           //Время закрытия шибера
        countOpenShiber = findPreference("count_open_shiber");                                  //Кол-во открытий шибера

        operAutoGreaser = findPreference("oper_auto_greaser");                                  //Время работы авто смазчика
        pauseGreaser = findPreference("pause_greaser");                                         //Время паузы авто смазчика

        waitDischarge = findPreference("wait_discharge");                                       //Время до аварийного предупреждения ожидания разгрузки, сек

        hydroGate = findPreference("hydro_gate");                                               //2-х катушкчный привод

        openMiddleShiber = findPreference("open_middle_shiber");                                //Откр. смесителя до середины
        useSensorMiddleMixer = findPreference("use_sensor_middle_mixer");                       //по датчику
        timeOpenMiddleShiber = findPreference("time_open_middle_shiber");                       //Время откр
        timePauseOpenMiddleShiber = findPreference("time_pause_open_middle_shiber");            //Время паузы

        mixerCapacity = findPreference("mixer_capacity");                                       //Объем смесителя

        noSensorShiberCheckerOpen = findPreference("no_sensor_shiber_shecker_open");            //Использовать датчик открытого положения шибера
        noSensorShiberCheckerClose = findPreference("no_sensor_shiber_shecker_close");          //Использовать датчик закрытого положения шибера

        saveBtn = findPreference("saveBtn");

        ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();

    }

    private void initActions() {
        timeWorkFv.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timePauseFv.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeDischargeConveyor.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        openShiber.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        closeShiber.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        countOpenShiber.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        operAutoGreaser.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        pauseGreaser.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        waitDischarge.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timeOpenMiddleShiber.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        timePauseOpenMiddleShiber.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));
        mixerCapacity.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));

        if (funnelVibrator != null) {
            funnelVibrator.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                timeWorkFv.setVisible(isEnabled);
                timePauseFv.setVisible(isEnabled);
                return true;
            });
        }
        if (dischargeConveyor != null) {
            dischargeConveyor.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                timeDischargeConveyor.setVisible(isEnabled);
                return true;
            });
        }
        if (dischargeConveyor != null) {
            dischargeConveyor.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                timeDischargeConveyor.setVisible(isEnabled);
                return true;
            });
        }
        if (openMiddleShiber != null) {
            openMiddleShiber.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                useSensorMiddleMixer.setVisible(isEnabled);
                timeOpenMiddleShiber.setVisible(isEnabled);
                timePauseOpenMiddleShiber.setVisible(isEnabled);
                return true;
            });
        }
        if (impulseDischarge != null) {
            impulseDischarge.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                closeShiber.setVisible(isEnabled);
                countOpenShiber.setVisible(isEnabled);
                return true;
            });
        }

        saveBtn.setOnPreferenceClickListener(e -> {
            new Thread(() -> {
                try {
                    ((PreferenceCategory) findPreference("pref_key_loading")).addPreference(new LoadingPreference(getActivity()));
                    findPreference("saveCategory").setVisible(false);

                    Tag tag = tagListOptions.get(0);
                    tag.setRealValueIf(Float.parseFloat(mixerCapacity.getText()));
                    new CommandDispatcher(tag).writeSingleRegisterWithLock();

                    //TODO humidityMixerSensor
                    humidityMixerSensor.setChecked(factoryComplectation.isHumidityMixerSensor());

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
                    tag = tagListOptions.get(110);
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

                    if (useSensorMiddleMixer.isChecked())
                        new CommandDispatcher(tagListManual.get(187)).writeSingleRegisterWithValue(false);
                    else
                        new CommandDispatcher(tagListManual.get(187)).writeSingleRegisterWithValue(true);

                    if (noSensorShiberCheckerOpen.isChecked()) {
                        new CommandDispatcher(tagListManual.get(179)).writeSingleRegisterWithValue(true);
                    } else {
                        if (!humidityMixerSensor.isChecked())
                            new CommandDispatcher(tagListManual.get(179)).writeSingleRegisterWithValue(false);
                    }

                    if (noSensorShiberCheckerClose.isChecked()) {
                        new CommandDispatcher(tagListManual.get(178)).writeSingleRegisterWithValue(true);
                    } else {
                        if (!humidityMixerSensor.isChecked())
                            new CommandDispatcher(tagListManual.get(178)).writeSingleRegisterWithValue(false);
                    }

                    new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "hydroGate", String.valueOf(hydroGate.isChecked()));
                    new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "dropConveyor", String.valueOf(dischargeConveyor.isChecked()));
                    new DBUtilUpdate(getContext()).updateParameterTypeTable(DBConstants.TABLE_NAME_FACTORY_COMPLECTATION, "humidityMixerSensor", humidityMixerSensor.isChecked() + "");

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ((PreferenceCategory) findPreference("pref_key_loading")).removeAll();
                    findPreference("saveCategory").setVisible(true);
                }

            }).start();
            Toast.makeText(getContext(), "Смеситель - Сохранено", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void initThread() {
        new Thread(() -> {
            try {
                optionsController.initTags(getContext());
                optionsController.readValues();

                new Handler(Looper.getMainLooper()).post(() -> {
                    mixerCapacity.setText(String.valueOf(optionsController.getMixCapacity()));

                    dischargeConveyor.setChecked(optionsController.getUseDropConveyorOption() == 1);
                    timeDischargeConveyor.setText(String.valueOf((float) optionsController.getTimeDropUnloadConveyor() / 1000));

                    humidityMixerSensor.setChecked(factoryComplectation.isHumidityMixerSensor());

                    //todo
                    if (optionsController.getUseImpulseModeForShiberMixerOption() == 1) {
                        impulseDischarge.setChecked(true);
                        openShiber.setText(String.valueOf((float) optionsController.getTimeOpenImpulseDK() / 1000));
                    } else {
                        openShiber.setText(String.valueOf((float) optionsController.getTimeDropMixer() / 1000));
                    }

                    closeShiber.setText(String.valueOf((float) optionsController.getTimeCloseImpulseDK() / 1000));
                    countOpenShiber.setText(String.valueOf(optionsController.getCountUnloadingMixer()));

                    operAutoGreaser.setText(String.valueOf((float) optionsController.getTimeWorkAutoOilMixer() / 1000));
                    pauseGreaser.setText(String.valueOf((float) optionsController.getTimeWaitAutoOilMixer() / 1000));

                    waitDischarge.setText(String.valueOf((float) optionsController.getTimeWaitingOpenMixer() / 1000));

                    hydroGate.setChecked(optionsController.getHydroGateOption() == 1);

                    useSensorMiddleMixer.setChecked(optionsController.getSensorMiddleMixer() == 0);

                    openMiddleShiber.setChecked(optionsController.getOpenMiddleSensorShiberMixerOption() == 1);

                    timeOpenMiddleShiber.setText(String.valueOf((float) optionsController.getTimeOpenMiddleSensorShiberMixer() / 1000));
                    timePauseOpenMiddleShiber.setText(String.valueOf((float) optionsController.getTimeWaitMiddleSensorShiberMixer() / 1000));

                    noSensorShiberCheckerOpen.setChecked(optionsController.getNoSensorShiberOpen() == 1);
                    noSensorShiberCheckerClose.setChecked(optionsController.getNoSensorShiberClose() == 1);

                    funnelVibrator.setChecked(optionsController.getOptionVibroFunnel() == 1);
                    float curr = optionsController.getTimeWorkVibroFunnel();
                    curr /=1000;

                    timeWorkFv.setText(String.valueOf(curr));

                    curr = optionsController.getTimePauseVibroFunnel();
                    curr /=1000;

                    timePauseFv.setText(String.valueOf(curr));

                    timeDischargeConveyor.setVisible(dischargeConveyor.isChecked());
                    closeShiber.setVisible(impulseDischarge.isChecked());
                    countOpenShiber.setVisible(impulseDischarge.isChecked());
                    useSensorMiddleMixer.setVisible(openMiddleShiber.isChecked());
                    timeOpenMiddleShiber.setVisible(openMiddleShiber.isChecked());
                    timePauseOpenMiddleShiber.setVisible(openMiddleShiber.isChecked());
                    timeWorkFv.setVisible(funnelVibrator.isChecked());
                    timePauseFv.setVisible(funnelVibrator.isChecked());
                });
            } catch (Exception ex3) {
                ex3.printStackTrace();
            }
        }).start();
    }

}