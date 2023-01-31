package ru.zzbo.concretemobile.db.builders;

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

        }

        return scadaDroidConfig;
    }
}
