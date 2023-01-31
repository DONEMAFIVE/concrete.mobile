package ru.zzbo.concretemobile.models;

public class DispatcherStates {

    private String factoryState;
    private String productionCapacityDay;
    private String alarmState;
    private String currentRecepie;
    private String currentOrder;
    private String partyCapacity;
    private String mixCapacity;
    private String mixCounter;
    private String operatorName;
    private String currentOrg;
    private String currentTrans;

    public DispatcherStates(String factoryState, String productionCapacityDay, String alarmState,
                            String currentRecepie, String currentOrder, String partyCapacity,
                            String mixCapacity, String mixCounter, String operatorName,
                            String currentOrg, String currentTrans) {
        this.factoryState = factoryState;
        this.productionCapacityDay = productionCapacityDay;
        this.alarmState = alarmState;
        this.currentRecepie = currentRecepie;
        this.currentOrder = currentOrder;
        this.partyCapacity = partyCapacity;
        this.mixCapacity = mixCapacity;
        this.mixCounter = mixCounter;
        this.operatorName = operatorName;
        this.currentOrg = currentOrg;
        this.currentTrans = currentTrans;
    }

    public String getCurrentOrg() {
        return currentOrg;
    }

    public void setCurrentOrg(String currentOrg) {
        this.currentOrg = currentOrg;
    }

    public String getCurrentTrans() {
        return currentTrans;
    }

    public void setCurrentTrans(String currentTrans) {
        this.currentTrans = currentTrans;
    }

    public String getFactoryState() {
        return factoryState;
    }

    public void setFactoryState(String factoryState) {
        this.factoryState = factoryState;
    }

    public String getProductionCapacityDay() {
        return productionCapacityDay;
    }

    public void setProductionCapacityDay(String productionCapacityDay) {
        this.productionCapacityDay = productionCapacityDay;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getCurrentRecepie() {
        return currentRecepie;
    }

    public void setCurrentRecepie(String currentRecepie) {
        this.currentRecepie = currentRecepie;
    }

    public String getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(String currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getPartyCapacity() {
        return partyCapacity;
    }

    public void setPartyCapacity(String partyCapacity) {
        this.partyCapacity = partyCapacity;
    }

    public String getMixCapacity() {
        return mixCapacity;
    }

    public void setMixCapacity(String mixCapacity) {
        this.mixCapacity = mixCapacity;
    }

    public String getMixCounter() {
        return mixCounter;
    }

    public void setMixCounter(String mixCounter) {
        this.mixCounter = mixCounter;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
