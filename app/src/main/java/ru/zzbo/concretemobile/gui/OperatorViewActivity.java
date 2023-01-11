package ru.zzbo.concretemobile.gui;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static ru.zzbo.concretemobile.utils.Constants.animationMixerState;
import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.editedOrder;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.globalFactoryState;
import static ru.zzbo.concretemobile.utils.Constants.globalModeState;
import static ru.zzbo.concretemobile.utils.Constants.hydroGateOption;
import static ru.zzbo.concretemobile.utils.Constants.globalMixStartTime;
import static ru.zzbo.concretemobile.utils.Constants.operatorLogin;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrder;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrg;
import static ru.zzbo.concretemobile.utils.Constants.selectedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.selectedTrans;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.OkHttpUtil.sendGet;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.R;

import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.db.builders.FactoryComplectationBuilder;
import ru.zzbo.concretemobile.gui.catalogs.EditOrderActivity;
import ru.zzbo.concretemobile.gui.dialogs.editing.CatalogMenuDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.MixCapacityDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.OrganizationListDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.PartyCapacityDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.RecipeLoaderDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.TransporterListDialog;
import ru.zzbo.concretemobile.models.Current;
import ru.zzbo.concretemobile.models.MasterFactoryComplectation;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.protocol.DataManager;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.reflections.ReflectionRetrieval;
import ru.zzbo.concretemobile.reporting.ReportRecordingUtil;
import ru.zzbo.concretemobile.utils.AlarmUtil;
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class OperatorViewActivity extends AppCompatActivity {
    private RadioButton silosSelector1;
    private RadioButton silosSelector2;
    private ImageView horLineView;
    private SeekBar touchUnlock;
    private ImageView touchLock;
    private DrawerLayout touchLockLayout;

    private Button closeAlarmLayoutBtn;
    private Button resetAlarmBtn;
    private DrawerLayout alarmsWarningLayout;
    private EditText alarmWarningText;

    private LinearLayout hopper1;
    private LinearLayout hopper2;
    private LinearLayout hopper3;
    private LinearLayout hopper4;

    private ImageView doser11;
    private ImageView doser12;
    private ImageView doser21;
    private ImageView doser22;
    private ImageView doser31;
    private ImageView doser32;
    private ImageView doser41;
    private ImageView doser42;
    private ImageView pumpWater;
    private ImageView pumpWater2;
    private ImageView doserShnek1;
    private ImageView doserShnek2;
    private ImageView doserDispenserCement;
    private ImageView doserDispenserWater;
    private ImageView doserDispenserChemy;
    private ImageView skipUp;
    private ImageView reportsMenu;
    private ImageView catalogsBtn;
    private ImageView buncker2View;
    private ImageView bunckerChemy1;
    private ImageView bunckerChemy2;
    private ImageView bunckerChemy3;

    private LinearLayout chemy2LL;
    private LinearLayout chemy3LL;
    private LinearLayout water2LL;
    private LinearLayout cement2LL;
    private TextView recepieName;
    private TextView doseWater1;
    private TextView recepieWater1;
    private ImageView silos2View;
    private TextView doseSilos1;
    private TextView titleSilos2;
    private TextView doseSilos2;
    private ImageView silos1View;
    private ImageView levelDownSIlos2;
    private ImageView levelUpSilos2;
    private TextView recepieSilos2;
    private TextView recepieSilos1;
    private TextView titleSilos1;
    private Button aerationSilos;
    private Button vibroSilos;
    private Button filterSilos;
    private Button recepieOptionBtn;
    private Button partyOptionBtn;
    private Button mixOptionBtn;
    private Button openMixer;
    private Button closeMixer;
    private Button manualAutoSwitcher;
    private Button stopCycle;
    private Button runCycleBtn;
    private TextView operatorName;
    private TextView mixCounterTotal;
    private TextView mixCounterCurrent;
    private TextView stateFactory;
    private TextView dailyCounter;
    private TextView titleChemy1;
    private TextView titleChemy2;
    private TextView titleChemy3;
    private TextView recepieChemy2;
    private TextView recepieChemy3;
    private TextView doseChemy2;
    private TextView doseChemy3;
    private TextView recepieChemy1;
    private TextView doseChemy1;
    private Button startSelfDK, startSelfChemy, startSelfCement, startSelfWater;
    private ImageView mixerView;
    private TextView amperage;
    private ImageView mixFully;
    private ImageView buncker1View;
    private ImageView buncker3View;
    private ImageView buncker4View;
    private CheckBox autoDropChecker;
    private ImageView skipDown;
    private ImageView skipMiddle;
    private TextView titleBuncker1;
    private TextView titleBuncker4;
    private TextView recepieBuncker4;
    private TextView doseBuncker4;
    private TextView titleBuncker3;
    private TextView titleBuncker2;
    private TextView recepieBuncker1;
    private TextView recepieBuncker2;
    private TextView doseBuncker1;
    private TextView doseBuncker2;
    private ImageView dispenserChemyView;
    private ImageView dispenserWaterView;
    private ImageView dispenserCementView;
    private ImageView doserChemy1;
    private ImageView doserChemy2;
    private ImageView doserChemy3;
    private TextView weigthDC;
    private TextView weigthWater;
    private TextView weightDCh;
    private TextView weightDK;
    private Button startMixerEngine;
    private ImageButton incrementWater;
    private ImageButton decrementWater;
    private ImageButton skipArrowUp;
    private ImageButton skipArrowDown;
    private ImageView verticalConveyorUpArrowIndication;
    private ImageView dropProcessArrow;
    private ImageView overflowChemy;
    private ImageView overflowWater;
    private ImageView levelDownSilos1;
    private ImageView levelUpSilos1;
    private ImageView closeSensorMixer;
    private ImageView middleSensorMixer;
    private ImageView openSensorMixer;
    private ImageView skipSensorUp2;
    private ImageView skipSensorUp1;
    private ImageView skipSensorDown2;
    private ImageView skipSensorDown1;
    private ImageView closeSensorDC;
    private ImageView openSensorDC;
    private ImageView lineArrowIndication;
    private ImageView verticalConveyorView;
    private TextView recepieBuncker3;
    private TextView doseBuncker3;
    private ImageView valveWater;
    private ImageView alarm;
    private Button runVerticalConv, horLineStart;
    private MasterFactoryComplectation factoryOptionList;
    private Button organizationSelectBtn, transporterSelectBtn, resetCounterBtn;
    private TextView performance;
    private int runClickCount = 0;

    boolean separationHopper1 = false;
    boolean separationHopper2 = false;
    boolean separationHopper3 = false;
    boolean separationHopper4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.factory_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        factoryOptionList = new FactoryComplectationBuilder().parseList(new DBUtilGet(getApplicationContext()).getFromParameterTable("factory_complectation"));
        configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(getApplicationContext()).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        separationHopper1 = settings.getBoolean("hopper1", false);
        separationHopper2 = settings.getBoolean("hopper2", false);
        separationHopper3 = settings.getBoolean("hopper3", false);
        separationHopper4 = settings.getBoolean("hopper4", false);

        setsID();
        setComplectation();

        new DataManager(getApplicationContext()).runCollector();

        startPolling();
        startThreads();
        initActions();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выход");
        builder.setMessage("Вы действительно хотите завершить работу");

        builder.setPositiveButton("Да", (dialog, id) -> {
            try {
                System.exit(0);
                finishAffinity();
            } catch (Exception ex) {
                Log.e("EXIT", ex.getMessage());
            }
        });
        builder.setNegativeButton("Нет", (dialog, id) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * при создании активити создаются все элементы, читаем конфиг и прячем то, что выключено
     */
    private void setComplectation() {
        //количество бункеров
        switch (factoryOptionList.getInertBunckerCounter()) {
            case 1: {
                hopper2.setVisibility(INVISIBLE);
                hopper3.setVisibility(INVISIBLE);
                hopper4.setVisibility(INVISIBLE);
                break;
            }
            case 2: {
                hopper3.setVisibility(INVISIBLE);
                hopper4.setVisibility(INVISIBLE);
                break;
            }
            case 3: {
                hopper4.setVisibility(INVISIBLE);
                break;
            }
        }


        //1 или по 2 заслонки на каждом бункере
        if (factoryOptionList.isComboBunckerOption()) {
            doser12.setVisibility(INVISIBLE);
            doser22.setVisibility(INVISIBLE);
            doser32.setVisibility(INVISIBLE);
            doser42.setVisibility(INVISIBLE);
        }

        if (!separationHopper1) doser12.setVisibility(View.GONE);
        if (!separationHopper2) doser22.setVisibility(View.GONE);
        if (!separationHopper3) doser32.setVisibility(View.GONE);
        if (!separationHopper4) doser42.setVisibility(View.GONE);

        //количество силосов
        switch (factoryOptionList.getSilosCounter()) {
            case 1:
                cement2LL.setVisibility(View.GONE);
                break;
            case 2:
                cement2LL.setVisibility(VISIBLE);
                break;
        }

        if (!factoryOptionList.isWater2()) water2LL.setVisibility(View.GONE);

        //todo: humidityMixerSensor
        switch (factoryOptionList.getChemyCounter()) {
            case 1: {
                chemy2LL.setVisibility(View.GONE);
                chemy3LL.setVisibility(View.GONE);
                break;
            }
            case 2: {
                chemy3LL.setVisibility(View.GONE);
                break;
            }
        }

        hydroGateOption = factoryOptionList.isHydroGate();

        //тип транспортера:
        // 0 - лента
        // 1 - лента-лента
        // 2 - лента скип

        // (0 -лента 11-лента-скип 12 -лента-лента)
        switch (factoryOptionList.getTransporterType()) {
            case 0: {
                verticalConveyorView.setVisibility(VISIBLE);
                runVerticalConv.setVisibility(INVISIBLE);

                skipArrowUp.setVisibility(INVISIBLE);
                skipArrowDown.setVisibility(INVISIBLE);

                skipUp.setVisibility(INVISIBLE);
                skipMiddle.setVisibility(INVISIBLE);
                skipDown.setVisibility(INVISIBLE);

                skipSensorDown1.setVisibility(INVISIBLE);
                skipSensorDown2.setVisibility(INVISIBLE);
                skipSensorUp1.setVisibility(INVISIBLE);
                skipSensorUp2.setVisibility(INVISIBLE);
                break;
            }
            case 12: {
                verticalConveyorView.setVisibility(VISIBLE);
                runVerticalConv.setVisibility(VISIBLE);

                skipArrowUp.setVisibility(INVISIBLE);
                skipArrowDown.setVisibility(INVISIBLE);

                skipUp.setVisibility(INVISIBLE);
                skipMiddle.setVisibility(INVISIBLE);
                skipDown.setVisibility(INVISIBLE);

                skipSensorDown1.setVisibility(INVISIBLE);
                skipSensorDown2.setVisibility(INVISIBLE);
                skipSensorUp1.setVisibility(INVISIBLE);
                skipSensorUp2.setVisibility(INVISIBLE);
                break;
            }
            case 11: {
                verticalConveyorView.setVisibility(INVISIBLE);
                runVerticalConv.setVisibility(INVISIBLE);
                skipArrowUp.setVisibility(VISIBLE);
                skipArrowDown.setVisibility(VISIBLE);
                break;
            }
        }
    }

    private void startPolling() {
        new Thread(() -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) mixerView.getBackground();
            ReflectionRetrieval retrieval = new ReflectionRetrieval();

            DecimalFormat decFormat = new DecimalFormat("#0.0");
            while (true) {
                //todo: здесь условие для break прерывания работы while(true)
                retrieval.getValues();
                try {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        //рецепты на бункерах
                        recepieBuncker1.setText(decFormat.format(retrieval.getHopper11RecipeValue()) + "/" + decFormat.format(retrieval.getShortageHopper11Value()));
                        recepieBuncker2.setText(decFormat.format(retrieval.getHopper21RecipeValue()) + "/" + decFormat.format(retrieval.getShortageHopper21Value()));
                        recepieBuncker3.setText(decFormat.format(retrieval.getHopper31RecipeValue()) + "/" + decFormat.format(retrieval.getShortageHopper31Value()));
                        recepieBuncker4.setText(decFormat.format(retrieval.getHopper41RecipeValue()) + "/" + decFormat.format(retrieval.getShortageHopper41Value()));
                        recepieChemy1.setText(decFormat.format(retrieval.getChemy1RecipeValue()) + "/" + decFormat.format(retrieval.getShortageChemy1Value()));
                        recepieChemy2.setText(decFormat.format(retrieval.getChemy2RecipeValue()));
                        recepieWater1.setText(decFormat.format(retrieval.getWaterRecipeValue()) + "/" + decFormat.format(retrieval.getShortageWaterValue()));
                        recepieSilos1.setText(decFormat.format(retrieval.getCement1RecipeValue()) + "/" + decFormat.format(retrieval.getShortageSilos1Value()));
                        recepieSilos2.setText(decFormat.format(retrieval.getCement2RecipeValue()) + "/" + decFormat.format(retrieval.getShortageSilos2Value()));

                        //набранные дозы
                        doseBuncker1.setText(decFormat.format(retrieval.getDoseHopper11Value()));
                        doseBuncker2.setText(decFormat.format(retrieval.getDoseHopper21Value()));
                        doseBuncker3.setText(decFormat.format(retrieval.getDoseHopper31Value()));
                        doseBuncker4.setText(decFormat.format(retrieval.getDoseHopper41Value()));
                        doseChemy1.setText(decFormat.format(retrieval.getDoseChemy1Value()));
                        doseChemy2.setText(decFormat.format(retrieval.getDoseChemy2Value()));
                        doseWater1.setText(decFormat.format(retrieval.getDoseWaterValue()));
                        doseSilos1.setText(decFormat.format(retrieval.getDoseSilos1Value()));
                        doseSilos2.setText(decFormat.format(retrieval.getDoseSilos2Value()));

                        //весы
                        weightDK.setText(decFormat.format(retrieval.getCurrentWeightDKValue()));
                        weigthDC.setText(decFormat.format(retrieval.getCurrentWeightCementValue()));
                        weightDCh.setText(decFormat.format(retrieval.getCurrentWeightChemyValue()));
                        weigthWater.setText(decFormat.format(retrieval.getCurrentWeightWaterValue()));

                        amperage.setText("Сила тока (А): " + decFormat.format(retrieval.getAmperageMixerValue()));

                        //инфо блок слева
                        operatorName.setText("Оператор смены: " + operatorLogin);
                        mixCounterCurrent.setText("Текущий замес: " + retrieval.getMixCounterValue());
                        mixCounterTotal.setText("Всего замесов: " + retrieval.getTotalMixCounterValue());

                        if (globalFactoryState)
                            stateFactory.setText("Статус работы завода: работа");
                        else stateFactory.setText("Статус работы завода: ожидание");

                        partyOptionBtn.setText("Партия\n" + retrieval.getBatchVolumeValue());
                        mixOptionBtn.setText("Замес\n" + retrieval.getMixingCapacity());
                        recepieOptionBtn.setText("Рецепт\n" + selectedRecepie);

                        //сброс с смесителя
                        if (retrieval.isMixerCloseValue() == 1) {
                            dropProcessArrow.setVisibility(INVISIBLE);
                            closeSensorMixer.setImageResource(R.drawable.shiber_open);
                        } else {
                            dropProcessArrow.setVisibility(VISIBLE);
                            closeSensorMixer.setImageResource(R.drawable.shiber_empty);
                        }

                        if (retrieval.isMixerHalfOpenValue() == 1)
                            middleSensorMixer.setImageResource(R.drawable.shiber_open);
                        else middleSensorMixer.setImageResource(R.drawable.shiber_empty);

                        if (retrieval.isMixerOpenValue() == 1)
                            openSensorMixer.setImageResource(R.drawable.shiber_open);
                        else openSensorMixer.setImageResource(R.drawable.shiber_empty);

                        //смеситель не пуст
                        if (retrieval.isMixerNotEmptyValue() == 1)
                            mixFully.setVisibility(VISIBLE);
                        else mixFully.setVisibility(INVISIBLE);

                        if (retrieval.isMixerRollersWorkIndicationValue() == 1) {
                            startMixerEngine.setBackgroundColor(Color.GREEN);
                            if (!animationMixerState) { //анимация смесителя вкл
                                animationMixerState = true;
                                animationDrawable.start();
                            }
                        } else {
                            startMixerEngine.setBackgroundColor(Color.WHITE);
                            animationMixerState = false;
                            animationDrawable.stop();
                        }

                        //скип стрелки
                        if (retrieval.isSkipMoveUpValue() == 1)
                            skipArrowUp.setImageResource(R.drawable.arrow_up);
                        else skipArrowUp.setImageResource(R.drawable.arrow_up_off);

                        if (retrieval.isSkipMoveDownValue() == 1)
                            skipArrowDown.setImageResource(R.drawable.arrow_down);
                        else skipArrowDown.setImageResource(R.drawable.arrow_down_off);


                        if (factoryOptionList.getTransporterType() == 11) {
                            //скип датчики положения
                            if ((retrieval.getSkipSensorDown1Value() == 1) || (retrieval.getSkipSensorDown2Value() == 1))
                                skipDown.setVisibility(VISIBLE);
                            else skipDown.setVisibility(INVISIBLE);

                            if ((retrieval.getSkipSensorUp1Value() == 1) || (retrieval.getSkipSensorUp2Value() == 1))
                                skipUp.setVisibility(VISIBLE);
                            else skipUp.setVisibility(INVISIBLE);

                            if ((retrieval.getSkipSensorDown1Value() == 0) && (retrieval.getSkipSensorDown2Value() == 0) && (retrieval.getSkipSensorUp1Value() == 0) && (retrieval.getSkipSensorUp2Value() == 0))
                                skipMiddle.setVisibility(VISIBLE);
                            else skipMiddle.setVisibility(INVISIBLE);

                            if (retrieval.getSkipSensorDown1Value() == 1)
                                skipSensorDown1.setImageResource(R.drawable.shiber_open);
                            else
                                skipSensorDown1.setImageResource(R.drawable.shiber_lock);
                            if (retrieval.getSkipSensorDown2Value() == 1)
                                skipSensorDown2.setImageResource(R.drawable.shiber_open);
                            else
                                skipSensorDown2.setImageResource(R.drawable.shiber_lock);
                            if (retrieval.getSkipSensorUp1Value() == 1)
                                skipSensorUp1.setImageResource(R.drawable.shiber_open);
                            else
                                skipSensorUp1.setImageResource(R.drawable.shiber_lock);
                            if (retrieval.getSkipSensorUp2Value() == 1)
                                skipSensorUp2.setImageResource(R.drawable.shiber_open);
                            else skipSensorUp2.setImageResource(R.drawable.shiber_lock);

                        }

                        //перелив
                        if (retrieval.isWaterOverflowSensorValue() == 1)
                            overflowWater.setImageResource(R.drawable.shiber_lock);
                        else
                            overflowWater.setImageResource(R.drawable.shiber_empty);
                        if (retrieval.isChemyOverflowSensorValue() == 1)
                            overflowChemy.setImageResource(R.drawable.shiber_lock);
                        else
                            overflowChemy.setImageResource(R.drawable.shiber_empty);

                        //цемент концевые
                        if (retrieval.isCementDisFlapOpenPosIndValue() == 1)
                            openSensorDC.setImageResource(R.drawable.shiber_open);
                        else openSensorDC.setImageResource(R.drawable.shiber_empty);

                        if (retrieval.isCementDisFlapClosePosIndValue() == 1)
                            closeSensorDC.setImageResource(R.drawable.shiber_open);
                        else closeSensorDC.setImageResource(R.drawable.shiber_empty);

                        //заслонки
                        if (retrieval.isHopper11FlapOpenIndValue() == 1)
                            doser11.setImageResource(R.drawable.doser_open);
                        else
                            doser11.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper12FlapOpenIndValue() == 1)
                            doser12.setImageResource(R.drawable.doser_open);
                        else
                            doser12.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper21FlapOpenIndValue() == 1)
                            doser21.setImageResource(R.drawable.doser_open);
                        else
                            doser21.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper22FlapOpenIndValue() == 1)
                            doser22.setImageResource(R.drawable.doser_open);
                        else
                            doser22.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper31FlapOpenIndValue() == 1)
                            doser31.setImageResource(R.drawable.doser_open);
                        else
                            doser31.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper32FlapOpenIndValue() == 1)
                            doser32.setImageResource(R.drawable.doser_open);
                        else
                            doser32.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper41FlapOpenIndValue() == 1)
                            doser41.setImageResource(R.drawable.doser_open);
                        else
                            doser41.setImageResource(R.drawable.doser_close);
                        if (retrieval.isHopper42FlapOpenIndValue() == 1)
                            doser42.setImageResource(R.drawable.doser_open);
                        else
                            doser42.setImageResource(R.drawable.doser_close);
                        if (retrieval.isCement1AugerOnIndValue() == 1)
                            doserShnek1.setImageResource(R.drawable.doser_open);
                        else
                            doserShnek1.setImageResource(R.drawable.doser_close);
                        if (retrieval.isCement2AugerOnIndValue() == 1)
                            doserShnek2.setImageResource(R.drawable.doser_open);
                        else
                            doserShnek2.setImageResource(R.drawable.doser_close);
                        if (retrieval.isCementDisFlapOpenIndValue() == 1)
                            doserDispenserCement.setImageResource(R.drawable.doser_open);
                        else
                            doserDispenserCement.setImageResource(R.drawable.doser_close);
                        if (retrieval.isChemyDisFlapOpenIndValue() == 1)
                            doserDispenserChemy.setImageResource(R.drawable.doser_open);
                        else
                            doserDispenserChemy.setImageResource(R.drawable.doser_close);
                        if (retrieval.isWaterDisFlapOpenIndValue() == 1)
                            doserDispenserWater.setImageResource(R.drawable.doser_open);
                        else
                            doserDispenserWater.setImageResource(R.drawable.doser_close);
                        if (retrieval.isChemy1PumpOnIndValue() == 1)
                            doserChemy1.setImageResource(R.drawable.doser_open);
                        else
                            doserChemy1.setImageResource(R.drawable.doser_close);
                        if (retrieval.isChemy2PumpOnIndValue() == 1)
                            doserChemy2.setImageResource(R.drawable.doser_open);
                        else
                            doserChemy2.setImageResource(R.drawable.doser_close);
                        if (retrieval.isWaterPumpOnIndValue() == 1)
                            pumpWater.setImageResource(R.drawable.doser_open);
                        else
                            pumpWater.setImageResource(R.drawable.doser_close);
                        if (retrieval.isValveWaterBunckerValue() == 1)
                            valveWater.setImageResource(R.drawable.faucet_open);
                        else
                            valveWater.setImageResource(R.drawable.faucet_close);

                        //бункер открытый зеленый
                        if ((retrieval.isHopper11FlapOpenIndValue() == 1) || (retrieval.isHopper12FlapOpenIndValue() == 1))
                            buncker1View.setImageResource(R.drawable.buncker_inert_active);
                        else buncker1View.setImageResource(R.drawable.buncker_inert);

                        if ((retrieval.isHopper21FlapOpenIndValue() == 1) || (retrieval.isHopper22FlapOpenIndValue() == 1))
                            buncker2View.setImageResource(R.drawable.buncker_inert_active);
                        else buncker2View.setImageResource(R.drawable.buncker_inert);

                        if ((retrieval.isHopper31FlapOpenIndValue() == 1) || (retrieval.isHopper32FlapOpenIndValue() == 1))
                            buncker3View.setImageResource(R.drawable.buncker_inert_active);
                        else buncker3View.setImageResource(R.drawable.buncker_inert);

                        if ((retrieval.isHopper41FlapOpenIndValue() == 1) || (retrieval.isHopper42FlapOpenIndValue() == 1))
                            buncker4View.setImageResource(R.drawable.buncker_inert_active);
                        else buncker4View.setImageResource(R.drawable.buncker_inert);

                        if (retrieval.isAutoDropChckerValue() == 1)
                            autoDropChecker.setChecked(true);
                        else autoDropChecker.setChecked(false);

                        //горизонтальный конвейер стрелка
                        if (retrieval.isHorConveyorOnIndValue() == 1) {
                            horLineStart.setBackgroundColor(Color.GREEN);
                            horLineStart.setText("Стоп");
                            lineArrowIndication.setVisibility(VISIBLE);
                        } else {
                            horLineStart.setBackgroundColor(Color.WHITE);
                            horLineStart.setText("Старт");
                            lineArrowIndication.setVisibility(INVISIBLE);
                        }

                        //наклонный конвейер
                        if (retrieval.isVerticalConveyorIndValue() == 1) {
                            verticalConveyorUpArrowIndication.setVisibility(VISIBLE);
                            runVerticalConv.setBackgroundColor(Color.GREEN);
                            runVerticalConv.setText("Стоп");
                        } else {
                            runVerticalConv.setBackgroundColor(Color.WHITE);
                            runVerticalConv.setText("Старт");
                            verticalConveyorUpArrowIndication.setVisibility(INVISIBLE);
                        }

                        //переключалка силосов
                        if (retrieval.isSilosSelectorValue() == 1) {
                            silosSelector1.setChecked(false);
                            silosSelector2.setChecked(true);
                        } else {
                            silosSelector1.setChecked(true);
                            silosSelector2.setChecked(false);
                        }

                        //фильтр аэрация шнек
                        if (retrieval.isSilosCementFilterValue() == 1)
                            filterSilos.setBackgroundColor(Color.GREEN);
                        else filterSilos.setBackgroundColor(Color.WHITE);
                        if (retrieval.isAerationOnIndicationValue() == 1)
                            aerationSilos.setBackgroundColor(Color.GREEN);
                        else aerationSilos.setBackgroundColor(Color.WHITE);
                        if (retrieval.isVibroSilos1Value() == 1)
                            vibroSilos.setBackgroundColor(Color.GREEN);
                        else vibroSilos.setBackgroundColor(Color.WHITE);

                        //кнопки полуавтомата
                        if (retrieval.isSelfDChStateValue() == 1)
                            startSelfChemy.setBackgroundColor(Color.GREEN);
                        else startSelfChemy.setBackgroundColor(Color.WHITE);
                        if (retrieval.isSelfDKStateValue() == 1)
                            startSelfDK.setBackgroundColor(Color.GREEN);
                        else startSelfDK.setBackgroundColor(Color.WHITE);
                        if (retrieval.isSelfDCStateValue() == 1)
                            startSelfCement.setBackgroundColor(Color.GREEN);
                        else startSelfCement.setBackgroundColor(Color.WHITE);
                        if (retrieval.isSelfDWStateValue() == 1)
                            startSelfWater.setBackgroundColor(Color.GREEN);
                        else startSelfWater.setBackgroundColor(Color.WHITE);

                        //основной аварийный флаг
                        if (retrieval.isGlobalCrashFlagValue() == 1) alarm.setVisibility(VISIBLE);
                        else alarm.setVisibility(INVISIBLE);

                        if (retrieval.isManualAutoModeValue() == 0) {
                            globalModeState = false;
                            //TODO Ручной
                            resetCounterBtn.setVisibility(VISIBLE);
                            manualAutoSwitcher.setBackgroundColor(Color.WHITE);
                            manualAutoSwitcher.setText("Переключить в автомат");
                            if (factoryOptionList.getTransporterType() != 11)
                                runVerticalConv.setVisibility(VISIBLE);
                            runCycleBtn.setVisibility(View.GONE);
                            startSelfChemy.setVisibility(VISIBLE);
                            startSelfWater.setVisibility(VISIBLE);
                            startSelfCement.setVisibility(VISIBLE);
                            startSelfDK.setVisibility(VISIBLE);
                            horLineStart.setVisibility(VISIBLE);
                            if (factoryOptionList.getTransporterType() == 11) {
                                skipArrowUp.setVisibility(VISIBLE);
                                skipArrowDown.setVisibility(VISIBLE);
                            }
                        } else {
                            globalModeState = true;
                            //TODO Автомат
                            resetCounterBtn.setVisibility(View.GONE);
                            manualAutoSwitcher.setBackgroundColor(Color.GRAY);
                            manualAutoSwitcher.setText("Переключить в ручной");
                            runVerticalConv.setVisibility(INVISIBLE);
                            runCycleBtn.setVisibility(VISIBLE);
                            startSelfChemy.setVisibility(INVISIBLE);
                            startSelfWater.setVisibility(INVISIBLE);
                            startSelfCement.setVisibility(INVISIBLE);
                            startSelfDK.setVisibility(INVISIBLE);
                            horLineStart.setVisibility(INVISIBLE);

                            if (factoryOptionList.getTransporterType() == 11) {
                                skipArrowUp.setVisibility(INVISIBLE);
                                skipArrowDown.setVisibility(INVISIBLE);
                            }
                        }
                        organizationSelectBtn.setText("Заказчик\n" + selectedOrg);
                        transporterSelectBtn.setText("Водитель\n" + selectedTrans);

                        performance.setText(String.valueOf(retrieval.getScadaPerformanceValue()));
                    });
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //TODO GET
    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    private void initActions() {
        touchLock.setOnClickListener(view -> {
            touchLockLayout.setVisibility(VISIBLE);
        });

        touchUnlock.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 95) touchLockLayout.setVisibility(View.GONE);
                else seekBar.setThumb(getResources().getDrawable(R.drawable.lock, null));

                seekBar.setProgress(0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 95)
                    seekBar.setThumb(getResources().getDrawable(R.drawable.unlock, null));
                else seekBar.setThumb(getResources().getDrawable(R.drawable.lock, null));
            }
        });

        closeAlarmLayoutBtn.setOnClickListener(view -> {
            alarmsWarningLayout.setVisibility(View.GONE);
        });

        alarm.setOnClickListener(view -> {
            alarmsWarningLayout.setVisibility(VISIBLE);
            try {
                runOnUiThread(() -> alarmWarningText.setText(AlarmUtil.getAlarms()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        manualAutoSwitcher.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(0)).writeSingleInvertedBoolRegister();
        });

        doser11.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(2)).writeSingleInvertedBoolRegister();
        });
        doser12.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(3)).writeSingleInvertedBoolRegister();
        });
        doser21.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(4)).writeSingleInvertedBoolRegister();
        });
        doser22.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(5)).writeSingleInvertedBoolRegister();
        });
        doser31.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(6)).writeSingleInvertedBoolRegister();
        });
        doser32.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(7)).writeSingleInvertedBoolRegister();
        });
        doser41.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(8)).writeSingleInvertedBoolRegister();
        });
        doser42.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(9)).writeSingleInvertedBoolRegister();
        });

        doserDispenserWater.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(10)).writeSingleInvertedBoolRegister();
        });
        doserDispenserChemy.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(11)).writeSingleInvertedBoolRegister();
        });
        doserDispenserCement.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(12)).writeSingleInvertedBoolRegister();
        });
        horLineStart.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(13)).writeSingleInvertedBoolRegister();
        });
        pumpWater.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(14)).writeSingleInvertedBoolRegister();
        });
        doserChemy1.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(15)).writeSingleInvertedBoolRegister();
        });
        doserChemy2.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(16)).writeSingleInvertedBoolRegister();
        });
        doserShnek1.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(18)).writeSingleInvertedBoolRegister();
        });
        doserShnek2.setOnClickListener(view -> {
            if (!globalModeState)
                new CommandDispatcher(tagListManual.get(19)).writeSingleInvertedBoolRegister();
        });
        openMixer.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(21)).writeSingleInvertedBoolRegister();
        });
        closeMixer.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(82)).writeSingleInvertedBoolRegister();
        });

        skipArrowUp.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: { //удержание
                    skipArrowUp.setImageResource(R.drawable.arrow_up);
                    new CommandDispatcher(tagListManual.get(22)).writeSingleRegisterWithValue(true);
                    break;
                }
                case MotionEvent.ACTION_UP: { // опускание
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    skipArrowUp.setImageResource(R.drawable.arrow_up_off);
                    new CommandDispatcher(tagListManual.get(22)).writeSingleRegisterWithValue(false);
                    break;
                }
            }
            return false;
        });
        skipArrowDown.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: { //удержание
                    skipArrowDown.setImageResource(R.drawable.arrow_down);
                    new CommandDispatcher(tagListManual.get(23)).writeSingleRegisterWithValue(true);
                    break;
                }
                case MotionEvent.ACTION_UP: { // опускание
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    skipArrowDown.setImageResource(R.drawable.arrow_down_off);
                    new CommandDispatcher(tagListManual.get(23)).writeSingleRegisterWithValue(false);
                    break;
                }
            }
            return false;
        });
        aerationSilos.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(24)).writeSingleInvertedBoolRegister();
        });
        startMixerEngine.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(25)).writeSingleInvertedBoolRegister();
        });
        vibroSilos.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(80)).writeSingleInvertedBoolRegister();
        });
        filterSilos.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(81)).writeSingleInvertedBoolRegister();
        });
        openMixer.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: { //удержание
                    new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(true);
                    break;
                }
                case MotionEvent.ACTION_UP: { // опускание
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (hydroGateOption)
                        new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(false);
                    break;
                }
            }
            return false;
        });
        closeMixer.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: { //удержание
                    if (hydroGateOption)
                        new CommandDispatcher(tagListManual.get(82)).writeSingleRegisterWithValue(true);
                    else
                        new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(false);
                    break;
                }
                case MotionEvent.ACTION_UP: { // опускание
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (hydroGateOption)
                        new CommandDispatcher(tagListManual.get(82)).writeSingleRegisterWithValue(false);
                    break;
                }
            }
            return false;
        });
        valveWater.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(83)).writeSingleInvertedBoolRegister();
        });

        //TODO: Попытка сделать защиту от случайногог нажатия
        Handler handlerRunCycle = new Handler();
        Runnable runCycle = () -> {
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                globalMixStartTime = timeFormat.format(new Date());
                if (exchangeLevel == 1) {
                    new Thread(() -> {
                        new CommandDispatcher(84).writeValue("true");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new CommandDispatcher(84).writeValue("false");
                    }).start();
                } else
                    new CommandDispatcher(tagListManual.get(84)).writeSingleFrontBoolRegister(500);

                globalFactoryState = true;
                new DBUtilUpdate(getApplicationContext()).updCurrentTable("state", "work");   //смена статуса
                runClickCount = 0;

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Перемешивание");
                builder.setMessage("Автоматический режим запущен!");
                builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        runCycleBtn.setOnTouchListener((arg0, arg1) -> {
            switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    vibrate(100);
                    runClickCount++;
                    if (runClickCount == 3) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Подсказка");
                        builder.setMessage("Для запуска цикла, удерживайте кнопку в течении 3-x секунд");
                        builder.setPositiveButton("OK", (dialog, id) -> {
                            runClickCount = 0;
                            dialog.dismiss();
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    }
                    handlerRunCycle.postDelayed(runCycle, 3000);
                    break;
                case MotionEvent.ACTION_UP:
                    handlerRunCycle.removeCallbacks(runCycle);
                    break;
            }
            return true;
        });

        stopCycle.setOnClickListener(view -> {
            new Thread(()->{
                new CommandDispatcher(tagListManual.get(84)).writeSingleRegisterWithValue(false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                new CommandDispatcher(tagListManual.get(85)).writeSingleRegisterWithValue(false);
                globalFactoryState = false;
                new DBUtilUpdate(getApplicationContext()).updCurrentTable("state", "idle");
            }).start();
        });

        startSelfDK.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(110)).writeSingleInvertedBoolRegister();
        });
        startSelfCement.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(112)).writeSingleInvertedBoolRegister();
        });
        startSelfChemy.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(111)).writeSingleInvertedBoolRegister();
        });
        startSelfWater.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(113)).writeSingleInvertedBoolRegister();
        });
        runVerticalConv.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(115)).writeSingleInvertedBoolRegister();
        });
        incrementWater.setOnClickListener(view -> {
            if (exchangeLevel == 1)
                new Thread(() -> new CommandDispatcher(135).writeInverted()).start();
            else new CommandDispatcher(tagListManual.get(135)).writeSingleFrontBoolRegister(200);
        });
        decrementWater.setOnClickListener(view -> {
            if (exchangeLevel == 1)
                new Thread(() -> new CommandDispatcher(136).writeInverted()).start();
            else new CommandDispatcher(tagListManual.get(136)).writeSingleFrontBoolRegister(200);
        });
        autoDropChecker.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(156)).writeSingleInvertedBoolRegister();
            //todo: тут смена статуса на окончание для индикации "готово к разгрузке"
        });
        silosSelector1.setOnClickListener(view -> {
            new Thread(()->{
                new CommandDispatcher(tagListManual.get(158)).writeSingleRegisterWithValue(false);
            }).start();
        });
        silosSelector2.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(158)).writeSingleRegisterWithValue(true);
        });
        organizationSelectBtn.setOnClickListener(view -> {
            new Thread(() -> {
                List<Organization> orgList = new ArrayList<>();
                if (exchangeLevel == 1) {
                    orgList.addAll(new Gson().fromJson(OkHttpUtil.getOrganization(), new TypeToken<List<Organization>>() {
                    }.getType()));
                } else {
                    orgList = new DBUtilGet(this).getOrgs();
                }
                OrganizationListDialog dialog = new OrganizationListDialog(orgList);
                dialog.show(getSupportFragmentManager(), "custom");
            }).start();
        });

        transporterSelectBtn.setOnClickListener(view -> {
            new Thread(() -> {
                List<Transporter> transList = new ArrayList<>();
                if (exchangeLevel == 1) {
                    transList.addAll(new Gson().fromJson(OkHttpUtil.getTransporters(), new TypeToken<List<Transporter>>() {
                    }.getType()));
                } else transList = new DBUtilGet(this).getTrans();

                TransporterListDialog dialog = new TransporterListDialog(transList);
                dialog.show(getSupportFragmentManager(), "custom");
            }).start();
        });

        recepieOptionBtn.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(getApplicationContext(), recepieOptionBtn);
            popupMenu.getMenuInflater().inflate(R.menu.popup_select_rec_ord, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.recipe: {
                        new Thread(() -> {
                            List<Recepie> recepieList = new ArrayList<>();
                            if (exchangeLevel == 1) {
                                recepieList.addAll(new Gson().fromJson(OkHttpUtil.getRecipes(), new TypeToken<List<Recepie>>() {
                                }.getType()));
                            } else recepieList = new DBUtilGet(this).getRecepies();

                            if (recepieList != null) {
                                RecipeLoaderDialog recDialog = new RecipeLoaderDialog(recepieList);
                                recDialog.show(getSupportFragmentManager(), "custom");
                            } else {
                                Toast.makeText(this, "Рецепты отсутствуют!", Toast.LENGTH_SHORT).show();
                            }
                        }).start();
                        break;
                    }
                    case R.id.order: {
                        Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            });
            popupMenu.show();

        });

        partyOptionBtn.setOnClickListener(view -> {
            PartyCapacityDialog dialog = new PartyCapacityDialog();
            dialog.show(getSupportFragmentManager(), "custom");
        });
        mixOptionBtn.setOnClickListener(view -> {
            MixCapacityDialog dialog = new MixCapacityDialog();
            dialog.show(getSupportFragmentManager(), "custom");
        });
        catalogsBtn.setOnClickListener(view -> {
            CatalogMenuDialog dialog = new CatalogMenuDialog();
            dialog.show(getSupportFragmentManager(), "custom");
        });

        resetCounterBtn.setOnClickListener(view -> {
            if (exchangeLevel == 1)
                new Thread(() -> new CommandDispatcher(71).writeInverted()).start();
            else new CommandDispatcher(tagListManual.get(71)).writeSingleFrontBoolRegister(2000);
        });
        resetAlarmBtn.setOnClickListener(view -> {
            alarmsWarningLayout.setVisibility(INVISIBLE);
            if (exchangeLevel == 1)
                new Thread(() -> new CommandDispatcher(1).writeInverted()).start();
            else new CommandDispatcher(tagListManual.get(1)).writeSingleFrontBoolRegister(2000);
        });
        reportsMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ReportsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
    }

    private void startThreads() {
        //plc
        if (exchangeLevel == 0) {
            //поток записи доз в учет по метке "Веса готорвы к чтению"
            new Thread(() -> {
                ReflectionRetrieval retrieval = new ReflectionRetrieval();
                while (true) {
                    retrieval.getValues();
                    try {
                        if (retrieval.isWeightsReadyReadValue() == 1) {
                            Current current = new DBUtilGet(getApplicationContext()).getCurrent();
                            Recepie recepie = new DBUtilGet(getApplicationContext()).getRecepieForID(current.getRecepieID());

                            System.out.println(selectedOrder);
                            //TODO:
                            if (selectedOrder.equals("Не указано")){
                                new ReportRecordingUtil().recordWeights(
                                        getApplicationContext(),
                                        "",
                                        0,
                                        "",
                                        String.valueOf(operatorName.getText()),
                                        selectedOrg,
                                        0,
                                        selectedTrans,
                                        0,
                                        recepie.getName(),
                                        retrieval.getMixingCapacity(),
                                        recepie.getId(), retrieval.getBatchVolumeValue()
                                );
                            } else {
                                Order selectedOrder = new DBUtilGet(getApplicationContext()).getOrderForID(current.getOrderID());
                                selectedOrder.setCurrentMixCount(selectedOrder.getCurrentMixCount() + 1);

                                if (selectedOrder.getCurrentMixCount() == selectedOrder.getTotalMixCounter()) {
                                    selectedOrder.setState(1);
                                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                                    selectedOrder.setCompletionDate(date.format(new Date()));

                                    Constants.selectedOrder = "Не указано";
                                }
                                new DBUtilUpdate(getApplicationContext()).updateOrder(selectedOrder);

                                new ReportRecordingUtil().recordWeights(
                                        getApplicationContext(),
                                        selectedOrder.getNameOrder(),
                                        selectedOrder.getNumberOrder(),
                                        selectedOrder.getUploadAddress(),
                                        operatorLogin,
                                        selectedOrder.getOrganizationName(),
                                        selectedOrder.getOrganizationID(),
                                        selectedOrder.getTransporter(),
                                        selectedOrder.getTransporterID(),
                                        recepie.getName(),
                                        selectedOrder.getMaxMixCapacity(),
                                        recepie.getId(),
                                        selectedOrder.getTotalCapacity()
                                );
                            }
                            Thread.sleep(10000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    List<Mix> mixList = new ArrayList<>();
                    if (exchangeLevel == 1) {
                        try {
                            mixList = new Gson().fromJson(OkHttpUtil.getMixes(date.format(new Date()), date.format(new Date())), new TypeToken<List<Mix>>() {}.getType());
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }
                    } else mixList = new DBUtilGet(this).getMixListForDate(date.format(new Date()));

                    float result = 0;
                    for (Mix mix : mixList) {
                        if ((mix.getSilos1() == 0) && (mix.getSilos2() == 0)) continue;
                        if (mix.getCompleteCapacity() != 0) result += mix.getCompleteCapacity();
                    }
                    float finalResult = result;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        dailyCounter.setText("Произведено за сегодня м3: " + finalResult);
                        recepieName.setText("Рецепт/заказ: " +selectedRecepie +"/"+selectedOrder);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setsID() {
        silosSelector1 = findViewById(R.id.silosSelector1);
        silosSelector2 = findViewById(R.id.silosSelector2);
        horLineView = findViewById(R.id.horLineView);
        touchUnlock = findViewById(R.id.seekBar);
        touchLock = findViewById(R.id.touchLock);
        touchLockLayout = findViewById(R.id.touchLockLayout);
        doser11 = findViewById(R.id.doser11);
        doser12 = findViewById(R.id.doser12);
        doser21 = findViewById(R.id.doser21);
        doser22 = findViewById(R.id.doser22);
        doser31 = findViewById(R.id.doser31);
        doser32 = findViewById(R.id.doser32);
        doser41 = findViewById(R.id.doser41);
        doser42 = findViewById(R.id.doser42);
        pumpWater = findViewById(R.id.pumpWater);
        pumpWater2 = findViewById(R.id.pumpWater2);
        doserShnek1 = findViewById(R.id.doserShnek1);
        doserShnek2 = findViewById(R.id.doserShnek2);
        doserDispenserCement = findViewById(R.id.doserDispenserCement);
        doserDispenserWater = findViewById(R.id.doserDispenserWater);
        doserDispenserChemy = findViewById(R.id.doserDispenserChemy);
        skipUp = findViewById(R.id.skipUp);
        reportsMenu = findViewById(R.id.reportsMenu);
        catalogsBtn = findViewById(R.id.catalogsMenu);
        buncker2View = findViewById(R.id.buncker2View);
        bunckerChemy1 = findViewById(R.id.bunckerChemy1);

        chemy2LL = findViewById(R.id.chemy2LL);
        chemy3LL = findViewById(R.id.chemy3LL);
        water2LL = findViewById(R.id.water2LL);

        cement2LL = findViewById(R.id.cement2LL);

        recepieName = findViewById(R.id.recepieName);
        doseWater1 = findViewById(R.id.doseWater1);
        recepieWater1 = findViewById(R.id.recepieWater1);
        bunckerChemy2 = findViewById(R.id.bunckerChemy2);
        bunckerChemy3 = findViewById(R.id.bunckerChemy3);
        doseSilos1 = findViewById(R.id.doseSilos1);

        silos2View = findViewById(R.id.silos2View);
        titleSilos2 = findViewById(R.id.titleSilos2);
        doseSilos2 = findViewById(R.id.doseSilos2);
        silos1View = findViewById(R.id.silos1View);
        levelDownSIlos2 = findViewById(R.id.levelDownSIlos2);
        levelUpSilos2 = findViewById(R.id.levelUpSilos2);
        recepieSilos2 = findViewById(R.id.recepieSilos2);
        recepieSilos1 = findViewById(R.id.recepieSilos1);
        titleSilos1 = findViewById(R.id.titleSilos1);
        aerationSilos = findViewById(R.id.aerationSilos);
        vibroSilos = findViewById(R.id.vibroSilos);
        filterSilos = findViewById(R.id.filterSilos);
        recepieOptionBtn = findViewById(R.id.recepieOptionBtn);
        partyOptionBtn = findViewById(R.id.partyOptionBtn);
        mixOptionBtn = findViewById(R.id.mixOptionBtn);
        openMixer = findViewById(R.id.openMixer);
        closeMixer = findViewById(R.id.closeMixer);
        manualAutoSwitcher = findViewById(R.id.manualAutoSwitcher);
        stopCycle = findViewById(R.id.stopCycle);
        runCycleBtn = findViewById(R.id.runCycle);
        operatorName = findViewById(R.id.operatorName);
        mixCounterTotal = findViewById(R.id.mixCounterTotal);
        mixCounterCurrent = findViewById(R.id.mixCounterCurrent);
        stateFactory = findViewById(R.id.stateFactory);
        dailyCounter = findViewById(R.id.dailyCounter);
        titleChemy1 = findViewById(R.id.titleChemy1);
        titleChemy2 = findViewById(R.id.titleChemy2);
        titleChemy3 = findViewById(R.id.titleChemy3);
        recepieChemy2 = findViewById(R.id.recepieChemy2);
        recepieChemy3 = findViewById(R.id.recepieChemy3);
        doseChemy2 = findViewById(R.id.doseChemy2);
        doseChemy3 = findViewById(R.id.doseChemy3);
        recepieChemy1 = findViewById(R.id.recepieChemy1);
        doseChemy1 = findViewById(R.id.doseChemy1);
        startSelfDK = findViewById(R.id.startSelfDK);
        startSelfChemy = findViewById(R.id.startSelfChemy);
        startSelfCement = findViewById(R.id.startSelfCement);
        startSelfWater = findViewById(R.id.startSelfWater);
        amperage = findViewById(R.id.amperage);
        mixFully = findViewById(R.id.mixFully);
        mixerView = findViewById(R.id.mixerView);
        mixerView.setBackgroundResource(R.drawable.mixer_animation);

        closeAlarmLayoutBtn = findViewById(R.id.closeAlarmLayoutBtn);
        resetAlarmBtn = findViewById(R.id.resetAlarmBtn);
        alarmsWarningLayout = findViewById(R.id.alarmsWarningLayout);
        alarmWarningText = findViewById(R.id.alarmWarningText);

        hopper1 = findViewById(R.id.hopper1);
        hopper2 = findViewById(R.id.hopper2);
        hopper3 = findViewById(R.id.hopper3);
        hopper4 = findViewById(R.id.hopper4);

        buncker1View = findViewById(R.id.buncker1View);
        buncker3View = findViewById(R.id.buncker3View);
        buncker4View = findViewById(R.id.buncker4View);
        autoDropChecker = findViewById(R.id.autoDropChecker);
        skipDown = findViewById(R.id.skipDown);
        skipMiddle = findViewById(R.id.skipMiddle);
        titleBuncker1 = findViewById(R.id.titleBuncker1);
        titleBuncker4 = findViewById(R.id.titleBuncker4);
        recepieBuncker4 = findViewById(R.id.recepieBuncker4);
        doseBuncker4 = findViewById(R.id.doseBuncker4);
        titleBuncker3 = findViewById(R.id.titleBuncker3);
        titleBuncker2 = findViewById(R.id.titleBuncker2);
        recepieBuncker1 = findViewById(R.id.recepieBuncker1);
        recepieBuncker2 = findViewById(R.id.recepieBuncker2);
        doseBuncker1 = findViewById(R.id.doseBuncker1);
        doseBuncker2 = findViewById(R.id.doseBuncker2);
        dispenserChemyView = findViewById(R.id.dispenserChemyView);
        dispenserWaterView = findViewById(R.id.dispenserWaterView);
        dispenserCementView = findViewById(R.id.dispenserCementView);
        doserChemy1 = findViewById(R.id.doserChemy1);
        doserChemy2 = findViewById(R.id.doserChemy2);
        doserChemy3 = findViewById(R.id.doserChemy3);
        weigthDC = findViewById(R.id.weigthDC);
        weigthWater = findViewById(R.id.weigthWater);
        weightDCh = findViewById(R.id.weightDCh);
        weightDK = findViewById(R.id.weightDK);
        startMixerEngine = findViewById(R.id.startMixerEngine);
        incrementWater = findViewById(R.id.incrementWater);
        decrementWater = findViewById(R.id.decrementWater);
        skipArrowUp = findViewById(R.id.skipArrowUp);
        skipArrowDown = findViewById(R.id.skipArrowDown);
        skipSensorUp2 = findViewById(R.id.skipSensorUp2);
        skipSensorUp1 = findViewById(R.id.skipSensorUp1);
        skipSensorDown2 = findViewById(R.id.skipSensorDown2);
        skipSensorDown1 = findViewById(R.id.skipSensorDown1);
        closeSensorDC = findViewById(R.id.closeSensorDC);
        verticalConveyorUpArrowIndication = findViewById(R.id.verticalConveyorUpArrowIndication);
        dropProcessArrow = findViewById(R.id.dropProcessArrow);
        overflowChemy = findViewById(R.id.overflowChemy);
        overflowWater = findViewById(R.id.overflowWater);
        levelDownSilos1 = findViewById(R.id.levelDownSilos1);
        levelUpSilos1 = findViewById(R.id.levelUpSilos1);
        closeSensorMixer = findViewById(R.id.closeSensorMixer);
        middleSensorMixer = findViewById(R.id.middleSensorMixer);
        openSensorMixer = findViewById(R.id.openSensorMixer);
        openSensorDC = findViewById(R.id.openSensorDC);
        lineArrowIndication = findViewById(R.id.lineArrowIndication);
        verticalConveyorView = findViewById(R.id.verticalConveyorView);
        recepieBuncker3 = findViewById(R.id.recepieBuncker3);
        doseBuncker3 = findViewById(R.id.doseBuncker3);
        valveWater = findViewById(R.id.valveWater);
        alarm = findViewById(R.id.alarm);
        runVerticalConv = findViewById(R.id.runVerticalConv);

        horLineStart = findViewById(R.id.horLineStart);
        organizationSelectBtn = findViewById(R.id.organizationSelectBtn);
        transporterSelectBtn = findViewById(R.id.driverSelectBtn);
        resetCounterBtn = findViewById(R.id.resetCounterBtn);
        performance = findViewById(R.id.performance);

    }

    @SuppressWarnings("deprecation")
    private void vibrate(int ms) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = (VibratorManager) getApplicationContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            Vibrator vibrator = vibratorManager.getDefaultVibrator();
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            // API < 26
            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(ms);
        }
    }
}


