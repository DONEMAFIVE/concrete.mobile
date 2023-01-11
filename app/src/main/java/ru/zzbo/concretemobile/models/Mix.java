package ru.zzbo.concretemobile.models;

public class Mix {

    private int id;
    private String nameOrder;
    private int numberOrder;
    private String date;
    private String time;
    private String uploadAddress;
    private float amountConcrete;
    private String paymentOption;
    private String operator;
    private String organization;
    private int organizationID;
    private String transporter;
    private int transporterID;
    private String recipe;
    private int recipeID;
    private int mixCounter;
    private float completeCapacity;
    private float totalCapacity;
    private float buncker11;
    private float buncker12;
    private float buncker21;
    private float buncker22;
    private float buncker31;
    private float buncker32;
    private float buncker41;
    private float buncker42;
    private float silos1;
    private float silos2;
    private float water1;
    private float water2;
    private float dwpl;
    private float chemy1;
    private float chemy2;
    private String loadingTime;

    public Mix(int id, String nameOrder, int numberOrder, String date, String time, String uploadAddress, float amountConcrete, String paymentOption, String operator, String organization, int organizationID, String transporter, int transporterID, String recipe, int recipeID, int mixCounter, float completeCapacity, float totalCapacity, float buncker11, float buncker12, float buncker21, float buncker22, float buncker31, float buncker32, float buncker41, float buncker42, float silos1, float silos2, float water1, float water2, float dwpl, float chemy1, float chemy2, String loadingTime) {
        this.id = id;
        this.nameOrder = nameOrder;
        this.numberOrder = numberOrder;
        this.date = date;
        this.time = time;
        this.uploadAddress = uploadAddress;
        this.amountConcrete = amountConcrete;
        this.paymentOption = paymentOption;
        this.operator = operator;
        this.organization = organization;
        this.organizationID = organizationID;
        this.transporter = transporter;
        this.transporterID = transporterID;
        this.recipe = recipe;
        this.recipeID = recipeID;
        this.mixCounter = mixCounter;
        this.completeCapacity = completeCapacity;
        this.totalCapacity = totalCapacity;
        this.buncker11 = buncker11;
        this.buncker12 = buncker12;
        this.buncker21 = buncker21;
        this.buncker22 = buncker22;
        this.buncker31 = buncker31;
        this.buncker32 = buncker32;
        this.buncker41 = buncker41;
        this.buncker42 = buncker42;
        this.silos1 = silos1;
        this.silos2 = silos2;
        this.water1 = water1;
        this.water2 = water2;
        this.dwpl = dwpl;
        this.chemy1 = chemy1;
        this.chemy2 = chemy2;
        this.loadingTime = loadingTime;
    }

    public Mix(String nameOrder, int numberOrder, String date, String time, String uploadAddress, float amountConcrete, String paymentOption, String operator, String organization, int organizationID, String transporter, int transporterID, String recipe, int recipeID, int mixCounter, float completeCapacity, float totalCapacity, float buncker11, float buncker12, float buncker21, float buncker22, float buncker31, float buncker32, float buncker41, float buncker42, float silos1, float silos2, float water1, float water2, float dwpl, float chemy1, float chemy2, String loadingTime) {
        this.nameOrder = nameOrder;
        this.numberOrder = numberOrder;
        this.date = date;
        this.time = time;
        this.uploadAddress = uploadAddress;
        this.amountConcrete = amountConcrete;
        this.paymentOption = paymentOption;
        this.operator = operator;
        this.organization = organization;
        this.organizationID = organizationID;
        this.transporter = transporter;
        this.transporterID = transporterID;
        this.recipe = recipe;
        this.recipeID = recipeID;
        this.mixCounter = mixCounter;
        this.completeCapacity = completeCapacity;
        this.totalCapacity = totalCapacity;
        this.buncker11 = buncker11;
        this.buncker12 = buncker12;
        this.buncker21 = buncker21;
        this.buncker22 = buncker22;
        this.buncker31 = buncker31;
        this.buncker32 = buncker32;
        this.buncker41 = buncker41;
        this.buncker42 = buncker42;
        this.silos1 = silos1;
        this.silos2 = silos2;
        this.water1 = water1;
        this.water2 = water2;
        this.dwpl = dwpl;
        this.chemy1 = chemy1;
        this.chemy2 = chemy2;
        this.loadingTime = loadingTime;
    }

    public int getId() {
        return id;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public float getAmountConcrete() {
        return amountConcrete;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public String getOperator() {
        return operator;
    }

    public String getOrganization() {
        return organization;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public String getTransporter() {
        return transporter;
    }

    public int getTransporterID() {
        return transporterID;
    }

    public String getRecipe() {
        return recipe;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public int getMixCounter() {
        return mixCounter;
    }

    public float getCompleteCapacity() {
        return completeCapacity;
    }

    public float getTotalCapacity() {
        return totalCapacity;
    }

    public float getBuncker11() {
        return buncker11;
    }

    public float getBuncker12() {
        return buncker12;
    }

    public float getBuncker21() {
        return buncker21;
    }

    public float getBuncker22() {
        return buncker22;
    }

    public float getBuncker31() {
        return buncker31;
    }

    public float getBuncker32() {
        return buncker32;
    }

    public float getBuncker41() {
        return buncker41;
    }

    public float getBuncker42() {
        return buncker42;
    }

    public float getSilos1() {
        return silos1;
    }

    public float getSilos2() {
        return silos2;
    }

    public float getWater1() {
        return water1;
    }

    public float getWater2() {
        return water2;
    }

    public float getDwpl() {
        return dwpl;
    }

    public float getChemy1() {
        return chemy1;
    }

    public float getChemy2() {
        return chemy2;
    }

    public String getLoadingTime() {
        return loadingTime;
    }
}
