package ru.zzbo.concretemobile.utils;
import static ru.zzbo.concretemobile.utils.Constants.retrieval;

public class AlarmUtil {


    public static String getAlarms() {
        String alarms = "";

        if (retrieval.getAlarmMixerShiberOpennedValue() == 1) alarms += "[Авария] - смеситель не закрыт\n";
        if (retrieval.getAlarmMixerThermalProtectionValue() == 1) alarms += "[Авария] - сработала тепловая защита смесителя\n";
        if (retrieval.getAlarmSkipThermalProtectionValue() == 1) alarms += "[Авария] - сработала тепловая защита скипа\n";
        if (retrieval.getAlarmDKThermalProtectionValue() == 1) alarms += "[Авария] - сработала тепловая защита конвейера \n";
        if (retrieval.getAlarmMixerWindowOpennedValue() == 1) alarms += "[Авария] - Крышка смотрового окна не закрыта\n";
        if (retrieval.getAlarmTimeDosingDoser11FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 1-1\n";
        if (retrieval.getAlarmTimeDosingDoser12FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 1-2\n";
        if (retrieval.getAlarmTimeDosingDoser21FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 2-1\n";
        if (retrieval.getAlarmTimeDosingDoser22FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 2-2\n";
        if (retrieval.getAlarmTimeDosingDoser31FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 3-1\n";
        if (retrieval.getAlarmTimeDosingDoser32FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 3-2\n";
        if (retrieval.getAlarmTimeDosingDoser41FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 4-1\n";
        if (retrieval.getAlarmTimeDosingDoser42FaultValue() == 1) alarms += "[Авария] - Превышено время дозирования бункера 4-2\n";
        if (retrieval.getWarningAutoModeNotActivatedValue() == 1) alarms += "[Предупреждение] - не включен автоматический режим\n";
        if (retrieval.getWarningMixerNotEmptyValue() == 1) alarms += "[Предупреждение] - смеситель не пуст\n";
        if (retrieval.getWarningAutoDropDisableValue() == 1) alarms += "[Предупреждение] - авторазгрузка выключена\n";
        if (retrieval.getAlarmWeightDKNotEmptyValue() == 1) alarms += "[Авария] - Дозирующий комплекс не пустой, очистите или выполните калибровку весов\n";
        if (retrieval.getAlarmDCWeightNotEmptyValue() == 1) alarms += "[Авария] - Дозатор цемента не пустой, очистите или выполните калибровку весов\n";
        if (retrieval.getAlarmDWWeightNotEmptyValue() == 1) alarms += "[Авария] - Дозатор воды не пустой, очистите или выполните калибровку весов\n";
        if (retrieval.getAlarmDChWeightNotEmptyValue() == 1) alarms += "[Авария] - Дозатор химии не пустой, очистите или выполните калибровку весов\n";
        if (retrieval.getAlarmDKCalibrateErrorValue() == 1) alarms += "[Авария] - Дозирующий комплекс отсутствует калибровка или неисправны весы\n";
        if (retrieval.getAlarmDCCalibrateErrorValue() == 1) alarms += "[Авария] - Дозатор цемента отсутствует калибровка или неисправны весы\n";
        if (retrieval.getAlarmDWCalibrateErrorValue() == 1) alarms += "[Авария] - Дозатор воды отсутствует калибровка или неисправны весы\n";
        if (retrieval.getAlarmDChCalibrateErrorValue() == 1) alarms += "[Авария] - Дозатор химии отсутствует калибровка или неисправны весы\n";
        if (retrieval.getAlarmShnekThermalDefenceValue() == 1) alarms += "[Авария] - Сработала тепловая защита шнека\n";
        if (retrieval.getAlarmMixerDropErrorValue() == 1) alarms += "[Авария] - Не открылся смеситель\n";
        if (retrieval.getWarningAutoPowerOffMixerDisabledValue() == 1) alarms += "[Предупреждение] - Автоотключение смесителя после окончания работы в автомате\n";
        if (retrieval.getAlarmMixerEngineNotStartedValue() == 1) alarms += "[Авария] - Смеситель не запустился\n";
        if (retrieval.getWarningConveyorUploadNotStartedValue() == 1) alarms += "[Предупреждение] - Не включен конвейер выгрузки\n";
        if (retrieval.getAlarmOverflowChemyValue() == 1) alarms += "[Авария] - Перелив химии\n";
        if (retrieval.getAlarmOverflowWaterValue() == 1) alarms += "[Авария] - Перелив воды\n";
        if (retrieval.getAlarmOverFlowDKValue() == 1) alarms += "[Внимание] - ДК Готов к разгрузке, скип не внизу\n";
        if (retrieval.getAlarmMixerCloseErrorStopSkipValue() == 1) alarms += "[Авария] - Смеситель не закрылся скип остановлен\n";
        if (retrieval.getAlarmDCShiberErrorValue() == 1) alarms += "[Предупреждение] - Дозатор цемента не закрыт\n";
        if (retrieval.getAlarmSkipDoubleSensorCrashValue() == 1) alarms += "[Авария] - Сработали верхний и нижний концевые скипа\n";

        return alarms;
    }
}
