package ru.zzbo.concretemobile.protocol.profinet.collectors;

import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.lockStateRequests;
import static ru.zzbo.concretemobile.utils.Constants.tagListMain;
import static ru.zzbo.concretemobile.utils.OkHttpUtil.sendGet;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.*;
import ru.zzbo.concretemobile.protocol.profinet.reflections.ReflectionRetrieval;
import ru.zzbo.concretemobile.utils.ConnectionUtil;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class DynamicTagCollector {

    private final int tagCounter = 220;     //количество тэгов - количество из tag_main_thread+1, следить за актуальностью этого значения если добавляются новые тэги в основной поток чтения

    public static DynamicTagCollector tagCollector;

    private static List<Tag> tagList;                       //список тегов полученных из БД
    private static List<BlockBool> tagBoolAnswer;
    private static List<BlockMultiple> tagRealAnswer;
    private static List<BlockMultiple> tagDIntAnswer;
    private static List<BlockMultiple> tagIntAnswer;

    private DynamicTagCollector() {
    }

    public synchronized static DynamicTagCollector getTagCollector() {
        if (tagCollector == null) {
            tagCollector = new DynamicTagCollector();
        }
        tagList = tagListMain;
        DynamicTagBuilder dynamicTagCollector = new DynamicTagBuilder(tagList);
        dynamicTagCollector.buildSortedTags();
        tagBoolAnswer = dynamicTagCollector.getTagBoolAnswer();
        tagRealAnswer = dynamicTagCollector.getTagRealAnswer();
        tagIntAnswer = dynamicTagCollector.getTagIntAnswer();
        tagDIntAnswer = dynamicTagCollector.getTagDIntAnswer();
        return tagCollector;
    }

    //DB16
    private int manualAutoMode;                  //Ручной/автоматический режим
    private int pendingProductionState;         //Статус работы завода ожидание/производство
    private int globalCrashFlag;                //Глобальный флаг аварии
    private int hopper11FlapOpenInd;            //Индикация открытия заслонки бункер инертных 11
    private int hopper12FlapOpenInd;            //Индикация открытия заслонки бункер инертных 12
    private int hopper21FlapOpenInd;            //Индикация открытия заслонки бункер инертных 21
    private int hopper22FlapOpenInd;            //Индикация открытия заслонки бункер инертных 22
    private int hopper31FlapOpenInd;            //Индикация открытия заслонки бункер инертных 31
    private int hopper32FlapOpenInd;            //Индикация открытия заслонки бункер инертных 32
    private int hopper41FlapOpenInd;            //Индикация открытия заслонки бункер инертных 41
    private int hopper42FlapOpenInd;            //Индикация открытия заслонки бункер инертных 42
    private int waterDisFlapOpenInd;            //Индикация открытия заслонки дозатор воды
    private int cementDisFlapOpenInd;          //Индикация открытия заслонки дозатор цемента
    private int cementDisFlapOpenPosInd;        //Индикация положения заслонки дозатор цемента открыто
    private int cementDisFlapClosePosInd;       //Индикация положения заслонки дозатор цемента закрыто
    private int chemyDisFlapOpenInd;            //Индикация открытия заслонки дозатор химии
    private int horConveyorOnInd;               //Индикация включение горизонтального конвейера
    private int waterPumpOnInd;                 //Индикация включения насоса воды
    private int chemy1PumpOnInd;                //Индикация включения насоса химии 1
    private int chemy2PumpOnInd;                //Индикация включения насоса химии 1
    private int cement1AugerOnInd;              //Индикация включения шнека цемента 1
    private int cement2AugerOnInd;              //Индикация включения шнека цемента 2
    private int cement1SiloLevelSensorUp;       //Датчик уровня силоса цемента 1 верх
    private int cement1SiloLevelSensorDown;     //Датчик уровня силоса цемента 1 низ
    private int cement2SiloLevelSensorUp;       //Датчик уровня силоса цемента 2 верх
    private int cement2SiloLevelSensorDown;     //Датчик уровня силоса цемента 2 низ
    private int waterOverflowSensor;            //Датчик перелива вода
    private int chemyOverflowSensor;            //Датчик перелива химия
    private int mixerWindowViewOpenSensor;      //Датчик открытия смотрового окна смесителя
    private int skipPosEndSensorUp;             //Концевой датчик положения скипа - верх
    private int skipPosEndSensorDown;           //Концевой датчик положения скипа - низ
    private int skipPosEndSensorCrashUp;        //Концевой аварийный датчик положения скипа - верх
    private int skipMoveUp;                     //Скип едет вверх
    private int skipMoveDown;                   //Скип едет вниз
    private int aerationOnIndication;           //Индикация включения аэрации
    private int mixerRollersWorkIndication;     //Индикация работы валов смесителя
    private int mixerNotEmpty;                  //Смеситель не пуст
    private int weightsReadyRead;               //Веса готовы к чтению
    private int hopper11DoseDialed;             //Галочка доза Бункер11 набрана
    private int hopper12DoseDialed;             //Галочка доза Бункер12 набрана
    private int hopper21DoseDialed;             //Галочка доза Бункер21 набрана
    private int hopper22DoseDialed;             //Галочка доза Бункер22 набрана
    private int hopper31DoseDialed;             //Галочка доза Бункер31 набрана
    private int hopper32DoseDialed;             //Галочка доза Бункер32 набрана
    private int hopper41DoseDialed;             //Галочка доза Бункер41 набрана
    private int hopper42DoseDialed;             //Галочка доза Бункер42 набрана
    private int waterDoseDialed;                //Галочка доза Вода набрана
    private int chemy1DoseDialed;               //Галочка доза Химия 1 набрана
    private int cement1DoseDialed;              //Галочка доза Цемент 1 набрана
    private int cement2DoseDialed;              //Галочка доза Цемент 1 набрана

    //DB14
    private float hopper11Recipe = 0;           //Рецепт_Бункер_1_1
    private float hopper12Recipe = 0;           //Рецепт_Бункер_1_2
    private float hopper21Recipe = 0;           //Рецепт_Бункер_2_1
    private float hopper22Recipe = 0;           //Рецепт_Бункер_2_2
    private float hopper31Recipe = 0;           //Рецепт_Бункер_3_1
    private float hopper32Recipe = 0;           //Рецепт_Бункер_3_2
    private float hopper41Recipe = 0;           //Рецепт_Бункер_4_1
    private float hopper42Recipe = 0;           //Рецепт_Бункер_4_2
    private float waterRecipe = 0;              //Рецепт_Вода
    private float chemy1Recipe = 0;             //Рецепт_Химия_1
    private float chemy2Recipe = 0;             //Рецепт_Химия_1
    private float cement1Recipe = 0;            //Рецепт_Цемент_1
    private float cement2Recipe = 0;            //Рецепт_Цемент_2
    private float shortageHopper11 = 0;         //Недосып_Бункер_1_1
    private float shortageHopper12 = 0;         //Недосып_Бункер_1_2
    private float shortageHopper21 = 0;         //Недосып_Бункер_2_1
    private float shortageHopper22 = 0;         //Недосып_Бункер_2_2
    private float shortageHopper31 = 0;         //Недосып_Бункер_3_1
    private float shortageHopper32 = 0;         //Недосып_Бункер_3_2
    private float shortageHopper41 = 0;         //Недосып_Бункер_4_1
    private float shortageHopper42 = 0;         //Недосып_Бункер_4_2
    private float shortageWater = 0;            //Недосып_Вода
    private float shortageChemy1 = 0;           //Недосып_Химия_1
    private float volumeCurrentMixerLoad = 0;   //Объем_текущей_загрузки_смесителя
    private float weightCubeConcrete = 2360;    //Масса_одного_куба_бетона
    private float batchVolume = 0;              //Объем_партии
    private float mixingTime = 0;               //Время_перемешивания_уст
    private float horConveyorStartTimer = 0;    //Таймер_включения_горизонтального_конвейера
    private float currentValueDKScale = 0;      //Текущее значение на весах ДК
    private float currentValueWaterScale = 0;   //Текущее значение на весах Вода
    private float currentValueChemyScale = 0;   //Текущее значение на весах Химия
    private float currentValueCementScale = 0;  //Текущее значение на весах Цемент
    private int countdownTimeMix = 0;           //Время_обратного_отсчета_перемеш.
    private float doseHopper11 = 0;             //Доза_Бункер_1_1
    private float doseHopper12 = 0;             //Доза_Бункер_1_2
    private float doseHopper21 = 0;             //Доза_Бункер_2_1
    private float doseHopper22 = 0;             //Доза_Бункер_2_2
    private float doseHopper31 = 0;             //Доза_Бункер_3_1
    private float doseHopper32 = 0;             //Доза_Бункер_3_2
    private float doseHopper41 = 0;             //Доза_Бункер_4_1
    private float doseHopper42 = 0;             //Доза_Бункер_4_2
    private float doseWater = 0;                //Доза_Вода
    private float doseSilos1 = 0;               //Доза_Цемент_1
    private float doseSilos2 = 0;               //Доза_Цемент_2
    private float doseChemy1 = 0;               //Доза_Химия_1
    private float doseChemy2 = 0;               //Доза_Химия_2
    private int skipPosEndSensorCrashDown;      //Концевой аварийный датчик положения скипа - вниз
    private float shortageSilos1 = 0;           //Недосып_Цемент_1
    private float shortageSilos2 = 0;           //Недосып_Цемент_2
    private int vibroSilos1;                    //Вибратор_СЦ1
    private int mixerClose;                     //Смеситель_закр.
    private int mixerHalfOpen;                  //Смеситель_полу.откр.
    private int mixerOpen;                      //Смеситель_откр.

    private float mixingCapacity = 0;           //Замес, объем текущей загрузки

    private int mixCounter = 0;                 //Текущий цикл
    private int totalMixCounter = 0;            //Всего циклов
    private int dkVibro;                        //Вибраторы ДК
    private int dropConveyer;                   //Конвейер выгрузки
    private int dcVibro;                        //Вибратор ДЦ
    private int silosCementVibro;               //Вибратор силоса цемента
    private int silosCementFilter;              //Фильтр цемента
    private int valveWaterBuncker;              //Индикация клапана воды
    private int autoDropChcker;                 //Автоматическая разгрузка смесителя
    private int dropImpulseChcker;              //Импульсная разгрузка смесителя

    private float amperageMixer = 0;            //Сила тока двигателя смесителя
    private float firmwareVersion = 0;          //Версия прошивки контроллера
    private int vibroColdBunckersIndication;    //Галочка на экране при активации опции включения вибраторов ДК

    private int silosSelector;                  //Выбор силоса false - 1, true - 2
    private int vibroDispenserCement;           //Индикация вибратора дозатор цемента

    private int vibroBuncker11Option;           //Включение вибратора 11 и тд
    private int vibroBuncker12Option;           //
    private int vibroBuncker21Option;           //
    private int vibroBuncker22Option;           //
    private int vibroBuncker31Option;           //
    private int vibroBuncker32Option;           //
    private int vibroBuncker41Option;           //
    private int vibroBuncker42Option;           //

    private int selfDKState;                    //статус работы полуавтомата ДК
    private int selfDChState;                   //статус работы полуавтомата ДХ
    private int selfDCState;                    //статус работы полуавтомата ДЦ
    private int selfDWState;                    //статус работы полуавтомата ДВ

    private int skipAlarmIndicator;             //отображение кнопки аварийного запуска скипа в случае остановки из-за отрытого смесителя
    private int verticalConveyorInd;            //наклонный конвейер, только лента-лента транспортер
    private int fibraVibratorIndicator;         //индикатора включения фибры
    private float fibraWeightSensor;            //индикатора включения фибры

    private int skipSensorUp1;                  //концевой скипа верх 1
    private int skipSensorUp2;                  //концевой скипа верх 2
    private int skipSensorDown1;                //концевой скипа низ 1
    private int skipSensorDown2;                //концевой скипа низ 2

    private float recepieWater2;                //рецепт вода 2
    private float shortageWater2;               //недолив вода 2
    private float doseWater2;                   //доза вода 2
    private int pumpWater2Ind;                  //индикация включения насоса воды

    private int reverseDKInd;                   //индикация реверс ДК
    private int uploadConveyorInd;              //индикация конвейер выгрузки ДК

    //моточасы наработка
    private int motoClockHorConveyor;
    private int motoClockVertConv;
    private int motoClockSkip;
    private int motoClockMixer;
    private int motoClockShnek1;
    private int motoClockShnek2;
    private int motoClockShnek3;
    private int motoClockValveWater;
    private int motoClockPumpWater;
    private int motoClockPumpChemy1;
    private int motoClockPumpChemy2;
    private int motoClockOilStation;

    private float sensorHumidity;                 //датчик влажности - текущее значение
    private int waterPumpCounter;               //счетчик насоса воды
    private int waterPumpHumCorrectionControl;  //управление/индикация насос воды для корректировки влажности смеси

    private int alarmMixerShiberOpenned;            //  185;[Авария] - смеситель не закрыт;53;232;0;DB;Int;0;0
    private int alarmMixerThermalProtection;        //  186;[Авария] - сработала тепловая защита смесителя;53;234;0;DB;Int;0;0
    private int alarmSkipThermalProtection;         //  187;[Авария] - сработала тепловая защита скипа;53;236;0;DB;Int;0;0
    private int alarmDKThermalProtection;           //  188;[Авария] - сработала тепловая защита конвейера;53;238;0;DB;Int;0;0
    private int alarmMixerWindowOpenned;            //  189;[Авария] - Крышка смотрового окна не закрыта;53;240;0;DB;Int;0;0
    private int alarmTimeDosingDoser11Fault;        //  190;[Авария] - Превышено время дозирования бункера 1-1;53;242;0;DB;Int;0;0
    private int alarmTimeDosingDoser12Fault;        //  191;[Авария] - Превышено время дозирования бункера 1-2;53;244;0;DB;Int;0;0
    private int alarmTimeDosingDoser21Fault;        //  192;[Авария] - Превышено время дозирования бункера 2-1;53;246;0;DB;Int;0;0
    private int alarmTimeDosingDoser22Fault;        //  193;[Авария] - Превышено время дозирования бункера 2-2;53;248;0;DB;Int;0;0
    private int alarmTimeDosingDoser31Fault;        //  194;[Авария] - Превышено время дозирования бункера 3-1;53;250;0;DB;Int;0;0
    private int alarmTimeDosingDoser32Fault;        //  195;[Авария] - Превышено время дозирования бункера 3-2;53;252;0;DB;Int;0;0
    private int alarmTimeDosingDoser41Fault;        //  196;[Авария] - Превышено время дозирования бункера 4-1;53;254;0;DB;Int;0;0
    private int alarmTimeDosingDoser42Fault;        //  197;[Авария] - Превышено время дозирования бункера 4-2;53;256;0;DB;Int;0;0
    private int warningAutoModeNotActivated;        //  198;[Предупреждение] - не включен автоматический режим;53;258;0;DB;Int;0;0
    private int warningMixerNotEmpty;               //  199;[Предупреждение] - смеситель не пуст;53;260;0;DB;Int;0;0
    private int warningAutoDropDisable;             //  200;[Предупреждение] - авторазгрузка выключена;53;262;0;DB;Int;0;0
    private int alarmWeightDKNotEmpty;              //  201;[Авария] - Дозирующий комплекс не пустой, очистите или выполните калибровку весов;53;264;0;DB;Int;0;0
    private int alarmDCWeightNotEmpty;              //  202;[Авария] - Дозатор цемента не пустой, очистите или выполните калибровку весов;53;266;0;DB;Int;0;0
    private int alarmDWWeightNotEmpty;              //  203;[Авария] - Дозатор воды не пустой, очистите или выполните калибровку весов;53;268;0;DB;Int;0;0
    private int alarmDChWeightNotEmpty;             //  204;[Авария] - Дозатор химии не пустой, очистите или выполните калибровку весов;53;270;0;DB;Int;0;0
    private int alarmDKCalibrateError;              //  205;[Авария] - Дозирующий комплекс отсутствует калибровка или неисправны весы;53;272;0;DB;Int;0;0
    private int alarmDCCalibrateError;              //  206;[Авария] - Дозатор цемента отсутствует калибровка или неисправны весы;53;274;0;DB;Int;0;0
    private int alarmDWCalibrateError;              //  207;[Авария] - Дозатор воды отсутствует калибровка или неисправны весы;53;276;0;DB;Int;0;0
    private int alarmDChCalibrateError;             //  208;[Авария] - Дозатор химии отсутствует калибровка или неисправны весы;53;278;0;DB;Int;0;0
    private int alarmShnekThermalDefence;           //  209;[Авария] - Сработала тепловая защита шнека;53;280;0;DB;Int;0;0
    private int alarmMixerDropError;                //  210;[Авария] - Не открылся смеситель;53;282;0;DB;Int;0;0
    private int warningAutoPowerOffMixerDisabled;   //  211;[Предупреждение] - Автоотключение смесителя после окончания работы в автомате;53;284;0;DB;Int;0;0
    private int alarmMixerEngineNotStarted;         //  212;[Авария] - Смеситель не запустился;53;286;0;DB;Int;0;0
    private int warningConveyorUploadNotStarted;    //  213;[Предупреждение] - Не включен конвейер выгрузки;53;288;0;DB;Int;0;0
    private int alarmOverflowChemy;                 //  214;[Авария] - Перелив химии;53;290;0;DB;Int;0;0
    private int alarmOverflowWater;                 //  215;[Авария] - Перелив воды;53;292;0;DB;Int;0;0
    private int alarmOverFlowDK;                    //  216;[Внимание] - ДК Готов к разгрузке, скип не внизу;53;294;0;DB;Int;0;0
    private int alarmMixerCloseErrorStopSkip;       //  217;[Авария] - Смеситель не закрылся скип остановлен;53;296;0;DB;Int;0;0
    private int alarmDCShiberError;                 //  218;[Предупреждение] - Дозатор цемента не закрыт;53;298;0;DB;Int;0;0
    private int alarmSkipDoubleSensorCrash;         //  219;[Авария] - Одновременно сработали верхние и нижние концевые скипа ;53;298;0;DB;Int;0;0

    private double scadaPerformance = 0;

    public void getValuesFromPLC() {

        List<Tag> request = null;
        Map<Integer, Tag> answer = null;
        CommandDispatcher commandDispatcher;
        long m;
        while (true) {
            try {
                // === статичная часть, замер времени рантайма и расстановка тэгов после получения ответа ===
                m = System.currentTimeMillis();

                commandDispatcher = new CommandDispatcher();

                if (!lockStateRequests) request = commandDispatcher.readMultipleBoolRegister(tagBoolAnswer);
                if (!lockStateRequests) request.addAll(commandDispatcher.readMultipleRealRegister(tagRealAnswer, tagListMain));
                if (!lockStateRequests) request.addAll(commandDispatcher.readMultipleIntRegister(tagIntAnswer, tagListMain));
                if (!lockStateRequests) request.addAll(commandDispatcher.readMultipleDIntRegister(tagDIntAnswer, tagListMain));
                if (request == null) continue;
                if (!lockStateRequests) answer = sortTagAnswer(request);
                // === ответ от PLC сформирован в коллекции answer

                //todo: от рефлексии при таком подходе пользы не будет
                manualAutoMode = answer.get(1).getIntValueIf();
                pendingProductionState = answer.get(2).getIntValueIf();
                globalCrashFlag = answer.get(3).getIntValueIf();

                hopper11FlapOpenInd = answer.get(4).getIntValueIf();
                hopper12FlapOpenInd = answer.get(5).getIntValueIf();
                hopper21FlapOpenInd = answer.get(6).getIntValueIf();
                hopper22FlapOpenInd = answer.get(7).getIntValueIf();
                hopper31FlapOpenInd = answer.get(8).getIntValueIf();
                hopper32FlapOpenInd = answer.get(9).getIntValueIf();
                hopper41FlapOpenInd = answer.get(10).getIntValueIf();
                hopper42FlapOpenInd = answer.get(11).getIntValueIf();

                waterDisFlapOpenInd = answer.get(12).getIntValueIf();
                cementDisFlapOpenInd = answer.get(15).getIntValueIf();
                cementDisFlapOpenPosInd = answer.get(16).getIntValueIf();
                cementDisFlapClosePosInd = answer.get(17).getIntValueIf();
                chemyDisFlapOpenInd = answer.get(18).getIntValueIf();
                horConveyorOnInd = answer.get(21).getIntValueIf();
                waterPumpOnInd = answer.get(22).getIntValueIf();
                chemy1PumpOnInd = answer.get(23).getIntValueIf();
                chemy2PumpOnInd = answer.get(24).getIntValueIf();
                cement1AugerOnInd = answer.get(26).getIntValueIf();
                cement2AugerOnInd = answer.get(27).getIntValueIf();
                cement1SiloLevelSensorUp = answer.get(29).getIntValueIf();
                cement1SiloLevelSensorDown = answer.get(30).getIntValueIf();
                cement2SiloLevelSensorUp = answer.get(31).getIntValueIf();
                cement2SiloLevelSensorDown = answer.get(32).getIntValueIf();
                waterOverflowSensor = answer.get(35).getIntValueIf();
                chemyOverflowSensor = answer.get(36).getIntValueIf();
                mixerWindowViewOpenSensor = answer.get(37).getIntValueIf();
                skipPosEndSensorUp = answer.get(38).getIntValueIf();
                skipPosEndSensorDown = answer.get(39).getIntValueIf();
                skipPosEndSensorCrashUp = answer.get(40).getIntValueIf();
                skipMoveUp = answer.get(41).getIntValueIf();
                skipMoveDown = answer.get(42).getIntValueIf();
                aerationOnIndication = answer.get(43).getIntValueIf();
                mixerRollersWorkIndication = answer.get(44).getIntValueIf();
                mixerNotEmpty = answer.get(45).getIntValueIf();
                weightsReadyRead = answer.get(46).getIntValueIf();

                hopper11DoseDialed = answer.get(47).getIntValueIf();
                hopper12DoseDialed = answer.get(48).getIntValueIf();
                hopper21DoseDialed = answer.get(49).getIntValueIf();
                hopper22DoseDialed = answer.get(50).getIntValueIf();
                hopper31DoseDialed = answer.get(51).getIntValueIf();
                hopper32DoseDialed = answer.get(52).getIntValueIf();
                hopper41DoseDialed = answer.get(53).getIntValueIf();
                hopper42DoseDialed = answer.get(54).getIntValueIf();

                waterDoseDialed = answer.get(55).getIntValueIf();
                chemy1DoseDialed = answer.get(56).getIntValueIf();
                cement1DoseDialed = answer.get(59).getIntValueIf();
                cement2DoseDialed = answer.get(60).getIntValueIf();

                hopper11Recipe = answer.get(62).getRealValueIf();
                hopper12Recipe = answer.get(63).getRealValueIf();
                hopper21Recipe = answer.get(64).getRealValueIf();
                hopper22Recipe = answer.get(65).getRealValueIf();
                hopper31Recipe = answer.get(66).getRealValueIf();
                hopper32Recipe = answer.get(67).getRealValueIf();
                hopper41Recipe = answer.get(68).getRealValueIf();
                hopper42Recipe = answer.get(69).getRealValueIf();

                waterRecipe = answer.get(70).getRealValueIf();
                chemy1Recipe = answer.get(71).getRealValueIf();
                chemy2Recipe = answer.get(72).getRealValueIf();
                cement1Recipe = answer.get(74).getRealValueIf();
                cement2Recipe = answer.get(75).getRealValueIf();

                shortageHopper11 = answer.get(77).getRealValueIf();
                shortageHopper12 = answer.get(78).getRealValueIf();
                shortageHopper21 = answer.get(79).getRealValueIf();
                shortageHopper22 = answer.get(80).getRealValueIf();
                shortageHopper31 = answer.get(81).getRealValueIf();
                shortageHopper32 = answer.get(82).getRealValueIf();
                shortageHopper41 = answer.get(83).getRealValueIf();
                shortageHopper42 = answer.get(84).getRealValueIf();

                shortageWater = answer.get(85).getRealValueIf();
                shortageChemy1 = answer.get(86).getRealValueIf();
                volumeCurrentMixerLoad = answer.get(89).getRealValueIf();
                weightCubeConcrete = answer.get(90).getRealValueIf();
                batchVolume = answer.get(91).getRealValueIf();
                mixingTime = answer.get(92).getRealValueIf();
                horConveyorStartTimer = answer.get(93).getRealValueIf();
                currentValueDKScale = answer.get(94).getRealValueIf();
                currentValueWaterScale = answer.get(95).getRealValueIf();
                currentValueChemyScale = answer.get(96).getRealValueIf();
                currentValueCementScale = answer.get(97).getRealValueIf();
                countdownTimeMix = answer.get(98).getIntValueIf();

                doseHopper11 = answer.get(99).getRealValueIf();
                doseHopper12 = answer.get(100).getRealValueIf();
                doseHopper21 = answer.get(101).getRealValueIf();
                doseHopper22 = answer.get(102).getRealValueIf();
                doseHopper31 = answer.get(103).getRealValueIf();
                doseHopper32 = answer.get(104).getRealValueIf();
                doseHopper41 = answer.get(105).getRealValueIf();
                doseHopper42 = answer.get(106).getRealValueIf();

                doseWater = answer.get(107).getRealValueIf();
                doseSilos1 = answer.get(108).getRealValueIf();
                doseSilos2 = answer.get(109).getRealValueIf();

                doseChemy1 = answer.get(111).getRealValueIf();
                doseChemy2 = answer.get(112).getRealValueIf();
                skipPosEndSensorCrashDown = answer.get(114).getIntValueIf();
                shortageSilos1 = answer.get(115).getRealValueIf();
                vibroSilos1 = answer.get(122).getIntValueIf();
                mixerClose = answer.get(123).getIntValueIf();
                mixerHalfOpen = answer.get(124).getIntValueIf();
                mixerOpen = answer.get(125).getIntValueIf();
                mixingCapacity = answer.get(126).getRealValueIf();
                mixCounter = answer.get(127).getIntValueIf();
                totalMixCounter = answer.get(128).getIntValueIf();
                dropConveyer = answer.get(130).getIntValueIf();
                vibroDispenserCement = answer.get(131).getIntValueIf();
                silosCementFilter = answer.get(133).getIntValueIf();
                valveWaterBuncker = answer.get(134).getIntValueIf();
                autoDropChcker = answer.get(135).getIntValueIf();
                dropImpulseChcker = answer.get(136).getIntValueIf();
                amperageMixer = answer.get(137).getRealValueIf();
                vibroColdBunckersIndication = answer.get(138).getIntValueIf();
                firmwareVersion = answer.get(139).getRealValueIf();

                vibroBuncker11Option = answer.get(141).getIntValueIf();
                vibroBuncker12Option = answer.get(142).getIntValueIf();
                vibroBuncker21Option = answer.get(143).getIntValueIf();
                vibroBuncker22Option = answer.get(144).getIntValueIf();
                vibroBuncker31Option = answer.get(145).getIntValueIf();
                vibroBuncker32Option = answer.get(146).getIntValueIf();
                vibroBuncker41Option = answer.get(147).getIntValueIf();
                vibroBuncker42Option = answer.get(148).getIntValueIf();

                silosSelector = answer.get(140).getIntValueIf();

                selfDKState = answer.get(149).getIntValueIf();
                selfDChState = answer.get(150).getIntValueIf();
                selfDCState = answer.get(151).getIntValueIf();
                selfDWState = answer.get(152).getIntValueIf();

                skipAlarmIndicator = answer.get(153).getIntValueIf();
                verticalConveyorInd = answer.get(154).getIntValueIf();
                fibraVibratorIndicator = answer.get(155).getIntValueIf();
                fibraWeightSensor = answer.get(156).getRealValueIf();

                skipSensorUp1 = answer.get(157).getIntValueIf();
                skipSensorUp2 = answer.get(158).getIntValueIf();
                skipSensorDown1 = answer.get(159).getIntValueIf();
                skipSensorDown2 = answer.get(160).getIntValueIf();

                reverseDKInd = answer.get(166).getIntValueIf();
                uploadConveyorInd = answer.get(167).getIntValueIf();

                motoClockHorConveyor = answer.get(168).getIntValueIf();
                motoClockVertConv = answer.get(169).getIntValueIf();
                motoClockSkip = answer.get(170).getIntValueIf();
                motoClockMixer = answer.get(171).getIntValueIf();
                motoClockShnek1 = answer.get(172).getIntValueIf();
                motoClockShnek2 = answer.get(173).getIntValueIf();
                motoClockShnek3 = answer.get(174).getIntValueIf();
                motoClockValveWater = answer.get(175).getIntValueIf();
                motoClockPumpWater = answer.get(176).getIntValueIf();
                motoClockPumpChemy1 = answer.get(177).getIntValueIf();
                motoClockPumpChemy2 = answer.get(178).getIntValueIf();
                motoClockOilStation = answer.get(179).getIntValueIf();

                sensorHumidity = answer.get(180).getRealValueIf();
                waterPumpCounter = answer.get(181).getIntValueIf();
                waterPumpHumCorrectionControl = answer.get(182).getIntValueIf();

//                alarmMixerShiberOpenned = answer.get(183).getIntValueIf(); //"Галочка Корректировка рецепта"
//                alarmMixerThermalProtection = answer.get(184).getIntValueIf(); //"Галочка Автокорректировка недосыпа шнека"

                //АВАРИИ И ПРЕДУПРЕЖДЕНИЯ
                alarmMixerShiberOpenned = answer.get(185).getIntValueIf();
                alarmMixerThermalProtection = answer.get(186).getIntValueIf();
                alarmSkipThermalProtection = answer.get(187).getIntValueIf();
                alarmDKThermalProtection = answer.get(188).getIntValueIf();
                alarmMixerWindowOpenned = answer.get(189).getIntValueIf();
                alarmTimeDosingDoser11Fault = answer.get(190).getIntValueIf();
                alarmTimeDosingDoser12Fault = answer.get(191).getIntValueIf();
                alarmTimeDosingDoser21Fault = answer.get(192).getIntValueIf();
                alarmTimeDosingDoser22Fault = answer.get(193).getIntValueIf();
                alarmTimeDosingDoser31Fault = answer.get(194).getIntValueIf();
                alarmTimeDosingDoser32Fault = answer.get(195).getIntValueIf();
                alarmTimeDosingDoser41Fault = answer.get(196).getIntValueIf();
                alarmTimeDosingDoser42Fault = answer.get(197).getIntValueIf();
                warningAutoModeNotActivated = answer.get(198).getIntValueIf();
                warningMixerNotEmpty = answer.get(199).getIntValueIf();
                warningAutoDropDisable = answer.get(200).getIntValueIf();
                alarmWeightDKNotEmpty = answer.get(201).getIntValueIf();
                alarmDCWeightNotEmpty = answer.get(202).getIntValueIf();
                alarmDWWeightNotEmpty = answer.get(203).getIntValueIf();
                alarmDChWeightNotEmpty = answer.get(204).getIntValueIf();
                alarmDKCalibrateError = answer.get(205).getIntValueIf();
                alarmDCCalibrateError = answer.get(206).getIntValueIf();
                alarmDWCalibrateError = answer.get(207).getIntValueIf();
                alarmDChCalibrateError = answer.get(208).getIntValueIf();
                alarmShnekThermalDefence = answer.get(209).getIntValueIf();
                alarmMixerDropError = answer.get(210).getIntValueIf();
                warningAutoPowerOffMixerDisabled = answer.get(211).getIntValueIf();
                alarmMixerEngineNotStarted = answer.get(212).getIntValueIf();
                warningConveyorUploadNotStarted = answer.get(213).getIntValueIf();
                alarmOverflowChemy = answer.get(214).getIntValueIf();
                alarmOverflowWater = answer.get(215).getIntValueIf();
                alarmOverFlowDK = answer.get(216).getIntValueIf();
                alarmMixerCloseErrorStopSkip = answer.get(217).getIntValueIf();
                alarmDCShiberError = answer.get(218).getIntValueIf();
                alarmSkipDoubleSensorCrash = answer.get(219).getIntValueIf();

                scadaPerformance = (double) System.currentTimeMillis() - m;
                if (scadaPerformance > 15) System.out.println("performance: " + scadaPerformance);

            } catch (Exception e) {
                System.err.println("Null Pointer Exception. No connection");
            }
        }
    }

    public void getValuesFromPC(Context context) {
        Gson gson = new Gson();
        ReflectionRetrieval retrieval;
        long m = 0;
        while (true) {
            try {
                m = System.currentTimeMillis();
//                Thread.sleep(480);
                retrieval = gson.fromJson(OkHttpUtil.getPlcData(), ReflectionRetrieval.class);
                manualAutoMode = retrieval.isManualAutoModeValue();
                pendingProductionState = retrieval.isPendingProductionStateValue();
                globalCrashFlag = retrieval.isGlobalCrashFlagValue();
                hopper11FlapOpenInd = retrieval.isHopper11FlapOpenIndValue();
                hopper12FlapOpenInd = retrieval.isHopper12FlapOpenIndValue();
                hopper21FlapOpenInd = retrieval.isHopper21FlapOpenIndValue();
                hopper22FlapOpenInd = retrieval.isHopper22FlapOpenIndValue();
                hopper31FlapOpenInd = retrieval.isHopper31FlapOpenIndValue();
                hopper32FlapOpenInd = retrieval.isHopper32FlapOpenIndValue();
                hopper41FlapOpenInd = retrieval.isHopper41FlapOpenIndValue();
                hopper42FlapOpenInd = retrieval.isHopper42FlapOpenIndValue();
                waterDisFlapOpenInd = retrieval.isWaterDisFlapOpenIndValue();
                cementDisFlapOpenInd = retrieval.isCementDisFlapOpenIndValue();
                cementDisFlapOpenPosInd = retrieval.isCementDisFlapOpenPosIndValue();
                cementDisFlapClosePosInd = retrieval.isCementDisFlapClosePosIndValue();
                chemyDisFlapOpenInd = retrieval.isChemyDisFlapOpenIndValue();
                horConveyorOnInd = retrieval.isHorConveyorOnIndValue();
                waterPumpOnInd = retrieval.isWaterPumpOnIndValue();
                chemy1PumpOnInd = retrieval.isChemy1PumpOnIndValue();
                chemy2PumpOnInd = retrieval.isChemy2PumpOnIndValue();
                cement1AugerOnInd = retrieval.isCement1AugerOnIndValue();
                cement2AugerOnInd = retrieval.isCement2AugerOnIndValue();
                cement1SiloLevelSensorUp = retrieval.isCement1SiloLevelSensorUpValue();
                cement1SiloLevelSensorDown = retrieval.isCement1SiloLevelSensorDownValue();
                cement2SiloLevelSensorUp = retrieval.isCement2SiloLevelSensorUpValue();
                cement2SiloLevelSensorDown = retrieval.isCement2SiloLevelSensorDownValue();
                waterOverflowSensor = retrieval.isWaterOverflowSensorValue();
                chemyOverflowSensor = retrieval.isChemyOverflowSensorValue();
                mixerWindowViewOpenSensor = retrieval.isMixerWindowViewOpenSensorValue();
                skipPosEndSensorUp = retrieval.isSkipPosEndSensorUpValue();
                skipPosEndSensorDown = retrieval.isSkipPosEndSensorDownValue();
                skipPosEndSensorCrashUp = retrieval.isSkipPosEndSensorCrashUpValue();
                skipMoveUp = retrieval.isSkipMoveUpValue();
                skipMoveDown = retrieval.isSkipMoveDownValue();
                aerationOnIndication = retrieval.isAerationOnIndicationValue();
                mixerRollersWorkIndication = retrieval.isMixerRollersWorkIndicationValue();
                mixerNotEmpty = retrieval.isMixerNotEmptyValue();
                weightsReadyRead = retrieval.isWeightsReadyReadValue();

                //DB14
                hopper11Recipe = retrieval.getHopper11RecipeValue();
                hopper12Recipe = retrieval.getHopper12RecipeValue();
                hopper21Recipe = retrieval.getHopper21RecipeValue();
                hopper22Recipe = retrieval.getHopper22RecipeValue();
                hopper31Recipe = retrieval.getHopper31RecipeValue();
                hopper32Recipe = retrieval.getHopper32RecipeValue();
                hopper41Recipe = retrieval.getHopper41RecipeValue();
                hopper42Recipe = retrieval.getHopper42RecipeValue();
                waterRecipe = retrieval.getWaterRecipeValue();
                chemy1Recipe = retrieval.getChemy1RecipeValue();
                chemy2Recipe = retrieval.getChemy2RecipeValue();
                cement1Recipe = retrieval.getCement1RecipeValue();
                cement2Recipe = retrieval.getCement2RecipeValue();
                shortageHopper11 = retrieval.getShortageHopper11Value();
                shortageHopper12 = retrieval.getShortageHopper12Value();
                shortageHopper21 = retrieval.getShortageHopper21Value();
                shortageHopper22 = retrieval.getShortageHopper22Value();
                shortageHopper31 = retrieval.getShortageHopper31Value();
                shortageHopper32 = retrieval.getShortageHopper32Value();
                shortageHopper41 = retrieval.getShortageHopper41Value();
                shortageHopper42 = retrieval.getShortageHopper42Value();
                shortageWater = retrieval.getShortageWaterValue();
                shortageChemy1 = retrieval.getShortageChemy1Value();
                volumeCurrentMixerLoad = retrieval.getVolumeCurrentMixerLoadValue();
                weightCubeConcrete = retrieval.getWeightCubeConcreteValue();
                batchVolume = retrieval.getBatchVolumeValue();
                mixingTime = retrieval.getMixingTimeValue();
                horConveyorStartTimer = retrieval.getHorConveyorStartTimerValue();
                currentValueDKScale = retrieval.getCurrentWeightDKValue();
                currentValueWaterScale = retrieval.getCurrentWeightWaterValue();
                currentValueChemyScale = retrieval.getCurrentWeightChemyValue();
                currentValueCementScale = retrieval.getCurrentWeightCementValue();
                countdownTimeMix = retrieval.getCountdownTimeMixValue();
                doseHopper11 = retrieval.getDoseHopper11Value();
                doseHopper12 = retrieval.getDoseHopper12Value();
                doseHopper21 = retrieval.getDoseHopper21Value();
                doseHopper22 = retrieval.getDoseHopper22Value();
                doseHopper31 = retrieval.getDoseHopper31Value();
                doseHopper32 = retrieval.getDoseHopper32Value();
                doseHopper41 = retrieval.getDoseHopper41Value();
                doseHopper42 = retrieval.getDoseHopper42Value();
                doseWater = retrieval.getDoseWaterValue();
                doseSilos1 = retrieval.getDoseSilos1Value();
                doseSilos2 = retrieval.getDoseSilos2Value();
                doseChemy1 = retrieval.getDoseChemy1Value();
                doseChemy2 = retrieval.getDoseChemy2Value();
                skipPosEndSensorCrashDown = retrieval.isSkipPosEndSensorCrashDownValue();
                shortageSilos1 = retrieval.getShortageSilos1Value();
                shortageSilos2 = retrieval.getShortageSilos1Value();
                vibroSilos1 = retrieval.isVibroSilos1Value();
                mixerClose = retrieval.isMixerCloseValue();
                mixerHalfOpen = retrieval.isMixerHalfOpenValue();
                mixerOpen = retrieval.isMixerOpenValue();

                mixingCapacity = retrieval.getMixingCapacity();

                mixCounter = retrieval.getMixCounterValue();
                totalMixCounter = retrieval.getTotalMixCounterValue();
//                dkVibro = retrieval.isVibroDKValue();
                dropConveyer = retrieval.getConveyorDropValue();
                dcVibro = retrieval.isVibroDispenserDCValue();
                silosCementVibro = retrieval.isVibroSilos1Value();
                silosCementFilter = retrieval.isSilosCementFilterValue();
                valveWaterBuncker = retrieval.isValveWaterBunckerValue();
                autoDropChcker = retrieval.isAutoDropChckerValue();
                dropImpulseChcker = retrieval.isDropImpulseChckerValue();

                amperageMixer = retrieval.getAmperageMixerValue();
                firmwareVersion = retrieval.getFirmwareVersionValue();
                vibroColdBunckersIndication = retrieval.isVibroColdBunckersIndicationValue();

                silosSelector = retrieval.isSilosSelectorValue();
                vibroDispenserCement = retrieval.isVibroDispenserDCValue();

                vibroBuncker11Option = retrieval.getVibroBuncker11OptionValue();
                vibroBuncker12Option = retrieval.getVibroBuncker12OptionValue();
                vibroBuncker21Option = retrieval.getVibroBuncker21OptionValue();
                vibroBuncker22Option = retrieval.getVibroBuncker22OptionValue();
                vibroBuncker31Option = retrieval.getVibroBuncker31OptionValue();
                vibroBuncker32Option = retrieval.getVibroBuncker32OptionValue();
                vibroBuncker41Option = retrieval.getVibroBuncker41OptionValue();
                vibroBuncker42Option = retrieval.getVibroBuncker42OptionValue();

                selfDKState = retrieval.isSelfDKStateValue();
                selfDChState = retrieval.isSelfDChStateValue();
                selfDCState = retrieval.isSelfDCStateValue();
                selfDWState = retrieval.isSelfDWStateValue();

                skipAlarmIndicator = retrieval.getSkipAlarmIndicatorValue();
                verticalConveyorInd = retrieval.getVerticalConveyorIndValue();
                fibraVibratorIndicator = retrieval.getFibraVibratorIndicatorValue();
                fibraWeightSensor = retrieval.getFibraWeightSensorValue();

                skipSensorUp1 = retrieval.getSkipSensorUp1Value();
                skipSensorUp2 = retrieval.getSkipSensorUp2Value();
                skipSensorDown1 = retrieval.getSkipSensorDown1Value();
                skipSensorDown2 = retrieval.getSkipSensorDown2Value();

//                recepieWater2 = retrieval.water2();
//                shortageWater2 = retrieval.getShortageWater2Value();
                doseWater2 = retrieval.getDoseWater2Value();
//                pumpWater2Ind = retrieval.();

                reverseDKInd = retrieval.getReverseDKValue();
                uploadConveyorInd = retrieval.getUploadConveyorDKValue();

                //моточасы наработка
                motoClockHorConveyor = retrieval.getMotoClockHorConveyorValue();
                motoClockVertConv = retrieval.getMotoClockVertConvValue();
                motoClockSkip = retrieval.getMotoClockSkipValue();
                motoClockMixer = retrieval.getMotoClockMixerValue();
                motoClockShnek1 = retrieval.getMotoClockShnek1Value();
                motoClockShnek2 = retrieval.getMotoClockShnek2Value();
                motoClockShnek3 = retrieval.getMotoClockShnek3Value();
                motoClockValveWater = retrieval.getMotoClockValveWaterValue();
                motoClockPumpWater = retrieval.getMotoClockPumpWaterValue();
                motoClockPumpChemy1 = retrieval.getMotoClockPumpChemy1Value();
                motoClockPumpChemy2 = retrieval.getMotoClockPumpChemy2Value();
                motoClockOilStation = retrieval.getMotoClockOilStationValue();

                sensorHumidity = retrieval.getSensorHumidityValue();
                waterPumpCounter = retrieval.getWaterPumpCounterValue();
                waterPumpHumCorrectionControl = retrieval.getWaterPumpHumCorrectionControlValue();

                alarmMixerShiberOpenned = retrieval.getAlarmMixerShiberOpennedValue();
                alarmMixerThermalProtection = retrieval.getAlarmMixerThermalProtectionValue();
                alarmSkipThermalProtection = retrieval.getAlarmSkipThermalProtectionValue();
                alarmDKThermalProtection = retrieval.getAlarmDKThermalProtectionValue();
                alarmMixerWindowOpenned = retrieval.getAlarmMixerWindowOpennedValue();
                alarmTimeDosingDoser11Fault = retrieval.getAlarmTimeDosingDoser11FaultValue();
                alarmTimeDosingDoser12Fault = retrieval.getAlarmTimeDosingDoser12FaultValue();
                alarmTimeDosingDoser21Fault = retrieval.getAlarmTimeDosingDoser21FaultValue();
                alarmTimeDosingDoser22Fault = retrieval.getAlarmTimeDosingDoser22FaultValue();
                alarmTimeDosingDoser31Fault = retrieval.getAlarmTimeDosingDoser31FaultValue();
                alarmTimeDosingDoser32Fault = retrieval.getAlarmTimeDosingDoser32FaultValue();
                alarmTimeDosingDoser41Fault = retrieval.getAlarmTimeDosingDoser41FaultValue();
                alarmTimeDosingDoser42Fault = retrieval.getAlarmTimeDosingDoser42FaultValue();
                warningAutoModeNotActivated = retrieval.getWarningAutoModeNotActivatedValue();
                warningMixerNotEmpty = retrieval.getWarningMixerNotEmptyValue();
                warningAutoDropDisable = retrieval.getWarningAutoDropDisableValue();
                alarmWeightDKNotEmpty = retrieval.getAlarmWeightDKNotEmptyValue();
                alarmDCWeightNotEmpty = retrieval.getAlarmDCWeightNotEmptyValue();
                alarmDWWeightNotEmpty = retrieval.getAlarmDWWeightNotEmptyValue();
                alarmDChWeightNotEmpty = retrieval.getAlarmDChWeightNotEmptyValue();
                alarmDKCalibrateError = retrieval.getAlarmDKCalibrateErrorValue();
                alarmDCCalibrateError = retrieval.getAlarmDCCalibrateErrorValue();
                alarmDWCalibrateError = retrieval.getAlarmDWCalibrateErrorValue();
                alarmDChCalibrateError = retrieval.getAlarmDChCalibrateErrorValue();
                alarmShnekThermalDefence = retrieval.getAlarmShnekThermalDefenceValue();
                alarmMixerDropError = retrieval.getAlarmMixerDropErrorValue();
                warningAutoPowerOffMixerDisabled = retrieval.getWarningAutoPowerOffMixerDisabledValue();
                alarmMixerEngineNotStarted = retrieval.getAlarmMixerEngineNotStartedValue();
                warningConveyorUploadNotStarted = retrieval.getWarningConveyorUploadNotStartedValue();
                alarmOverflowChemy = retrieval.getAlarmOverflowChemyValue();
                alarmOverflowWater = retrieval.getAlarmOverflowWaterValue();
                alarmOverFlowDK = retrieval.getAlarmOverFlowDKValue();
                alarmMixerCloseErrorStopSkip = retrieval.getAlarmMixerCloseErrorStopSkipValue();
                alarmDCShiberError = retrieval.getAlarmDCShiberErrorValue();
                alarmSkipDoubleSensorCrash = retrieval.getAlarmSkipDoubleSensorCrashValue();

                scadaPerformance = (double) System.currentTimeMillis() - m;
                if (scadaPerformance > 15) System.out.println("performance: " + scadaPerformance);

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> {
//                    failed to connect to /192.168.250.59 (port 5050) from /192.168.250.51 (port 47844) after 10000ms
                    Toast.makeText(context, "Не удалось установить связь c " + configList.getScadaIP() , Toast.LENGTH_LONG).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (!ConnectionUtil.isWifiConnected(context)) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(context, "Соединение с ПК прервано! Подключите устройство к сети WIFI", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
                    });
                    try {Thread.sleep(10000);} catch (InterruptedException ex) {ex.printStackTrace();}
                }
            }
        }
    }

    public void getValuesFromRest() {
        //todo: implement this
    }

    //разложить по номерам пришедший от PLC ответ в HashMap
    private Map<Integer, Tag> sortTagAnswer(List<Tag> request) {
        if (request == null) return null;
        Map<Integer, Tag> result = new HashMap<>();
        int chk = 0;
        for (int i = 0; i < tagCounter; i++) {
            for (Tag tag : request) {
                if (i == tag.getId()) {
                    result.put(i, tag);
                    chk = 1;
                    break;
                }
            }
            if (chk == 0) { //чтобы не выполнять проверку на null, если вдруг ответ таки придет в null заполняю пустые тэги
                result.put(i, new Tag(0, 0, 0, 0, "", false, -1, -1, -1, "", 0));
            }
            if (chk == 1) chk = 0;
        }
        return result;
    }

}
