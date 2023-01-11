package ru.zzbo.concretemobile.protocol.profinet.reflections;

import static ru.zzbo.concretemobile.protocol.profinet.collectors.DynamicTagCollector.tagCollector;

import java.lang.reflect.Field;

public class ReflectionRetrieval {

    private int manualAutoModeValue;
    private int pendingProductionStateValue;
    private int globalCrashFlagValue;

    private int hopper11FlapOpenIndValue;
    private int hopper12FlapOpenIndValue;
    private int hopper21FlapOpenIndValue;
    private int hopper22FlapOpenIndValue;
    private int hopper31FlapOpenIndValue;
    private int hopper32FlapOpenIndValue;
    private int hopper41FlapOpenIndValue;
    private int hopper42FlapOpenIndValue;

    private int waterDisFlapOpenIndValue;
    private int cementDisFlapOpenIndValue;
    private int cementDisFlapOpenPosIndValue;
    private int cementDisFlapClosePosIndValue;
    private int chemyDisFlapOpenIndValue;
    private int horConveyorOnIndValue;
    private int waterPumpOnIndValue;
    private int valveWaterBunckerValue;
    private int chemy1PumpOnIndValue;
    private int chemy2PumpOnIndValue;
    private int cement1AugerOnIndValue;
    private int cement2AugerOnIndValue;
    private int silosCementFilterValue;
    private int cement1SiloLevelSensorUpValue;
    private int cement1SiloLevelSensorDownValue;
    private int cement2SiloLevelSensorUpValue;
    private int cement2SiloLevelSensorDownValue;
    private int waterOverflowSensorValue;
    private int chemyOverflowSensorValue;
    private int skipPosEndSensorUpValue;
    private int skipPosEndSensorDownValue;
    private int skipPosEndSensorCrashUpValue;
    private int skipPosEndSensorCrashDownValue;
    private int skipMoveUpValue;
    private int skipMoveDownValue;
    private int aerationOnIndicationValue;
    private int mixerRollersWorkIndicationValue;
    private int mixerWindowViewOpenSensorValue;
    private int mixerNotEmptyValue;
    private float hopper11RecipeValue;
    private float hopper12RecipeValue;
    private float hopper21RecipeValue;
    private float hopper22RecipeValue;
    private float hopper31RecipeValue;
    private float hopper32RecipeValue;
    private float hopper41RecipeValue;
    private float hopper42RecipeValue;

    private float waterRecipeValue;
    private float chemy1RecipeValue;
    private float chemy2RecipeValue;
    private float cement1RecipeValue;
    private float cement2RecipeValue;

    private float shortageHopper11Value;
    private float shortageHopper12Value;
    private float shortageHopper21Value;
    private float shortageHopper22Value;
    private float shortageHopper31Value;
    private float shortageHopper32Value;
    private float shortageHopper41Value;
    private float shortageHopper42Value;

    private float shortageWaterValue;
    private float shortageChemy1Value;

    private float shortageSilos1Value;
    private float shortageSilos2Value;

    private float volumeCurrentMixerLoadValue;
    private float weightCubeConcreteValue;
    private float batchVolumeValue;
    private float mixingTimeValue;
    private float horConveyorStartTimerValue;
    private float currentWeightDKValue;
    private float currentWeightWaterValue;
    private float currentWeightChemyValue;
    private float currentWeightCementValue;
    private int countdownTimeMixValue;
    private int mixerCloseValue;
    private int mixerHalfOpenValue;
    private int mixerOpenValue;
    private int autoDropChckerValue;
    private int dropImpulseChckerValue;

    private float doseHopper11Value;
    private float doseHopper12Value;
    private float doseHopper21Value;
    private float doseHopper22Value;
    private float doseHopper31Value;
    private float doseHopper32Value;
    private float doseHopper41Value;
    private float doseHopper42Value;

    private float doseWaterValue;
    private float doseWater2Value;
    private float doseSilos1Value;
    private float doseSilos2Value;
    private float doseChemy1Value;
    private float doseChemy2Value;
    private int vibroSilos1Value;
    private int vibroDispenserDCValue;
    private float mixingCapacity;
    private int mixCounterValue = -1;
    private float amperageMixerValue;
    private int totalMixCounterValue = -2;

    private int vibroBuncker11OptionValue;
    private int vibroBuncker12OptionValue;
    private int vibroBuncker21OptionValue;
    private int vibroBuncker22OptionValue;
    private int vibroBuncker31OptionValue;
    private int vibroBuncker32OptionValue;
    private int vibroBuncker41OptionValue;
    private int vibroBuncker42OptionValue;

    private int weightsReadyReadValue;
    private int vibroColdBunckersIndicationValue;
    private float firmwareVersionValue;
    private float fibraWeightSensorValue;

    private int selfDKStateValue;
    private int selfDChStateValue;
    private int selfDCStateValue;
    private int selfDWStateValue;
    private int silosSelectorValue;
    private int skipAlarmIndicatorValue;
    private int verticalConveyorIndValue;
    private int fibraVibratorIndicatorValue;
    private int conveyorDropValue;

    private int skipSensorUp1Value;
    private int skipSensorUp2Value;
    private int skipSensorDown1Value;
    private int skipSensorDown2Value;

    private int reverseDKValue;
    private int uploadConveyorDKValue;

    private int motoClockHorConveyorValue;
    private int motoClockVertConvValue;
    private int motoClockSkipValue;
    private int motoClockMixerValue;
    private int motoClockShnek1Value;
    private int motoClockShnek2Value;
    private int motoClockShnek3Value;
    private int motoClockValveWaterValue;
    private int motoClockPumpWaterValue;
    private int motoClockPumpChemy1Value;
    private int motoClockPumpChemy2Value;
    private int motoClockOilStationValue;

    private float sensorHumidityValue;
    private int waterPumpCounterValue;
    private int waterPumpHumCorrectionControlValue;

    private int alarmMixerShiberOpennedValue;
    private int alarmMixerThermalProtectionValue;
    private int alarmSkipThermalProtectionValue;
    private int alarmDKThermalProtectionValue;
    private int alarmMixerWindowOpennedValue;
    private int alarmTimeDosingDoser11FaultValue;
    private int alarmTimeDosingDoser12FaultValue;
    private int alarmTimeDosingDoser21FaultValue;
    private int alarmTimeDosingDoser22FaultValue;
    private int alarmTimeDosingDoser31FaultValue;
    private int alarmTimeDosingDoser32FaultValue;
    private int alarmTimeDosingDoser41FaultValue;
    private int alarmTimeDosingDoser42FaultValue;
    private int warningAutoModeNotActivatedValue;
    private int warningMixerNotEmptyValue;
    private int warningAutoDropDisableValue;
    private int alarmWeightDKNotEmptyValue;
    private int alarmDCWeightNotEmptyValue;
    private int alarmDWWeightNotEmptyValue;
    private int alarmDChWeightNotEmptyValue;
    private int alarmDKCalibrateErrorValue;
    private int alarmDCCalibrateErrorValue;
    private int alarmDWCalibrateErrorValue;
    private int alarmDChCalibrateErrorValue;
    private int alarmShnekThermalDefenceValue;
    private int alarmMixerDropErrorValue;
    private int warningAutoPowerOffMixerDisabledValue;
    private int alarmMixerEngineNotStartedValue;
    private int warningConveyorUploadNotStartedValue;
    private int alarmOverflowChemyValue;
    private int alarmOverflowWaterValue;
    private int alarmOverFlowDKValue;
    private int alarmMixerCloseErrorStopSkipValue;
    private int alarmDCShiberErrorValue;
    private int alarmSkipSensorValue;
    private double scadaPerformanceValue;

    //инициализация переменных
    public void getValues() {
        try {
            Field manualAutoModeField = tagCollector.getClass().getDeclaredField("manualAutoMode");
            manualAutoModeField.setAccessible(true);
            manualAutoModeValue = (int) manualAutoModeField.get(tagCollector);

            Field pendingProductionStateField = tagCollector.getClass().getDeclaredField("pendingProductionState");
            pendingProductionStateField.setAccessible(true);
            pendingProductionStateValue = (int) pendingProductionStateField.get(tagCollector);

            Field globalCrashFlagField = tagCollector.getClass().getDeclaredField("globalCrashFlag");
            globalCrashFlagField.setAccessible(true);
            globalCrashFlagValue = (int) globalCrashFlagField.get(tagCollector);

            Field hopper11FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper11FlapOpenInd");
            hopper11FlapOpenIndField.setAccessible(true);
            hopper11FlapOpenIndValue = (int) hopper11FlapOpenIndField.get(tagCollector);

            Field hopper12FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper12FlapOpenInd");
            hopper12FlapOpenIndField.setAccessible(true);
            hopper12FlapOpenIndValue = (int) hopper12FlapOpenIndField.get(tagCollector);

            Field hopper21FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper21FlapOpenInd");
            hopper21FlapOpenIndField.setAccessible(true);
            hopper21FlapOpenIndValue = (int) hopper21FlapOpenIndField.get(tagCollector);

            Field hopper22FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper22FlapOpenInd");
            hopper22FlapOpenIndField.setAccessible(true);
            hopper22FlapOpenIndValue = (int) hopper22FlapOpenIndField.get(tagCollector);

            Field hopper31FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper31FlapOpenInd");
            hopper31FlapOpenIndField.setAccessible(true);
            hopper31FlapOpenIndValue = (int) hopper31FlapOpenIndField.get(tagCollector);

            Field hopper32FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper32FlapOpenInd");
            hopper32FlapOpenIndField.setAccessible(true);
            hopper32FlapOpenIndValue = (int) hopper32FlapOpenIndField.get(tagCollector);

            Field hopper41FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper41FlapOpenInd");
            hopper41FlapOpenIndField.setAccessible(true);
            hopper41FlapOpenIndValue = (int) hopper41FlapOpenIndField.get(tagCollector);

            Field hopper42FlapOpenIndField = tagCollector.getClass().getDeclaredField("hopper42FlapOpenInd");
            hopper42FlapOpenIndField.setAccessible(true);
            hopper42FlapOpenIndValue = (int) hopper42FlapOpenIndField.get(tagCollector);

            Field waterDisFlapOpenIndField = tagCollector.getClass().getDeclaredField("waterDisFlapOpenInd");
            waterDisFlapOpenIndField.setAccessible(true);
            waterDisFlapOpenIndValue = (int) waterDisFlapOpenIndField.get(tagCollector);

            Field cementDisFlapOpenIndField = tagCollector.getClass().getDeclaredField("cementDisFlapOpenInd");
            cementDisFlapOpenIndField.setAccessible(true);
            cementDisFlapOpenIndValue = (int) cementDisFlapOpenIndField.get(tagCollector);

            Field cementDisFlapOpenPosIndField = tagCollector.getClass().getDeclaredField("cementDisFlapOpenPosInd");
            cementDisFlapOpenPosIndField.setAccessible(true);
            cementDisFlapOpenPosIndValue = (int) cementDisFlapOpenPosIndField.get(tagCollector);

            Field cementDisFlapClosePosIndField = tagCollector.getClass().getDeclaredField("cementDisFlapClosePosInd");
            cementDisFlapClosePosIndField.setAccessible(true);
            cementDisFlapClosePosIndValue = (int) cementDisFlapClosePosIndField.get(tagCollector);

            Field chemyDisFlapOpenIndField = tagCollector.getClass().getDeclaredField("chemyDisFlapOpenInd");
            chemyDisFlapOpenIndField.setAccessible(true);
            chemyDisFlapOpenIndValue = (int) chemyDisFlapOpenIndField.get(tagCollector);

            Field horConveyorOnIndField = tagCollector.getClass().getDeclaredField("horConveyorOnInd");
            horConveyorOnIndField.setAccessible(true);
            horConveyorOnIndValue = (int) horConveyorOnIndField.get(tagCollector);

            Field waterPumpOnIndField = tagCollector.getClass().getDeclaredField("waterPumpOnInd");
            waterPumpOnIndField.setAccessible(true);
            waterPumpOnIndValue = (int) waterPumpOnIndField.get(tagCollector);

            Field valveWaterBunckerField = tagCollector.getClass().getDeclaredField("valveWaterBuncker");
            valveWaterBunckerField.setAccessible(true);
            valveWaterBunckerValue = (int) valveWaterBunckerField.get(tagCollector);

            Field chemy1PumpOnIndField = tagCollector.getClass().getDeclaredField("chemy1PumpOnInd");
            chemy1PumpOnIndField.setAccessible(true);
            chemy1PumpOnIndValue = (int) chemy1PumpOnIndField.get(tagCollector);

            Field chemy2PumpOnIndField = tagCollector.getClass().getDeclaredField("chemy2PumpOnInd");
            chemy2PumpOnIndField.setAccessible(true);
            chemy2PumpOnIndValue = (int) chemy2PumpOnIndField.get(tagCollector);

            Field cement1AugerOnIndField = tagCollector.getClass().getDeclaredField("cement1AugerOnInd");
            cement1AugerOnIndField.setAccessible(true);
            cement1AugerOnIndValue = (int) cement1AugerOnIndField.get(tagCollector);

            Field cement2AugerOnIndField = tagCollector.getClass().getDeclaredField("cement2AugerOnInd");
            cement2AugerOnIndField.setAccessible(true);
            cement2AugerOnIndValue = (int) cement2AugerOnIndField.get(tagCollector);

            Field silosCementFilterField = tagCollector.getClass().getDeclaredField("silosCementFilter");
            silosCementFilterField.setAccessible(true);
            silosCementFilterValue = (int) silosCementFilterField.get(tagCollector);

            Field cement1SiloLevelSensorUpField = tagCollector.getClass().getDeclaredField("cement1SiloLevelSensorUp");
            cement1SiloLevelSensorUpField.setAccessible(true);
            cement1SiloLevelSensorUpValue = (int) cement1SiloLevelSensorUpField.get(tagCollector);

            Field cement1SiloLevelSensorDownField = tagCollector.getClass().getDeclaredField("cement1SiloLevelSensorDown");
            cement1SiloLevelSensorDownField.setAccessible(true);
            cement1SiloLevelSensorDownValue = (int) cement1SiloLevelSensorDownField.get(tagCollector);

            Field cement2SiloLevelSensorUpField = tagCollector.getClass().getDeclaredField("cement2SiloLevelSensorUp");
            cement2SiloLevelSensorUpField.setAccessible(true);
            cement2SiloLevelSensorUpValue = (int) cement2SiloLevelSensorUpField.get(tagCollector);

            Field cement2SiloLevelSensorDownField = tagCollector.getClass().getDeclaredField("cement2SiloLevelSensorDown");
            cement2SiloLevelSensorDownField.setAccessible(true);
            cement2SiloLevelSensorDownValue = (int) cement2SiloLevelSensorDownField.get(tagCollector);

            Field waterOverflowSensorField = tagCollector.getClass().getDeclaredField("waterOverflowSensor");
            waterOverflowSensorField.setAccessible(true);
            waterOverflowSensorValue = (int) waterOverflowSensorField.get(tagCollector);

            Field chemyOverflowSensorField = tagCollector.getClass().getDeclaredField("chemyOverflowSensor");
            chemyOverflowSensorField.setAccessible(true);
            chemyOverflowSensorValue = (int) chemyOverflowSensorField.get(tagCollector);

            Field skipPosEndSensorUpField = tagCollector.getClass().getDeclaredField("skipPosEndSensorUp");
            skipPosEndSensorUpField.setAccessible(true);
            skipPosEndSensorUpValue = (int) skipPosEndSensorUpField.get(tagCollector);

            Field skipPosEndSensorDownField = tagCollector.getClass().getDeclaredField("skipPosEndSensorDown");
            skipPosEndSensorDownField.setAccessible(true);
            skipPosEndSensorDownValue = (int) skipPosEndSensorDownField.get(tagCollector);

            Field skipPosEndSensorCrashUpField = tagCollector.getClass().getDeclaredField("skipPosEndSensorCrashUp");
            skipPosEndSensorCrashUpField.setAccessible(true);
            skipPosEndSensorCrashUpValue = (int) skipPosEndSensorCrashUpField.get(tagCollector);

            Field skipPosEndSensorCrashDownField = tagCollector.getClass().getDeclaredField("skipPosEndSensorCrashDown");
            skipPosEndSensorCrashDownField.setAccessible(true);
            skipPosEndSensorCrashDownValue = (int) skipPosEndSensorCrashDownField.get(tagCollector);

            Field skipMoveUpField = tagCollector.getClass().getDeclaredField("skipMoveUp");
            skipMoveUpField.setAccessible(true);
            skipMoveUpValue = (int) skipMoveUpField.get(tagCollector);

            Field skipMoveDownField = tagCollector.getClass().getDeclaredField("skipMoveDown");
            skipMoveDownField.setAccessible(true);
            skipMoveDownValue = (int) skipMoveDownField.get(tagCollector);

            Field aerationOnIndicationField = tagCollector.getClass().getDeclaredField("aerationOnIndication");
            aerationOnIndicationField.setAccessible(true);
            aerationOnIndicationValue = (int) aerationOnIndicationField.get(tagCollector);

            Field mixerRollersWorkIndicationField = tagCollector.getClass().getDeclaredField("mixerRollersWorkIndication");
            mixerRollersWorkIndicationField.setAccessible(true);
            mixerRollersWorkIndicationValue = (int) mixerRollersWorkIndicationField.get(tagCollector);

            Field mixerNotEmptyField = tagCollector.getClass().getDeclaredField("mixerNotEmpty");
            mixerNotEmptyField.setAccessible(true);
            mixerNotEmptyValue = (int) mixerNotEmptyField.get(tagCollector);

            Field mixerWindowViewOpenSensorField = tagCollector.getClass().getDeclaredField("mixerWindowViewOpenSensor");
            mixerWindowViewOpenSensorField.setAccessible(true);
            mixerWindowViewOpenSensorValue = (int) mixerWindowViewOpenSensorField.get(tagCollector);

            //DB14 (REAL)
            Field hopper11RecipeField = tagCollector.getClass().getDeclaredField("hopper11Recipe");
            hopper11RecipeField.setAccessible(true);
            hopper11RecipeValue = (float) hopper11RecipeField.get(tagCollector);

            Field hopper12RecipeField = tagCollector.getClass().getDeclaredField("hopper12Recipe");
            hopper12RecipeField.setAccessible(true);
            hopper12RecipeValue = (float) hopper12RecipeField.get(tagCollector);

            Field hopper21RecipeField = tagCollector.getClass().getDeclaredField("hopper21Recipe");
            hopper21RecipeField.setAccessible(true);
            hopper21RecipeValue = (float) hopper21RecipeField.get(tagCollector);

            Field hopper22RecipeField = tagCollector.getClass().getDeclaredField("hopper22Recipe");
            hopper22RecipeField.setAccessible(true);
            hopper22RecipeValue = (float) hopper22RecipeField.get(tagCollector);

            Field hopper31RecipeField = tagCollector.getClass().getDeclaredField("hopper31Recipe");
            hopper31RecipeField.setAccessible(true);
            hopper31RecipeValue = (float) hopper31RecipeField.get(tagCollector);

            Field hopper32RecipeField = tagCollector.getClass().getDeclaredField("hopper32Recipe");
            hopper32RecipeField.setAccessible(true);
            hopper32RecipeValue = (float) hopper32RecipeField.get(tagCollector);

            Field hopper41RecipeField = tagCollector.getClass().getDeclaredField("hopper41Recipe");
            hopper41RecipeField.setAccessible(true);
            hopper41RecipeValue = (float) hopper41RecipeField.get(tagCollector);

            Field hopper42RecipeField = tagCollector.getClass().getDeclaredField("hopper42Recipe");
            hopper42RecipeField.setAccessible(true);
            hopper42RecipeValue = (float) hopper42RecipeField.get(tagCollector);

            Field waterRecipeField = tagCollector.getClass().getDeclaredField("waterRecipe");
            waterRecipeField.setAccessible(true);
            waterRecipeValue = (float) waterRecipeField.get(tagCollector);

            Field chemy1RecipeField = tagCollector.getClass().getDeclaredField("chemy1Recipe");
            chemy1RecipeField.setAccessible(true);
            chemy1RecipeValue = (float) chemy1RecipeField.get(tagCollector);

            Field chemy2RecipeField = tagCollector.getClass().getDeclaredField("chemy2Recipe");
            chemy2RecipeField.setAccessible(true);
            chemy2RecipeValue = (float) chemy2RecipeField.get(tagCollector);

            Field cement1RecipeField = tagCollector.getClass().getDeclaredField("cement1Recipe");
            cement1RecipeField.setAccessible(true);
            cement1RecipeValue = (float) cement1RecipeField.get(tagCollector);

            Field cement2RecipeField = tagCollector.getClass().getDeclaredField("cement2Recipe");
            cement2RecipeField.setAccessible(true);
            cement2RecipeValue = (float) cement2RecipeField.get(tagCollector);

            Field shortageHopper11Field = tagCollector.getClass().getDeclaredField("shortageHopper11");
            shortageHopper11Field.setAccessible(true);
            shortageHopper11Value = (float) shortageHopper11Field.get(tagCollector);

            Field shortageHopper12Field = tagCollector.getClass().getDeclaredField("shortageHopper12");
            shortageHopper12Field.setAccessible(true);
            shortageHopper12Value = (float) shortageHopper12Field.get(tagCollector);

            Field shortageHopper21Field = tagCollector.getClass().getDeclaredField("shortageHopper21");
            shortageHopper21Field.setAccessible(true);
            shortageHopper21Value = (float) shortageHopper21Field.get(tagCollector);

            Field shortageHopper22Field = tagCollector.getClass().getDeclaredField("shortageHopper22");
            shortageHopper22Field.setAccessible(true);
            shortageHopper22Value = (float) shortageHopper22Field.get(tagCollector);

            Field shortageHopper31Field = tagCollector.getClass().getDeclaredField("shortageHopper31");
            shortageHopper31Field.setAccessible(true);
            shortageHopper31Value = (float) shortageHopper31Field.get(tagCollector);

            Field shortageHopper32Field = tagCollector.getClass().getDeclaredField("shortageHopper32");
            shortageHopper32Field.setAccessible(true);
            shortageHopper32Value = (float) shortageHopper32Field.get(tagCollector);

            Field shortageHopper41Field = tagCollector.getClass().getDeclaredField("shortageHopper41");
            shortageHopper41Field.setAccessible(true);
            shortageHopper41Value = (float) shortageHopper41Field.get(tagCollector);

            Field shortageHopper42Field = tagCollector.getClass().getDeclaredField("shortageHopper42");
            shortageHopper42Field.setAccessible(true);
            shortageHopper42Value = (float) shortageHopper42Field.get(tagCollector);

            Field shortageWaterField = tagCollector.getClass().getDeclaredField("shortageWater");
            shortageWaterField.setAccessible(true);
            shortageWaterValue = (float) shortageWaterField.get(tagCollector);

            Field shortageChemy1Field = tagCollector.getClass().getDeclaredField("shortageChemy1");
            shortageChemy1Field.setAccessible(true);
            shortageChemy1Value = (float) shortageChemy1Field.get(tagCollector);

            Field shortageSilos1Field = tagCollector.getClass().getDeclaredField("shortageSilos1");
            shortageSilos1Field.setAccessible(true);
            shortageSilos1Value = (float) shortageSilos1Field.get(tagCollector);

            Field shortageSilos2Field = tagCollector.getClass().getDeclaredField("shortageSilos2");
            shortageSilos2Field.setAccessible(true);
            shortageSilos2Value = (float) shortageSilos2Field.get(tagCollector);

            Field volumeCurrentMixerLoadField = tagCollector.getClass().getDeclaredField("volumeCurrentMixerLoad");
            volumeCurrentMixerLoadField.setAccessible(true);
            volumeCurrentMixerLoadValue = (float) volumeCurrentMixerLoadField.get(tagCollector);

            Field weightCubeConcreteField = tagCollector.getClass().getDeclaredField("weightCubeConcrete");
            weightCubeConcreteField.setAccessible(true);
            weightCubeConcreteValue = (float) weightCubeConcreteField.get(tagCollector);

            Field batchVolumeField = tagCollector.getClass().getDeclaredField("batchVolume");
            batchVolumeField.setAccessible(true);
            batchVolumeValue = (float) batchVolumeField.get(tagCollector);

            Field mixingTimeField = tagCollector.getClass().getDeclaredField("mixingTime");
            mixingTimeField.setAccessible(true);
            mixingTimeValue = (float) mixingTimeField.get(tagCollector);

            Field horConveyorStartTimerField = tagCollector.getClass().getDeclaredField("horConveyorStartTimer");
            horConveyorStartTimerField.setAccessible(true);
            horConveyorStartTimerValue = (float) horConveyorStartTimerField.get(tagCollector);

            Field currentValueDKScaleField = tagCollector.getClass().getDeclaredField("currentValueDKScale");
            currentValueDKScaleField.setAccessible(true);
            currentWeightDKValue = (float) currentValueDKScaleField.get(tagCollector);

            Field currentValueWaterScaleField = tagCollector.getClass().getDeclaredField("currentValueWaterScale");
            currentValueWaterScaleField.setAccessible(true);
            currentWeightWaterValue = (float) currentValueWaterScaleField.get(tagCollector);

            Field currentValueChemyScaleField = tagCollector.getClass().getDeclaredField("currentValueChemyScale");
            currentValueChemyScaleField.setAccessible(true);
            currentWeightChemyValue = (float) currentValueChemyScaleField.get(tagCollector);

            Field currentValueCementScaleField = tagCollector.getClass().getDeclaredField("currentValueCementScale");
            currentValueCementScaleField.setAccessible(true);
            currentWeightCementValue = (float) currentValueCementScaleField.get(tagCollector);

            Field countdownTimeMixField = tagCollector.getClass().getDeclaredField("countdownTimeMix");
            countdownTimeMixField.setAccessible(true);
            countdownTimeMixValue = (int) countdownTimeMixField.get(tagCollector);

            //mixer drop
            Field mixerCloseField = tagCollector.getClass().getDeclaredField("mixerClose");
            mixerCloseField.setAccessible(true);
            mixerCloseValue = (int) mixerCloseField.get(tagCollector);

            Field mixerHalfOpenField = tagCollector.getClass().getDeclaredField("mixerHalfOpen");
            mixerHalfOpenField.setAccessible(true);
            mixerHalfOpenValue = (int) mixerHalfOpenField.get(tagCollector);

            Field mixerOpenField = tagCollector.getClass().getDeclaredField("mixerOpen");
            mixerOpenField.setAccessible(true);
            mixerOpenValue = (int) mixerOpenField.get(tagCollector);

            Field autoDropChckerField = tagCollector.getClass().getDeclaredField("autoDropChcker");
            autoDropChckerField.setAccessible(true);
            autoDropChckerValue = (int) autoDropChckerField.get(tagCollector);

            Field dropImpulseChckerField = tagCollector.getClass().getDeclaredField("dropImpulseChcker");
            dropImpulseChckerField.setAccessible(true);
            dropImpulseChckerValue = (int) dropImpulseChckerField.get(tagCollector);

            //doseComplite
            Field doseHopper11Field = tagCollector.getClass().getDeclaredField("doseHopper11");
            doseHopper11Field.setAccessible(true);
            doseHopper11Value = (float) doseHopper11Field.get(tagCollector);

            Field doseHopper12Field = tagCollector.getClass().getDeclaredField("doseHopper12");
            doseHopper12Field.setAccessible(true);
            doseHopper12Value = (float) doseHopper12Field.get(tagCollector);

            Field doseHopper21Field = tagCollector.getClass().getDeclaredField("doseHopper21");
            doseHopper21Field.setAccessible(true);
            doseHopper21Value = (float) doseHopper21Field.get(tagCollector);

            Field doseHopper22Field = tagCollector.getClass().getDeclaredField("doseHopper22");
            doseHopper22Field.setAccessible(true);
            doseHopper22Value = (float) doseHopper22Field.get(tagCollector);

            Field doseHopper31Field = tagCollector.getClass().getDeclaredField("doseHopper31");
            doseHopper31Field.setAccessible(true);
            doseHopper31Value = (float) doseHopper31Field.get(tagCollector);

            Field doseHopper32Field = tagCollector.getClass().getDeclaredField("doseHopper32");
            doseHopper32Field.setAccessible(true);
            doseHopper32Value = (float) doseHopper32Field.get(tagCollector);

            Field doseHopper41Field = tagCollector.getClass().getDeclaredField("doseHopper41");
            doseHopper41Field.setAccessible(true);
            doseHopper41Value = (float) doseHopper41Field.get(tagCollector);

            Field doseHopper42Field = tagCollector.getClass().getDeclaredField("doseHopper42");
            doseHopper42Field.setAccessible(true);
            doseHopper42Value = (float) doseHopper42Field.get(tagCollector);

            Field doseWaterField = tagCollector.getClass().getDeclaredField("doseWater");
            doseWaterField.setAccessible(true);
            doseWaterValue = (float) doseWaterField.get(tagCollector);

            Field doseSilos1Field = tagCollector.getClass().getDeclaredField("doseSilos1");
            doseSilos1Field.setAccessible(true);
            doseSilos1Value = (float) doseSilos1Field.get(tagCollector);

            Field doseSilos2Field = tagCollector.getClass().getDeclaredField("doseSilos2");
            doseSilos2Field.setAccessible(true);
            doseSilos2Value = (float) doseSilos2Field.get(tagCollector);

            Field doseChemy1Field = tagCollector.getClass().getDeclaredField("doseChemy1");
            doseChemy1Field.setAccessible(true);
            doseChemy1Value = (float) doseChemy1Field.get(tagCollector);

            Field doseChemy2Field = tagCollector.getClass().getDeclaredField("doseChemy2");
            doseChemy2Field.setAccessible(true);
            doseChemy2Value = (float) doseChemy2Field.get(tagCollector);

            Field vibroSilos1Field = tagCollector.getClass().getDeclaredField("vibroSilos1");
            vibroSilos1Field.setAccessible(true);
            vibroSilos1Value = (int) vibroSilos1Field.get(tagCollector);

            Field vibroDispenserCementField = tagCollector.getClass().getDeclaredField("vibroDispenserCement");
            vibroDispenserCementField.setAccessible(true);
            vibroDispenserDCValue = (int) vibroDispenserCementField.get(tagCollector);

            Field mixingField = tagCollector.getClass().getDeclaredField("mixingCapacity");
            mixingField.setAccessible(true);
            mixingCapacity = (float) mixingField.get(tagCollector);

            Field mixCounterField = tagCollector.getClass().getDeclaredField("mixCounter");
            mixCounterField.setAccessible(true);
            mixCounterValue = (int) mixCounterField.get(tagCollector);

            Field amperageMixerField = tagCollector.getClass().getDeclaredField("amperageMixer");
            amperageMixerField.setAccessible(true);
            amperageMixerValue = (float) amperageMixerField.get(tagCollector);

            Field totalMixCounterField = tagCollector.getClass().getDeclaredField("totalMixCounter");
            totalMixCounterField.setAccessible(true);
            totalMixCounterValue = (int) totalMixCounterField.get(tagCollector);

            Field vibroBuncker11OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker11Option");
            vibroBuncker11OptionField.setAccessible(true);
            vibroBuncker11OptionValue = (int) vibroBuncker11OptionField.get(tagCollector);

            Field vibroBuncker12OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker12Option");
            vibroBuncker12OptionField.setAccessible(true);
            vibroBuncker12OptionValue = (int) vibroBuncker12OptionField.get(tagCollector);

            Field vibroBuncker21OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker21Option");
            vibroBuncker21OptionField.setAccessible(true);
            vibroBuncker21OptionValue = (int) vibroBuncker21OptionField.get(tagCollector);

            Field vibroBuncker22OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker22Option");
            vibroBuncker22OptionField.setAccessible(true);
            vibroBuncker22OptionValue = (int) vibroBuncker22OptionField.get(tagCollector);

            Field vibroBuncker31OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker31Option");
            vibroBuncker31OptionField.setAccessible(true);
            vibroBuncker31OptionValue = (int) vibroBuncker31OptionField.get(tagCollector);

            Field vibroBuncker32OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker32Option");
            vibroBuncker32OptionField.setAccessible(true);
            vibroBuncker32OptionValue = (int) vibroBuncker32OptionField.get(tagCollector);

            Field vibroBuncker41OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker41Option");
            vibroBuncker41OptionField.setAccessible(true);
            vibroBuncker41OptionValue = (int) vibroBuncker41OptionField.get(tagCollector);

            Field vibroBuncker42OptionField = tagCollector.getClass().getDeclaredField("vibroBuncker42Option");
            vibroBuncker42OptionField.setAccessible(true);
            vibroBuncker42OptionValue = (int) vibroBuncker42OptionField.get(tagCollector);

            Field weightsReadyReadField = tagCollector.getClass().getDeclaredField("weightsReadyRead");
            weightsReadyReadField.setAccessible(true);
            weightsReadyReadValue = (int) weightsReadyReadField.get(tagCollector);

            Field vibroColdBunckersIndicationField = tagCollector.getClass().getDeclaredField("vibroColdBunckersIndication");
            vibroColdBunckersIndicationField.setAccessible(true);
            vibroColdBunckersIndicationValue = (int) vibroColdBunckersIndicationField.get(tagCollector);

            Field firmwareVersionField = tagCollector.getClass().getDeclaredField("firmwareVersion");
            firmwareVersionField.setAccessible(true);
            firmwareVersionValue = (float) firmwareVersionField.get(tagCollector);

            Field fibraWeightSensorField = tagCollector.getClass().getDeclaredField("fibraWeightSensor");
            fibraWeightSensorField.setAccessible(true);
            fibraWeightSensorValue = (float) fibraWeightSensorField.get(tagCollector);

            Field silosSelectorField = tagCollector.getClass().getDeclaredField("silosSelector");
            silosSelectorField.setAccessible(true);
            silosSelectorValue = (int) silosSelectorField.get(tagCollector);

            Field selfDKStateField = tagCollector.getClass().getDeclaredField("selfDKState");
            selfDKStateField.setAccessible(true);
            selfDKStateValue = (int) selfDKStateField.get(tagCollector);

            Field selfDChStateField = tagCollector.getClass().getDeclaredField("selfDChState");
            selfDChStateField.setAccessible(true);
            selfDChStateValue = (int) selfDChStateField.get(tagCollector);

            Field selfDCStateField = tagCollector.getClass().getDeclaredField("selfDCState");
            selfDCStateField.setAccessible(true);
            selfDCStateValue = (int) selfDCStateField.get(tagCollector);

            Field selfDWStateField = tagCollector.getClass().getDeclaredField("selfDWState");
            selfDWStateField.setAccessible(true);
            selfDWStateValue = (int) selfDWStateField.get(tagCollector);

            Field skipAlarmIndicatorField = tagCollector.getClass().getDeclaredField("skipAlarmIndicator");
            skipAlarmIndicatorField.setAccessible(true);
            skipAlarmIndicatorValue = (int) skipAlarmIndicatorField.get(tagCollector);

            Field verticalConveyorIndField = tagCollector.getClass().getDeclaredField("verticalConveyorInd");
            verticalConveyorIndField.setAccessible(true);
            verticalConveyorIndValue = (int) verticalConveyorIndField.get(tagCollector);

            Field dropConveyerField = tagCollector.getClass().getDeclaredField("dropConveyer");
            dropConveyerField.setAccessible(true);
            conveyorDropValue = (int) dropConveyerField.get(tagCollector);

            Field skipSensorUp1Field = tagCollector.getClass().getDeclaredField("skipSensorUp1");
            skipSensorUp1Field.setAccessible(true);
            skipSensorUp1Value = (int) skipSensorUp1Field.get(tagCollector);

            Field skipSensorUp2Field = tagCollector.getClass().getDeclaredField("skipSensorUp2");
            skipSensorUp2Field.setAccessible(true);
            skipSensorUp2Value = (int) skipSensorUp2Field.get(tagCollector);

            Field skipSensorDown1Field = tagCollector.getClass().getDeclaredField("skipSensorDown1");
            skipSensorDown1Field.setAccessible(true);
            skipSensorDown1Value = (int) skipSensorDown1Field.get(tagCollector);

            Field skipSensorDown2Field = tagCollector.getClass().getDeclaredField("skipSensorDown2");
            skipSensorDown2Field.setAccessible(true);
            skipSensorDown2Value = (int) skipSensorDown2Field.get(tagCollector);

            Field fibraVibratorIndicatorField = tagCollector.getClass().getDeclaredField("fibraVibratorIndicator");
            fibraVibratorIndicatorField.setAccessible(true);
            fibraVibratorIndicatorValue = (int) fibraVibratorIndicatorField.get(tagCollector);

            Field reverseDKIndField = tagCollector.getClass().getDeclaredField("reverseDKInd");
            reverseDKIndField.setAccessible(true);
            reverseDKValue = (int) reverseDKIndField.get(tagCollector);

            Field uploadConveyorIndField = tagCollector.getClass().getDeclaredField("uploadConveyorInd");
            uploadConveyorIndField.setAccessible(true);
            uploadConveyorDKValue = (int) uploadConveyorIndField.get(tagCollector);

            Field motoClockHorConveyorField = tagCollector.getClass().getDeclaredField("motoClockHorConveyor");
            motoClockHorConveyorField.setAccessible(true);
            motoClockHorConveyorValue = (int) motoClockHorConveyorField.get(tagCollector);

            Field motoClockVertConvField = tagCollector.getClass().getDeclaredField("motoClockVertConv");
            motoClockVertConvField.setAccessible(true);
            motoClockVertConvValue = (int) motoClockVertConvField.get(tagCollector);

            Field motoClockSkipField = tagCollector.getClass().getDeclaredField("motoClockSkip");
            motoClockSkipField.setAccessible(true);
            motoClockSkipValue = (int) motoClockSkipField.get(tagCollector);

            Field motoClockMixerField = tagCollector.getClass().getDeclaredField("motoClockMixer");
            motoClockMixerField.setAccessible(true);
            motoClockMixerValue = (int) motoClockMixerField.get(tagCollector);

            Field motoClockShnek1Field = tagCollector.getClass().getDeclaredField("motoClockShnek1");
            motoClockShnek1Field.setAccessible(true);
            motoClockShnek1Value = (int) motoClockShnek1Field.get(tagCollector);

            Field motoClockShnek2Field = tagCollector.getClass().getDeclaredField("motoClockShnek2");
            motoClockShnek2Field.setAccessible(true);
            motoClockShnek2Value = (int) motoClockShnek2Field.get(tagCollector);

            Field motoClockShnek3Field = tagCollector.getClass().getDeclaredField("motoClockShnek3");
            motoClockShnek3Field.setAccessible(true);
            motoClockShnek3Value = (int) motoClockShnek3Field.get(tagCollector);

            Field motoClockValveWaterField = tagCollector.getClass().getDeclaredField("motoClockValveWater");
            motoClockValveWaterField.setAccessible(true);
            motoClockValveWaterValue = (int) motoClockValveWaterField.get(tagCollector);

            Field motoClockPumpWaterField = tagCollector.getClass().getDeclaredField("motoClockPumpWater");
            motoClockPumpWaterField.setAccessible(true);
            motoClockPumpWaterValue = (int) motoClockPumpWaterField.get(tagCollector);

            Field motoClockPumpChemy1Field = tagCollector.getClass().getDeclaredField("motoClockPumpChemy1");
            motoClockPumpChemy1Field.setAccessible(true);
            motoClockPumpChemy1Value = (int) motoClockPumpChemy1Field.get(tagCollector);

            Field motoClockPumpChemy2Field = tagCollector.getClass().getDeclaredField("motoClockPumpChemy2");
            motoClockPumpChemy2Field.setAccessible(true);
            motoClockPumpChemy2Value = (int) motoClockPumpChemy2Field.get(tagCollector);

            Field motoClockOilStationField = tagCollector.getClass().getDeclaredField("motoClockOilStation");
            motoClockOilStationField.setAccessible(true);
            motoClockOilStationValue = (int) motoClockOilStationField.get(tagCollector);

            Field sensorHumidityField = tagCollector.getClass().getDeclaredField("sensorHumidity");
            sensorHumidityField.setAccessible(true);
            sensorHumidityValue = (float) sensorHumidityField.get(tagCollector);

            Field waterPumpCounterField = tagCollector.getClass().getDeclaredField("waterPumpCounter");
            waterPumpCounterField.setAccessible(true);
            waterPumpCounterValue = (int) waterPumpCounterField.get(tagCollector);

            Field waterPumpHumCorrectionControlField = tagCollector.getClass().getDeclaredField("waterPumpHumCorrectionControl");
            waterPumpHumCorrectionControlField.setAccessible(true);
            waterPumpHumCorrectionControlValue = (int) waterPumpHumCorrectionControlField.get(tagCollector);

            //АВАРИИ И ПРЕДУПРЕЖДЕНИЯ

            Field alarmMixerShiberOpennedField = tagCollector.getClass().getDeclaredField("alarmMixerShiberOpenned");
            alarmMixerShiberOpennedField.setAccessible(true);
            alarmMixerShiberOpennedValue = (int) alarmMixerShiberOpennedField.get(tagCollector);

            Field alarmMixerThermalProtectionField = tagCollector.getClass().getDeclaredField("alarmMixerThermalProtection");
            alarmMixerThermalProtectionField.setAccessible(true);
            alarmMixerThermalProtectionValue  = (int) alarmMixerThermalProtectionField.get(tagCollector);

            Field alarmSkipThermalProtectionField = tagCollector.getClass().getDeclaredField("alarmSkipThermalProtection");
            alarmSkipThermalProtectionField.setAccessible(true);
            alarmSkipThermalProtectionValue  = (int) alarmSkipThermalProtectionField.get(tagCollector);

            Field alarmDKThermalProtectionField = tagCollector.getClass().getDeclaredField("alarmDKThermalProtection");
            alarmDKThermalProtectionField.setAccessible(true);
            alarmDKThermalProtectionValue  = (int) alarmDKThermalProtectionField.get(tagCollector);

            Field alarmMixerWindowOpennedField = tagCollector.getClass().getDeclaredField("alarmMixerWindowOpenned");
            alarmMixerWindowOpennedField.setAccessible(true);
            alarmMixerWindowOpennedValue  = (int) alarmMixerWindowOpennedField.get(tagCollector);

            Field alarmTimeDosingDoser11FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser11Fault");
            alarmTimeDosingDoser11FaultField.setAccessible(true);
            alarmTimeDosingDoser11FaultValue = (int) alarmTimeDosingDoser11FaultField.get(tagCollector);

            Field alarmTimeDosingDoser12FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser12Fault");
            alarmTimeDosingDoser12FaultField.setAccessible(true);
            alarmTimeDosingDoser12FaultValue = (int) alarmTimeDosingDoser12FaultField.get(tagCollector);

            Field alarmTimeDosingDoser21FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser21Fault");
            alarmTimeDosingDoser21FaultField.setAccessible(true);
            alarmTimeDosingDoser21FaultValue = (int) alarmTimeDosingDoser21FaultField.get(tagCollector);

            Field alarmTimeDosingDoser22FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser22Fault");
            alarmTimeDosingDoser22FaultField.setAccessible(true);
            alarmTimeDosingDoser22FaultValue = (int) alarmTimeDosingDoser22FaultField.get(tagCollector);

            Field alarmTimeDosingDoser31FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser31Fault");
            alarmTimeDosingDoser31FaultField.setAccessible(true);
            alarmTimeDosingDoser31FaultValue = (int) alarmTimeDosingDoser31FaultField.get(tagCollector);

            Field alarmTimeDosingDoser32FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser32Fault");
            alarmTimeDosingDoser32FaultField.setAccessible(true);
            alarmTimeDosingDoser32FaultValue = (int) alarmTimeDosingDoser32FaultField.get(tagCollector);

            Field alarmTimeDosingDoser41FaultField = tagCollector.getClass().getDeclaredField("alarmTimeDosingDoser41Fault");
            alarmTimeDosingDoser41FaultField.setAccessible(true);
            alarmTimeDosingDoser41FaultValue = (int) alarmTimeDosingDoser41FaultField.get(tagCollector);

            Field warningAutoModeNotActivatedField = tagCollector.getClass().getDeclaredField("warningAutoModeNotActivated");
            warningAutoModeNotActivatedField.setAccessible(true);
            warningAutoModeNotActivatedValue = (int) warningAutoModeNotActivatedField.get(tagCollector);

            Field warningMixerNotEmptyField = tagCollector.getClass().getDeclaredField("warningMixerNotEmpty");
            warningMixerNotEmptyField.setAccessible(true);
            warningMixerNotEmptyValue = (int) warningMixerNotEmptyField.get(tagCollector);

            Field warningAutoDropDisableField = tagCollector.getClass().getDeclaredField("warningAutoDropDisable");
            warningAutoDropDisableField.setAccessible(true);
            warningAutoDropDisableValue = (int) warningAutoDropDisableField.get(tagCollector);

            Field alarmWeightDKNotEmptyField = tagCollector.getClass().getDeclaredField("alarmWeightDKNotEmpty");
            alarmWeightDKNotEmptyField.setAccessible(true);
            alarmWeightDKNotEmptyValue = (int) alarmWeightDKNotEmptyField.get(tagCollector);

            Field alarmDCWeightNotEmptyField = tagCollector.getClass().getDeclaredField("alarmDCWeightNotEmpty");
            alarmDCWeightNotEmptyField.setAccessible(true);
            alarmDCWeightNotEmptyValue = (int) alarmDCWeightNotEmptyField.get(tagCollector);

            Field alarmDWWeightNotEmptyField = tagCollector.getClass().getDeclaredField("alarmDWWeightNotEmpty");
            alarmDWWeightNotEmptyField.setAccessible(true);
            alarmDWWeightNotEmptyValue = (int) alarmDWWeightNotEmptyField.get(tagCollector);

            Field alarmDChWeightNotEmptyField = tagCollector.getClass().getDeclaredField("alarmDChWeightNotEmpty");
            alarmDChWeightNotEmptyField.setAccessible(true);
            alarmDChWeightNotEmptyValue = (int) alarmDChWeightNotEmptyField.get(tagCollector);

            Field alarmDKCalibrateErrorField = tagCollector.getClass().getDeclaredField("alarmDKCalibrateError");
            alarmDKCalibrateErrorField.setAccessible(true);
            alarmDKCalibrateErrorValue = (int) alarmDKCalibrateErrorField.get(tagCollector);

            Field alarmDCCalibrateErrorField = tagCollector.getClass().getDeclaredField("alarmDCCalibrateError");
            alarmDCCalibrateErrorField.setAccessible(true);
            alarmDCCalibrateErrorValue = (int) alarmDCCalibrateErrorField.get(tagCollector);

            Field alarmDWCalibrateErrorField = tagCollector.getClass().getDeclaredField("alarmDWCalibrateError");
            alarmDWCalibrateErrorField.setAccessible(true);
            alarmDWCalibrateErrorValue = (int) alarmDWCalibrateErrorField.get(tagCollector);

            Field alarmDChCalibrateErrorField = tagCollector.getClass().getDeclaredField("alarmDChCalibrateError");
            alarmDChCalibrateErrorField.setAccessible(true);
            alarmDChCalibrateErrorValue = (int) alarmDChCalibrateErrorField.get(tagCollector);

            Field alarmShnekThermalDefenceField = tagCollector.getClass().getDeclaredField("alarmShnekThermalDefence");
            alarmShnekThermalDefenceField.setAccessible(true);
            alarmShnekThermalDefenceValue = (int) alarmShnekThermalDefenceField.get(tagCollector);

            Field alarmMixerDropErrorField = tagCollector.getClass().getDeclaredField("alarmMixerDropError");
            alarmMixerDropErrorField.setAccessible(true);
            alarmMixerDropErrorValue = (int) alarmMixerDropErrorField.get(tagCollector);

            Field warningAutoPowerOffMixerDisabledField = tagCollector.getClass().getDeclaredField("warningAutoPowerOffMixerDisabled");
            warningAutoPowerOffMixerDisabledField.setAccessible(true);
            warningAutoPowerOffMixerDisabledValue = (int) warningAutoPowerOffMixerDisabledField.get(tagCollector);

            Field alarmMixerEngineNotStartedField = tagCollector.getClass().getDeclaredField("alarmMixerEngineNotStarted");
            alarmMixerEngineNotStartedField.setAccessible(true);
            alarmMixerEngineNotStartedValue = (int) alarmMixerEngineNotStartedField.get(tagCollector);

            Field warningConveyorUploadNotStartedField = tagCollector.getClass().getDeclaredField("warningConveyorUploadNotStarted");
            warningConveyorUploadNotStartedField.setAccessible(true);
            warningConveyorUploadNotStartedValue = (int) warningConveyorUploadNotStartedField.get(tagCollector);

            Field alarmOverflowChemyField = tagCollector.getClass().getDeclaredField("alarmOverflowChemy");
            alarmOverflowChemyField.setAccessible(true);
            alarmOverflowChemyValue = (int) alarmOverflowChemyField.get(tagCollector);

            Field alarmOverflowWaterField = tagCollector.getClass().getDeclaredField("alarmOverflowWater");
            alarmOverflowWaterField.setAccessible(true);
            alarmOverflowWaterValue = (int) alarmOverflowWaterField.get(tagCollector);

            Field alarmOverFlowDKField = tagCollector.getClass().getDeclaredField("alarmOverFlowDK");
            alarmOverFlowDKField.setAccessible(true);
            alarmOverFlowDKValue = (int) alarmOverFlowDKField.get(tagCollector);

            Field alarmMixerCloseErrorStopSkipField = tagCollector.getClass().getDeclaredField("alarmMixerCloseErrorStopSkip");
            alarmMixerCloseErrorStopSkipField.setAccessible(true);
            alarmMixerCloseErrorStopSkipValue = (int) alarmMixerCloseErrorStopSkipField.get(tagCollector);

            Field alarmDCShiberErrorField = tagCollector.getClass().getDeclaredField("alarmDCShiberError");
            alarmDCShiberErrorField.setAccessible(true);
            alarmDCShiberErrorValue = (int) alarmDCShiberErrorField.get(tagCollector);

            Field alarmSkipSensorErrorField = tagCollector.getClass().getDeclaredField("alarmSkipDoubleSensorCrash");
            alarmSkipSensorErrorField.setAccessible(true);
            alarmSkipSensorValue = (int) alarmSkipSensorErrorField.get(tagCollector);

            Field scadaPerformanceField = tagCollector.getClass().getDeclaredField("scadaPerformance");
            scadaPerformanceField.setAccessible(true);
            scadaPerformanceValue = (Double) scadaPerformanceField.get(tagCollector);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //get
    public double getScadaPerformanceValue() {
        return scadaPerformanceValue;
    }

    public int isManualAutoModeValue() {
        return manualAutoModeValue;
    }

    public int isGlobalCrashFlagValue() {
        return globalCrashFlagValue;
    }

    public int isHopper11FlapOpenIndValue() {
        return hopper11FlapOpenIndValue;
    }

    public int isHopper21FlapOpenIndValue() {
        return hopper21FlapOpenIndValue;
    }

    public int isWaterDisFlapOpenIndValue() {
        return waterDisFlapOpenIndValue;
    }

    public int isCementDisFlapOpenIndValue() {
        return cementDisFlapOpenIndValue;
    }

    public int isCementDisFlapOpenPosIndValue() {
        return cementDisFlapOpenPosIndValue;
    }

    public int isCementDisFlapClosePosIndValue() {
        return cementDisFlapClosePosIndValue;
    }

    public int isChemyDisFlapOpenIndValue() {
        return chemyDisFlapOpenIndValue;
    }

    public int isHorConveyorOnIndValue() {
        return horConveyorOnIndValue;
    }

    public int isWaterPumpOnIndValue() {
        return waterPumpOnIndValue;
    }

    public int isValveWaterBunckerValue() {
        return valveWaterBunckerValue;
    }

    public int isChemy1PumpOnIndValue() {
        return chemy1PumpOnIndValue;
    }

    public int isCement1AugerOnIndValue() {
        return cement1AugerOnIndValue;
    }

    public int isCement2AugerOnIndValue() {
        return cement2AugerOnIndValue;
    }

    public int isSilosCementFilterValue() {
        return silosCementFilterValue;
    }

    public int isCement1SiloLevelSensorUpValue() {
        return cement1SiloLevelSensorUpValue;
    }

    public int isCement1SiloLevelSensorDownValue() {
        return cement1SiloLevelSensorDownValue;
    }

    public int isCement2SiloLevelSensorUpValue() {
        return cement2SiloLevelSensorUpValue;
    }

    public int isCement2SiloLevelSensorDownValue() {
        return cement2SiloLevelSensorDownValue;
    }

    public int isWaterOverflowSensorValue() {
        return waterOverflowSensorValue;
    }

    public int isChemyOverflowSensorValue() {
        return chemyOverflowSensorValue;
    }

    public int isSkipPosEndSensorUpValue() {
        return skipPosEndSensorUpValue;
    }

    public int isSkipPosEndSensorDownValue() {
        return skipPosEndSensorDownValue;
    }

    public int isSkipPosEndSensorCrashUpValue() {
        return skipPosEndSensorCrashUpValue;
    }

    public int isSkipPosEndSensorCrashDownValue() {
        return skipPosEndSensorCrashDownValue;
    }

    public int isSkipMoveUpValue() {
        return skipMoveUpValue;
    }

    public int isSkipMoveDownValue() {
        return skipMoveDownValue;
    }

    public int isAerationOnIndicationValue() {
        return aerationOnIndicationValue;
    }

    public int isMixerRollersWorkIndicationValue() {
        return mixerRollersWorkIndicationValue;
    }

    public int isMixerNotEmptyValue() {
        return mixerNotEmptyValue;
    }

    public float getHopper11RecipeValue() {
        return hopper11RecipeValue;
    }

    public float getHopper21RecipeValue() {
        return hopper21RecipeValue;
    }

    public float getHopper12RecipeValue() {
        return hopper12RecipeValue;
    }

    public float getHopper22RecipeValue() {
        return hopper22RecipeValue;
    }

    public float getHopper31RecipeValue() {
        return hopper31RecipeValue;
    }

    public float getHopper32RecipeValue() {
        return hopper32RecipeValue;
    }

    public float getHopper41RecipeValue() {
        return hopper41RecipeValue;
    }

    public float getHopper42RecipeValue() {
        return hopper42RecipeValue;
    }

    public float getWaterRecipeValue() {
        return waterRecipeValue;
    }

    public float getChemy1RecipeValue() {
        return chemy1RecipeValue;
    }

    public float getCement1RecipeValue() {
        return cement1RecipeValue;
    }

    public float getCement2RecipeValue() {
        return cement2RecipeValue;
    }

    public float getShortageHopper11Value() {
        return shortageHopper11Value;
    }

    public float getShortageHopper12Value() {
        return shortageHopper12Value;
    }

    public float getShortageHopper21Value() {
        return shortageHopper21Value;
    }

    public float getShortageHopper22Value() {
        return shortageHopper22Value;
    }

    public float getShortageHopper31Value() {
        return shortageHopper31Value;
    }

    public float getShortageHopper32Value() {
        return shortageHopper32Value;
    }

    public float getShortageHopper41Value() {
        return shortageHopper41Value;
    }

    public float getShortageHopper42Value() {
        return shortageHopper42Value;
    }

    public float getShortageWaterValue() {
        return shortageWaterValue;
    }

    public float getShortageChemy1Value() {
        return shortageChemy1Value;
    }

    public float getShortageSilos1Value() {
        return shortageSilos1Value;
    }

    public float getShortageSilos2Value() {
        return shortageSilos2Value;
    }

    public float getVolumeCurrentMixerLoadValue() {
        return volumeCurrentMixerLoadValue;
    }

    public float getWeightCubeConcreteValue() {
        return weightCubeConcreteValue;
    }

    public float getBatchVolumeValue() {
        return batchVolumeValue;
    }

    public float getMixingTimeValue() {
        return mixingTimeValue;
    }

    public float getHorConveyorStartTimerValue() {
        return horConveyorStartTimerValue;
    }

    public float getCurrentWeightDKValue() {
        return currentWeightDKValue;
    }

    public float getCurrentWeightWaterValue() {
        return currentWeightWaterValue;
    }

    public float getCurrentWeightChemyValue() {
        return currentWeightChemyValue;
    }

    public float getCurrentWeightCementValue() {
        return currentWeightCementValue;
    }

    public int getCountdownTimeMixValue() {
        return countdownTimeMixValue;
    }

    public int isMixerCloseValue() {
        return mixerCloseValue;
    }

    public int isMixerHalfOpenValue() {
        return mixerHalfOpenValue;
    }

    public int isMixerOpenValue() {
        return mixerOpenValue;
    }

    public int isAutoDropChckerValue() {
        return autoDropChckerValue;
    }

    public int isDropImpulseChckerValue() {
        return dropImpulseChckerValue;
    }

    public float getDoseHopper11Value() {
        return doseHopper11Value;
    }

    public float getDoseHopper12Value() {
        return doseHopper12Value;
    }

    public float getDoseHopper21Value() {
        return doseHopper21Value;
    }

    public float getDoseHopper22Value() {
        return doseHopper22Value;
    }

    public float getDoseHopper31Value() {
        return doseHopper31Value;
    }

    public float getDoseHopper32Value() {
        return doseHopper32Value;
    }

    public float getDoseHopper41Value() {
        return doseHopper41Value;
    }

    public float getDoseHopper42Value() {
        return doseHopper42Value;
    }

    public float getDoseWaterValue() {
        return doseWaterValue;
    }

    public float getDoseSilos1Value() {
        return doseSilos1Value;
    }

    public float getDoseSilos2Value() {
        return doseSilos2Value;
    }

    public float getDoseChemy1Value() {
        return doseChemy1Value;
    }

    public float getDoseChemy2Value() {
        return doseChemy2Value;
    }

    public int isVibroSilos1Value() {
        return vibroSilos1Value;
    }

    public int isVibroDispenserDCValue() {
        return vibroDispenserDCValue;
    }

    public float getMixingCapacity() {
        return mixingCapacity;
    }

    public int getMixCounterValue() {
        return mixCounterValue;
    }

    public float getAmperageMixerValue() {
        return amperageMixerValue;
    }

    public int getTotalMixCounterValue() {
        return totalMixCounterValue;
    }

    public int isWeightsReadyReadValue() {
        return weightsReadyReadValue;
    }

    public int getVibroBuncker11OptionValue() {
        return vibroBuncker11OptionValue;
    }

    public int getVibroBuncker12OptionValue() {
        return vibroBuncker12OptionValue;
    }

    public int getVibroBuncker21OptionValue() {
        return vibroBuncker21OptionValue;
    }

    public int getVibroBuncker22OptionValue() {
        return vibroBuncker22OptionValue;
    }

    public int getVibroBuncker31OptionValue() {
        return vibroBuncker31OptionValue;
    }

    public int getVibroBuncker32OptionValue() {
        return vibroBuncker32OptionValue;
    }

    public int getVibroBuncker41OptionValue() {
        return vibroBuncker41OptionValue;
    }

    public int getVibroBuncker42OptionValue() {
        return vibroBuncker42OptionValue;
    }

    public int isHopper12FlapOpenIndValue() {
        return hopper12FlapOpenIndValue;
    }

    public int isHopper22FlapOpenIndValue() {
        return hopper22FlapOpenIndValue;
    }

    public int isHopper31FlapOpenIndValue() {
        return hopper31FlapOpenIndValue;
    }

    public int isHopper32FlapOpenIndValue() {
        return hopper32FlapOpenIndValue;
    }

    public int isHopper41FlapOpenIndValue() {
        return hopper41FlapOpenIndValue;
    }

    public int isHopper42FlapOpenIndValue() {
        return hopper42FlapOpenIndValue;
    }

    public int isVibroColdBunckersIndicationValue() {
        return vibroColdBunckersIndicationValue;
    }

    public int isPendingProductionStateValue() {
        return pendingProductionStateValue;
    }

    public int isMixerWindowViewOpenSensorValue() {
        return mixerWindowViewOpenSensorValue;
    }

    public float getFirmwareVersionValue() {
        return firmwareVersionValue;
    }

    public float getChemy2RecipeValue() {
        return chemy2RecipeValue;
    }

    public int isSilosSelectorValue() {
        return silosSelectorValue;
    }

    //set
    public void setManualAutoModeValue(int manualAutoModeValue) {
        this.manualAutoModeValue = manualAutoModeValue;
    }

    public void setPendingProductionStateValue(int pendingProductionStateValue) {
        this.pendingProductionStateValue = pendingProductionStateValue;
    }

    public void setGlobalCrashFlagValue(int globalCrashFlagValue) {
        this.globalCrashFlagValue = globalCrashFlagValue;
    }

    public void setHopper11FlapOpenIndValue(int hopper11FlapOpenIndValue) {
        this.hopper11FlapOpenIndValue = hopper11FlapOpenIndValue;
    }

    public void setHopper12FlapOpenIndValue(int hopper12FlapOpenIndValue) {
        this.hopper12FlapOpenIndValue = hopper12FlapOpenIndValue;
    }

    public void setHopper21FlapOpenIndValue(int hopper21FlapOpenIndValue) {
        this.hopper21FlapOpenIndValue = hopper21FlapOpenIndValue;
    }

    public void setHopper22FlapOpenIndValue(int hopper22FlapOpenIndValue) {
        this.hopper22FlapOpenIndValue = hopper22FlapOpenIndValue;
    }

    public void setHopper31FlapOpenIndValue(int hopper31FlapOpenIndValue) {
        this.hopper31FlapOpenIndValue = hopper31FlapOpenIndValue;
    }

    public void setHopper32FlapOpenIndValue(int hopper32FlapOpenIndValue) {
        this.hopper32FlapOpenIndValue = hopper32FlapOpenIndValue;
    }

    public void setHopper41FlapOpenIndValue(int hopper41FlapOpenIndValue) {
        this.hopper41FlapOpenIndValue = hopper41FlapOpenIndValue;
    }

    public void setHopper42FlapOpenIndValue(int hopper42FlapOpenIndValue) {
        this.hopper42FlapOpenIndValue = hopper42FlapOpenIndValue;
    }

    public void setWaterDisFlapOpenIndValue(int waterDisFlapOpenIndValue) {
        this.waterDisFlapOpenIndValue = waterDisFlapOpenIndValue;
    }

    public void setCementDisFlapOpenIndValue(int cementDisFlapOpenIndValue) {
        this.cementDisFlapOpenIndValue = cementDisFlapOpenIndValue;
    }

    public void setCementDisFlapOpenPosIndValue(int cementDisFlapOpenPosIndValue) {
        this.cementDisFlapOpenPosIndValue = cementDisFlapOpenPosIndValue;
    }

    public void setCementDisFlapClosePosIndValue(int cementDisFlapClosePosIndValue) {
        this.cementDisFlapClosePosIndValue = cementDisFlapClosePosIndValue;
    }

    public void setChemyDisFlapOpenIndValue(int chemyDisFlapOpenIndValue) {
        this.chemyDisFlapOpenIndValue = chemyDisFlapOpenIndValue;
    }

    public void setHorConveyorOnIndValue(int horConveyorOnIndValue) {
        this.horConveyorOnIndValue = horConveyorOnIndValue;
    }

    public void setWaterPumpOnIndValue(int waterPumpOnIndValue) {
        this.waterPumpOnIndValue = waterPumpOnIndValue;
    }

    public void setValveWaterBunckerValue(int valveWaterBunckerValue) {
        this.valveWaterBunckerValue = valveWaterBunckerValue;
    }

    public void setChemy1PumpOnIndValue(int chemy1PumpOnIndValue) {
        this.chemy1PumpOnIndValue = chemy1PumpOnIndValue;
    }

    public int isChemy2PumpOnIndValue() {
        return chemy2PumpOnIndValue;
    }

    public void setCement1AugerOnIndValue(int cement1AugerOnIndValue) {
        this.cement1AugerOnIndValue = cement1AugerOnIndValue;
    }

    public void setSilosCementFilterValue(int silosCementFilterValue) {
        this.silosCementFilterValue = silosCementFilterValue;
    }

    public void setCement1SiloLevelSensorUpValue(int cement1SiloLevelSensorUpValue) {
        this.cement1SiloLevelSensorUpValue = cement1SiloLevelSensorUpValue;
    }

    public void setCement1SiloLevelSensorDownValue(int cement1SiloLevelSensorDownValue) {
        this.cement1SiloLevelSensorDownValue = cement1SiloLevelSensorDownValue;
    }

    public void setCement2SiloLevelSensorUpValue(int cement2SiloLevelSensorUpValue) {
        this.cement2SiloLevelSensorUpValue = cement2SiloLevelSensorUpValue;
    }

    public void setCement2SiloLevelSensorDownValue(int cement2SiloLevelSensorDownValue) {
        this.cement2SiloLevelSensorDownValue = cement2SiloLevelSensorDownValue;
    }

    public void setWaterOverflowSensorValue(int waterOverflowSensorValue) {
        this.waterOverflowSensorValue = waterOverflowSensorValue;
    }

    public void setChemyOverflowSensorValue(int chemyOverflowSensorValue) {
        this.chemyOverflowSensorValue = chemyOverflowSensorValue;
    }

    public void setSkipPosEndSensorUpValue(int skipPosEndSensorUpValue) {
        this.skipPosEndSensorUpValue = skipPosEndSensorUpValue;
    }

    public void setSkipPosEndSensorDownValue(int skipPosEndSensorDownValue) {
        this.skipPosEndSensorDownValue = skipPosEndSensorDownValue;
    }

    public void setSkipPosEndSensorCrashUpValue(int skipPosEndSensorCrashUpValue) {
        this.skipPosEndSensorCrashUpValue = skipPosEndSensorCrashUpValue;
    }

    public void setSkipPosEndSensorCrashDownValue(int skipPosEndSensorCrashDownValue) {
        this.skipPosEndSensorCrashDownValue = skipPosEndSensorCrashDownValue;
    }

    public void setSkipMoveUpValue(int skipMoveUpValue) {
        this.skipMoveUpValue = skipMoveUpValue;
    }

    public void setSkipMoveDownValue(int skipMoveDownValue) {
        this.skipMoveDownValue = skipMoveDownValue;
    }

    public void setAerationOnIndicationValue(int aerationOnIndicationValue) {
        this.aerationOnIndicationValue = aerationOnIndicationValue;
    }

    public void setMixerRollersWorkIndicationValue(int mixerRollersWorkIndicationValue) {
        this.mixerRollersWorkIndicationValue = mixerRollersWorkIndicationValue;
    }

    public void setMixerWindowViewOpenSensorValue(int mixerWindowViewOpenSensorValue) {
        this.mixerWindowViewOpenSensorValue = mixerWindowViewOpenSensorValue;
    }

    public void setMixerNotEmptyValue(int mixerNotEmptyValue) {
        this.mixerNotEmptyValue = mixerNotEmptyValue;
    }

    public void setHopper11RecipeValue(float hopper11RecipeValue) {
        this.hopper11RecipeValue = hopper11RecipeValue;
    }

    public void setHopper12RecipeValue(float hopper12RecipeValue) {
        this.hopper12RecipeValue = hopper12RecipeValue;
    }

    public void setHopper21RecipeValue(float hopper21RecipeValue) {
        this.hopper21RecipeValue = hopper21RecipeValue;
    }

    public void setHopper22RecipeValue(float hopper22RecipeValue) {
        this.hopper22RecipeValue = hopper22RecipeValue;
    }

    public void setHopper31RecipeValue(float hopper31RecipeValue) {
        this.hopper31RecipeValue = hopper31RecipeValue;
    }

    public void setHopper32RecipeValue(float hopper32RecipeValue) {
        this.hopper32RecipeValue = hopper32RecipeValue;
    }

    public void setHopper41RecipeValue(float hopper41RecipeValue) {
        this.hopper41RecipeValue = hopper41RecipeValue;
    }

    public void setHopper42RecipeValue(float hopper42RecipeValue) {
        this.hopper42RecipeValue = hopper42RecipeValue;
    }

    public void setWaterRecipeValue(float waterRecipeValue) {
        this.waterRecipeValue = waterRecipeValue;
    }

    public void setChemy1RecipeValue(float chemy1RecipeValue) {
        this.chemy1RecipeValue = chemy1RecipeValue;
    }

    public void setCement1RecipeValue(float cement1RecipeValue) {
        this.cement1RecipeValue = cement1RecipeValue;
    }

    public void setCement2RecipeValue(float cement2RecipeValue) {
        this.cement2RecipeValue = cement2RecipeValue;
    }

    public void setShortageHopper11Value(float shortageHopper11Value) {
        this.shortageHopper11Value = shortageHopper11Value;
    }

    public void setShortageHopper12Value(float shortageHopper12Value) {
        this.shortageHopper12Value = shortageHopper12Value;
    }

    public void setShortageHopper21Value(float shortageHopper21Value) {
        this.shortageHopper21Value = shortageHopper21Value;
    }

    public void setShortageHopper22Value(float shortageHopper22Value) {
        this.shortageHopper22Value = shortageHopper22Value;
    }

    public void setShortageHopper31Value(float shortageHopper31Value) {
        this.shortageHopper31Value = shortageHopper31Value;
    }

    public void setShortageHopper32Value(float shortageHopper32Value) {
        this.shortageHopper32Value = shortageHopper32Value;
    }

    public void setShortageHopper41Value(float shortageHopper41Value) {
        this.shortageHopper41Value = shortageHopper41Value;
    }

    public void setShortageHopper42Value(float shortageHopper42Value) {
        this.shortageHopper42Value = shortageHopper42Value;
    }

    public void setShortageWaterValue(float shortageWaterValue) {
        this.shortageWaterValue = shortageWaterValue;
    }

    public void setShortageChemy1Value(float shortageChemy1Value) {
        this.shortageChemy1Value = shortageChemy1Value;
    }

    public void setShortageSilos1Value(float shortageSilos1Value) {
        this.shortageSilos1Value = shortageSilos1Value;
    }

    public void setShortageSilos2Value(float shortageSilos2Value) {
        this.shortageSilos2Value = shortageSilos2Value;
    }

    public void setVolumeCurrentMixerLoadValue(float volumeCurrentMixerLoadValue) {
        this.volumeCurrentMixerLoadValue = volumeCurrentMixerLoadValue;
    }

    public void setWeightCubeConcreteValue(float weightCubeConcreteValue) {
        this.weightCubeConcreteValue = weightCubeConcreteValue;
    }

    public void setBatchVolumeValue(float batchVolumeValue) {
        this.batchVolumeValue = batchVolumeValue;
    }

    public void setMixingTimeValue(float mixingTimeValue) {
        this.mixingTimeValue = mixingTimeValue;
    }

    public void setHorConveyorStartTimerValue(float horConveyorStartTimerValue) {
        this.horConveyorStartTimerValue = horConveyorStartTimerValue;
    }

    public void setCurrentWeightDKValue(float currentWeightDKValue) {
        this.currentWeightDKValue = currentWeightDKValue;
    }

    public void setCurrentWeightWaterValue(float currentWeightWaterValue) {
        this.currentWeightWaterValue = currentWeightWaterValue;
    }

    public void setCurrentWeightChemyValue(float currentWeightChemyValue) {
        this.currentWeightChemyValue = currentWeightChemyValue;
    }

    public void setCurrentWeightCementValue(float currentWeightCementValue) {
        this.currentWeightCementValue = currentWeightCementValue;
    }

    public void setCountdownTimeMixValue(int countdownTimeMixValue) {
        this.countdownTimeMixValue = countdownTimeMixValue;
    }

    public void setMixerCloseValue(int mixerCloseValue) {
        this.mixerCloseValue = mixerCloseValue;
    }

    public void setMixerHalfOpenValue(int mixerHalfOpenValue) {
        this.mixerHalfOpenValue = mixerHalfOpenValue;
    }

    public void setMixerOpenValue(int mixerOpenValue) {
        this.mixerOpenValue = mixerOpenValue;
    }

    public void setAutoDropChckerValue(int autoDropChckerValue) {
        this.autoDropChckerValue = autoDropChckerValue;
    }

    public void setDropImpulseChckerValue(int dropImpulseChckerValue) {
        this.dropImpulseChckerValue = dropImpulseChckerValue;
    }

    public void setDoseHopper11Value(float doseHopper11Value) {
        this.doseHopper11Value = doseHopper11Value;
    }

    public void setDoseHopper12Value(float doseHopper12Value) {
        this.doseHopper12Value = doseHopper12Value;
    }

    public void setDoseHopper21Value(float doseHopper21Value) {
        this.doseHopper21Value = doseHopper21Value;
    }

    public void setDoseHopper22Value(float doseHopper22Value) {
        this.doseHopper22Value = doseHopper22Value;
    }

    public void setDoseHopper31Value(float doseHopper31Value) {
        this.doseHopper31Value = doseHopper31Value;
    }

    public void setDoseHopper32Value(float doseHopper32Value) {
        this.doseHopper32Value = doseHopper32Value;
    }

    public void setDoseHopper41Value(float doseHopper41Value) {
        this.doseHopper41Value = doseHopper41Value;
    }

    public void setDoseHopper42Value(float doseHopper42Value) {
        this.doseHopper42Value = doseHopper42Value;
    }

    public void setDoseWaterValue(float doseWaterValue) {
        this.doseWaterValue = doseWaterValue;
    }

    public void setDoseSilos1Value(float doseSilos1Value) {
        this.doseSilos1Value = doseSilos1Value;
    }

    public void setDoseSilos2Value(float doseSilos2Value) {
        this.doseSilos2Value = doseSilos2Value;
    }

    public void setDoseChemy1Value(float doseChemy1Value) {
        this.doseChemy1Value = doseChemy1Value;
    }

    public void setDoseChemy2Value(float doseChemy2Value) {
        this.doseChemy2Value = doseChemy2Value;
    }

    public void setVibroSilos1Value(int vibroSilos1Value) {
        this.vibroSilos1Value = vibroSilos1Value;
    }

    public void setMixingCapacity(float mixingCapacity) {
        this.mixingCapacity = mixingCapacity;
    }

    public void setMixCounterValue(int mixCounterValue) {
        this.mixCounterValue = mixCounterValue;
    }

    public void setAmperageMixerValue(float amperageMixerValue) {
        this.amperageMixerValue = amperageMixerValue;
    }

    public void setTotalMixCounterValue(int totalMixCounterValue) {
        this.totalMixCounterValue = totalMixCounterValue;
    }

    public void setWeightsReadyReadValue(int weightsReadyReadValue) {
        this.weightsReadyReadValue = weightsReadyReadValue;
    }

    public void setVibroColdBunckersIndicationValue(int vibroColdBunckersIndicationValue) {
        this.vibroColdBunckersIndicationValue = vibroColdBunckersIndicationValue;
    }

    public void setFirmwareVersionValue(float firmwareVersionValue) {
        this.firmwareVersionValue = firmwareVersionValue;
    }

    public void setChemy2PumpOnIndValue(int chemy2PumpOnIndValue) {
        this.chemy2PumpOnIndValue = chemy2PumpOnIndValue;
    }

    public void setChemy2RecipeValue(float chemy2RecipeValue) {
        this.chemy2RecipeValue = chemy2RecipeValue;
    }

    public void setSilosSelectorValue(int silosSelectorValue) {
        this.silosSelectorValue = silosSelectorValue;
    }

    public void setVibroDispenserDCValue(int vibroDispenserDCValue) {
        this.vibroDispenserDCValue = vibroDispenserDCValue;
    }

    public void setVibroBuncker11OptionValue(int vibroBuncker11OptionValue) {
        this.vibroBuncker11OptionValue = vibroBuncker11OptionValue;
    }

    public void setVibroBuncker12OptionValue(int vibroBuncker12OptionValue) {
        this.vibroBuncker12OptionValue = vibroBuncker12OptionValue;
    }

    public void setVibroBuncker21OptionValue(int vibroBuncker21OptionValue) {
        this.vibroBuncker21OptionValue = vibroBuncker21OptionValue;
    }

    public void setVibroBuncker22OptionValue(int vibroBuncker22OptionValue) {
        this.vibroBuncker22OptionValue = vibroBuncker22OptionValue;
    }

    public void setVibroBuncker31OptionValue(int vibroBuncker31OptionValue) {
        this.vibroBuncker31OptionValue = vibroBuncker31OptionValue;
    }

    public void setVibroBuncker32OptionValue(int vibroBuncker32OptionValue) {
        this.vibroBuncker32OptionValue = vibroBuncker32OptionValue;
    }

    public void setVibroBuncker41OptionValue(int vibroBuncker41OptionValue) {
        this.vibroBuncker41OptionValue = vibroBuncker41OptionValue;
    }

    public void setVibroBuncker42OptionValue(int vibroBuncker42OptionValue) {
        this.vibroBuncker42OptionValue = vibroBuncker42OptionValue;
    }

    public int isSelfDKStateValue() {
        return selfDKStateValue;
    }

    public int isSelfDChStateValue() {
        return selfDChStateValue;
    }

    public int isSelfDCStateValue() {
        return selfDCStateValue;
    }

    public int isSelfDWStateValue() {
        return selfDWStateValue;
    }

    public void setSelfDKStateValue(int selfDKStateValue) {
        this.selfDKStateValue = selfDKStateValue;
    }

    public void setSelfDChStateValue(int selfDChStateValue) {
        this.selfDChStateValue = selfDChStateValue;
    }

    public void setSelfDCStateValue(int selfDCStateValue) {
        this.selfDCStateValue = selfDCStateValue;
    }

    public void setSelfDWStateValue(int selfDWStateValue) {
        this.selfDWStateValue = selfDWStateValue;
    }

    public int isSkipAlarmIndicatorValue() {
        return skipAlarmIndicatorValue;
    }

    public int isVerticalConveyorIndValue() {
        return verticalConveyorIndValue;
    }

    public void setVerticalConveyorIndValue(int verticalConveyorIndValue) {
        this.verticalConveyorIndValue = verticalConveyorIndValue;
    }

    public void setSkipAlarmIndicatorValue(int skipAlarmIndicatorValue) {
        this.skipAlarmIndicatorValue = skipAlarmIndicatorValue;
    }

    public int getConveyorDropValue() {
        return conveyorDropValue;
    }

    public void setConveyorDropValue(int conveyorDropValue) {
        this.conveyorDropValue = conveyorDropValue;
    }

    public int getFibraVibratorIndicatorValue() {
        return fibraVibratorIndicatorValue;
    }

    public void setFibraVibratorIndicatorValue(int fibraVibratorIndicatorValue) {
        this.fibraVibratorIndicatorValue = fibraVibratorIndicatorValue;
    }

    public float getFibraWeightSensorValue() {
        return fibraWeightSensorValue;
    }

    public int getSkipSensorUp1Value() {
        return skipSensorUp1Value;
    }

    public void setSkipSensorUp1Value(int skipSensorUp1Value) {
        this.skipSensorUp1Value = skipSensorUp1Value;
    }

    public int getSkipSensorUp2Value() {
        return skipSensorUp2Value;
    }

    public void setSkipSensorUp2Value(int skipSensorUp2Value) {
        this.skipSensorUp2Value = skipSensorUp2Value;
    }

    public int getSkipSensorDown1Value() {
        return skipSensorDown1Value;
    }

    public void setSkipSensorDown1Value(int skipSensorDown1Value) {
        this.skipSensorDown1Value = skipSensorDown1Value;
    }

    public int getSkipSensorDown2Value() {
        return skipSensorDown2Value;
    }

    public void setSkipSensorDown2Value(int skipSensorDown2Value) {
        this.skipSensorDown2Value = skipSensorDown2Value;
    }

    public float getDoseWater2Value() {
        return doseWater2Value;
    }

    public void setDoseWater2Value(float doseWater2Value) {
        this.doseWater2Value = doseWater2Value;
    }

    public int getReverseDKValue() {
        return reverseDKValue;
    }

    public int getUploadConveyorDKValue() {
        return uploadConveyorDKValue;
    }

    public void setReverseDKValue(int reverseDKValue) {
        this.reverseDKValue = reverseDKValue;
    }

    public void setUploadConveyorDKValue(int uploadConveyorDKValue) {
        this.uploadConveyorDKValue = uploadConveyorDKValue;
    }

    public int getMotoClockHorConveyorValue() {
        return motoClockHorConveyorValue;
    }

    public int getMotoClockVertConvValue() {
        return motoClockVertConvValue;
    }

    public int getMotoClockSkipValue() {
        return motoClockSkipValue;
    }

    public int getMotoClockMixerValue() {
        return motoClockMixerValue;
    }

    public int getMotoClockShnek1Value() {
        return motoClockShnek1Value;
    }

    public int getMotoClockShnek2Value() {
        return motoClockShnek2Value;
    }

    public int getMotoClockShnek3Value() {
        return motoClockShnek3Value;
    }

    public int getMotoClockValveWaterValue() {
        return motoClockValveWaterValue;
    }

    public int getMotoClockPumpWaterValue() {
        return motoClockPumpWaterValue;
    }

    public int getMotoClockPumpChemy1Value() {
        return motoClockPumpChemy1Value;
    }

    public int getMotoClockPumpChemy2Value() {
        return motoClockPumpChemy2Value;
    }

    public int getMotoClockOilStationValue() {
        return motoClockOilStationValue;
    }

    public float getSensorHumidityValue() {
        return sensorHumidityValue;
    }

    public void setSensorHumidityValue(float sensorHumidityValue) {
        this.sensorHumidityValue = sensorHumidityValue;
    }

    public int getWaterPumpCounterValue() {
        return waterPumpCounterValue;
    }

    public void setWaterPumpCounterValue(int waterPumpCounterValue) {
        this.waterPumpCounterValue = waterPumpCounterValue;
    }

    public int getWaterPumpHumCorrectionControlValue() {
        return waterPumpHumCorrectionControlValue;
    }

    public void setWaterPumpHumCorrectionControlValue(int waterPumpHumCorrectionControlValue) {
        this.waterPumpHumCorrectionControlValue = waterPumpHumCorrectionControlValue;
    }

    public int getSkipAlarmIndicatorValue() {
        return skipAlarmIndicatorValue;
    }

    public int getVerticalConveyorIndValue() {
        return verticalConveyorIndValue;
    }

    public int getAlarmMixerShiberOpennedValue() {
        return alarmMixerShiberOpennedValue;
    }

    public int getAlarmMixerThermalProtectionValue() {
        return alarmMixerThermalProtectionValue;
    }

    public int getAlarmSkipThermalProtectionValue() {
        return alarmSkipThermalProtectionValue;
    }

    public int getAlarmDKThermalProtectionValue() {
        return alarmDKThermalProtectionValue;
    }

    public int getAlarmMixerWindowOpennedValue() {
        return alarmMixerWindowOpennedValue;
    }

    public int getAlarmTimeDosingDoser11FaultValue() {
        return alarmTimeDosingDoser11FaultValue;
    }

    public int getAlarmTimeDosingDoser12FaultValue() {
        return alarmTimeDosingDoser12FaultValue;
    }

    public int getAlarmTimeDosingDoser21FaultValue() {
        return alarmTimeDosingDoser21FaultValue;
    }

    public int getAlarmTimeDosingDoser22FaultValue() {
        return alarmTimeDosingDoser22FaultValue;
    }

    public int getAlarmTimeDosingDoser31FaultValue() {
        return alarmTimeDosingDoser31FaultValue;
    }

    public int getAlarmTimeDosingDoser32FaultValue() {
        return alarmTimeDosingDoser32FaultValue;
    }

    public int getAlarmTimeDosingDoser41FaultValue() {
        return alarmTimeDosingDoser41FaultValue;
    }

    public int getAlarmTimeDosingDoser42FaultValue() {
        return alarmTimeDosingDoser42FaultValue;
    }

    public int getWarningAutoModeNotActivatedValue() {
        return warningAutoModeNotActivatedValue;
    }

    public int getWarningMixerNotEmptyValue() {
        return warningMixerNotEmptyValue;
    }

    public int getWarningAutoDropDisableValue() {
        return warningAutoDropDisableValue;
    }

    public int getAlarmWeightDKNotEmptyValue() {
        return alarmWeightDKNotEmptyValue;
    }

    public int getAlarmDCWeightNotEmptyValue() {
        return alarmDCWeightNotEmptyValue;
    }

    public int getAlarmDWWeightNotEmptyValue() {
        return alarmDWWeightNotEmptyValue;
    }

    public int getAlarmDChWeightNotEmptyValue() {
        return alarmDChWeightNotEmptyValue;
    }

    public int getAlarmDKCalibrateErrorValue() {
        return alarmDKCalibrateErrorValue;
    }

    public int getAlarmDCCalibrateErrorValue() {
        return alarmDCCalibrateErrorValue;
    }

    public int getAlarmDWCalibrateErrorValue() {
        return alarmDWCalibrateErrorValue;
    }

    public int getAlarmDChCalibrateErrorValue() {
        return alarmDChCalibrateErrorValue;
    }

    public int getAlarmShnekThermalDefenceValue() {
        return alarmShnekThermalDefenceValue;
    }

    public int getAlarmMixerDropErrorValue() {
        return alarmMixerDropErrorValue;
    }

    public int getWarningAutoPowerOffMixerDisabledValue() {
        return warningAutoPowerOffMixerDisabledValue;
    }

    public int getAlarmMixerEngineNotStartedValue() {
        return alarmMixerEngineNotStartedValue;
    }

    public int getWarningConveyorUploadNotStartedValue() {
        return warningConveyorUploadNotStartedValue;
    }

    public int getAlarmOverflowChemyValue() {
        return alarmOverflowChemyValue;
    }

    public int getAlarmOverflowWaterValue() {
        return alarmOverflowWaterValue;
    }

    public int getAlarmOverFlowDKValue() {
        return alarmOverFlowDKValue;
    }

    public int getAlarmMixerCloseErrorStopSkipValue() {
        return alarmMixerCloseErrorStopSkipValue;
    }

    public int getAlarmDCShiberErrorValue() {
        return alarmDCShiberErrorValue;
    }

    public int getAlarmSkipDoubleSensorCrashValue() {
        return alarmSkipSensorValue;
    }

    @Override
    public String toString() {
        return "ReflectionRetrieval{" +
                "manualAutoModeValue=" + manualAutoModeValue +
                ", pendingProductionStateValue=" + pendingProductionStateValue +
                ", globalCrashFlagValue=" + globalCrashFlagValue +
                ", hopper11FlapOpenIndValue=" + hopper11FlapOpenIndValue +
                ", hopper12FlapOpenIndValue=" + hopper12FlapOpenIndValue +
                ", hopper21FlapOpenIndValue=" + hopper21FlapOpenIndValue +
                ", hopper22FlapOpenIndValue=" + hopper22FlapOpenIndValue +
                ", hopper31FlapOpenIndValue=" + hopper31FlapOpenIndValue +
                ", hopper32FlapOpenIndValue=" + hopper32FlapOpenIndValue +
                ", hopper41FlapOpenIndValue=" + hopper41FlapOpenIndValue +
                ", hopper42FlapOpenIndValue=" + hopper42FlapOpenIndValue +
                ", waterDisFlapOpenIndValue=" + waterDisFlapOpenIndValue +
                ", cementDisFlapOpenIndValue=" + cementDisFlapOpenIndValue +
                ", cementDisFlapOpenPosIndValue=" + cementDisFlapOpenPosIndValue +
                ", cementDisFlapClosePosIndValue=" + cementDisFlapClosePosIndValue +
                ", chemyDisFlapOpenIndValue=" + chemyDisFlapOpenIndValue +
                ", horConveyorOnIndValue=" + horConveyorOnIndValue +
                ", waterPumpOnIndValue=" + waterPumpOnIndValue +
                ", valveWaterBunckerValue=" + valveWaterBunckerValue +
                ", chemy1PumpOnIndValue=" + chemy1PumpOnIndValue +
                ", chemy2PumpOnIndValue=" + chemy2PumpOnIndValue +
                ", cement1AugerOnIndValue=" + cement1AugerOnIndValue +
                ", cement2AugerOnIndValue=" + cement2AugerOnIndValue +
                ", silosCementFilterValue=" + silosCementFilterValue +
                ", cement1SiloLevelSensorUpValue=" + cement1SiloLevelSensorUpValue +
                ", cement1SiloLevelSensorDownValue=" + cement1SiloLevelSensorDownValue +
                ", cement2SiloLevelSensorUpValue=" + cement2SiloLevelSensorUpValue +
                ", cement2SiloLevelSensorDownValue=" + cement2SiloLevelSensorDownValue +
                ", waterOverflowSensorValue=" + waterOverflowSensorValue +
                ", chemyOverflowSensorValue=" + chemyOverflowSensorValue +
                ", skipPosEndSensorUpValue=" + skipPosEndSensorUpValue +
                ", skipPosEndSensorDownValue=" + skipPosEndSensorDownValue +
                ", skipPosEndSensorCrashUpValue=" + skipPosEndSensorCrashUpValue +
                ", skipPosEndSensorCrashDownValue=" + skipPosEndSensorCrashDownValue +
                ", skipMoveUpValue=" + skipMoveUpValue +
                ", skipMoveDownValue=" + skipMoveDownValue +
                ", aerationOnIndicationValue=" + aerationOnIndicationValue +
                ", mixerRollersWorkIndicationValue=" + mixerRollersWorkIndicationValue +
                ", mixerWindowViewOpenSensorValue=" + mixerWindowViewOpenSensorValue +
                ", mixerNotEmptyValue=" + mixerNotEmptyValue +
                ", hopper11RecipeValue=" + hopper11RecipeValue +
                ", hopper12RecipeValue=" + hopper12RecipeValue +
                ", hopper21RecipeValue=" + hopper21RecipeValue +
                ", hopper22RecipeValue=" + hopper22RecipeValue +
                ", hopper31RecipeValue=" + hopper31RecipeValue +
                ", hopper32RecipeValue=" + hopper32RecipeValue +
                ", hopper41RecipeValue=" + hopper41RecipeValue +
                ", hopper42RecipeValue=" + hopper42RecipeValue +
                ", waterRecipeValue=" + waterRecipeValue +
                ", chemy1RecipeValue=" + chemy1RecipeValue +
                ", chemy2RecipeValue=" + chemy2RecipeValue +
                ", cement1RecipeValue=" + cement1RecipeValue +
                ", cement2RecipeValue=" + cement2RecipeValue +
                ", shortageHopper11Value=" + shortageHopper11Value +
                ", shortageHopper12Value=" + shortageHopper12Value +
                ", shortageHopper21Value=" + shortageHopper21Value +
                ", shortageHopper22Value=" + shortageHopper22Value +
                ", shortageHopper31Value=" + shortageHopper31Value +
                ", shortageHopper32Value=" + shortageHopper32Value +
                ", shortageHopper41Value=" + shortageHopper41Value +
                ", shortageHopper42Value=" + shortageHopper42Value +
                ", shortageWaterValue=" + shortageWaterValue +
                ", shortageChemy1Value=" + shortageChemy1Value +
                ", shortageSilos1Value=" + shortageSilos1Value +
                ", shortageSilos2Value=" + shortageSilos2Value +
                ", volumeCurrentMixerLoadValue=" + volumeCurrentMixerLoadValue +
                ", weightCubeConcreteValue=" + weightCubeConcreteValue +
                ", batchVolumeValue=" + batchVolumeValue +
                ", mixingTimeValue=" + mixingTimeValue +
                ", horConveyorStartTimerValue=" + horConveyorStartTimerValue +
                ", currentWeightDKValue=" + currentWeightDKValue +
                ", currentWeightWaterValue=" + currentWeightWaterValue +
                ", currentWeightChemyValue=" + currentWeightChemyValue +
                ", currentWeightCementValue=" + currentWeightCementValue +
                ", countdownTimeMixValue=" + countdownTimeMixValue +
                ", mixerCloseValue=" + mixerCloseValue +
                ", mixerHalfOpenValue=" + mixerHalfOpenValue +
                ", mixerOpenValue=" + mixerOpenValue +
                ", autoDropChckerValue=" + autoDropChckerValue +
                ", dropImpulseChckerValue=" + dropImpulseChckerValue +
                ", doseHopper11Value=" + doseHopper11Value +
                ", doseHopper12Value=" + doseHopper12Value +
                ", doseHopper21Value=" + doseHopper21Value +
                ", doseHopper22Value=" + doseHopper22Value +
                ", doseHopper31Value=" + doseHopper31Value +
                ", doseHopper32Value=" + doseHopper32Value +
                ", doseHopper41Value=" + doseHopper41Value +
                ", doseHopper42Value=" + doseHopper42Value +
                ", doseWaterValue=" + doseWaterValue +
                ", doseWater2Value=" + doseWater2Value +
                ", doseSilos1Value=" + doseSilos1Value +
                ", doseSilos2Value=" + doseSilos2Value +
                ", doseChemy1Value=" + doseChemy1Value +
                ", doseChemy2Value=" + doseChemy2Value +
                ", vibroSilos1Value=" + vibroSilos1Value +
                ", vibroDispenserDCValue=" + vibroDispenserDCValue +
                ", mixingCapacity=" + mixingCapacity +
                ", mixCounterValue=" + mixCounterValue +
                ", amperageMixerValue=" + amperageMixerValue +
                ", totalMixCounterValue=" + totalMixCounterValue +
                ", vibroBuncker11OptionValue=" + vibroBuncker11OptionValue +
                ", vibroBuncker12OptionValue=" + vibroBuncker12OptionValue +
                ", vibroBuncker21OptionValue=" + vibroBuncker21OptionValue +
                ", vibroBuncker22OptionValue=" + vibroBuncker22OptionValue +
                ", vibroBuncker31OptionValue=" + vibroBuncker31OptionValue +
                ", vibroBuncker32OptionValue=" + vibroBuncker32OptionValue +
                ", vibroBuncker41OptionValue=" + vibroBuncker41OptionValue +
                ", vibroBuncker42OptionValue=" + vibroBuncker42OptionValue +
                ", weightsReadyReadValue=" + weightsReadyReadValue +
                ", vibroColdBunckersIndicationValue=" + vibroColdBunckersIndicationValue +
                ", firmwareVersionValue=" + firmwareVersionValue +
                ", fibraWeightSensorValue=" + fibraWeightSensorValue +
                ", selfDKStateValue=" + selfDKStateValue +
                ", selfDChStateValue=" + selfDChStateValue +
                ", selfDCStateValue=" + selfDCStateValue +
                ", selfDWStateValue=" + selfDWStateValue +
                ", silosSelectorValue=" + silosSelectorValue +
                ", skipAlarmIndicatorValue=" + skipAlarmIndicatorValue +
                ", verticalConveyorIndValue=" + verticalConveyorIndValue +
                ", fibraVibratorIndicatorValue=" + fibraVibratorIndicatorValue +
                ", conveyorDropValue=" + conveyorDropValue +
                ", skipSensorUp1Value=" + skipSensorUp1Value +
                ", skipSensorUp2Value=" + skipSensorUp2Value +
                ", skipSensorDown1Value=" + skipSensorDown1Value +
                ", skipSensorDown2Value=" + skipSensorDown2Value +
                ", reverseDKValue=" + reverseDKValue +
                ", uploadConveyorDKValue=" + uploadConveyorDKValue +
                ", motoClockHorConveyorValue=" + motoClockHorConveyorValue +
                ", motoClockVertConvValue=" + motoClockVertConvValue +
                ", motoClockSkipValue=" + motoClockSkipValue +
                ", motoClockMixerValue=" + motoClockMixerValue +
                ", motoClockShnek1Value=" + motoClockShnek1Value +
                ", motoClockShnek2Value=" + motoClockShnek2Value +
                ", motoClockShnek3Value=" + motoClockShnek3Value +
                ", motoClockValveWaterValue=" + motoClockValveWaterValue +
                ", motoClockPumpWaterValue=" + motoClockPumpWaterValue +
                ", motoClockPumpChemy1Value=" + motoClockPumpChemy1Value +
                ", motoClockPumpChemy2Value=" + motoClockPumpChemy2Value +
                ", motoClockOilStationValue=" + motoClockOilStationValue +
                ", sensorHumidityValue=" + sensorHumidityValue +
                ", waterPumpCounterValue=" + waterPumpCounterValue +
                ", waterPumpHumCorrectionControlValue=" + waterPumpHumCorrectionControlValue +
                '}';
    }
}
