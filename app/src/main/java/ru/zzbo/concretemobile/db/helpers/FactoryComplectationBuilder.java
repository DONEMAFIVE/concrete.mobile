package ru.zzbo.concretemobile.db.helpers;

import java.util.List;

import ru.zzbo.concretemobile.models.MasterFactoryComplectation;
import ru.zzbo.concretemobile.models.Parameter;

public class FactoryComplectationBuilder {

    public MasterFactoryComplectation parseList(List<Parameter> parameters){

        MasterFactoryComplectation result = new MasterFactoryComplectation();

        for (Parameter parameter : parameters){

            if (parameter.getParameter().equals("inertBunckerCounter")) result.setInertBunckerCounter(Integer.parseInt(parameter.getValue()));
            if (parameter.getParameter().equals("comboBunckerOption")) result.setComboBunckerOption(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("silosCounter")) result.setSilosCounter(Integer.parseInt(parameter.getValue()));
            if (parameter.getParameter().equals("water2")) result.setWater2(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("humidityMixerSensor")) result.setHumidityMixerSensor(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("chemyCounter")) result.setChemyCounter(Integer.parseInt(parameter.getValue()));
            if (parameter.getParameter().equals("transporterType")) result.setTransporterType(Integer.parseInt(parameter.getValue()));
            if (parameter.getParameter().equals("fibraOption")) result.setFibraOption(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("dropConveyor")) result.setDropConveyor(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("mixCapacity")) result.setMixCapacity(Float.parseFloat(parameter.getValue()));
            if (parameter.getParameter().equals("hydroGate")) result.setHydroGate(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("amperageSensor")) result.setAmperageSensor(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("dwpl")) result.setDwpl(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("dDryCHDispenser")) result.setdDryCHDispenser(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("vibroFunnelMixer")) result.setVibroFunnelMixer(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("humSensorInert")) result.setHumSensorInert(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("planetarTypeMixer")) result.setPlanetarTypeMixer(Boolean.parseBoolean(parameter.getValue()));
            if (parameter.getParameter().equals("reverseModuleFactory")) result.setReverseModuleFactory(Boolean.parseBoolean(parameter.getValue()));

        }

        return result;

    };

}
