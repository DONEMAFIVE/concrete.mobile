package ru.zzbo.concretemobile.utils;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.models.Configs;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

public class Constants {

    private Constants(){};

    public static int exchangeLevel = -1;

    public static final String SOFTWARE_VERSION = "0.1";

    //connection options
    public static final int RACK = 0;
    public static final int SLOT = 1;

    public static final int REQUEST_TAG_SLEEP = 50;

    public static boolean lockStateRequests = false;        //необходимо при записи области памяти контроллера основной поток чтения приостанавливать до тех пор пока не отработает команда записи
    public static boolean globalFactoryState = false;       //для того, всех кому необходимо знать работает сейчас завод или стоит, false стоит, true производит
    public static boolean globalModeState = false;

    public static Configs configList;

    //анимации
    public static boolean animationMixerState = false;

    public static boolean hydroGateOption = false;
    public static String globalMixStartTime;

    public static List<Tag> tagListMain;
    public static List<Tag> tagListManual;
    public static List<Tag> tagListOptions;
    public static List<Tag> answer = new ArrayList<>();

    public static int accessLevel = -1;

    public static String operatorLogin = "Оператор по умолчанию";

    //учетные данные
    public static String selectedOrg = "Не указано";
    public static String selectedTrans = "Не указано";
    public static String selectedRecepie = "Не указано";
    public static String selectedOrder = "Не указано";

    //для обмена между activity
    public static Recepie editedRecepie = null;
    public static Organization editedOrganization = null;
    public static Transporter editedTransporter = null;
    public static Order editedOrder = null;


}
