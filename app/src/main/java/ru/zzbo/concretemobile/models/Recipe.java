package ru.zzbo.concretemobile.models;

public class Recipe {

    private int id;
    private String date;
    private String time;
    private String name;
    private String mark;
    private String classPie;
    private String description;
    private float bunckerRecepie11;
    private float bunckerRecepie12;
    private float bunckerRecepie21;
    private float bunckerRecepie22;
    private float bunckerRecepie31;
    private float bunckerRecepie32;
    private float bunckerRecepie41;
    private float bunckerRecepie42;
    private float bunckerShortage11;
    private float bunckerShortage12;
    private float bunckerShortage21;
    private float bunckerShortage22;
    private float bunckerShortage31;
    private float bunckerShortage32;
    private float bunckerShortage41;
    private float bunckerShortage42;
    private float chemyRecepie1;
    private float chemyShortage1;
    private float chemyShortage2;
    private float water1Recepie;
    private float water2Recepie;
    private float water1Shortage;
    private float water2Shortage;
    private float silosRecepie1;
    private float silosRecepie2;
    private float silosShortage1;
    private float silosShortage2;
    private float humidity11;
    private float humidity12;
    private float humidity21;
    private float humidity22;
    private float humidity31;
    private float humidity32;
    private float humidity41;
    private float humidity42;
    private String uniNumber;
    private int timeMix;
    private float chemy2Recepie;
    private float chemy3Recepie;
    private float chemy2Shortage;
    private float chemy3Shortage;
    private int pathToHumidity;
    private int preDosingWaterPercent;

    public Recipe(int id, String date, String time, String name, String mark, String classPie, String description, float bunckerRecepie11, float bunckerRecepie12, float bunckerRecepie21, float bunckerRecepie22, float bunckerRecepie31, float bunckerRecepie32, float bunckerRecepie41, float bunckerRecepie42, float bunckerShortage11, float bunckerShortage12, float bunckerShortage21, float bunckerShortage22, float bunckerShortage31, float bunckerShortage32, float bunckerShortage41, float bunckerShortage42, float chemyRecepie1, float chemyShortage1, float chemyShortage2, float water1Recepie, float water2Recepie, float water1Shortage, float water2Shortage, float silosRecepie1, float silosRecepie2, float silosShortage1, float silosShortage2, float humidity11, float humidity12, float humidity21, float humidity22, float humidity31, float humidity32, float humidity41, float humidity42, String uniNumber, int timeMix, float chemy2Recepie, float chemy3Recepie, float chemy2Shortage, float chemy3Shortage, int pathToHumidity, int preDosingWaterPercent) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.mark = mark;
        this.classPie = classPie;
        this.description = description;
        this.bunckerRecepie11 = bunckerRecepie11;
        this.bunckerRecepie12 = bunckerRecepie12;
        this.bunckerRecepie21 = bunckerRecepie21;
        this.bunckerRecepie22 = bunckerRecepie22;
        this.bunckerRecepie31 = bunckerRecepie31;
        this.bunckerRecepie32 = bunckerRecepie32;
        this.bunckerRecepie41 = bunckerRecepie41;
        this.bunckerRecepie42 = bunckerRecepie42;
        this.bunckerShortage11 = bunckerShortage11;
        this.bunckerShortage12 = bunckerShortage12;
        this.bunckerShortage21 = bunckerShortage21;
        this.bunckerShortage22 = bunckerShortage22;
        this.bunckerShortage31 = bunckerShortage31;
        this.bunckerShortage32 = bunckerShortage32;
        this.bunckerShortage41 = bunckerShortage41;
        this.bunckerShortage42 = bunckerShortage42;
        this.chemyRecepie1 = chemyRecepie1;
        this.chemyShortage1 = chemyShortage1;
        this.chemyShortage2 = chemyShortage2;
        this.water1Recepie = water1Recepie;
        this.water2Recepie = water2Recepie;
        this.water1Shortage = water1Shortage;
        this.water2Shortage = water2Shortage;
        this.silosRecepie1 = silosRecepie1;
        this.silosRecepie2 = silosRecepie2;
        this.silosShortage1 = silosShortage1;
        this.silosShortage2 = silosShortage2;
        this.humidity11 = humidity11;
        this.humidity12 = humidity12;
        this.humidity21 = humidity21;
        this.humidity22 = humidity22;
        this.humidity31 = humidity31;
        this.humidity32 = humidity32;
        this.humidity41 = humidity41;
        this.humidity42 = humidity42;
        this.uniNumber = uniNumber;
        this.timeMix = timeMix;
        this.chemy2Recepie = chemy2Recepie;
        this.chemy3Recepie = chemy3Recepie;
        this.chemy2Shortage = chemy2Shortage;
        this.chemy3Shortage = chemy3Shortage;
        this.pathToHumidity = pathToHumidity;
        this.preDosingWaterPercent = preDosingWaterPercent;
    }

    public Recipe(String date, String time, String name, String mark, String classPie, String description, float bunckerRecepie11, float bunckerRecepie12, float bunckerRecepie21, float bunckerRecepie22, float bunckerRecepie31, float bunckerRecepie32, float bunckerRecepie41, float bunckerRecepie42, float bunckerShortage11, float bunckerShortage12, float bunckerShortage21, float bunckerShortage22, float bunckerShortage31, float bunckerShortage32, float bunckerShortage41, float bunckerShortage42, float chemyRecepie1, float chemyShortage1, float chemyShortage2, float water1Recepie, float water2Recepie, float water1Shortage, float water2Shortage, float silosRecepie1, float silosRecepie2, float silosShortage1, float silosShortage2, float humidity11, float humidity12, float humidity21, float humidity22, float humidity31, float humidity32, float humidity41, float humidity42, String uniNumber, int timeMix, float chemy2Recepie, float chemy3Recepie, float chemy2Shortage, float chemy3Shortage, int pathToHumidity, int preDosingWaterPercent) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.mark = mark;
        this.classPie = classPie;
        this.description = description;
        this.bunckerRecepie11 = bunckerRecepie11;
        this.bunckerRecepie12 = bunckerRecepie12;
        this.bunckerRecepie21 = bunckerRecepie21;
        this.bunckerRecepie22 = bunckerRecepie22;
        this.bunckerRecepie31 = bunckerRecepie31;
        this.bunckerRecepie32 = bunckerRecepie32;
        this.bunckerRecepie41 = bunckerRecepie41;
        this.bunckerRecepie42 = bunckerRecepie42;
        this.bunckerShortage11 = bunckerShortage11;
        this.bunckerShortage12 = bunckerShortage12;
        this.bunckerShortage21 = bunckerShortage21;
        this.bunckerShortage22 = bunckerShortage22;
        this.bunckerShortage31 = bunckerShortage31;
        this.bunckerShortage32 = bunckerShortage32;
        this.bunckerShortage41 = bunckerShortage41;
        this.bunckerShortage42 = bunckerShortage42;
        this.chemyRecepie1 = chemyRecepie1;
        this.chemyShortage1 = chemyShortage1;
        this.chemyShortage2 = chemyShortage2;
        this.water1Recepie = water1Recepie;
        this.water2Recepie = water2Recepie;
        this.water1Shortage = water1Shortage;
        this.water2Shortage = water2Shortage;
        this.silosRecepie1 = silosRecepie1;
        this.silosRecepie2 = silosRecepie2;
        this.silosShortage1 = silosShortage1;
        this.silosShortage2 = silosShortage2;
        this.humidity11 = humidity11;
        this.humidity12 = humidity12;
        this.humidity21 = humidity21;
        this.humidity22 = humidity22;
        this.humidity31 = humidity31;
        this.humidity32 = humidity32;
        this.humidity41 = humidity41;
        this.humidity42 = humidity42;
        this.uniNumber = uniNumber;
        this.timeMix = timeMix;
        this.chemy2Recepie = chemy2Recepie;
        this.chemy3Recepie = chemy3Recepie;
        this.chemy2Shortage = chemy2Shortage;
        this.chemy3Shortage = chemy3Shortage;
        this.pathToHumidity = pathToHumidity;
        this.preDosingWaterPercent = preDosingWaterPercent;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }

    public String getClassPie() {
        return classPie;
    }

    public String getDescription() {
        return description;
    }

    public float getBunckerRecepie11() {
        return bunckerRecepie11;
    }

    public float getBunckerRecepie12() {
        return bunckerRecepie12;
    }

    public float getBunckerRecepie21() {
        return bunckerRecepie21;
    }

    public float getBunckerRecepie22() {
        return bunckerRecepie22;
    }

    public float getBunckerRecepie31() {
        return bunckerRecepie31;
    }

    public float getBunckerRecepie32() {
        return bunckerRecepie32;
    }

    public float getBunckerRecepie41() {
        return bunckerRecepie41;
    }

    public float getBunckerRecepie42() {
        return bunckerRecepie42;
    }

    public float getBunckerShortage11() {
        return bunckerShortage11;
    }

    public float getBunckerShortage12() {
        return bunckerShortage12;
    }

    public float getBunckerShortage21() {
        return bunckerShortage21;
    }

    public float getBunckerShortage22() {
        return bunckerShortage22;
    }

    public float getBunckerShortage31() {
        return bunckerShortage31;
    }

    public float getBunckerShortage32() {
        return bunckerShortage32;
    }

    public float getBunckerShortage41() {
        return bunckerShortage41;
    }

    public float getBunckerShortage42() {
        return bunckerShortage42;
    }

    public float getChemyRecepie1() {
        return chemyRecepie1;
    }

    public float getChemyShortage1() {
        return chemyShortage1;
    }

    public float getChemyShortage2() {
        return chemyShortage2;
    }

    public float getWater1Recepie() {
        return water1Recepie;
    }

    public float getWater2Recepie() {
        return water2Recepie;
    }

    public float getWater1Shortage() {
        return water1Shortage;
    }

    public float getWater2Shortage() {
        return water2Shortage;
    }

    public float getSilosRecepie1() {
        return silosRecepie1;
    }

    public float getSilosRecepie2() {
        return silosRecepie2;
    }

    public float getSilosShortage1() {
        return silosShortage1;
    }

    public float getSilosShortage2() {
        return silosShortage2;
    }

    public float getHumidity11() {
        return humidity11;
    }

    public float getHumidity12() {
        return humidity12;
    }

    public float getHumidity21() {
        return humidity21;
    }

    public float getHumidity22() {
        return humidity22;
    }

    public float getHumidity31() {
        return humidity31;
    }

    public float getHumidity32() {
        return humidity32;
    }

    public float getHumidity41() {
        return humidity41;
    }

    public float getHumidity42() {
        return humidity42;
    }

    public String getUniNumber() {
        return uniNumber;
    }

    public int getTimeMix() {
        return timeMix;
    }

    public float getChemy2Recepie() {
        return chemy2Recepie;
    }

    public float getChemy3Recepie() {
        return chemy3Recepie;
    }

    public float getChemy2Shortage() {
        return chemy2Shortage;
    }

    public float getChemy3Shortage() {
        return chemy3Shortage;
    }

    public int getPathToHumidity() {
        return pathToHumidity;
    }

    public int getPreDosingWaterPercent() {
        return preDosingWaterPercent;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setClassPie(String classPie) {
        this.classPie = classPie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBunckerRecepie11(float bunckerRecepie11) {
        this.bunckerRecepie11 = bunckerRecepie11;
    }

    public void setBunckerRecepie12(float bunckerRecepie12) {
        this.bunckerRecepie12 = bunckerRecepie12;
    }

    public void setBunckerRecepie21(float bunckerRecepie21) {
        this.bunckerRecepie21 = bunckerRecepie21;
    }

    public void setBunckerRecepie22(float bunckerRecepie22) {
        this.bunckerRecepie22 = bunckerRecepie22;
    }

    public void setBunckerRecepie31(float bunckerRecepie31) {
        this.bunckerRecepie31 = bunckerRecepie31;
    }

    public void setBunckerRecepie32(float bunckerRecepie32) {
        this.bunckerRecepie32 = bunckerRecepie32;
    }

    public void setBunckerRecepie41(float bunckerRecepie41) {
        this.bunckerRecepie41 = bunckerRecepie41;
    }

    public void setBunckerRecepie42(float bunckerRecepie42) {
        this.bunckerRecepie42 = bunckerRecepie42;
    }

    public void setBunckerShortage11(float bunckerShortage11) {
        this.bunckerShortage11 = bunckerShortage11;
    }

    public void setBunckerShortage12(float bunckerShortage12) {
        this.bunckerShortage12 = bunckerShortage12;
    }

    public void setBunckerShortage21(float bunckerShortage21) {
        this.bunckerShortage21 = bunckerShortage21;
    }

    public void setBunckerShortage22(float bunckerShortage22) {
        this.bunckerShortage22 = bunckerShortage22;
    }

    public void setBunckerShortage31(float bunckerShortage31) {
        this.bunckerShortage31 = bunckerShortage31;
    }

    public void setBunckerShortage32(float bunckerShortage32) {
        this.bunckerShortage32 = bunckerShortage32;
    }

    public void setBunckerShortage41(float bunckerShortage41) {
        this.bunckerShortage41 = bunckerShortage41;
    }

    public void setBunckerShortage42(float bunckerShortage42) {
        this.bunckerShortage42 = bunckerShortage42;
    }

    public void setChemyRecepie1(float chemyRecepie1) {
        this.chemyRecepie1 = chemyRecepie1;
    }

    public void setChemyShortage1(float chemyShortage1) {
        this.chemyShortage1 = chemyShortage1;
    }

    public void setChemyShortage2(float chemyShortage2) {
        this.chemyShortage2 = chemyShortage2;
    }

    public void setWater1Recepie(float water1Recepie) {
        this.water1Recepie = water1Recepie;
    }

    public void setWater2Recepie(float water2Recepie) {
        this.water2Recepie = water2Recepie;
    }

    public void setWater1Shortage(float water1Shortage) {
        this.water1Shortage = water1Shortage;
    }

    public void setWater2Shortage(float water2Shortage) {
        this.water2Shortage = water2Shortage;
    }

    public void setSilosRecepie1(float silosRecepie1) {
        this.silosRecepie1 = silosRecepie1;
    }

    public void setSilosRecepie2(float silosRecepie2) {
        this.silosRecepie2 = silosRecepie2;
    }

    public void setSilosShortage1(float silosShortage1) {
        this.silosShortage1 = silosShortage1;
    }

    public void setSilosShortage2(float silosShortage2) {
        this.silosShortage2 = silosShortage2;
    }

    public void setHumidity11(float humidity11) {
        this.humidity11 = humidity11;
    }

    public void setHumidity12(float humidity12) {
        this.humidity12 = humidity12;
    }

    public void setHumidity21(float humidity21) {
        this.humidity21 = humidity21;
    }

    public void setHumidity22(float humidity22) {
        this.humidity22 = humidity22;
    }

    public void setHumidity31(float humidity31) {
        this.humidity31 = humidity31;
    }

    public void setHumidity32(float humidity32) {
        this.humidity32 = humidity32;
    }

    public void setHumidity41(float humidity41) {
        this.humidity41 = humidity41;
    }

    public void setHumidity42(float humidity42) {
        this.humidity42 = humidity42;
    }

    public void setUniNumber(String uniNumber) {
        this.uniNumber = uniNumber;
    }

    public void setTimeMix(int timeMix) {
        this.timeMix = timeMix;
    }

    public void setChemy2Recepie(float chemy2Recepie) {
        this.chemy2Recepie = chemy2Recepie;
    }

    public void setChemy3Recepie(float chemy3Recepie) {
        this.chemy3Recepie = chemy3Recepie;
    }

    public void setChemy2Shortage(float chemy2Shortage) {
        this.chemy2Shortage = chemy2Shortage;
    }

    public void setChemy3Shortage(float chemy3Shortage) {
        this.chemy3Shortage = chemy3Shortage;
    }

    public void setPathToHumidity(int pathToHumidity) {
        this.pathToHumidity = pathToHumidity;
    }

    public void setPreDosingWaterPercent(int preDosingWaterPercent) {
        this.preDosingWaterPercent = preDosingWaterPercent;
    }
}