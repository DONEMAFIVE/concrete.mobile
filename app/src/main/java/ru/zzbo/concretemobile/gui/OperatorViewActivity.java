package ru.zzbo.concretemobile.gui;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.os.Build.VERSION.SDK_INT;
import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static ru.zzbo.concretemobile.db.DBConstants.DATABASE_VERSION;
import static ru.zzbo.concretemobile.utils.Constants.APP_PREFERENCES;
import static ru.zzbo.concretemobile.utils.Constants.APP_PREFERENCES_TAP_TARGET;
import static ru.zzbo.concretemobile.utils.Constants.PERMISSION_STORAGE;
import static ru.zzbo.concretemobile.utils.Constants.REQUEST_EXTERNAL_STORAGE;
import static ru.zzbo.concretemobile.utils.Constants.accessLevel;
import static ru.zzbo.concretemobile.utils.Constants.androidID;
import static ru.zzbo.concretemobile.utils.Constants.animationMixerState;
import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.globalFactoryState;
import static ru.zzbo.concretemobile.utils.Constants.globalMixStartTime;
import static ru.zzbo.concretemobile.utils.Constants.globalModeState;
import static ru.zzbo.concretemobile.utils.Constants.hydroGateOption;
import static ru.zzbo.concretemobile.utils.Constants.mPlayer;
import static ru.zzbo.concretemobile.utils.Constants.mSettings;
import static ru.zzbo.concretemobile.utils.Constants.operatorLogin;
import static ru.zzbo.concretemobile.utils.Constants.retrieval;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrder;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrg;
import static ru.zzbo.concretemobile.utils.Constants.selectedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.selectedTrans;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.BuildConfig;
import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.db.helpers.ConfigBuilder;
import ru.zzbo.concretemobile.db.helpers.FactoryComplectationBuilder;
import ru.zzbo.concretemobile.db.helpers.StorageMillageBuilder;
import ru.zzbo.concretemobile.db.models.StorageMillage;
import ru.zzbo.concretemobile.gui.catalogs.RequisitesActivity;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.MixCapacityDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.OrganizationListDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.PartyCapacityDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.TimeMixingDialog;
import ru.zzbo.concretemobile.gui.dialogs.uploaders.TransporterListDialog;
import ru.zzbo.concretemobile.models.Current;
import ru.zzbo.concretemobile.models.DispatcherStates;
import ru.zzbo.concretemobile.models.MasterFactoryComplectation;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.protocol.DataManager;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.commands.StartAutoCycle;
import ru.zzbo.concretemobile.reporting.ReportRecordingUtil;
import ru.zzbo.concretemobile.utils.AlarmUtil;
import ru.zzbo.concretemobile.utils.CalcUtil;
import ru.zzbo.concretemobile.utils.ConnectionUtil;
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.NotificationUtil;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class OperatorViewActivity extends AppCompatActivity {
    private ToolTipsManager toolTipsManager;
    private RadioButton silosSelector1;
    private RadioButton silosSelector2;
    private RadioButton silosSelector3;
    //    private ImageView horLineView;
    private SeekBar touchUnlock;
    private TabLayout tabMenu;
    private LinearLayout fillers;
    private TableLayout tableMenu;
    private ImageView touchLock;
    private DrawerLayout touchLockLayout;
    private DrawerLayout mainMenuDL;
    private ImageButton recipesMenu;
    private ImageButton ordersMenu;
    private ImageButton orgsMenu;
    private ImageButton driversMenu;
    private ImageButton helpMenu;
    private ImageButton usersMenu;
    private ImageButton passportMenu;
    private ImageButton invoiceMenu;
    private ImageButton nameBunckersMenu;
    private ImageButton requisitesMenu;
    private ImageButton exitMenu;
    private ImageButton learningMenu;
    private LinearLayout usersLayout;
    private LinearLayout requisitesLayout;
    private LinearLayout learningLayout;

    private ImageButton correctionBtn;
    private Button closeAlarmLayoutBtn;
    private Button reverseConveyor;
    private Button conveyorUploadDrop;
    private Button resetAlarmBtn;
    private DrawerLayout alarmsWarningLayout;
    private EditText alarmWarningText;

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
    //    private ImageView pumpWater2;
    private ImageView doserShnek1;
    private ImageView doserShnek2;
    private ImageView doserDispenserCement;
    private ImageView doserDispenserWater;
    private ImageView doserDispenserChemy;
    private ImageView skipUp;
    private ImageButton reportsMenu;
    private ImageView catalogsBtn;
    private ImageView buncker2View;
//    private ImageView bunckerChemy1;
//    private ImageView bunckerChemy2;
//    private ImageView bunckerChemy3;

    private LinearLayout chemy2LL;
    private LinearLayout chemy3LL;
    private LinearLayout water2LL;
    private LinearLayout cement2LL;
    private TextView timeMix;
    private TextView rtDK, rtLT, rtVW, rtPW, rtOil, rtShnek1, rtShnek2, rtShnek3, rtShnek4, rtMixer, rtChemy1, rtChemy2, rtSkip;
    private TextView buncker1, buncker2, buncker3, buncker4, chemy1, chemy2, chemy3, water1, water2, cement1, cement2, cement3, cement4;
    private TextView recipeName;
    private TextView orderName;
    private TextView transporter;
    private TextView organization;
    private TextView doseWater1;
    private TextView recipeWater1;
    private ImageView silos2View;
    private TextView doseSilos1;
    private TextView titleSilos2;
    private TextView doseSilos2;
    private ImageView silos1View;
    private ImageView levelDownSIlos2;
    private ImageView levelUpSilos2;
    private TextView recipeSilos2;
    private TextView recipeSilos1;
    private TextView titleSilos1;
    private ImageButton aerationSilos;
    private ImageButton vibroSilos;
    private ImageButton filterSilos;
    //    private Button recipeOptionBtn;
    private Button partyOptionBtn;
    private Button timeMixBtn;
    private Button mixOptionBtn;
    private ImageButton openMixer;
    private ImageButton closeMixer;
    private ImageButton manualAutoSwitcher;
    private ImageButton stopCycle;
    private ImageButton runCycleBtn;
    private TextView operatorName;
    private TextView mixCounterTotal;
    private TextView mixCounterCurrent;
    private TextView stateFactory;
    private TextView dailyCounter;

    private TextView titleChemy1;
    private TextView titleChemy2;
    private TextView titleChemy3;
    private TextView titleWater1;
    private TextView titleWater2;
    private TextView recipeChemy2;
    //    private TextView recipeChemy3;
    private TextView doseChemy2;
    //    private TextView doseChemy3;
    private TextView recipeChemy1;
    private TextView doseChemy1;
    private Button startSelfDK, startSelfChemy, startSelfCement, startSelfWater;
    private LinearLayout halfAutoMode;
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
    private TextView titleBuncker2;
    private TextView titleBuncker3;
    private TextView titleBuncker4;
    private TextView recipeBuncker1;
    private TextView recipeBuncker4;
    private TextView doseBuncker4;
    private TextView recipeBuncker2;
    private TextView doseBuncker1;
    private TextView doseBuncker2;
    //    private ImageView dispenserChemyView;
//    private ImageView dispenserWaterView;
//    private ImageView dispenserCementView;
    private ImageView doserChemy1;
    private ImageView doserChemy2;
    //    private ImageView doserChemy3;
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
    //    private ImageView levelDownSilos1;
//    private ImageView levelUpSilos1;
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
    private TextView recipeBuncker3;
    private TextView doseBuncker3;
    private ImageView valveWater;
    private ImageView alarm;
    private ImageView blade1;
    private ImageView blade2;
    private Button runVerticalConv, horLineStart;
    private MasterFactoryComplectation factoryOptionList;
    private Button organizationSelectBtn, transporterSelectBtn;
    private ImageButton resetBtn;
    private TextView performance, infoApp;
    private int runClickCount = 0;
    private DispatcherStates disp = null;
    private Current current = null;

    private boolean isReverseConveyor = false;
    private boolean separationHopper1 = false;
    private boolean separationHopper2 = false;
    private boolean separationHopper3 = false;
    private boolean separationHopper4 = false;

    private DBUtilGet dbUtilGet;
    private DBUtilUpdate dbUtilUpdate;

    private boolean isBeepWork = false;
    private boolean isBeepIdle = false;
    private ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    private AlertDialog.Builder mCorrectionBuilder;
    private AlertDialog correctionDialog;
    private View mCorrectionView;
    private Button closeDialog;
    private ImageButton infoRecipeCorrectionWater;
    private CheckBox autoCorrectionShnekSelector;
    private CheckBox autoCorrectionInertOption;
    private CheckBox recepieCorrectionOption;
    private ProgressBar pbChemy;
    private ProgressBar pbSilos;
    private ProgressBar pbWater;
    private ProgressBar pbInert;
    private boolean skipSendUp = false;
    private boolean skipSendDown = false;
    private boolean tapTarget;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.factory_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        dbUtilGet = new DBUtilGet(this);
        dbUtilUpdate = new DBUtilUpdate(this);

        factoryOptionList = new FactoryComplectationBuilder().parseList(dbUtilGet.getFromParameterTable("factory_complectation"));
        configList = new ConfigBuilder().buildScadaParameters(dbUtilGet.getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

        setsID();

        setComplectation();
        new DataManager(getApplicationContext()).runCollector();
        startPolling();
        startThreads();
        setCurrentOptions();
        initActions();

        verifyStoragePermission(this);
        
        tapTarget = mSettings.getBoolean(APP_PREFERENCES_TAP_TARGET, true);
        if (tapTarget) helpTapTarget();

    }
    public void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager() && permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSION_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);

                Intent intent = new Intent();
                intent.setAction(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
    private void helpTapTarget() {
        new TapTargetSequence(this).targets(
                TapTarget.forView(catalogsBtn, "Меню", "Запустить обучение Вы сможете в любое время из меню. Для продолжения коснитесь подсвеченной области.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(touchLock, "Блокировка экрана", "Блокировка экрана защитит, предотвратит ошибки и аварии из-за неосторожных движений и случайных нажатий.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(filterSilos, "Фильтр", "Предназначен для очистки и выброса в атмосферу избытка воздуха, улавливания пыли в запыленном воздухе.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(aerationSilos, "Аэрация", "Воздух, проходящий через слой сыпучего материала «разъединяет» частицы и уменьшает трение между ними, тем самым придавая материалу свойства жидкости.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(vibroSilos, "Вибратор", "Благодаря направленной вибрации гарантирует равномерную подачу материала из силоса.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(overflowChemy, "Датчик", "Датчик перелива химии в дозаторе")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(overflowWater, "Датчик", "Датчик перелива воды в дозаторе")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(closeSensorDC, "Датчики положения затвора", "Датчик положения затвора ДЦ\n1 - закрыто\n2 - открыто")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(closeSensorMixer, "Датчики положения шибера", "Датчик положения шибера смесителя\n1 - закрыто\n2 - открыто на половину\n3 - открыто")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(resetBtn, "Сброс циклов", "Выполняется для обнуления счетчика циклов, перед началом запуска следующей партии.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(correctionBtn, "Корректировка", "Автоматическая корректировка недосыпа шнека. Корректировка рецепта (только для одного замеса).")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(stopCycle, "Стоп перемашивание", "Оставновка перемешивания в автоматическом режиме.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(runCycleBtn, "Старт перемешивание", "Запуск перемешивания в автоматическом режиме, для старта необходимо переключиться в автоматический режим и зажать кнопку в течении 2 секунд.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(manualAutoSwitcher, "Режим работы", "Кнопка переключения режима работы Автоматический/Ручной. Вы можете переключаться между режимами работы, менять настройки и корректировать процессы. Некоторые элементы экрана скрываются и не доступны в ручном режиме.")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                TapTarget.forView(autoDropChecker, "Авторазгрузка", "Автоматический сброс по истечению таймера перемешивания в автоматическом режиме!")
                        .outerCircleColor(R.color.yellow_zzbo_200)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(14)
                        .descriptionTextColor(R.color.black)
                        .textColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(!tapTarget)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60)
        ).listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                //TODO: Установить флаг завершения обучения
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean(APP_PREFERENCES_TAP_TARGET, false);
                editor.apply();
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {

            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            touchLockLayout.setVisibility(VISIBLE);

            Toast.makeText(this, "Проведите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выход");
            builder.setIcon(R.drawable.warning);
            builder.setMessage("Вы действительно хотите закрыть приложение и завершить работу?");

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
    }

    /**
     * при создании активити создаются все элементы, читаем конфиг и прячем то, что выключено
     */
    private void setComplectation() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isReverseConveyor = settings.getBoolean("reverseConveyor", false);
        separationHopper1 = settings.getBoolean("hopper1", false);
        separationHopper2 = settings.getBoolean("hopper2", false);
        separationHopper3 = settings.getBoolean("hopper3", false);
        separationHopper4 = settings.getBoolean("hopper4", false);

        titleBuncker1.setText(configList.getBuncker11());
        titleBuncker2.setText(configList.getBuncker21());
        titleBuncker3.setText(configList.getBuncker31());
        titleBuncker4.setText(configList.getBuncker41());

        titleChemy1.setText(configList.getChemy1());
        titleChemy2.setText(configList.getChemy2());
        titleChemy3.setText(configList.getChemy3());

        titleWater1.setText(configList.getWater1());
        titleWater2.setText(configList.getWater2());

        silosSelector1.setText(configList.getSilos1());
        silosSelector2.setText(configList.getSilos2());
        silosSelector3.setText(configList.getSilos3());

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

        if (!isReverseConveyor) reverseConveyor.setVisibility(GONE);

        if (!separationHopper1) doser12.setVisibility(GONE);
        if (!separationHopper2) doser22.setVisibility(GONE);
        if (!separationHopper3) doser32.setVisibility(GONE);
        if (!separationHopper4) doser42.setVisibility(GONE);

        //количество силосов
        switch (factoryOptionList.getSilosCounter()) {
            case 1:
                cement2LL.setVisibility(GONE);
                break;
            case 2:
                cement2LL.setVisibility(VISIBLE);
                break;
        }

        if (!factoryOptionList.isWater2()) water2LL.setVisibility(GONE);

        //todo: humidityMixerSensor
        switch (factoryOptionList.getChemyCounter()) {
            case 1: {
                chemy2LL.setVisibility(GONE);
                chemy3LL.setVisibility(GONE);
                break;
            }
            case 2: {
                chemy3LL.setVisibility(GONE);
                break;
            }
        }

        hydroGateOption = factoryOptionList.isHydroGate();

        if (factoryOptionList.isDropConveyor()) conveyorUploadDrop.setVisibility(VISIBLE);
        else conveyorUploadDrop.setVisibility(GONE);

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
                if (accessLevel != 1) runVerticalConv.setVisibility(VISIBLE);

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
                if (accessLevel != 1) {
                    skipArrowUp.setVisibility(VISIBLE);
                    skipArrowDown.setVisibility(VISIBLE);
                }
                break;
            }
        }

        if (!factoryOptionList.isAmperageSensor()) amperage.setVisibility(INVISIBLE);

    }

    @SuppressLint("SetTextI18n")
    private void startPolling() {
        new Thread(() -> {
            RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, .5f, RotateAnimation.RELATIVE_TO_SELF, .5f);
            ra.setDuration(500);
            ra.setInterpolator(new LinearInterpolator());
            ra.setRepeatCount(Animation.INFINITE);
            ra.setRepeatMode(Animation.RESTART);

            DecimalFormat decFormat = new DecimalFormat("#0.0");
            CalcUtil calc = new CalcUtil();
            while (true) {
                //todo: здесь условие для break прерывания работы while(true)
                try {
                    if (exchangeLevel == 1) {
                        disp = new Gson().fromJson(OkHttpUtil.getDispatcherStates(), DispatcherStates.class);
                        current = new Gson().fromJson(OkHttpUtil.getCurrent(), Current.class);
                    }

                    //Если состояние завода - работа, то
                    if (globalFactoryState) {
                        stateFactory.setTextColor(Color.GREEN);
                        int dkSum = (int) (retrieval.getHopper11RecipeValue() + retrieval.getHopper12RecipeValue() +
                                retrieval.getHopper21RecipeValue() + retrieval.getHopper22RecipeValue() +
                                retrieval.getHopper31RecipeValue() + retrieval.getHopper32RecipeValue() +
                                retrieval.getHopper41RecipeValue() + retrieval.getHopper42RecipeValue());

                        int chemySum = (int) (retrieval.getChemy1RecipeValue() + retrieval.getChemy2RecipeValue());
                        int cementSum = (int) (retrieval.getCement1RecipeValue() + retrieval.getCement2RecipeValue());

                        int percentDK = (int) (retrieval.getCurrentWeightDKValue() / dkSum * 100);
                        int percentDCh = (int) (retrieval.getCurrentWeightChemyValue() / chemySum * 100);
                        int percentDC = (int) (retrieval.getCurrentWeightCementValue() / cementSum * 100);
                        int percentDW = (int) (retrieval.getCurrentWeightWaterValue() / retrieval.getWaterRecipeValue() * 100);

                        runOnUiThread(() -> {
                            pbInert.setProgress(percentDK, true);
                            pbChemy.setProgress(percentDCh, true);
                            pbWater.setProgress(percentDW, true);
                            pbSilos.setProgress(percentDC, true);
                        });

                        //todo: если стоят галочки набора доз то setValue набранного дозатора должен быть 100%
                    } else {
                        stateFactory.setTextColor(Color.YELLOW);
                        pbInert.setProgress(0);
                        pbChemy.setProgress(0);
                        pbWater.setProgress(0);
                        pbSilos.setProgress(0);
                    }

                    if (retrieval.isSkipPosEndSensorUpValue() == 1 && retrieval.isSkipPosEndSensorDownValue() == 0) {
                        if (skipSendUp) {
                            new CommandDispatcher(tagListManual.get(22)).writeSingleRegisterWithValue(false);
                            skipSendUp = false;
                        }
                    }
                    if (retrieval.isSkipPosEndSensorDownValue() == 1 && retrieval.isSkipPosEndSensorUpValue() == 0) {
                        if (skipSendDown) {
                            new CommandDispatcher(tagListManual.get(23)).writeSingleRegisterWithValue(false);
                            skipSendDown = false;
                        }
                    }

                    new Handler(Looper.getMainLooper()).post(() -> {
                        if ((retrieval.getMixCounterValue() == retrieval.getTotalMixCounterValue()) && (retrieval.getTotalMixCounterValue() != 0)) {
                            stateFactory.setTextColor(Color.YELLOW);
                            stateFactory.setText("Статус работы завода: ожидание");
                            if (globalFactoryState) {
                                mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.context_023_end_auto_cycle);
                                mPlayer.start();
                            }
                            globalFactoryState = false;
                        }

                        recepieCorrectionOption.setChecked(retrieval.getRecepieCorrectOptionValue() == 1);
                        autoCorrectionShnekSelector.setChecked(retrieval.getAutoCorrectShnekOptionValue() == 1);
                        autoCorrectionInertOption.setChecked(retrieval.getAutoCorrectionInertOptValue() == 1);

                        rtDK.setText(String.valueOf(retrieval.getMotoClockHorConveyorValue()));
                        rtLT.setText(String.valueOf(retrieval.getMotoClockVertConvValue()));
                        rtSkip.setText(String.valueOf(retrieval.getMotoClockSkipValue()));
                        rtMixer.setText(String.valueOf(retrieval.getMotoClockMixerValue()));
                        rtShnek1.setText(String.valueOf(retrieval.getMotoClockShnek1Value()));
                        rtShnek2.setText(String.valueOf(retrieval.getMotoClockShnek2Value()));
                        rtShnek3.setText(String.valueOf(retrieval.getMotoClockShnek3Value()));
                        rtVW.setText(String.valueOf(retrieval.getMotoClockValveWaterValue()));
                        rtPW.setText(String.valueOf(retrieval.getMotoClockPumpWaterValue()));
                        rtChemy1.setText(String.valueOf(retrieval.getMotoClockPumpChemy1Value()));
                        rtChemy2.setText(String.valueOf(retrieval.getMotoClockPumpChemy2Value()));
                        rtOil.setText(String.valueOf(retrieval.getMotoClockOilStationValue()));

                        timeMix.setText("Таймер: " + retrieval.getCountdownTimeMixValue());

                        //рецепты на бункерах
                        recipeBuncker1.setText(decFormat.format(retrieval.getHopper11RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageHopper11Value()) + "/" +
                                decFormat.format(retrieval.getShortageHopper11FactValue()));
                        recipeBuncker2.setText(decFormat.format(retrieval.getHopper21RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageHopper21Value()) + "/" +
                                decFormat.format(retrieval.getShortageHopper21FactValue()));
                        recipeBuncker3.setText(decFormat.format(retrieval.getHopper31RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageHopper31Value()) + "/" +
                                decFormat.format(retrieval.getShortageHopper31FactValue()));
                        recipeBuncker4.setText(decFormat.format(retrieval.getHopper41RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageHopper41Value()) + "/" +
                                decFormat.format(retrieval.getShortageHopper41FactValue()));

                        recipeChemy1.setText(decFormat.format(retrieval.getChemy1RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageChemy1Value()));
                        recipeChemy2.setText(decFormat.format(retrieval.getChemy2RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageChemy2Value()));
                        recipeWater1.setText(decFormat.format(retrieval.getWaterRecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageWaterValue()));
                        recipeSilos1.setText(decFormat.format(retrieval.getCement1RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageSilos1Value()));
                        recipeSilos2.setText(decFormat.format(retrieval.getCement2RecipeValue()) + "/" +
                                decFormat.format(retrieval.getShortageSilos2Value()));

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

                        if (factoryOptionList.isAmperageSensor()) {
                            amperage.setText("Сила тока (А): " + decFormat.format(retrieval.getAmperageMixerValue()));
                        }

                        timeMixBtn.setText("Время перемешивания, сек: " + (int) retrieval.getMixingTimeValue() / 1000);

                        //инфо блок слева
                        if (exchangeLevel == 1) {
                            try {
                                if (disp != null) {
                                    operatorName.setText("Оператор смены: " + disp.getOperatorName());
                                    calc.cycleCalcCounter(Float.valueOf(disp.getPartyCapacity()), Float.valueOf(disp.getMixCapacity()));
                                    mixCounterTotal.setText("Всего замесов: " + calc.getCycleSum());
                                    mixCounterCurrent.setText("Текущий замес: " + disp.getMixCounter());
                                    recipeName.setText("Рецепт: " + disp.getCurrentRecepie());
                                    orderName.setText("Заказ: " + disp.getCurrentOrder());
                                    transporter.setText("Водитель: " + disp.getCurrentTrans());
                                    organization.setText("Заказчик: " + disp.getCurrentOrg());
                                    dailyCounter.setText("Произведено за сегодня м³: " + disp.getProductionCapacityDay());
                                    partyOptionBtn.setText("Партия м³\n" + disp.getPartyCapacity());
                                    mixOptionBtn.setText("Замес м³\n" + disp.getMixCapacity());

                                    selectedOrg = disp.getCurrentOrg();
                                    selectedTrans = disp.getCurrentTrans();

                                    if (disp.getFactoryState().equals("1"))
                                        stateFactory.setText("Статус работы завода: работа");
                                    else stateFactory.setText("Статус работы завода: ожидание");
                                }
                                if (current != null) {
                                    if (current.getState().equals("work")) {
                                        stateFactory.setText("Статус работы завода: работа");
                                        stateFactory.setTextColor(Color.GREEN);
                                        isBeepIdle = false;
                                        if (!isBeepWork) {
                                            toneG.startTone(ToneGenerator.TONE_PROP_ACK, 400);
                                            isBeepWork = true;
                                        }
                                    }
                                    if (current.getState().equals("idle")) {
                                        stateFactory.setText("Статус работы завода: ожидание");
                                        stateFactory.setTextColor(Color.YELLOW);
                                        isBeepWork = false;
                                        if (!isBeepIdle) {
                                            toneG.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE, 400);
                                            isBeepIdle = true;
                                        }
                                    }
                                }
                            } catch (Exception exc) {
                                exc.printStackTrace();
                            }
                        } else {
                            operatorName.setText("Оператор смены: " + operatorLogin);
                            mixCounterCurrent.setText("Текущий замес: " + retrieval.getMixCounterValue());
                            mixCounterTotal.setText("Всего замесов: " + retrieval.getTotalMixCounterValue());
                            partyOptionBtn.setText("Партия м³\n" + retrieval.getBatchVolumeValue());
                            mixOptionBtn.setText("Замес м³\n" + retrieval.getMixingCapacity());
//                            recipeOptionBtn.setText("Рецепт\n" + selectedRecepie);

                            if (globalFactoryState)
                                stateFactory.setText("Статус работы завода: работа");
                            else stateFactory.setText("Статус работы завода: ожидание");
                        }

                        //сброс с смесителя
                        if (retrieval.isMixerCloseValue() == 1) {
                            dropProcessArrow.setVisibility(INVISIBLE);
                            closeSensorMixer.setImageResource(R.drawable.shiber_open);
                        } else {
                            dropProcessArrow.setVisibility(VISIBLE);
                            closeSensorMixer.setImageResource(R.drawable.shiber_empty);
                        }

                        if (retrieval.isMixerHalfOpenValue() == 1) {
                            middleSensorMixer.setImageResource(R.drawable.shiber_open);
                        } else {
                            middleSensorMixer.setImageResource(R.drawable.shiber_empty);
                        }

                        if (retrieval.isMixerOpenValue() == 1) {
                            openSensorMixer.setImageResource(R.drawable.shiber_open);
                        } else {
                            openSensorMixer.setImageResource(R.drawable.shiber_empty);
                        }

                        //смеситель не пуст
                        if (retrieval.isMixerNotEmptyValue() == 1) {
                            mixFully.setVisibility(VISIBLE);
                        } else {
                            mixFully.setVisibility(INVISIBLE);
                        }

                        if (retrieval.isMixerRollersWorkIndicationValue() == 1) {
                            startMixerEngine.setBackgroundColor(Color.GREEN);
                            if (!animationMixerState) { //анимация смесителя вкл
                                animationMixerState = true;
                                blade1.startAnimation(ra);
                                blade2.startAnimation(ra);
                            }
                        } else {
                            startMixerEngine.setBackgroundColor(Color.WHITE);
                            animationMixerState = false;
                            blade1.clearAnimation();
                            blade2.clearAnimation();
                        }

                        //скип стрелки
                        if (retrieval.isSkipMoveUpValue() == 1) {
                            skipArrowUp.setVisibility(VISIBLE);
                            skipArrowUp.setImageResource(R.drawable.arrow_up);
                        } else {
                            skipArrowUp.setImageResource(R.drawable.arrow_up_off);
                            skipArrowUp.setVisibility(INVISIBLE);
                        }

                        if (retrieval.isSkipMoveDownValue() == 1) {
                            skipArrowDown.setVisibility(VISIBLE);
                            skipArrowDown.setImageResource(R.drawable.arrow_down);
                        } else {
                            skipArrowDown.setImageResource(R.drawable.arrow_down_off);
                            skipArrowDown.setVisibility(INVISIBLE);
                        }


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
                        if (retrieval.isWaterOverflowSensorValue() == 1) {
                            mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_026_w_overflow);
                            mPlayer.start();
                            overflowWater.setImageResource(R.drawable.shiber_lock);
                        } else overflowWater.setImageResource(R.drawable.shiber_empty);

                        if (retrieval.isChemyOverflowSensorValue() == 1) {
                            mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_025_ch_overflow);
                            mPlayer.start();
                            overflowChemy.setImageResource(R.drawable.shiber_lock);
                        } else overflowChemy.setImageResource(R.drawable.shiber_empty);

                        //цемент концевые
                        if (retrieval.isCementDisFlapOpenPosIndValue() == 1)
                            openSensorDC.setImageResource(R.drawable.shiber_open);
                        else openSensorDC.setImageResource(R.drawable.shiber_empty);

                        if (retrieval.isCementDisFlapClosePosIndValue() == 1) {
                            closeSensorDC.setImageResource(R.drawable.shiber_open);
                        } else {
                            closeSensorDC.setImageResource(R.drawable.shiber_empty);
                        }

                        //заслонки
                        if (retrieval.isHopper11FlapOpenIndValue() == 1) {
                            doser11.setImageResource(R.drawable.doser_open);
                        } else {
                            doser11.setImageResource(R.drawable.doser_close);
                        }
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

                        if (retrieval.getReverseDKValue() == 1)
                            reverseConveyor.setBackgroundColor(Color.GREEN);
                        else reverseConveyor.setBackgroundColor(Color.WHITE);

                        if (retrieval.getConveyorDropValue() == 1)
                            conveyorUploadDrop.setBackgroundColor(Color.GREEN);
                        else conveyorUploadDrop.setBackgroundColor(Color.WHITE);

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
                            runVerticalConv.setText("Стоп Конвейер");
                        } else {
                            runVerticalConv.setBackgroundColor(Color.WHITE);
                            runVerticalConv.setText("Старт Конвейер");
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

                        //фильтр аэрация вибратор
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
//                            manualAutoSwitcher.setText("Переключить в автомат");
                            manualAutoSwitcher.setImageResource(R.drawable.hand_mode);
                            if (factoryOptionList.getTransporterType() != 11 && accessLevel != 1)
                                runVerticalConv.setVisibility(VISIBLE);
                            runCycleBtn.setVisibility(GONE);
                            if (accessLevel != 1) {
                                halfAutoMode.setVisibility(VISIBLE);
                                horLineStart.setVisibility(VISIBLE);
                                if (isReverseConveyor) reverseConveyor.setVisibility(VISIBLE);
                            }

                            if (factoryOptionList.getTransporterType() == 11 && accessLevel != 1) {
                                skipArrowUp.setVisibility(VISIBLE);
                                skipArrowDown.setVisibility(VISIBLE);

                            }
                            if (accessLevel != 1) resetBtn.setVisibility(VISIBLE);
                        } else {
                            globalModeState = true;
                            //TODO Автомат
                            resetBtn.setVisibility(GONE);
//                            manualAutoSwitcher.setText("Переключить в ручной");
                            manualAutoSwitcher.setImageResource(R.drawable.gears_svgrepo_com);
                            runVerticalConv.setVisibility(INVISIBLE);
                            runCycleBtn.setVisibility(VISIBLE);
                            halfAutoMode.setVisibility(INVISIBLE);
                            horLineStart.setVisibility(INVISIBLE);
                            reverseConveyor.setVisibility(INVISIBLE);

//                            if (factoryOptionList.getTransporterType() == 11) {
//                                skipArrowUp.setVisibility(INVISIBLE);
//                                skipArrowDown.setVisibility(INVISIBLE);
//                            }
                        }

                        runOnUiThread(() -> {
                            organizationSelectBtn.setText("Заказчик\n" + selectedOrg);
                            transporterSelectBtn.setText("Водитель\n" + selectedTrans);
                            performance.setText(String.valueOf(retrieval.getScadaPerformanceValue()));
                            infoApp.setText(
                                    "Сборка: "+ BuildConfig.VERSION_NAME +
                                    "\nВерсия ПЛК: "+ retrieval.getFirmwareVersionValue() +
                                    "\nВерсия БД: "+ DATABASE_VERSION +
                                            "\nID Устройства: " + androidID
                            );
                        });

                    });
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    private void initActions() {
        recipesMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
        ordersMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
        orgsMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), OrganizationsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
        driversMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TransportersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
        touchLock.setOnClickListener(view -> {
            touchLockLayout.setVisibility(VISIBLE);
        });
        infoRecipeCorrectionWater.setOnClickListener(view -> {
            runOnUiThread(()->{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Корректировка рецепта по воде");
                builder.setMessage("Корректировка рецепта на первом замесе, при избыточном или недостаточном количестве воды в растворе." +
                        "\n\nДля этого перед запуском партии оператор активирует опцию «Корректировка рецепта» " +
                        "и отключает автоматическую выгрузку бетона из смесителя, после разгрузки дозаторов " +
                        "следующая порция набрана будет только после разгрузки смесителя, поэтому у оператора " +
                        "есть время на оценку показаний датчика тока количеству воды в растворе. " +
                        "\n\nИзменения в рецепт вносятся после окончания работы таймера «время перемешивания», обратного отсчета до выгрузки смеси.");
                builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
        });
        touchUnlock.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 95) touchLockLayout.setVisibility(GONE);
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
        closeAlarmLayoutBtn.setOnClickListener(view -> alarmsWarningLayout.setVisibility(GONE));
        alarm.setOnClickListener(view -> {
            alarmsWarningLayout.setVisibility(VISIBLE);
            try {
                runOnUiThread(() -> alarmWarningText.setText(AlarmUtil.getAlarms()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        correctionBtn.setOnLongClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Корректировка", Toast.LENGTH_SHORT).show();
            return true;
        });
        correctionBtn.setOnClickListener(view -> {
            recepieCorrectionOption.setSelected(true);
            autoCorrectionShnekSelector.setSelected(true);
            correctionDialog.show();
        });
        recepieCorrectionOption.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(154)).writeSingleInvertedBoolRegister();
        });
        autoCorrectionShnekSelector.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(155)).writeSingleInvertedBoolRegister();
        });
        autoCorrectionInertOption.setOnClickListener(view -> {
            new CommandDispatcher(tagListManual.get(169)).writeSingleInvertedBoolRegister();
        });
        closeDialog.setOnClickListener(view -> correctionDialog.hide());
        catalogsBtn.setOnClickListener(view -> {
            tabMenu.selectTab(tabMenu.getTabAt(0));
            tableMenu.setVisibility(VISIBLE);
            fillers.setVisibility(GONE);
            mainMenuDL.setVisibility(VISIBLE);
        });
        mainMenuDL.setOnTouchListener((view, motionEvent) -> {
            mainMenuDL.setVisibility(INVISIBLE);
            return false;
        });
        reportsMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ReportsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
        learningMenu.setOnClickListener(view -> {
            mainMenuDL.setVisibility(GONE);
            helpTapTarget();
        });
        helpMenu.setOnClickListener(view -> openPDF("tablet.pdf"));
        requisitesMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RequisitesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });
        usersMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        passportMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PassportActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        nameBunckersMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NameBunckerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });
        exitMenu.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выход");
            builder.setIcon(R.drawable.warning);
            builder.setMessage("Вы действительно хотите закрыть приложение и завершить работу?");

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
        });
        tabMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.err.println(tabMenu.getSelectedTabPosition());
                if (tabMenu.getSelectedTabPosition() >= 1) {
                    fillers.setVisibility(VISIBLE);
                    tableMenu.setVisibility(GONE);
                } else {
                    fillers.setVisibility(GONE);
                    tableMenu.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        switch (accessLevel) {
            case 1: { //Диспетчер
                stopCycle.setVisibility(INVISIBLE);
                startMixerEngine.setVisibility(INVISIBLE);
                reverseConveyor.setVisibility(INVISIBLE);
                horLineStart.setVisibility(INVISIBLE);
                runVerticalConv.setVisibility(INVISIBLE);
                openMixer.setVisibility(INVISIBLE);
                closeMixer.setVisibility(INVISIBLE);
                incrementWater.setVisibility(GONE);
                decrementWater.setVisibility(GONE);
                resetBtn.setVisibility(INVISIBLE);
                skipArrowUp.setVisibility(INVISIBLE);
                skipArrowDown.setVisibility(INVISIBLE);
                halfAutoMode.setVisibility(INVISIBLE);
                manualAutoSwitcher.setVisibility(INVISIBLE);
                correctionBtn.setVisibility(INVISIBLE);
                learningLayout.setVisibility(GONE);
                break;
            }
            case 0:
            case 3: {
                learningLayout.setVisibility(VISIBLE);
                usersLayout.setVisibility(GONE);

                reverseConveyor.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(137)).writeSingleInvertedBoolRegister();
                });
                conveyorUploadDrop.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(77)).writeSingleInvertedBoolRegister();
                });
                manualAutoSwitcher.setOnLongClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "Режим работы", Toast.LENGTH_SHORT).show();
                    return true;
                });
                manualAutoSwitcher.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Режим работы ");
                    String modeSwitchName = "\"Автоматический\"";
                    builder.setIcon(R.drawable.gears_svgrepo_com);

                    if (globalModeState) {
                        modeSwitchName = "\"Ручной\"";
                        builder.setIcon(R.drawable.hand_mode);
                    }
                    builder.setMessage("Изменить режим работы на " + modeSwitchName + "?");
                    builder.setPositiveButton("Да", (dialog, id) -> new CommandDispatcher(tagListManual.get(0)).writeSingleInvertedBoolRegister());
                    builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });

                doser11.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(2)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser12.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(3)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser21.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(4)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser22.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(5)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser31.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(6)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser32.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(7)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser41.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(8)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doser42.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(9)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

                doserDispenserWater.setOnClickListener(view -> {
                    if (retrieval.isChemyDisFlapOpenIndValue() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Выключите насос слива дозатора химии!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    if (!globalModeState){
                        new CommandDispatcher(tagListManual.get(10)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doserDispenserChemy.setOnClickListener(view -> {
                    if (retrieval.isWaterDisFlapOpenIndValue() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Выключите насос слива дозатора воды!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    if (!globalModeState){
                        new CommandDispatcher(tagListManual.get(11)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doserDispenserCement.setOnClickListener(view -> {
                    if (retrieval.isMixerRollersWorkIndicationValue() == 0) {
                        Constants.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_024_not_start_mixer);
                        mPlayer.start();
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Смеситель не запущен!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(12)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                horLineStart.setOnClickListener(view -> {
                    if (retrieval.isSkipPosEndSensorUpValue() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Скип вверху!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    if (!globalModeState) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Горизонтальный конвейер");
                        builder.setMessage("Вкл/выкл горизонтальный конвейер?");
                        builder.setPositiveButton("Да", (dialog, id) -> new CommandDispatcher(tagListManual.get(13)).writeSingleInvertedBoolRegister());
                        builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                pumpWater.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(14)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doserChemy1.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(15)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doserChemy2.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(16)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doserShnek1.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(18)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                doserShnek2.setOnClickListener(view -> {
                    if (!globalModeState) {
                        new CommandDispatcher(tagListManual.get(19)).writeSingleInvertedBoolRegister();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Включите ручной режим!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

                skipArrowUp.setOnClickListener(view -> {
                    if (retrieval.isMixerRollersWorkIndicationValue() == 0) {
                        Constants.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_024_not_start_mixer);
                        mPlayer.start();
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Смеситель не запущен!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    skipArrowUp.setImageResource(R.drawable.arrow_up);

                    boolean value = true;
                    if (retrieval.isSkipMoveUpValue() == 1) value = false;
                    new CommandDispatcher(tagListManual.get(22)).writeSingleRegisterWithValue(value);
                    new CommandDispatcher(tagListManual.get(23)).writeSingleRegisterWithValue(false);
                    skipSendUp = true;
                    skipSendDown = false;
                });
                skipArrowDown.setOnClickListener(view -> {
//                    if (retrieval.isMixerRollersWorkIndicationValue() == 0) {
//                        Constants.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_024_not_start_mixer);
//                        mPlayer.start();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setTitle("Предупреждение");
//                        builder.setMessage("Смеситель не запущен!");
//                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                        return;
//                    }
                    skipArrowDown.setImageResource(R.drawable.arrow_down);
                    boolean value = true;
                    if (retrieval.isSkipMoveDownValue() == 1) value = false;
                    new CommandDispatcher(tagListManual.get(23)).writeSingleRegisterWithValue(value);
                    new CommandDispatcher(tagListManual.get(22)).writeSingleRegisterWithValue(false);
                    skipSendUp = false;
                    skipSendDown = true;
                });

                aerationSilos.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(24)).writeSingleInvertedBoolRegister();
                    Toast.makeText(getApplicationContext(), "Аэрация", Toast.LENGTH_SHORT).show();
                });
                aerationSilos.setOnLongClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "Аэрация", Toast.LENGTH_LONG).show();
                    return true;
                });
                startMixerEngine.setOnClickListener(view -> {
                    if (retrieval.isCementDisFlapOpenIndValue() == 1){
                        Constants.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.warning_005_dc_open);
                        mPlayer.start();
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setIcon(R.drawable.warning);
                        builder.setTitle("Предупреждение");
                        builder.setMessage("Закройте дозатор цемента!");
                        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    new CommandDispatcher(tagListManual.get(25)).writeSingleInvertedBoolRegister();
                });
                vibroSilos.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(80)).writeSingleInvertedBoolRegister();
                    Toast.makeText(getApplicationContext(), "Вибратор", Toast.LENGTH_SHORT).show();
                });
                vibroSilos.setOnLongClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "Вибратор", Toast.LENGTH_LONG).show();
                    return true;
                });

                filterSilos.setOnLongClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "Фильтр", Toast.LENGTH_LONG).show();
                    return true;
                });
                filterSilos.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(81)).writeSingleInvertedBoolRegister();
                    Toast.makeText(getApplicationContext(), "Фильтр", Toast.LENGTH_SHORT).show();

                });

                openMixer.setOnClickListener(view -> {
                    new Thread(() -> {
                        new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(true);
                    }).start();
                });
                closeMixer.setOnClickListener(view -> {
                    new Thread(() -> {
                        new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(false);
                    }).start();
                });

//                openMixer.setOnTouchListener((view, motionEvent) -> {
//                    switch (motionEvent.getAction()) {
//                        case MotionEvent.ACTION_DOWN: { //удержание
//                            new Thread(() -> {
//                                new CommandDispatcher(tagListManual.get(82)).writeSingleRegisterWithValue(false);
//                                try {
//                                    Thread.sleep(300);
//                                } catch (InterruptedException ex) {
//                                    ex.printStackTrace();
//                                }
//                                new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(true);
//                            }).start();
//                            break;
//                        }
//                        case MotionEvent.ACTION_UP: { // опускание
//                            new Thread(() -> {
//                                try {
//                                    Thread.sleep(300);
//                                } catch (InterruptedException ex) {
//                                    ex.printStackTrace();
//                                }
//                                new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(false);
//                            }).start();
//                            break;
//                        }
//                    }
//                    return false;
//                });
//                closeMixer.setOnTouchListener((view, motionEvent) -> {
//                    switch (motionEvent.getAction()) {
//                            case MotionEvent.ACTION_DOWN: { //удержание
////                                if (hydroGateOption) new CommandDispatcher(tagListManual.get(82)).writeSingleRegisterWithValue(true);
////                                else new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(false);
//                                new Thread(() -> {
//                                    new CommandDispatcher(tagListManual.get(21)).writeSingleRegisterWithValue(false);
//                                    try {
//                                        Thread.sleep(300);
//                                    } catch (InterruptedException ex) {
//                                        ex.printStackTrace();
//                                    }
//                                    new CommandDispatcher(tagListManual.get(82)).writeSingleRegisterWithValue(true);
//                                }).start();
//                                break;
//                            }
//                            case MotionEvent.ACTION_UP: { // опускание
//                                try {
//                                    Thread.sleep(300);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                if (hydroGateOption) new CommandDispatcher(tagListManual.get(82)).writeSingleRegisterWithValue(false);
//                                break;
//                            }
//                        }
//                    return false;
//                });

                valveWater.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(83)).writeSingleInvertedBoolRegister();
                });

                //TODO: Попытка сделать защиту от случайного нажатия
                Handler handlerRunCycle = new Handler();
                Runnable runCycle = () -> {
                    if (new StartAutoCycle().checkProc(this)) {
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
                                    OkHttpUtil.updStateFactory(true);
                                }).start();
                            } else new CommandDispatcher(tagListManual.get(84)).writeSingleFrontBoolRegister(500);

                            globalFactoryState = true;

                            dbUtilUpdate.updCurrentTable("state", "work");   //смена статуса
                            runClickCount = 0;

                            Constants.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.context_024_start_auto_cycle);
                            mPlayer.start();

                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Перемешивание");
                            builder.setMessage("Автоматический режим запущен!");
                            builder.setIcon(R.drawable.play);
                            builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                    } else runClickCount = 0;
                };

                runCycleBtn.setOnTouchListener((arg0, arg1) -> {
                    switch (arg1.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                runClickCount++;
                                if (runClickCount == 3) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setTitle("Подсказка");
                                    builder.setMessage("Для запуска цикла, удерживайте кнопку в течении 2-x секунд");
                                    builder.setIcon(R.drawable.play);
                                    builder.setPositiveButton("OK", (dialog, id) -> {
                                        runClickCount = 0;
                                        dialog.dismiss();
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.show();
                                    break;
                                }
                                if(!globalFactoryState) {
                                    new CommandDispatcher(tagListManual.get(71)).writeSingleFrontBoolRegister(1000);
                                    dbUtilUpdate.updCurrentTable("state", "idle");
                                }
                                handlerRunCycle.postDelayed(runCycle, 2000);
                                break;
                            case MotionEvent.ACTION_UP:
                                handlerRunCycle.removeCallbacks(runCycle);
                                break;
                        }
                    return true;
                });
                stopCycle.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Подтвержение");
                    builder.setMessage("Остановить перемешивание ?");
                    builder.setIcon(R.drawable.stop);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Да", (dialog, id) -> {
                        new Thread(() -> {
                            new CommandDispatcher(tagListManual.get(84)).writeSingleRegisterWithValue(false);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            new CommandDispatcher(tagListManual.get(85)).writeSingleRegisterWithValue(false);
                            if (exchangeLevel == 1) {
                                OkHttpUtil.updStateFactory(false);
                            }
                            globalFactoryState = false;
                            dbUtilUpdate.updCurrentTable("state", "idle");
                        }).start();
                    });
                    builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
                    if (!animationMixerState) {
                        Constants.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_024_not_start_mixer);
                        mPlayer.start();
                        notification("Предупреждение", "Не включен смеситель!");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Уведомление");
                        builder.setMessage("Смеситель не включен!");
                        builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                    new CommandDispatcher(tagListManual.get(115)).writeSingleInvertedBoolRegister();
                });
                incrementWater.setOnClickListener(view -> {
                    if (exchangeLevel == 1)
                        new Thread(() -> new CommandDispatcher(135).writeValue("true")).start();
                    else
                        new CommandDispatcher(tagListManual.get(135)).writeSingleFrontBoolRegister(200);
                });
                decrementWater.setOnClickListener(view -> {
                    if (exchangeLevel == 1)
                        new Thread(() -> new CommandDispatcher(136).writeValue("true")).start();
                    else
                        new CommandDispatcher(tagListManual.get(136)).writeSingleFrontBoolRegister(200);
                });
                autoDropChecker.setOnClickListener(view -> {
                    new CommandDispatcher(tagListManual.get(156)).writeSingleInvertedBoolRegister();
                    //todo: тут смена статуса на окончание для индикации "готово к разгрузке"
                });
                silosSelector1.setOnClickListener(view -> {
                    new Thread(() -> {
                        new CommandDispatcher(tagListManual.get(158)).writeSingleRegisterWithValue(false);
                    }).start();
                });
                silosSelector2.setOnClickListener(view -> {
                    new Thread(() -> {
                        new CommandDispatcher(tagListManual.get(158)).writeSingleRegisterWithValue(true);
                    }).start();
                });
                timeMixBtn.setOnClickListener(view -> {
                    TimeMixingDialog dialog = new TimeMixingDialog();
                    dialog.show(getSupportFragmentManager(), "custom");
                });
                partyOptionBtn.setOnClickListener(view -> {
                    PartyCapacityDialog dialog = new PartyCapacityDialog();
                    dialog.show(getSupportFragmentManager(), "custom");
                });
                mixOptionBtn.setOnClickListener(view -> {
                    MixCapacityDialog dialog = new MixCapacityDialog();
                    dialog.show(getSupportFragmentManager(), "custom");
                });
                resetBtn.setOnLongClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "Обнуление цикла", Toast.LENGTH_SHORT).show();
                    return true;
                });

                resetBtn.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Сброс циклов ");
                    builder.setIcon(R.drawable.reset_svgrepo_com);
                    builder.setMessage("Выполнить обнуление счетчика циклов?");
                    builder.setPositiveButton("Да", (dialog, id) -> {
                        if (exchangeLevel == 1) {
                            new Thread(() -> {
                                new CommandDispatcher(71).writeValue("true");
                                OkHttpUtil.updStateFactory(false);
                            }).start();
                        } else {
                            new CommandDispatcher(tagListManual.get(71)).writeSingleFrontBoolRegister(2000);
                            dbUtilUpdate.updCurrentTable("state", "idle");
                        }
                    });
                    builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
                resetAlarmBtn.setOnClickListener(view -> {
                    alarmsWarningLayout.setVisibility(INVISIBLE);
                    if (exchangeLevel == 1)
                        new Thread(() -> new CommandDispatcher(1).writeValue("true")).start();
                    else
                        new CommandDispatcher(tagListManual.get(1)).writeSingleFrontBoolRegister(2000);
                });
                organizationSelectBtn.setOnClickListener(view -> {
                    new Thread(() -> {
                        List<Organization> orgList = new ArrayList<>();
                        if (exchangeLevel == 1) {
                            orgList.addAll(new Gson().fromJson(OkHttpUtil.getOrganization(), new TypeToken<List<Organization>>() {
                            }.getType()));
                        } else {
                            orgList = dbUtilGet.getOrgs();
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
                        } else transList = dbUtilGet.getTrans();

                        TransporterListDialog dialog = new TransporterListDialog(transList);
                        dialog.show(getSupportFragmentManager(), "custom");
                    }).start();
                });
            }
        }
    }

    private void startThreads() {
        new Thread(() -> {
            while(true){
                retrieval.getValues();
                try{Thread.sleep(100);}catch(InterruptedException e){e.printStackTrace();}
            }
        }).start();

        //plc
        if (exchangeLevel == 0) {
            //поток записи доз в учет по метке "Веса готорвы к чтению"
            new Thread(() -> {
                while (true) {
                    try {
                        if (retrieval.isWeightsReadyReadValue() == 1) {
                            Current current = dbUtilGet.getCurrent();
                            Recepie recepie = dbUtilGet.getRecipeForID(current.getRecipeID());

                            if (selectedOrder.equals("Не указано")) {
                                new ReportRecordingUtil().recordWeights(
                                        this,
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
                                        recepie.getId(),
                                        retrieval.getBatchVolumeValue()
                                );
                            } else {
                                Order selectedOrder = dbUtilGet.getOrderForID(current.getOrderID());
                                selectedOrder.setCurrentMixCount(selectedOrder.getCurrentMixCount() + 1);

                                if (selectedOrder.getCurrentMixCount() == selectedOrder.getTotalMixCounter()) {
                                    selectedOrder.setState(1);
                                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                                    selectedOrder.setCompletionDate(date.format(new Date()));

                                    Constants.selectedOrder = "Не указано";
                                }
                                dbUtilUpdate.updateOrder(selectedOrder);

                                new ReportRecordingUtil().recordWeights(
                                        this,
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

        new Thread(()->{
            while (true) {
                try{
                    Thread.sleep(4000);
                    if (!ConnectionUtil.isWifiConnected(getApplicationContext())) {
                        runOnUiThread(()-> Toast.makeText(getApplicationContext(), "Отсутствует подлючение к сети WiFi", Toast.LENGTH_SHORT).show());
                    }
                    if (!ConnectionUtil.isIpConnected(configList.getPlcIP())) {
                        runOnUiThread(()-> Toast.makeText(getApplicationContext(), "Отсутствует соединение с PLC", Toast.LENGTH_SHORT).show());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        //Произведено за сегодня
        new Thread(() -> {
            SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
            List<Mix> mixList = new ArrayList<>();
            while (true) {
                try {
                    Thread.sleep(2000);
                    if (exchangeLevel == 1) {
                        try {
                            String req = OkHttpUtil.getMixes(date.format(new Date()), date.format(new Date()));
                            if (req.trim().equals("Empty"))
                                mixList = new Gson().fromJson(req, new TypeToken<List<Mix>>() {}.getType());
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }
                    } else mixList = dbUtilGet.getMixListForDate(date.format(new Date()));

                    float result = 0;
                    for (Mix mix : mixList) {
                        if ((mix.getSilos1() == 0) && (mix.getSilos2() == 0)) continue;
                        if (mix.getCompleteCapacity() != 0) result += mix.getCompleteCapacity();
                    }
                    float finalResult = result;

                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (exchangeLevel != 1 && accessLevel != 1) {
                            dailyCounter.setText("Произведено за сегодня м³: " + finalResult);
                            recipeName.setText("Рецепт: " + selectedRecepie);
                            orderName.setText("Заказ: " + selectedOrder);
                            transporter.setText("Водитель: " + selectedTrans);
                            organization.setText("Заказчик: " + selectedOrg);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        if (true) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        StorageMillage storageMillage = new StorageMillageBuilder().getValues(getApplicationContext());
                        new Handler(Looper.getMainLooper()).post(() -> {
                            DecimalFormat df = new DecimalFormat("#0.00");
                            // Set UI elements
                            buncker1.setText(df.format(storageMillage.getBunckerMillage1()));
                            buncker2.setText(df.format(storageMillage.getBunckerMillage2()));
                            buncker3.setText(df.format(storageMillage.getBunckerMillage3()));
                            buncker4.setText(df.format(storageMillage.getBunckerMillage4()));
                            water1.setText(df.format(storageMillage.getWaterMillage()));

                            chemy1.setText(df.format(storageMillage.getChemy1Millage()));
                            chemy2.setText(df.format(storageMillage.getChemy2Millage()));

                            cement1.setText(df.format(storageMillage.getSilos1Millage()));
                            cement2.setText(df.format(storageMillage.getSilos2Millage()));
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void setsID() {
        tabMenu = findViewById(R.id.tabMenu);
        fillers = findViewById(R.id.fillers);
        tableMenu = findViewById(R.id.tableMenu);

        pbChemy = findViewById(R.id.pbChemy);
        pbSilos = findViewById(R.id.pbSilos);
        pbWater = findViewById(R.id.pbWater);
        pbInert = findViewById(R.id.pbInert);

        blade1 = findViewById(R.id.blade1);
        blade2 = findViewById(R.id.blade2);
        silosSelector1 = findViewById(R.id.silosSelector1);
        silosSelector2 = findViewById(R.id.silosSelector2);
        silosSelector3 = findViewById(R.id.silosSelector3);

        touchUnlock = findViewById(R.id.seekBar);
        touchLock = findViewById(R.id.touchLock);
        touchLockLayout = findViewById(R.id.touchLockLayout);
        mainMenuDL = findViewById(R.id.mainMenuDL);

        recipesMenu = findViewById(R.id.recipesMenu);
        ordersMenu = findViewById(R.id.ordersMenu);
        orgsMenu = findViewById(R.id.orgsMenu);
        driversMenu = findViewById(R.id.driversMenu);

        doser11 = findViewById(R.id.doser11);
        doser12 = findViewById(R.id.doser12);
        doser21 = findViewById(R.id.doser21);
        doser22 = findViewById(R.id.doser22);
        doser31 = findViewById(R.id.doser31);
        doser32 = findViewById(R.id.doser32);
        doser41 = findViewById(R.id.doser41);
        doser42 = findViewById(R.id.doser42);

        pumpWater = findViewById(R.id.pumpWater);
        doserShnek1 = findViewById(R.id.doserShnek1);
        doserShnek2 = findViewById(R.id.doserShnek2);
        doserDispenserCement = findViewById(R.id.doserDispenserCement);
        doserDispenserWater = findViewById(R.id.doserDispenserWater);
        doserDispenserChemy = findViewById(R.id.doserDispenserChemy);
        skipUp = findViewById(R.id.skipUp);
        reportsMenu = findViewById(R.id.reportsMenu);
        learningMenu = findViewById(R.id.learninMenu);
        usersLayout = findViewById(R.id.usersLayout);
        learningLayout = findViewById(R.id.learningLayout);
        requisitesLayout = findViewById(R.id.requisitesLayout);
        helpMenu = findViewById(R.id.helpMenu);
        usersMenu = findViewById(R.id.usersMenu);
        passportMenu = findViewById(R.id.passportMenu);
        invoiceMenu = findViewById(R.id.invoiceMenu);
        nameBunckersMenu = findViewById(R.id.nameBunckersMenu);
        requisitesMenu = findViewById(R.id.requisitesMenu);
        exitMenu = findViewById(R.id.exitMenu);
        catalogsBtn = findViewById(R.id.catalogsMenu);
        buncker2View = findViewById(R.id.buncker2View);

        chemy2LL = findViewById(R.id.chemy2LL);
        chemy3LL = findViewById(R.id.chemy3LL);
        water2LL = findViewById(R.id.water2LL);

        cement2LL = findViewById(R.id.cement2LL);

        buncker1 = findViewById(R.id.buncker1);
        buncker2 = findViewById(R.id.buncker2);
        buncker3 = findViewById(R.id.buncker3);
        buncker4 = findViewById(R.id.buncker4);
        chemy1 = findViewById(R.id.chemy1);
        chemy2 = findViewById(R.id.chemy2);
        chemy3 = findViewById(R.id.chemy3);
        water1 = findViewById(R.id.water1);
        water2 = findViewById(R.id.water2);
        cement1 = findViewById(R.id.cement1);
        cement2 = findViewById(R.id.cement2);
        cement3 = findViewById(R.id.cement3);
        cement4 = findViewById(R.id.cement4);

        rtDK = findViewById(R.id.rtDK);
        rtLT = findViewById(R.id.rtLT);
        rtSkip = findViewById(R.id.rtSkip);
        rtMixer = findViewById(R.id.rtMixer);
        rtShnek1 = findViewById(R.id.rtShnek1);
        rtShnek2 = findViewById(R.id.rtShnek2);
        rtShnek3 = findViewById(R.id.rtShnek3);
        rtShnek4 = findViewById(R.id.rtShnek4);
        rtVW = findViewById(R.id.rtVW);
        rtPW = findViewById(R.id.rtPW);
        rtChemy1 = findViewById(R.id.rtChemy1);
        rtChemy2 = findViewById(R.id.rtChemy2);
        rtOil = findViewById(R.id.rtOil);

        timeMix = findViewById(R.id.timeMix);
        recipeName = findViewById(R.id.recepieName);
        orderName = findViewById(R.id.orderName);
        transporter = findViewById(R.id.transporter);
        organization = findViewById(R.id.organization);
        doseWater1 = findViewById(R.id.doseWater1);
        recipeWater1 = findViewById(R.id.recepieWater1);
        doseSilos1 = findViewById(R.id.doseSilos1);

        silos2View = findViewById(R.id.silos2View);
        titleSilos2 = findViewById(R.id.silosSelector2);
        doseSilos2 = findViewById(R.id.doseSilos2);
        silos1View = findViewById(R.id.silos1View);
        levelDownSIlos2 = findViewById(R.id.levelDownSIlos2);
        levelUpSilos2 = findViewById(R.id.levelUpSilos2);
        recipeSilos2 = findViewById(R.id.recepieSilos2);
        recipeSilos1 = findViewById(R.id.recepieSilos1);
        aerationSilos = findViewById(R.id.aerationSilos);
        vibroSilos = findViewById(R.id.vibroSilos);
        filterSilos = findViewById(R.id.filterSilos);
        partyOptionBtn = findViewById(R.id.partyOptionBtn);
        timeMixBtn = findViewById(R.id.timeMixBtn);
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
        recipeChemy2 = findViewById(R.id.recepieChemy2);
        doseChemy2 = findViewById(R.id.doseChemy2);
        recipeChemy1 = findViewById(R.id.recepieChemy1);
        doseChemy1 = findViewById(R.id.doseChemy1);
        startSelfDK = findViewById(R.id.startSelfDK);
        startSelfChemy = findViewById(R.id.startSelfChemy);
        startSelfCement = findViewById(R.id.startSelfCement);
        startSelfWater = findViewById(R.id.startSelfWater);
        halfAutoMode = findViewById(R.id.halfAutoMode);
        amperage = findViewById(R.id.amperage);
        mixFully = findViewById(R.id.mixFully);
        mixerView = findViewById(R.id.mixerView);

        reverseConveyor = findViewById(R.id.reverseConveyor);
        conveyorUploadDrop = findViewById(R.id.conveyorUploadDrop);
        correctionBtn = findViewById(R.id.correctionBtn);
        closeAlarmLayoutBtn = findViewById(R.id.closeAlarmLayoutBtn);
        resetAlarmBtn = findViewById(R.id.resetAlarmBtn);
        alarmsWarningLayout = findViewById(R.id.alarmsWarningLayout);
        alarmWarningText = findViewById(R.id.alarmWarningText);

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
        titleBuncker2 = findViewById(R.id.titleBuncker2);
        titleBuncker3 = findViewById(R.id.titleBuncker3);
        titleBuncker4 = findViewById(R.id.titleBuncker4);

        titleChemy1 = findViewById(R.id.titleChemy1);
        titleChemy2 = findViewById(R.id.titleChemy2);
        titleChemy3 = findViewById(R.id.titleChemy3);

        titleWater1 = findViewById(R.id.titleWater1);
        titleWater2 = findViewById(R.id.titleWater2);

        recipeBuncker4 = findViewById(R.id.recepieBuncker4);
        doseBuncker4 = findViewById(R.id.doseBuncker4);
        recipeBuncker1 = findViewById(R.id.recepieBuncker1);
        recipeBuncker2 = findViewById(R.id.recepieBuncker2);
        doseBuncker1 = findViewById(R.id.doseBuncker1);
        doseBuncker2 = findViewById(R.id.doseBuncker2);
        doserChemy1 = findViewById(R.id.doserChemy1);
        doserChemy2 = findViewById(R.id.doserChemy2);
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
        closeSensorMixer = findViewById(R.id.closeSensorMixer);
        middleSensorMixer = findViewById(R.id.middleSensorMixer);
        openSensorMixer = findViewById(R.id.openSensorMixer);
        openSensorDC = findViewById(R.id.openSensorDC);
        lineArrowIndication = findViewById(R.id.lineArrowIndication);
        verticalConveyorView = findViewById(R.id.verticalConveyorView);
        recipeBuncker3 = findViewById(R.id.recepieBuncker3);
        doseBuncker3 = findViewById(R.id.doseBuncker3);
        valveWater = findViewById(R.id.valveWater);
        alarm = findViewById(R.id.alarm);
        runVerticalConv = findViewById(R.id.runVerticalConv);

        horLineStart = findViewById(R.id.horLineStart);
        organizationSelectBtn = findViewById(R.id.organizationSelectBtn);
        transporterSelectBtn = findViewById(R.id.driverSelectBtn);
        resetBtn = findViewById(R.id.resetCounterBtn);
        performance = findViewById(R.id.performance);
        infoApp = findViewById(R.id.infoApp);

        mCorrectionBuilder = new AlertDialog.Builder(this);
        mCorrectionView = getLayoutInflater().inflate(R.layout.dialog_correction, null);
        mCorrectionBuilder.setView(mCorrectionView);
        correctionDialog = mCorrectionBuilder.create();
        infoRecipeCorrectionWater = mCorrectionView.findViewById(R.id.infoRecipeCorrectionWater);
        autoCorrectionShnekSelector = mCorrectionView.findViewById(R.id.autoCorrectionShnekSelector);
        autoCorrectionInertOption = mCorrectionView.findViewById(R.id.autoCorrectionInertOption);
        recepieCorrectionOption = mCorrectionView.findViewById(R.id.recepieCorrectionOption);
        closeDialog = mCorrectionView.findViewById(R.id.hideDialog);
    }

    @SuppressWarnings("deprecation")
    private void vibrate(int ms) {
        if (SDK_INT >= android.os.Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = (VibratorManager) getApplicationContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            Vibrator vibrator = vibratorManager.getDefaultVibrator();
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            // API < 26
            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(ms);
        }
    }

    public void setCurrentOptions() {
        Current currentOptions = dbUtilGet.getCurrent();
        if (currentOptions != null) {
            Order order = dbUtilGet.getOrderForID(currentOptions.getOrderID());
            Recepie recepie = dbUtilGet.getRecipeForID(currentOptions.getRecipeID());

            if (recepie != null) selectedRecepie = recepie.getMark();
            if (order != null) selectedOrder = order.getNameOrder();

            if (currentOptions.getState() != null) {
                if (currentOptions.getState().equals("work")) globalFactoryState = true;
                if (currentOptions.getState().equals("idle")) globalFactoryState = false;
            }
        }
    }

    public void notification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.logo_zzbo_1)
                .setContentTitle(title)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentText(message);
        NotificationUtil.createNotificationChannel(this, "Предупреждения");
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void openPDF(String filename) {
        copyFile(filename);

        File file = new File(Environment.getExternalStorageDirectory(), "Download/"+ filename);
        Uri uriPdfPath = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
        pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
        pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        try {
            startActivity(pdfOpenIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(this,"Нет приложения для открытия файла PDF",Toast.LENGTH_LONG).show();

        }
    }

    private void copyFile(String name) {
        AssetManager assetManager = getAssets();
        InputStream in;
        OutputStream out;
        File file = new File(getFilesDir(), name);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                in = assetManager.open(name);
                out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/" + name);
                byte[] buffer = new byte[1024];
                int read;
                while((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
                in.close();
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.i("CopyFromAssets", "Не удалось скопировать файл: " + file.getName() + " " + e);
            }
        }
    }
}