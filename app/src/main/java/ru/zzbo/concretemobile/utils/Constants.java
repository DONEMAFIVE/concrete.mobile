package ru.zzbo.concretemobile.utils;

import android.Manifest;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.models.DroidConfig;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.models.Users;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.protocol.profinet.reflections.ReflectionRetrieval;

public class Constants {

    private Constants() {}

    // Static CONSTANT VALUE
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    // имя файла настройки
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_TAP_TARGET = "tap_target";
    public static SharedPreferences mSettings;

    public static int exchangeLevel = -1;

    //connection options
    public static final int RACK = 0;
    public static final int SLOT = 1;

    public static final int REQUEST_TAG_SLEEP = 50;

    public static boolean lockStateRequests = false;        //необходимо при записи области памяти контроллера основной поток чтения приостанавливать до тех пор пока не отработает команда записи
    public static boolean globalFactoryState = false;       //для того, всех кому необходимо знать работает сейчас завод или стоит, false стоит, true производит
    public static boolean globalModeState = false;

    public static DroidConfig configList;

    //анимации
    public static boolean animationMixerState = false;

    public static boolean hydroGateOption = false;
    public static String globalMixStartTime;

    public static List<Tag> tagListMain;
    public static List<Tag> tagListManual;
    public static List<Tag> tagListOptions;
    public static List<Tag> answer = new ArrayList<>();

    /**
     * 0 - operator
     * 1 - dispatcher
     * 2 - engineer
     * 3 - admin
     */
    public static int accessLevel = -1;

    public static String operatorLogin = "Оператор по умолчанию";

    //учетные данные
    public static String selectedOrg = "Не указано";
    public static String selectedTrans = "Не указано";
    public static String selectedRecepie = "Не указано";
    public static String selectedOrder = "Не указано";

    //для обмена между activity
    public static Recepie editedRecepie = null;
    public static Users editedUser = null;
    public static Organization editedOrganization = null;
    public static Transporter editedTransporter = null;
    public static Order editedOrder = null;

    public static ReflectionRetrieval retrieval = new ReflectionRetrieval();
    public static String androidID;
    public static Tag plcMac;

    public static boolean totalDone = false;
    public static boolean mixesDone = false;
    public static boolean partyDone = false;
    public static boolean marksDone = false;

    public static MediaPlayer mPlayer;
    public static AudioManager audioManager;


}
