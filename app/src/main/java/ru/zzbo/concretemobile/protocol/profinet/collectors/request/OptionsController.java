package ru.zzbo.concretemobile.protocol.profinet.collectors.request;


import static ru.zzbo.concretemobile.utils.Constants.lockStateRequests;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.protocol.profinet.collectors.DynamicTagBuilder;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.BlockMultiple;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.Constants;

public class OptionsController {

    private int tagCounter;

    private List<Tag> tagList;

    private static List<BlockMultiple> tagRealAnswer;
    private static List<BlockMultiple> tagDIntAnswer;
    private static List<BlockMultiple> tagIntAnswer;

    private float mixCapacity;                          //1;Объем смесителя;72;0;0;DB;Real;1.0;0
    private float mixTime ;                             //2;Время перемешивания;72;4;0;DB;Real;20.0;0
    private int splitBuncker1;                          //3;Настроки ДК - бункер 1 разлен на 1.1 и 1.2;73;0;0;DB;Int;0;0
    private int splitBuncker2;                          //4;Настроки ДК - бункер 2 разлен на 2.1 и 2.2;73;2;0;DB;Int;0;0
    private int splitBuncker3;                          //5;Настроки ДК - бункер 3 разлен на 3.1 и 3.2;73;4;0;DB;Int;0;0
    private int splitBuncker4;                          //6;Настроки ДК - бункер 4 разлен на 4.1 и 4.2;73;6;0;DB;Int;0;0
    private int typeDispenserWater1;                    //7;Настроки ДВ - дозатор 1 0-тензометр 1-импульсный;73;8;0;DB;Int;0;0
    private int typeDispenserWater2;                    //8;Настроки ДВ - дозатор 2 0-тензометр 1-импульсный;73;10;0;DB;Int;0;0
    private int typeDispenserWater3;                    //9;Настроки ДВ - дозатор 3 0-тензометр 1-импульсный;73;12;0;DB;Int;0;0
    private int typeDispenserWater4;                    //10;Настроки ДВ - дозатор 4 0-тензометр 1-импульсный;73;14;0;DB;Int;0;0
    private int typeTransporterDK;                      //11;Настроки ДК - тип транспортера (0 -лента 11-лента-скип 12 -лента-лента);73;50;0;DB;Int;0;0
    private int bunckerCountDK;                         //12;Настроки ДК - кол-во бункеров инерт (1..4);73;52;0;DB;Int;4;0
    private int bunckerCountDW;                         //13;Настроки ДВ - кол-во дозаторов воды (1..4);73;54;0;DB;Int;0;0
    private int bunckerCountDC;                         //14;Настроки ДЦ - кол-во дозаторов цемента (1..4);73;56;0;DB;Int;0;0
    private int silosCountDC1;                          //15;Настроки ДЦ1 - кол-во силосов на дозатор (1..3);73;58;0;DB;Int;0;0
    private int silosCountDC2;                          //16;Настроки ДЦ2 - кол-во силосов на дозатор (1..3);73;60;0;DB;Int;0;0
    private int silosCountDC3;                          //17;Настроки ДЦ3 - кол-во силосов на дозатор (1..3);73;62;0;DB;Int;0;0
    private int silosCountDC4;                          //18;Настроки ДЦ4 - кол-во силосов на дозатор (1..3);73;64;0;DB;Int;0;0
    private int countDispensersCh;                      //19;Настроки ДХ - кол-во дозаторов химии (1..4);73;66;0;DB;Int;0;0
    private int countBunckerDCh1;                       //20;Настроки ДХ1 - кол-во химий на дозатор (1..3);73;68;0;DB;Int;0;0
    private int countBunckerDCh2;                       //21;Настроки ДХ2 - кол-во химий на дозатор (1..3);73;70;0;DB;Int;0;0
    private int countBunckerDCh3;                       //22;Настроки ДХ3 - кол-во химий на дозатор (1..3);73;72;0;DB;Int;0;0
    private int countBunckerDCh4;                       //23;Настроки ДХ4 - кол-во химий на дозатор (1..3);73;74;0;DB;Int;0;0
    private long timeOpenImpulseDK;                     //24;Время открытия затвора при импульсном довешивании;75;0;0;DB;DInt;0;0
    private long timeCloseImpulseDK;                    //25;Время паузы (закрытия) затвора при имп. довешивании;75;4;0;DB;DInt;0;0
    private long timeDosingDK;                          //26;Время дозирования;75;8;0;DB;DInt;0;0
    private float mixCapacityMax;                       //27;Объем замеса максимальный;72;8;0;DB;Real;0;0
    private long countDownMixing;                       //28;Время мокрого перемешивания;75;12;0;DB;DInt;0;0
    private long timeDropMixer;                         //29;Время выгрузки смесителя;75;16;0;DB;DInt;0;0
    private long timeWaitingOpenMixer;                  //30;Вр ожид разгр смес;75;20;0;DB;DInt;0;0
    private long timeDashShiberMixer;                   //31;Вр рывка разг смес;75;24;0;DB;DInt;1000;0
    private float weightCountDownDK;                    //32;ДК - вес, при котором стартует таймер конца работы;72;12;0;DB;Real;100;0
    private float weightCountDownDC;                    //33;ДЦ - вес, при котором стартует таймер конца работы;72;16;0;DB;Real;0;0
    private float weightCountDownDCh;                   //34;ДХ - вес, при котором стартует таймер конца работы;72;20;0;DB;Real;0;0
    private float weightCountDownDW;                    //35;ДВ - вес, при котором стартует таймер конца работы;72;24;0;DB;Real;0;0
    private long timeRunAfterMixWeightDK;               //36;ДК - время работы после мин веса;75;28;0;DB;DInt;0;0
    private long timeRunAfterMixWeightDC;               //37;ДЦ - время работы после мин веса;75;32;0;DB;DInt;0;0
    private long timeRunAfterMixWeightDCh;              //38;ДХ - время работы после мин веса;75;36;0;DB;DInt;0;0
    private long timeRunAfterMixWeightDW;               //39;ДВ - время работы после мин веса;75;40;0;DB;DInt;0;0
    private long timeoutPumpCh;                         //40;НСХ - задержка вкл. насоса слива химии;75;44;0;DB;DInt;0;0
    private long timeoutValveCh;                        //41;КСХ - задержка откл. клапана слива химии;75;48;0;DB;DInt;0;0
    private long timeoutOnPumpDropW;                    //42;НСВ - задержка вкл. насоса слива воды;75;52;0;DB;DInt;0;0
    private long timeoutOffPumpDropW;                   //43;КСВ - задержка откл. клапана слива воды;75;56;0;DB;DInt;0;0
    private long timeWorkVibroDK;                       //44;Время включения работы вибратора ДК;75;60;0;DB;DInt;0;0
    private long timeWaitVibroDK;                       //45;Время паузы работы вибратора ДК;75;64;0;DB;DInt;0;0
    private long timeDropSkip;                          //46;Вр_выгрузки_скипа;75;68;0;DB;DInt;10000;0
    private long timeDropVertConveyor;                  //47;Вр_конвейер выгрузки;75;72;0;DB;DInt;0;0
    private float maxWeigthAutoCorrectTareDK;           //48;Максимальный суммарный вес автосброса весов ДК;72;28;0;DB;Real;0;0
    private float maxWeigthAutoCorrectTareDCh;          //49;Максимальный суммарный вес автосброса весов ДХ;72;32;0;DB;Real;0;0
    private float maxWeigthAutoCorrectTareDW;           //50;Максимальный суммарный вес автосброса весов ДВ;72;36;0;DB;Real;0;0
    private float maxWeigthAutoCorrectTareDC;           //51;Максимальный суммарный вес автосброса весов ДЦ;72;40;0;DB;Real;0;0
    private float autoNullWeightDK;                     //52;Вес после достижения которого сработает автосброс весов ДК;72;44;0;DB;Real;0;0
    private float autoNullWeightDCh;                    //53;Вес после достижения которого сработает автосброс весов ДХ;72;48;0;DB;Real;0;0
    private float autoNullWeightDW;                     //54;Вес после достижения которого сработает автосброс весов ДВ;72;52;0;DB;Real;0;0
    private float autoNullWeightDC;                     //55;Вес после достижения которого сработает автосброс весов ДЦ;72;56;0;DB;Real;0;0
    private int percentSwitchToImpulseModeDK;           //56;Процент веса переключения в импульсный режим;73;76;0;DB;Int;0;0
    private int autoNullOptionDW;                       //57;Вода галочка автоматичесий ноль весов;73;16;0;DB;Int;0;0
    private long timeOpenImpulseShiberMixer;            //58;Время открытия затвора при импульсной выгрузке бетона;75;76;0;DB;DInt;500;0
    private long timeCloseImpulseShiberMixer;           //59;Время паузы (закрытия) затвора при импульсной выгрузке бетона;75;80;0;DB;DInt;500;0
    private long timeWorkVibroDC;                       //60;Время работы вибратора ДЦ;75;84;0;DB;DInt;2000;0
    private long timwWaitVibroDC;                       //61;Время паузы вибратора ДЦ;75;88;0;DB;DInt;2000;0
    private long timeWorkHorConvQBOnly;                 //62;Время работы ДК - Лента (только для лентончных заводов);75;92;0;DB;DInt;0;0
    private long timeWaitHorConvQBOnly;                 //63;Время паузы ДК - Лента (только для ленточных заводов);75;96;0;DB;DInt;0;0
    private long timeWorkAutoOilMixer;                  //64;Время работы автосмазчика;75;100;0;DB;DInt;0;0
    private long timeWaitAutoOilMixer;                  //65;Время работы паузы автосмазчика;75;104;0;DB;DInt;60000;0
    private int queueDK;                                //66;Очередь ДК;73;78;0;DB;Int;0;0
    private int queueDCh;                               //67;Очередь ДХ;73;80;0;DB;Int;0;0
    private int queueDW;                                //68;Очередь ДВ;73;82;0;DB;Int;1;0
    private int queueDC;                                //69;Очередь ДЦ;73;84;0;DB;Int;1;0
    private long timeoutDropDC;                         //70;Время задержки разгрузки ДЦ;75;108;0;DB;DInt;1000;0
    private long timeoutDropDW;                         //71;Время задержки разгрузки ДВ;75;112;0;DB;DInt;1000;0
    private int percentSwitchToImpulseModeDoser11;      //72;Процент переход в импульсный режим бункер 11;73;86;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser12;      //73;Процент переход в импульсный режим бункер 12;73;88;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser21;      //74;Процент переход в импульсный режим бункер 21;73;90;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser22;      //75;Процент переход в импульсный режим бункер 22;73;92;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser31;      //76;Процент переход в импульсный режим бункер 31;73;94;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser32;      //77;Процент переход в импульсный режим бункер 32;73;96;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser41;      //78;Процент переход в импульсный режим бункер 41;73;98;0;DB;Int;0;0
    private int percentSwitchToImpulseModeDoser42;      //79;Процент переход в импульсный режим бункер 42;73;100;0;DB;Int;0;0
    private int countDashShiberMixer;                   //80;Количество рывков смесителя;73;102;0;DB;Int;0;0
    private int hydroGateOption;                        //81;На смесителе установлен гидрозатвор;73;20;0;DB;Int;0;0
    private int countUnloadingMixer;                    //82;Кол-во открытий смесителя;73;104;0;DB;Int;0;0
    private long timeDropUnloadConveyor;                //83;Вр. выгрузки конвейера;75;116;0;DB;DInt;0;0
    private long timeoutStartPumpDW;                    //84;Задержка вкл. насоса налива ДВ;75;120;0;DB;DInt;0;0
    private long timeoutStopPumpDW;                     //85;Задержка откл. клапана налива ДВ;75;124;0;DB;DInt;0;0
    private int openMiddleSensorShiberMixerOption;      //86;Галочка - открытие шибера смесителя до середины;73;22;0;DB;Int;0;0
    private long timeOpenMiddleSensorShiberMixer;       //87;Время открытия шибера смесителя до середины;75;128;0;DB;DInt;0;0
    private long timeWaitMiddleSensorShiberMixer;       //88;Время паузы шибера смесителя на середине;75;132;0;DB;DInt;0;0
    private long timeoutDropMixer;                      //89;Время ожидания разгрузки смесителя;75;136;0;DB;DInt;0;0
    private long timeoutStartSkipToClimb;               //90;Время задержки на подъем скипа;75;140;0;DB;DInt;0;0
    private int waitForDCLoadClimbSkip;                 //91;Не поднимать скип до набора цемента;73;24;0;DB;Int;0;0
    private int waitForDWLoadClimbSkip;                 //92;Не поднимать скип до набора воды;73;26;0;DB;Int;0;0
    private int waitForDropMixerClimbSkip;              //93;Подъем скипа после завершения разгрузки;73;28;0;DB;Int;0;0
    private int waitForBeginDropMixerClimbSkip;         //94;Подъем скипа после начала разгрузки;73;30;0;DB;Int;0;0
    private long alarmTimeClimbSkip;                    //95;Время до аварийной остановки скипа при открытом шибере смесителя;75;144;0;DB;DInt;0;0
    private int useVibroDCOption;                       //96;Разрешение на запуск вибратора ДЦ;73;32;0;DB;Int;0;0
    private long timeWorkVerticalConveyor;              //97;Время работы наклонного конвейера;75;148;0;DB;DInt;0;0
    private int calibrateDigFilterDK;                   //98;Калибровка ДК - вкл. цифровой фильтр;73;34;0;DB;Int;0;0
    private int calibrateMidFilterDK;                   //99;Калибровка ДК - вкл. усредн. фильтр;73;36;0;DB;Int;0;0
    private int calibrateDigFilterDW;                   //100;Калибровка ДВ - вкл. цифровой фильтр;73;38;0;DB;Int;0;0
    private int calibrateMidFilterDW;                   //101;Калибровка ДВ - вкл. усредн. фильтр;73;40;0;DB;Int;0;0
    private int calibrateDigFilterDC;                   //102;Калибровка ДЦ - вкл. цифровой фильтр;73;42;0;DB;Int;0;0
    private int calibrateMidFilterDC;                   //103;Калибровка ДЦ - вкл. усредн. фильтр;73;44;0;DB;Int;0;0
    private int calibrateDigFilterDCh;                  //104;Калибровка ДХ - вкл. цифровой фильтр;73;46;0;DB;Int;0;0
    private int calibrateMidFilterDCh;                  //105;Калибровка ДХ - вкл. усредн. фильтр;73;48;0;DB;Int;0;0
    private int calibrateDigFilterCoefADK;              //106;Калибровка ДК - цифр фильтр коэф А;73;106;0;DB;Int;0;0
    private int calibrateDigFilterCoefBDK;              //107;Калибровка ДК - цифр фильтр коэф B;73;108;0;DB;Int;0;0
    private int calibrateDigFilterMidCoefDK;            //108;Калибровка ДК - усредн фильтр коэф ;73;110;0;DB;Int;0;0
    private long calibrateDigFilterMidTimeBDK;          //109;Калибровка ДК - усредн фильтр время;75;152;0;DB;DInt;0;0
    private int calibrateDigFilterCoefADW;              //110;Калибровка ДВ - цифр фильтр коэф А;73;112;0;DB;Int;0;0
    private int calibrateDigFilterCoefBDW;              //111;Калибровка ДВ - цифр фильтр коэф B;73;114;0;DB;Int;0;0
    private int calibrateDigFilterMidCoefDW;            //112;Калибровка ДВ - усредн фильтр коэф ;73;116;0;DB;Int;0;0
    private long calibrateDigFilterMidTimeBDW;          //113;Калибровка ДВ - усредн фильтр время;75;156;0;DB;DInt;0;0
    private int calibrateDigFilterCoefADC;              //114;Калибровка ДЦ - цифр фильтр коэф А;73;118;0;DB;Int;0;0
    private int calibrateDigFilterCoefBDC;              //115;Калибровка ДЦ - цифр фильтр коэф B;73;120;0;DB;Int;0;0
    private int calibrateDigFilterMidCoefDC;            //116;Калибровка ДЦ - усредн фильтр коэф ;73;122;0;DB;Int;0;0
    private long calibrateDigFilterMidTimeBDC;          //117;Калибровка ДЦ - усредн фильтр время;75;160;0;DB;DInt;0;0
    private int calibrateDigFilterCoefADCh;             //118;Калибровка ДХ - цифр фильтр коэф А;73;124;0;DB;Int;0;0
    private int calibrateDigFilterCoefBDCh;             //119;Калибровка ДХ - цифр фильтр коэф B;73;126;0;DB;Int;0;0
    private int calibrateDigFilterMidCoefDCh;           //120;Калибровка ДХ - усредн фильтр коэф;73;128;0;DB;Int;0;0
    private long calibrateDigFilterMidTimeBDCh;         //121;Калибровка ДХ - усредн фильтр время;75;164;0;DB;DInt;0;0
    private int useDropConveyorOption;                  //122;Гал.конвейер выгрузки;73;18;0;DB;Int;0;0
    private int useImpulseModeForShiberMixerOption;     //123;Галочка ипульсная разгрузка смесителя;73;130;0;DB;Int;0;0
    private int useAutoQueueUploadSourcesOption;        //124;Галочка авт. очередь загрузки материалов;73;132;0;DB;Int;0;0
    private int autoNullWeightDCOption;                 //125;Галочка авт. обнуление весов - Цемент;73;134;0;DB;Int;0;0
    private int autoNullWeightDChOption;                //126;Галочка авт. обнуление весов - Химия;73;136;0;DB;Int;0;0
    private int autoNullWeightDWOption;                 //127;Галочка авт. обнуление весов - Вода;73;138;0;DB;Int;0;0
    private int autoNullWeightDKOption;                 //128;Галочка авт. обнуление весов — ДК;73;140;0;DB;Int;0;0
    private int optionDispenserFibra;                   //129 Галочка использовать дозатор фибра
    private int optionDispenserDDryCh;                  //130 Галочка использовать дозатор ДСХ
    private int optionVibroFunnel;                      //Галочка вибратор воронки смесителя
    private long timeWorkVibroFunnel;                   //вибратор воронки время работы
    private long timePauseVibroFunnel;                  //вибратор воронки время паузы
    private int optionHumidityInert;                    //галочка - включить корректировку по датчику влажности в бункере инертных
    private int optionHumidityInertBunckerNumber;       //номер бункера в котором стоит датчик влажности
    private int optionHumidityInertAdd;                 //галочка - включить корректировку без датчика влажности в дополнительно бункере инертных
    private int optionHumidityInertBunckerNumberAdd;    //номер бункера в котором производится дополнительная корректировка

    private int noSensorShiberOpen;                     //Использовать датчик открытого положения шибера смесителя
    private int noSensorShiberClose;                    //Использовать датчик закрытого положения шибера смесителя

    private int sensorMiddleMixer;                      //Опция - открытие до середины шибера по времени или по датчику (0 - датчик, 1 - время)
    private int sensorAlarmUpSkip;                      //Опция - верхний датчик скипа с аварийным расцепителем


    private Context context;
    public void initTags(Context context) {
        this.context = context;

        tagList = new DBTags(context).getTags("tags_additional_options");

        DynamicTagBuilder dynamicTagCollector = new DynamicTagBuilder(tagList);
        dynamicTagCollector.buildSortedTags();

        tagRealAnswer = dynamicTagCollector.getTagRealAnswer();
        tagDIntAnswer = dynamicTagCollector.getTagDIntAnswer();
        tagIntAnswer = dynamicTagCollector.getTagIntAnswer();

        tagCounter = tagList.size() + 1;

    }

    public void readValues() {
        lockStateRequests = true;
        List<Tag> request;
        Map<Integer, Tag> answer;
        CommandDispatcher commandDispatcher = new CommandDispatcher();

        request = commandDispatcher.readMultipleRealRegister(tagRealAnswer, tagList);
        request.addAll(commandDispatcher.readMultipleIntRegister(tagIntAnswer, tagList));
        request.addAll(commandDispatcher.readMultipleDIntRegister(tagDIntAnswer, tagList));
        lockStateRequests = false;
        answer = sortTagAnswer(request);

        mixCapacity = answer.get(27).getRealValueIf();
        mixTime = answer.get(2).getRealValueIf();
        splitBuncker1 = answer.get(3).getIntValueIf();
        splitBuncker2 = answer.get(4).getIntValueIf();
        splitBuncker3 = answer.get(5).getIntValueIf();
        splitBuncker4 = answer.get(6).getIntValueIf();
        typeDispenserWater1 = answer.get(7).getIntValueIf();
        typeDispenserWater2 = answer.get(8).getIntValueIf();
        typeDispenserWater3 = answer.get(9).getIntValueIf();
        typeDispenserWater4 = answer.get(10).getIntValueIf();
        typeTransporterDK = answer.get(11).getIntValueIf();
        bunckerCountDK = answer.get(12).getIntValueIf();
        bunckerCountDW = answer.get(13).getIntValueIf();
        bunckerCountDC = answer.get(14).getIntValueIf();
        silosCountDC1 = answer.get(15).getIntValueIf();
        silosCountDC2 = answer.get(16).getIntValueIf();
        silosCountDC3 = answer.get(17).getIntValueIf();
        silosCountDC4 = answer.get(18).getIntValueIf();
        countDispensersCh = answer.get(19).getIntValueIf();
        countBunckerDCh1 = answer.get(20).getIntValueIf();
        countBunckerDCh2 = answer.get(21).getIntValueIf();
        countBunckerDCh3 = answer.get(22).getIntValueIf();
        countBunckerDCh4 = answer.get(23).getIntValueIf();
        timeOpenImpulseDK = answer.get(24).getDIntValueIf();
        timeCloseImpulseDK = answer.get(25).getDIntValueIf();
        timeDosingDK = answer.get(26).getDIntValueIf();
        mixCapacityMax = answer.get(27).getRealValueIf();
        countDownMixing = answer.get(28).getDIntValueIf();
        timeDropMixer = answer.get(29).getDIntValueIf();
        timeWaitingOpenMixer = answer.get(30).getDIntValueIf();
        timeDashShiberMixer = answer.get(31).getDIntValueIf();
        weightCountDownDK = answer.get(32).getRealValueIf();
        weightCountDownDC = answer.get(33).getRealValueIf();
        weightCountDownDCh = answer.get(34).getRealValueIf();
        weightCountDownDW = answer.get(35).getRealValueIf();
        timeRunAfterMixWeightDK = answer.get(36).getDIntValueIf();
        timeRunAfterMixWeightDC = answer.get(37).getDIntValueIf();
        timeRunAfterMixWeightDCh = answer.get(38).getDIntValueIf();
        timeRunAfterMixWeightDW = answer.get(39).getDIntValueIf();
        timeoutPumpCh = answer.get(40).getDIntValueIf();
        timeoutValveCh = answer.get(41).getDIntValueIf();
        timeoutOnPumpDropW = answer.get(42).getDIntValueIf();
        timeoutOffPumpDropW = answer.get(43).getDIntValueIf();
        timeWorkVibroDK = answer.get(44).getDIntValueIf();
        timeWaitVibroDK = answer.get(45).getDIntValueIf();
        timeDropSkip = answer.get(46).getDIntValueIf();
        timeDropVertConveyor = answer.get(47).getDIntValueIf();
        maxWeigthAutoCorrectTareDK = answer.get(48).getRealValueIf();
        maxWeigthAutoCorrectTareDCh = answer.get(49).getRealValueIf();
        maxWeigthAutoCorrectTareDW = answer.get(50).getRealValueIf();
        maxWeigthAutoCorrectTareDC = answer.get(51).getRealValueIf();
        autoNullWeightDK = answer.get(52).getRealValueIf();
        autoNullWeightDCh = answer.get(53).getRealValueIf();
        autoNullWeightDW = answer.get(54).getRealValueIf();
        autoNullWeightDC = answer.get(55).getRealValueIf();
        percentSwitchToImpulseModeDK = answer.get(56).getIntValueIf();
        autoNullOptionDW = answer.get(57).getIntValueIf();
        timeOpenImpulseShiberMixer = answer.get(58).getDIntValueIf();
        timeCloseImpulseShiberMixer = answer.get(59).getDIntValueIf();
        timeWorkVibroDC = answer.get(60).getDIntValueIf();
        timwWaitVibroDC = answer.get(61).getDIntValueIf();
        timeWorkHorConvQBOnly = answer.get(62).getDIntValueIf();
        timeWaitHorConvQBOnly = answer.get(63).getDIntValueIf();
        timeWorkAutoOilMixer = answer.get(64).getDIntValueIf();
        timeWaitAutoOilMixer = answer.get(65).getDIntValueIf();
        queueDK = answer.get(66).getIntValueIf();
        queueDCh = answer.get(67).getIntValueIf();
        queueDW = answer.get(68).getIntValueIf();
        queueDC = answer.get(69).getIntValueIf();
        timeoutDropDC = answer.get(70).getDIntValueIf();
        timeoutDropDW = answer.get(71).getDIntValueIf();
        percentSwitchToImpulseModeDoser11 = answer.get(72).getIntValueIf();
        percentSwitchToImpulseModeDoser12 = answer.get(73).getIntValueIf();
        percentSwitchToImpulseModeDoser21 = answer.get(74).getIntValueIf();
        percentSwitchToImpulseModeDoser22 = answer.get(75).getIntValueIf();
        percentSwitchToImpulseModeDoser31 = answer.get(76).getIntValueIf();
        percentSwitchToImpulseModeDoser32 = answer.get(77).getIntValueIf();
        percentSwitchToImpulseModeDoser41 = answer.get(78).getIntValueIf();
        percentSwitchToImpulseModeDoser42 = answer.get(79).getIntValueIf();
        countDashShiberMixer = answer.get(80).getIntValueIf();
        hydroGateOption = answer.get(81).getIntValueIf();
        countUnloadingMixer = answer.get(82).getIntValueIf();
        timeDropUnloadConveyor = answer.get(83).getDIntValueIf();
        timeoutStartPumpDW = answer.get(84).getDIntValueIf();
        timeoutStopPumpDW = answer.get(85).getDIntValueIf();
        openMiddleSensorShiberMixerOption = answer.get(86).getIntValueIf();
        timeOpenMiddleSensorShiberMixer = answer.get(87).getDIntValueIf();
        timeWaitMiddleSensorShiberMixer = answer.get(88).getDIntValueIf();
        timeoutDropMixer = answer.get(89).getDIntValueIf();
        timeoutStartSkipToClimb = answer.get(90).getDIntValueIf();
        waitForDCLoadClimbSkip = answer.get(91).getIntValueIf();
        waitForDWLoadClimbSkip = answer.get(92).getIntValueIf();
        waitForDropMixerClimbSkip = answer.get(93).getIntValueIf();
        waitForBeginDropMixerClimbSkip = answer.get(94).getIntValueIf();
        alarmTimeClimbSkip = answer.get(95).getDIntValueIf();
        useVibroDCOption = answer.get(96).getIntValueIf();
        timeWorkVerticalConveyor = answer.get(97).getDIntValueIf();
        calibrateDigFilterDK = answer.get(98).getIntValueIf();
        calibrateMidFilterDK = answer.get(99).getIntValueIf();
        calibrateDigFilterDW = answer.get(100).getIntValueIf();
        calibrateMidFilterDW = answer.get(101).getIntValueIf();
        calibrateDigFilterDC = answer.get(102).getIntValueIf();
        calibrateMidFilterDC = answer.get(103).getIntValueIf();
        calibrateDigFilterDCh = answer.get(104).getIntValueIf();
        calibrateMidFilterDCh = answer.get(105).getIntValueIf();
        calibrateDigFilterCoefADK = answer.get(106).getIntValueIf();
        calibrateDigFilterCoefBDK = answer.get(107).getIntValueIf();
        calibrateDigFilterMidCoefDK = answer.get(108).getIntValueIf();
        calibrateDigFilterMidTimeBDK = answer.get(109).getDIntValueIf();
        calibrateDigFilterCoefADW = answer.get(110).getIntValueIf();
        calibrateDigFilterCoefBDW = answer.get(111).getIntValueIf();
        calibrateDigFilterMidCoefDW = answer.get(112).getIntValueIf();
        calibrateDigFilterMidTimeBDW = answer.get(113).getDIntValueIf();
        calibrateDigFilterCoefADC = answer.get(114).getIntValueIf();
        calibrateDigFilterCoefBDC = answer.get(115).getIntValueIf();
        calibrateDigFilterMidCoefDC = answer.get(116).getIntValueIf();
        calibrateDigFilterMidTimeBDC = answer.get(117).getDIntValueIf();
        calibrateDigFilterCoefADCh = answer.get(118).getIntValueIf();
        calibrateDigFilterCoefBDCh = answer.get(119).getIntValueIf();
        calibrateDigFilterMidCoefDCh = answer.get(120).getIntValueIf();
        calibrateDigFilterMidTimeBDCh = answer.get(121).getDIntValueIf();
        useDropConveyorOption = answer.get(122).getIntValueIf();
        useImpulseModeForShiberMixerOption = answer.get(123).getIntValueIf();
        useAutoQueueUploadSourcesOption = answer.get(124).getIntValueIf();
        autoNullWeightDCOption = answer.get(125).getIntValueIf();
        autoNullWeightDChOption = answer.get(126).getIntValueIf();
        autoNullWeightDWOption = answer.get(127).getIntValueIf();
        autoNullWeightDKOption = answer.get(128).getIntValueIf();
        optionDispenserFibra = answer.get(129).getIntValueIf();
        optionDispenserDDryCh = answer.get(130).getIntValueIf();
        optionVibroFunnel = answer.get(131).getIntValueIf();
        timeWorkVibroFunnel = answer.get(132).getDIntValueIf();
        timePauseVibroFunnel = answer.get(133).getDIntValueIf();
        optionHumidityInert = answer.get(134).getIntValueIf();
        optionHumidityInertBunckerNumber = answer.get(135).getIntValueIf();

        noSensorShiberOpen = answer.get(136).getIntValueIf();
        noSensorShiberClose = answer.get(137).getIntValueIf();

        optionHumidityInertAdd = answer.get(138).getIntValueIf();
        optionHumidityInertBunckerNumberAdd = answer.get(139).getIntValueIf();

        sensorMiddleMixer = answer.get(140).getIntValueIf();
        sensorAlarmUpSkip = answer.get(141).getIntValueIf();

    }

    private Map<Integer, Tag> sortTagAnswer(List<Tag> request){
        if (request == null) return null;
        Map<Integer, Tag> result = new HashMap<>();

        int chk = 0;
        for (int i = 0; i < tagCounter; i++) {
            for(Tag tag : request){
                if (i == tag.getId()) {
                    result.put(i, tag);
                    chk = 1;
                    break;
                }
            }

            if (chk == 0) { //чтобы не выполнять проверку на null, если вдруг ответ таки придет в null заполняю пустые тэги
                result.put(i, new Tag(0,0,0,0,"",false,-1,-1,-1,"","",0));
            }
            if (chk == 1) chk = 0;
        }

        return result;
    }

    public int getTagCounter() {
        return tagCounter;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public static List<BlockMultiple> getTagRealAnswer() {
        return tagRealAnswer;
    }

    public static List<BlockMultiple> getTagDIntAnswer() {
        return tagDIntAnswer;
    }

    public static List<BlockMultiple> getTagIntAnswer() {
        return tagIntAnswer;
    }

    public float getMixCapacity() {
        return mixCapacity;
    }

    public float getMixTime() {
        return mixTime;
    }

    public int getSplitBuncker1() {
        return splitBuncker1;
    }

    public int getSplitBuncker2() {
        return splitBuncker2;
    }

    public int getSplitBuncker3() {
        return splitBuncker3;
    }

    public int getSplitBuncker4() {
        return splitBuncker4;
    }

    public int getTypeDispenserWater1() {
        return typeDispenserWater1;
    }

    public int getTypeDispenserWater2() {
        return typeDispenserWater2;
    }

    public int getTypeDispenserWater3() {
        return typeDispenserWater3;
    }

    public int getTypeDispenserWater4() {
        return typeDispenserWater4;
    }

    public int getTypeTransporterDK() {
        return typeTransporterDK;
    }

    public int getBunckerCountDK() {
        return bunckerCountDK;
    }

    public int getBunckerCountDW() {
        return bunckerCountDW;
    }

    public int getBunckerCountDC() {
        return bunckerCountDC;
    }

    public int getSilosCountDC1() {
        return silosCountDC1;
    }

    public int getSilosCountDC2() {
        return silosCountDC2;
    }

    public int getSilosCountDC3() {
        return silosCountDC3;
    }

    public int getSilosCountDC4() {
        return silosCountDC4;
    }

    public int getCountDispensersCh() {
        return countDispensersCh;
    }

    public int getCountBunckerDCh1() {
        return countBunckerDCh1;
    }

    public int getCountBunckerDCh2() {
        return countBunckerDCh2;
    }

    public int getCountBunckerDCh3() {
        return countBunckerDCh3;
    }

    public int getCountBunckerDCh4() {
        return countBunckerDCh4;
    }

    public long getTimeOpenImpulseDK() {
        return timeOpenImpulseDK;
    }

    public long getTimeCloseImpulseDK() {
        return timeCloseImpulseDK;
    }

    public long getTimeDosingDK() {
        return timeDosingDK;
    }

    public float getMixCapacityMax() {
        return mixCapacityMax;
    }

    public long getCountDownMixing() {
        return countDownMixing;
    }

    public long getTimeDropMixer() {
        return timeDropMixer;
    }

    public long getTimeWaitingOpenMixer() {
        return timeWaitingOpenMixer;
    }

    public long getTimeDashShiberMixer() {
        return timeDashShiberMixer;
    }

    public float getWeightCountDownDK() {
        return weightCountDownDK;
    }

    public float getWeightCountDownDC() {
        return weightCountDownDC;
    }

    public float getWeightCountDownDCh() {
        return weightCountDownDCh;
    }

    public float getWeightCountDownDW() {
        return weightCountDownDW;
    }

    public long getTimeRunAfterMixWeightDK() {
        return timeRunAfterMixWeightDK;
    }

    public long getTimeRunAfterMixWeightDC() {
        return timeRunAfterMixWeightDC;
    }

    public long getTimeRunAfterMixWeightDCh() {
        return timeRunAfterMixWeightDCh;
    }

    public long getTimeRunAfterMixWeightDW() {
        return timeRunAfterMixWeightDW;
    }

    public long getTimeoutPumpCh() {
        return timeoutPumpCh;
    }

    public long getTimeoutValveCh() {
        return timeoutValveCh;
    }

    public long getTimeoutOnPumpDropW() {
        return timeoutOnPumpDropW;
    }

    public long getTimeoutOffPumpDropW() {
        return timeoutOffPumpDropW;
    }

    public long getTimeWorkVibroDK() {
        return timeWorkVibroDK;
    }

    public long getTimeWaitVibroDK() {
        return timeWaitVibroDK;
    }

    public long getTimeDropSkip() {
        return timeDropSkip;
    }

    public long getTimeDropVertConveyor() {
        return timeDropVertConveyor;
    }

    public float getMaxWeigthAutoCorrectTareDK() {
        return maxWeigthAutoCorrectTareDK;
    }

    public float getMaxWeigthAutoCorrectTareDCh() {
        return maxWeigthAutoCorrectTareDCh;
    }

    public float getMaxWeigthAutoCorrectTareDW() {
        return maxWeigthAutoCorrectTareDW;
    }

    public float getMaxWeigthAutoCorrectTareDC() {
        return maxWeigthAutoCorrectTareDC;
    }

    public float getAutoNullWeightDK() {
        return autoNullWeightDK;
    }

    public float getAutoNullWeightDCh() {
        return autoNullWeightDCh;
    }

    public float getAutoNullWeightDW() {
        return autoNullWeightDW;
    }

    public float getAutoNullWeightDC() {
        return autoNullWeightDC;
    }

    public int getPercentSwitchToImpulseModeDK() {
        return percentSwitchToImpulseModeDK;
    }

    public int getAutoNullOptionDW() {
        return autoNullOptionDW;
    }

    public long getTimeOpenImpulseShiberMixer() {
        return timeOpenImpulseShiberMixer;
    }

    public long getTimeCloseImpulseShiberMixer() {
        return timeCloseImpulseShiberMixer;
    }

    public long getTimeWorkVibroDC() {
        return timeWorkVibroDC;
    }

    public long getTimwWaitVibroDC() {
        return timwWaitVibroDC;
    }

    public long getTimeWorkHorConvQBOnly() {
        return timeWorkHorConvQBOnly;
    }

    public long getTimeWaitHorConvQBOnly() {
        return timeWaitHorConvQBOnly;
    }

    public long getTimeWorkAutoOilMixer() {
        return timeWorkAutoOilMixer;
    }

    public long getTimeWaitAutoOilMixer() {
        return timeWaitAutoOilMixer;
    }

    public int getQueueDK() {
        return queueDK;
    }

    public int getQueueDCh() {
        return queueDCh;
    }

    public int getQueueDW() {
        return queueDW;
    }

    public int getQueueDC() {
        return queueDC;
    }

    public long getTimeoutDropDC() {
        return timeoutDropDC;
    }

    public long getTimeoutDropDW() {
        return timeoutDropDW;
    }

    public int getPercentSwitchToImpulseModeDoser11() {
        return percentSwitchToImpulseModeDoser11;
    }

    public int getPercentSwitchToImpulseModeDoser12() {
        return percentSwitchToImpulseModeDoser12;
    }

    public int getPercentSwitchToImpulseModeDoser21() {
        return percentSwitchToImpulseModeDoser21;
    }

    public int getPercentSwitchToImpulseModeDoser22() {
        return percentSwitchToImpulseModeDoser22;
    }

    public int getPercentSwitchToImpulseModeDoser31() {
        return percentSwitchToImpulseModeDoser31;
    }

    public int getPercentSwitchToImpulseModeDoser32() {
        return percentSwitchToImpulseModeDoser32;
    }

    public int getPercentSwitchToImpulseModeDoser41() {
        return percentSwitchToImpulseModeDoser41;
    }

    public int getPercentSwitchToImpulseModeDoser42() {
        return percentSwitchToImpulseModeDoser42;
    }

    public int getCountDashShiberMixer() {
        return countDashShiberMixer;
    }

    public int getHydroGateOption() {
        return hydroGateOption;
    }

    public int getCountUnloadingMixer() {
        return countUnloadingMixer;
    }

    public long getTimeDropUnloadConveyor() {
        return timeDropUnloadConveyor;
    }

    public long getTimeoutStartPumpDW() {
        return timeoutStartPumpDW;
    }

    public long getTimeoutStopPumpDW() {
        return timeoutStopPumpDW;
    }

    public int getOpenMiddleSensorShiberMixerOption() {
        return openMiddleSensorShiberMixerOption;
    }

    public long getTimeOpenMiddleSensorShiberMixer() {
        return timeOpenMiddleSensorShiberMixer;
    }

    public long getTimeWaitMiddleSensorShiberMixer() {
        return timeWaitMiddleSensorShiberMixer;
    }

    public long getTimeoutDropMixer() {
        return timeoutDropMixer;
    }

    public long getTimeoutStartSkipToClimb() {
        return timeoutStartSkipToClimb;
    }

    public int getWaitForDCLoadClimbSkip() {
        return waitForDCLoadClimbSkip;
    }

    public int getWaitForDWLoadClimbSkip() {
        return waitForDWLoadClimbSkip;
    }

    public int getWaitForDropMixerClimbSkip() {
        return waitForDropMixerClimbSkip;
    }

    public int getWaitForBeginDropMixerClimbSkip() {
        return waitForBeginDropMixerClimbSkip;
    }

    public long getAlarmTimeClimbSkip() {
        return alarmTimeClimbSkip;
    }

    public int getUseVibroDCOption() {
        return useVibroDCOption;
    }

    public long getTimeWorkVerticalConveyor() {
        return timeWorkVerticalConveyor;
    }

    public int getCalibrateDigFilterDK() {
        return calibrateDigFilterDK;
    }

    public int getCalibrateMidFilterDK() {
        return calibrateMidFilterDK;
    }

    public int getCalibrateDigFilterDW() {
        return calibrateDigFilterDW;
    }

    public int getCalibrateMidFilterDW() {
        return calibrateMidFilterDW;
    }

    public int getCalibrateDigFilterDC() {
        return calibrateDigFilterDC;
    }

    public int getCalibrateMidFilterDC() {
        return calibrateMidFilterDC;
    }

    public int getCalibrateDigFilterDCh() {
        return calibrateDigFilterDCh;
    }

    public int getCalibrateMidFilterDCh() {
        return calibrateMidFilterDCh;
    }

    public int getCalibrateDigFilterCoefADK() {
        return calibrateDigFilterCoefADK;
    }

    public int getCalibrateDigFilterCoefBDK() {
        return calibrateDigFilterCoefBDK;
    }

    public int getCalibrateDigFilterMidCoefDK() {
        return calibrateDigFilterMidCoefDK;
    }

    public long getCalibrateDigFilterMidTimeBDK() {
        return calibrateDigFilterMidTimeBDK;
    }

    public int getCalibrateDigFilterCoefADW() {
        return calibrateDigFilterCoefADW;
    }

    public int getCalibrateDigFilterCoefBDW() {
        return calibrateDigFilterCoefBDW;
    }

    public int getCalibrateDigFilterMidCoefDW() {
        return calibrateDigFilterMidCoefDW;
    }

    public long getCalibrateDigFilterMidTimeBDW() {
        return calibrateDigFilterMidTimeBDW;
    }

    public int getCalibrateDigFilterCoefADC() {
        return calibrateDigFilterCoefADC;
    }

    public int getCalibrateDigFilterCoefBDC() {
        return calibrateDigFilterCoefBDC;
    }

    public int getCalibrateDigFilterMidCoefDC() {
        return calibrateDigFilterMidCoefDC;
    }

    public long getCalibrateDigFilterMidTimeBDC() {
        return calibrateDigFilterMidTimeBDC;
    }

    public int getCalibrateDigFilterCoefADCh() {
        return calibrateDigFilterCoefADCh;
    }

    public int getCalibrateDigFilterCoefBDCh() {
        return calibrateDigFilterCoefBDCh;
    }

    public int getCalibrateDigFilterMidCoefDCh() {
        return calibrateDigFilterMidCoefDCh;
    }

    public long getCalibrateDigFilterMidTimeBDCh() {
        return calibrateDigFilterMidTimeBDCh;
    }

    public int getUseDropConveyorOption() {
        return useDropConveyorOption;
    }

    public int getUseImpulseModeForShiberMixerOption() {
        return useImpulseModeForShiberMixerOption;
    }

    public int getUseAutoQueueUploadSourcesOption() {
        return useAutoQueueUploadSourcesOption;
    }

    public int getAutoNullWeightDCOption() {
        return autoNullWeightDCOption;
    }

    public int getAutoNullWeightDChOption() {
        return autoNullWeightDChOption;
    }

    public int getAutoNullWeightDWOption() {
        return autoNullWeightDWOption;
    }

    public int getAutoNullWeightDKOption() {
        return autoNullWeightDKOption;
    }

    public int getOptionDispenserFibra() {
        return optionDispenserFibra;
    }

    public int getOptionDispenserDDryCh() {
        return optionDispenserDDryCh;
    }

    public int getOptionVibroFunnel() {
        return optionVibroFunnel;
    }

    public long getTimeWorkVibroFunnel() {
        return timeWorkVibroFunnel;
    }

    public long getTimePauseVibroFunnel() {
        return timePauseVibroFunnel;
    }

    public int getOptionHumidityInert() {
        return optionHumidityInert;
    }

    public int getOptionHumidityInertBunckerNumber() {
        return optionHumidityInertBunckerNumber;
    }

    public int getNoSensorShiberOpen() {
        return noSensorShiberOpen;
    }

    public int getNoSensorShiberClose() {
        return noSensorShiberClose;
    }

    public int getOptionHumidityInertAdd() {
        return optionHumidityInertAdd;
    }

    public int getOptionHumidityInertBunckerNumberAdd() {
        return optionHumidityInertBunckerNumberAdd;
    }
    public int getSensorMiddleMixer() {
        return sensorMiddleMixer;
    }

    public int getSensorAlarmUpSkip() {
        return sensorAlarmUpSkip;
    }

}