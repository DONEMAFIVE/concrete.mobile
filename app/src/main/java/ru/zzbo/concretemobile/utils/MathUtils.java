package ru.zzbo.concretemobile.utils;

public class MathUtils {

    //простая проверка является ли строка числом
    public boolean isDigit(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    //найти процент числа от другого числа
    public int getPercent(float value1, float value2){
        if (value2 <= 0) return 0;
        return Math.round((value1/value2)*100);
    }

    public float getPercentForDeviation(float recipe, float dose){
        if ((recipe == 0) || (dose == 0)) return 0;
        float value;
        float percent;
        if (dose > recipe){ //пересыпало
            value = dose - recipe;
        } else { //недосыпало
            value = recipe - dose;
        }
        percent = Math.round((value/recipe)*100);
        return percent;
    }

    //пересчитать число по заданному проценту
    public float reCalcValueForRecent(float value, float percent){
        if (percent >= 100) return value;
        return (value * percent)/100;
    }

    public double getRoundValue(double value) {
        double scale = Math.pow(10, 1);     // 345.77774 => 345.7
        return Math.ceil(value * scale) / scale;
    }

}
