package ru.zzbo.concretemobile.db.helpers;

import java.util.List;

import ru.zzbo.concretemobile.models.DroidConfig;
import ru.zzbo.concretemobile.models.Parameter;

public class ConfigBuilder {

    public DroidConfig buildScadaParameters(List<Parameter> parameters){

        DroidConfig scadaDroidConfig = new DroidConfig();

        for (Parameter current : parameters){

            if (current.getParameter().equals("plc_ip")) scadaDroidConfig.setPlcIP(current.getValue());
            if (current.getParameter().equals("plc_port")) scadaDroidConfig.setPlcPort(current.getValue());
            if (current.getParameter().equals("first_run")) scadaDroidConfig.setFirstRun(current.getValue());
            if (current.getParameter().equals("yandex_option")) scadaDroidConfig.setYandexOption(current.getValue());
            if (current.getParameter().equals("time_sync")) scadaDroidConfig.setTimeSync(current.getValue());
            if (current.getParameter().equals("server_update")) scadaDroidConfig.setServerUpdate(current.getValue());
            if (current.getParameter().equals("localization_level")) scadaDroidConfig.setLocalizationLevel(current.getValue());
            if (current.getParameter().equals("hardkey")) scadaDroidConfig.setHardKey(current.getValue());
            if (current.getParameter().equals("productionNumber")) scadaDroidConfig.setProductionNumber(current.getValue());
            if (current.getParameter().equals("exchange_level")) scadaDroidConfig.setExchangeLevel(current.getValue());
            if (current.getParameter().equals("scada_ip")) scadaDroidConfig.setScadaIP(current.getValue());
            if (current.getParameter().equals("rest_server_ip")) scadaDroidConfig.setRestServerIP(current.getValue());
            if (current.getParameter().equals("hmi_ip")) scadaDroidConfig.setHmiIP(current.getValue());

            if (current.getParameter().equals("buncker11")) scadaDroidConfig.setBuncker11(current.getValue());
            if (current.getParameter().equals("buncker12")) scadaDroidConfig.setBuncker12(current.getValue());
            if (current.getParameter().equals("buncker21")) scadaDroidConfig.setBuncker21(current.getValue());
            if (current.getParameter().equals("buncker22")) scadaDroidConfig.setBuncker22(current.getValue());
            if (current.getParameter().equals("buncker31")) scadaDroidConfig.setBuncker31(current.getValue());
            if (current.getParameter().equals("buncker32")) scadaDroidConfig.setBuncker32(current.getValue());
            if (current.getParameter().equals("buncker41")) scadaDroidConfig.setBuncker41(current.getValue());
            if (current.getParameter().equals("buncker42")) scadaDroidConfig.setBuncker42(current.getValue());

            if (current.getParameter().equals("chemy1")) scadaDroidConfig.setChemy1(current.getValue());
            if (current.getParameter().equals("chemy2")) scadaDroidConfig.setChemy2(current.getValue());
            if (current.getParameter().equals("chemy3")) scadaDroidConfig.setChemy3(current.getValue());

            if (current.getParameter().equals("silos1")) scadaDroidConfig.setSilos1(current.getValue());
            if (current.getParameter().equals("silos2")) scadaDroidConfig.setSilos2(current.getValue());
            if (current.getParameter().equals("silos3")) scadaDroidConfig.setSilos3(current.getValue());
            if (current.getParameter().equals("silos4")) scadaDroidConfig.setSilos4(current.getValue());

            if (current.getParameter().equals("water1")) scadaDroidConfig.setWater1(current.getValue());
            if (current.getParameter().equals("water2")) scadaDroidConfig.setWater2(current.getValue());

        }

        return scadaDroidConfig;
    }
}
