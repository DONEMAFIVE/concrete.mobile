package ru.zzbo.concretemobile.utils;

/**
 * Объект который просто считает количество циклов по формуле,
 * на входе общий объем производимой партии и максимальный объем одного замеса
 */
public class CalcUtil {

    private float capacityMix;
    private int cycleSum;

    public float getCapacityMix() {
        return capacityMix;
    }

    public int getCycleSum() {
        return cycleSum;
    }

    public void cycleCalcCounter(float totalWeight, float maxMixCapacity) {
        float current = totalWeight / maxMixCapacity;
        this.cycleSum = (int) Math.ceil(current);
        this.capacityMix = totalWeight / this.cycleSum;
    }
    public float calcCapacityForCube(float value, float capacity){
        //привести к 1 м3, контроллер пересчитывает переданное число в соотвествии с заданным объемом, передавать нужно посчитанное от 1 м3
        capacity = 1/capacity;
        return value * capacity;
    }

}
