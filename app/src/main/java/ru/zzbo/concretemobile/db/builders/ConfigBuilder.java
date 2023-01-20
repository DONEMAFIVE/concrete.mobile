package ru.zzbo.concretemobile.db.builders;

import java.util.List;

import ru.zzbo.concretemobile.models.Configs;
import ru.zzbo.concretemobile.models.Parameter;

public class ConfigBuilder {

    public Configs buildScadaParameters(List<Parameter> parameters){

        Configs scadaConfigs = new Configs();

        for (Parameter current : parameters){

            if (current.getParameter().equals("plc_ip")) scadaConfigs.setPlcIP(current.getValue());
            if (current.getParameter().equals("plc_port")) scadaConfigs.setPlcPort(current.getValue());
            if (current.getParameter().equals("first_run")) scadaConfigs.setFirstRun(current.getValue());
            if (current.getParameter().equals("yandex_option")) scadaConfigs.setYandexOption(current.getValue());
            if (current.getParameter().equals("time_sync")) scadaConfigs.setTimeSync(current.getValue());
            if (current.getParameter().equals("server_update")) scadaConfigs.setServerUpdate(current.getValue());
            if (current.getParameter().equals("localization_level")) scadaConfigs.setLocalizationLevel(current.getValue());
            if (current.getParameter().equals("hardkey")) scadaConfigs.setHardKey(current.getValue());
            if (current.getParameter().equals("productionNumber")) scadaConfigs.setProductionNumber(current.getValue());
            if (current.getParameter().equals("exchange_level")) scadaConfigs.setExchangeLevel(current.getValue());
            if (current.getParameter().equals("scada_ip")) scadaConfigs.setScadaIP(current.getValue());
            if (current.getParameter().equals("rest_server_ip")) scadaConfigs.setRestServerIP(current.getValue());

        }

        return scadaConfigs;
    }
}
