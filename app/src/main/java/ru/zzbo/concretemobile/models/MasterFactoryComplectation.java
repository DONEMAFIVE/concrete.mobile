package ru.zzbo.concretemobile.models;

public class MasterFactoryComplectation {

    int inertBunckerCounter;
    boolean comboBunckerOption;
    int silosCounter;
    boolean water2;
    boolean humidityMixerSensor;
    int chemyCounter;
    int transporterType;
    boolean fibraOption;
    boolean dropConveyor;
    float mixCapacity;
    boolean hydroGate;
    boolean amperageSensor;
    boolean dwpl;
    boolean dDryCHDispenser;
    boolean vibroFunnelMixer;
    boolean humSensorInert;
    boolean planetarTypeMixer;
    boolean reverseModuleFactory;

    public MasterFactoryComplectation() {}

    public MasterFactoryComplectation(int inertBunckerCounter, boolean comboBunckerOption,
                                      int silosCounter, boolean water2, boolean humidityMixerSensor,
                                      int chemyCounter, int transporterType, boolean fibraOption,
                                      boolean dropConveyor, float mixCapacity, boolean hydroGate,
                                      boolean amperageSensor, boolean dwpl, boolean dDryCHDispenser,
                                      boolean vibroFunnelMixer, boolean humSensorInert, boolean planetarTypeMixer,
                                      boolean reverseModuleFactory
    ) {
        this.inertBunckerCounter = inertBunckerCounter;
        this.comboBunckerOption = comboBunckerOption;
        this.silosCounter = silosCounter;
        this.water2 = water2;
        this.humidityMixerSensor = humidityMixerSensor;
        this.chemyCounter = chemyCounter;
        this.transporterType = transporterType;
        this.fibraOption = fibraOption;
        this.dropConveyor = dropConveyor;
        this.mixCapacity = mixCapacity;
        this.hydroGate = hydroGate;
        this.amperageSensor = amperageSensor;
        this.dwpl = dwpl;
        this.dDryCHDispenser = dDryCHDispenser;
        this.vibroFunnelMixer = vibroFunnelMixer;
        this.humSensorInert = humSensorInert;
        this.planetarTypeMixer = planetarTypeMixer;
        this.reverseModuleFactory = reverseModuleFactory;
    }

    public boolean isdDryCHDispenser() {
        return dDryCHDispenser;
    }

    public void setdDryCHDispenser(boolean dDryCHDispenser) {
        this.dDryCHDispenser = dDryCHDispenser;
    }

    public int getInertBunckerCounter() {
        return inertBunckerCounter;
    }

    public boolean isComboBunckerOption() {
        return comboBunckerOption;
    }

    public int getSilosCounter() {
        return silosCounter;
    }

    public boolean isWater2() {
        return water2;
    }

    public boolean isHumidityMixerSensor() {
        return humidityMixerSensor;
    }

    public int getChemyCounter() {
        return chemyCounter;
    }

    public int getTransporterType() {
        return transporterType;
    }

    public boolean isFibraOption() {
        return fibraOption;
    }

    public boolean isDropConveyor() {
        return dropConveyor;
    }

    public float getMixCapacity() {
        return mixCapacity;
    }

    public boolean isHydroGate() {
        return hydroGate;
    }

    public void setInertBunckerCounter(int inertBunckerCounter) {
        this.inertBunckerCounter = inertBunckerCounter;
    }

    public void setComboBunckerOption(boolean comboBunckerOption) {
        this.comboBunckerOption = comboBunckerOption;
    }

    public void setSilosCounter(int silosCounter) {
        this.silosCounter = silosCounter;
    }

    public void setWater2(boolean water2) {
        this.water2 = water2;
    }

    public void setHumidityMixerSensor(boolean humidityMixerSensor) {
        this.humidityMixerSensor = humidityMixerSensor;
    }

    public void setChemyCounter(int chemyCounter) {
        this.chemyCounter = chemyCounter;
    }

    public void setTransporterType(int transporterType) {
        this.transporterType = transporterType;
    }

    public void setFibraOption(boolean fibraOption) {
        this.fibraOption = fibraOption;
    }

    public void setDropConveyor(boolean dropConveyor) {
        this.dropConveyor = dropConveyor;
    }

    public void setMixCapacity(float mixCapacity) {
        this.mixCapacity = mixCapacity;
    }

    public void setHydroGate(boolean hydroGate) {
        this.hydroGate = hydroGate;
    }

    public boolean isAmperageSensor() {
        return amperageSensor;
    }

    public void setAmperageSensor(boolean amperageSensor) {
        this.amperageSensor = amperageSensor;
    }

    public boolean isDwpl() {
        return dwpl;
    }

    public void setDwpl(boolean dwpl) {
        this.dwpl = dwpl;
    }

    public boolean isVibroFunnelMixer() {
        return vibroFunnelMixer;
    }

    public void setVibroFunnelMixer(boolean vibroFunnelMixer) {
        this.vibroFunnelMixer = vibroFunnelMixer;
    }

    public boolean isHumSensorInert() {
        return humSensorInert;
    }

    public void setHumSensorInert(boolean humSensorInert) {
        this.humSensorInert = humSensorInert;
    }

    public boolean isPlanetarTypeMixer() {
        return planetarTypeMixer;
    }

    public void setPlanetarTypeMixer(boolean planetarTypeMixer) {
        this.planetarTypeMixer = planetarTypeMixer;
    }

    public boolean isReverseModuleFactory() {
        return reverseModuleFactory;
    }

    public void setReverseModuleFactory(boolean reverseModuleFactory) {
        this.reverseModuleFactory = reverseModuleFactory;
    }
}
