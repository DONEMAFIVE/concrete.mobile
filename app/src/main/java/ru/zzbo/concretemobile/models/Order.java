package ru.zzbo.concretemobile.models;


/**
 * Объект Order используется для обработки параметров заказа
 */
public class Order {

    private int id;
    private String nameOrder;
    private int numberOrder;
    private String date;
    private String completionDate;  //дата закрытия заявки
    private String organizationName;
    private int organizationID;
    private String transporter;
    private int transporterID;
    private String recepie;
    private int recepieID;
    private float totalCapacity;    //общий объем
    private float maxMixCapacity;   //максимальный объем замеса
    private int totalMixCounter;    //счетчик замесов
    private String markConcrete;
    private String classConcrete;
    private float pieBuncker11;
    private float pieBuncker12;
    private float pieBuncker21;
    private float pieBuncker22;
    private float pieBuncker31;
    private float pieBuncker32;
    private float pieBuncker41;
    private float pieBuncker42;
    private float pieChemy1;
    private float pieChemy2;
    private float pieWater1;
    private float pieWater2;
    private float pieSilos1;
    private float pieSilos2;
    private float shortageBuncker11;
    private float shortageBuncker12;
    private float shortageBuncker21;
    private float shortageBuncker22;
    private float shortageBuncker31;
    private float shortageBuncker32;
    private float shortageBuncker41;
    private float shortageBuncker42;
    private float shortageChemy1;
    private float shortageChemy2;
    private float shortageWater1;
    private float shortageWater2;
    private float shortageSilos1;
    private float shortageSilos2;
    private float countBuncker11;
    private float countBuncker12;
    private float countBuncker21;
    private float countBuncker22;
    private float countBuncker31;
    private float countBuncker32;
    private float countBuncker41;
    private float countBuncker42;
    private float countChemy1;
    private float countChemy2;
    private float countWater1;
    private float countWater2;
    private float countSilos1;
    private float countSilos2;
    private int state;
    private int currentMixCount;
    private String uploadAddress;       //адрес выгрузки для транспортной накладной
    private String amountConcrete;      //Сумма за бетон
    private String paymentOption;       //Вариант оплаты (счет или терминал)
    private String operator;            //Оператор
    private String comment;            //Комментарий

    public Order() {
    }

    public Order(int id, String nameOrder, int numberOrder, String date, String completionDate, String organizationName, int organizationID, String transporter, int transporterID, String recepie, int recepieID, float totalCapacity, float maxMixCapacity, int totalMixCounter, String markConcrete, String classConcrete, float pieBuncker11, float pieBuncker12, float pieBuncker21, float pieBuncker22, float pieBuncker31, float pieBuncker32, float pieBuncker41, float pieBuncker42, float pieChemy1, float pieChemy2, float pieWater1, float pieWater2, float pieSilos1, float pieSilos2, float shortageBuncker11, float shortageBuncker12, float shortageBuncker21, float shortageBuncker22, float shortageBuncker31, float shortageBuncker32, float shortageBuncker41, float shortageBuncker42, float shortageChemy1, float shortageChemy2, float shortageWater1, float shortageWater2, float shortageSilos1, float shortageSilos2, float countBuncker11, float countBuncker12, float countBuncker21, float countBuncker22, float countBuncker31, float countBuncker32, float countBuncker41, float countBuncker42, float countChemy1, float countChemy2, float countWater1, float countWater2, float countSilos1, float countSilos2, int state, int currentMixCount, String uploadAddress, String amountConcrete, String paymentOption, String operator, String comment) {
        this.id = id;
        this.nameOrder = nameOrder;
        this.numberOrder = numberOrder;
        this.date = date;
        this.completionDate = completionDate;
        this.organizationName = organizationName;
        this.organizationID = organizationID;
        this.transporter = transporter;
        this.transporterID = transporterID;
        this.recepie = recepie;
        this.recepieID = recepieID;
        this.totalCapacity = totalCapacity;
        this.maxMixCapacity = maxMixCapacity;
        this.totalMixCounter = totalMixCounter;
        this.markConcrete = markConcrete;
        this.classConcrete = classConcrete;
        this.pieBuncker11 = pieBuncker11;
        this.pieBuncker12 = pieBuncker12;
        this.pieBuncker21 = pieBuncker21;
        this.pieBuncker22 = pieBuncker22;
        this.pieBuncker31 = pieBuncker31;
        this.pieBuncker32 = pieBuncker32;
        this.pieBuncker41 = pieBuncker41;
        this.pieBuncker42 = pieBuncker42;
        this.pieChemy1 = pieChemy1;
        this.pieChemy2 = pieChemy2;
        this.pieWater1 = pieWater1;
        this.pieWater2 = pieWater2;
        this.pieSilos1 = pieSilos1;
        this.pieSilos2 = pieSilos2;
        this.shortageBuncker11 = shortageBuncker11;
        this.shortageBuncker12 = shortageBuncker12;
        this.shortageBuncker21 = shortageBuncker21;
        this.shortageBuncker22 = shortageBuncker22;
        this.shortageBuncker31 = shortageBuncker31;
        this.shortageBuncker32 = shortageBuncker32;
        this.shortageBuncker41 = shortageBuncker41;
        this.shortageBuncker42 = shortageBuncker42;
        this.shortageChemy1 = shortageChemy1;
        this.shortageChemy2 = shortageChemy2;
        this.shortageWater1 = shortageWater1;
        this.shortageWater2 = shortageWater2;
        this.shortageSilos1 = shortageSilos1;
        this.shortageSilos2 = shortageSilos2;
        this.countBuncker11 = countBuncker11;
        this.countBuncker12 = countBuncker12;
        this.countBuncker21 = countBuncker21;
        this.countBuncker22 = countBuncker22;
        this.countBuncker31 = countBuncker31;
        this.countBuncker32 = countBuncker32;
        this.countBuncker41 = countBuncker41;
        this.countBuncker42 = countBuncker42;
        this.countChemy1 = countChemy1;
        this.countChemy2 = countChemy2;
        this.countWater1 = countWater1;
        this.countWater2 = countWater2;
        this.countSilos1 = countSilos1;
        this.countSilos2 = countSilos2;
        this.state = state;
        this.currentMixCount = currentMixCount;
        this.uploadAddress = uploadAddress;
        this.amountConcrete = amountConcrete;
        this.paymentOption = paymentOption;
        this.operator = operator;
        this.comment = comment;
    }

    public Order(String nameOrder, int numberOrder, String date, String completionDate, String organizationName, int organizationID, String transporter, int transporterID, String recepie, int recepieID, float totalCapacity, float maxMixCapacity, int totalMixCounter, String markConcrete, String classConcrete, float pieBuncker11, float pieBuncker12, float pieBuncker21, float pieBuncker22, float pieBuncker31, float pieBuncker32, float pieBuncker41, float pieBuncker42, float pieChemy1, float pieChemy2, float pieWater1, float pieWater2, float pieSilos1, float pieSilos2, float shortageBuncker11, float shortageBuncker12, float shortageBuncker21, float shortageBuncker22, float shortageBuncker31, float shortageBuncker32, float shortageBuncker41, float shortageBuncker42, float shortageChemy1, float shortageChemy2, float shortageWater1, float shortageWater2, float shortageSilos1, float shortageSilos2, float countBuncker11, float countBuncker12, float countBuncker21, float countBuncker22, float countBuncker31, float countBuncker32, float countBuncker41, float countBuncker42, float countChemy1, float countChemy2, float countWater1, float countWater2, float countSilos1, float countSilos2, int state, int currentMixCount, String uploadAddress, String amountConcrete, String paymentOption, String operator, String comment) {
        this.nameOrder = nameOrder;
        this.numberOrder = numberOrder;
        this.date = date;
        this.completionDate = completionDate;
        this.organizationName = organizationName;
        this.organizationID = organizationID;
        this.transporter = transporter;
        this.transporterID = transporterID;
        this.recepie = recepie;
        this.recepieID = recepieID;
        this.totalCapacity = totalCapacity;
        this.maxMixCapacity = maxMixCapacity;
        this.totalMixCounter = totalMixCounter;
        this.markConcrete = markConcrete;
        this.classConcrete = classConcrete;
        this.pieBuncker11 = pieBuncker11;
        this.pieBuncker12 = pieBuncker12;
        this.pieBuncker21 = pieBuncker21;
        this.pieBuncker22 = pieBuncker22;
        this.pieBuncker31 = pieBuncker31;
        this.pieBuncker32 = pieBuncker32;
        this.pieBuncker41 = pieBuncker41;
        this.pieBuncker42 = pieBuncker42;
        this.pieChemy1 = pieChemy1;
        this.pieChemy2 = pieChemy2;
        this.pieWater1 = pieWater1;
        this.pieWater2 = pieWater2;
        this.pieSilos1 = pieSilos1;
        this.pieSilos2 = pieSilos2;
        this.shortageBuncker11 = shortageBuncker11;
        this.shortageBuncker12 = shortageBuncker12;
        this.shortageBuncker21 = shortageBuncker21;
        this.shortageBuncker22 = shortageBuncker22;
        this.shortageBuncker31 = shortageBuncker31;
        this.shortageBuncker32 = shortageBuncker32;
        this.shortageBuncker41 = shortageBuncker41;
        this.shortageBuncker42 = shortageBuncker42;
        this.shortageChemy1 = shortageChemy1;
        this.shortageChemy2 = shortageChemy2;
        this.shortageWater1 = shortageWater1;
        this.shortageWater2 = shortageWater2;
        this.shortageSilos1 = shortageSilos1;
        this.shortageSilos2 = shortageSilos2;
        this.countBuncker11 = countBuncker11;
        this.countBuncker12 = countBuncker12;
        this.countBuncker21 = countBuncker21;
        this.countBuncker22 = countBuncker22;
        this.countBuncker31 = countBuncker31;
        this.countBuncker32 = countBuncker32;
        this.countBuncker41 = countBuncker41;
        this.countBuncker42 = countBuncker42;
        this.countChemy1 = countChemy1;
        this.countChemy2 = countChemy2;
        this.countWater1 = countWater1;
        this.countWater2 = countWater2;
        this.countSilos1 = countSilos1;
        this.countSilos2 = countSilos2;
        this.state = state;
        this.currentMixCount = currentMixCount;
        this.uploadAddress = uploadAddress;
        this.amountConcrete = amountConcrete;
        this.paymentOption = paymentOption;
        this.operator = operator;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public int getTransporterID() {
        return transporterID;
    }

    public void setTransporterID(int transporterID) {
        this.transporterID = transporterID;
    }

    public String getRecepie() {
        return recepie;
    }

    public void setRecepie(String recepie) {
        this.recepie = recepie;
    }

    public int getRecepieID() {
        return recepieID;
    }

    public void setRecepieID(int recepieID) {
        this.recepieID = recepieID;
    }

    public float getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(float totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public float getMaxMixCapacity() {
        return maxMixCapacity;
    }

    public void setMaxMixCapacity(float maxMixCapacity) {
        this.maxMixCapacity = maxMixCapacity;
    }

    public int getTotalMixCounter() {
        return totalMixCounter;
    }

    public void setTotalMixCounter(int totalMixCounter) {
        this.totalMixCounter = totalMixCounter;
    }

    public String getMarkConcrete() {
        return markConcrete;
    }

    public void setMarkConcrete(String markConcrete) {
        this.markConcrete = markConcrete;
    }

    public String getClassConcrete() {
        return classConcrete;
    }

    public void setClassConcrete(String classConcrete) {
        this.classConcrete = classConcrete;
    }

    public float getPieBuncker11() {
        return pieBuncker11;
    }

    public void setPieBuncker11(float pieBuncker11) {
        this.pieBuncker11 = pieBuncker11;
    }

    public float getPieBuncker12() {
        return pieBuncker12;
    }

    public void setPieBuncker12(float pieBuncker12) {
        this.pieBuncker12 = pieBuncker12;
    }

    public float getPieBuncker21() {
        return pieBuncker21;
    }

    public void setPieBuncker21(float pieBuncker21) {
        this.pieBuncker21 = pieBuncker21;
    }

    public float getPieBuncker22() {
        return pieBuncker22;
    }

    public void setPieBuncker22(float pieBuncker22) {
        this.pieBuncker22 = pieBuncker22;
    }

    public float getPieBuncker31() {
        return pieBuncker31;
    }

    public void setPieBuncker31(float pieBuncker31) {
        this.pieBuncker31 = pieBuncker31;
    }

    public float getPieBuncker32() {
        return pieBuncker32;
    }

    public void setPieBuncker32(float pieBuncker32) {
        this.pieBuncker32 = pieBuncker32;
    }

    public float getPieBuncker41() {
        return pieBuncker41;
    }

    public void setPieBuncker41(float pieBuncker41) {
        this.pieBuncker41 = pieBuncker41;
    }

    public float getPieBuncker42() {
        return pieBuncker42;
    }

    public void setPieBuncker42(float pieBuncker42) {
        this.pieBuncker42 = pieBuncker42;
    }

    public float getPieChemy1() {
        return pieChemy1;
    }

    public void setPieChemy1(float pieChemy1) {
        this.pieChemy1 = pieChemy1;
    }

    public float getPieWater1() {
        return pieWater1;
    }

    public void setPieWater1(float pieWater1) {
        this.pieWater1 = pieWater1;
    }

    public float getPieSilos1() {
        return pieSilos1;
    }

    public void setPieSilos1(float pieSilos1) {
        this.pieSilos1 = pieSilos1;
    }

    public float getPieSilos2() {
        return pieSilos2;
    }

    public void setPieSilos2(float pieSilos2) {
        this.pieSilos2 = pieSilos2;
    }

    public float getShortageBuncker11() {
        return shortageBuncker11;
    }

    public void setShortageBuncker11(float shortageBuncker11) {
        this.shortageBuncker11 = shortageBuncker11;
    }

    public float getShortageBuncker12() {
        return shortageBuncker12;
    }

    public void setShortageBuncker12(float shortageBuncker12) {
        this.shortageBuncker12 = shortageBuncker12;
    }

    public float getShortageBuncker21() {
        return shortageBuncker21;
    }

    public void setShortageBuncker21(float shortageBuncker21) {
        this.shortageBuncker21 = shortageBuncker21;
    }

    public float getShortageBuncker22() {
        return shortageBuncker22;
    }

    public void setShortageBuncker22(float shortageBuncker22) {
        this.shortageBuncker22 = shortageBuncker22;
    }

    public float getShortageBuncker31() {
        return shortageBuncker31;
    }

    public void setShortageBuncker31(float shortageBuncker31) {
        this.shortageBuncker31 = shortageBuncker31;
    }

    public float getShortageBuncker32() {
        return shortageBuncker32;
    }

    public void setShortageBuncker32(float shortageBuncker32) {
        this.shortageBuncker32 = shortageBuncker32;
    }

    public float getShortageBuncker41() {
        return shortageBuncker41;
    }

    public void setShortageBuncker41(float shortageBuncker41) {
        this.shortageBuncker41 = shortageBuncker41;
    }

    public float getShortageBuncker42() {
        return shortageBuncker42;
    }

    public void setShortageBuncker42(float shortageBuncker42) {
        this.shortageBuncker42 = shortageBuncker42;
    }

    public float getShortageChemy1() {
        return shortageChemy1;
    }

    public void setShortageChemy1(float shortageChemy1) {
        this.shortageChemy1 = shortageChemy1;
    }

    public float getShortageWater1() {
        return shortageWater1;
    }

    public void setShortageWater1(float shortageWater1) {
        this.shortageWater1 = shortageWater1;
    }

    public float getShortageSilos1() {
        return shortageSilos1;
    }

    public void setShortageSilos1(float shortageSilos1) {
        this.shortageSilos1 = shortageSilos1;
    }

    public float getShortageSilos2() {
        return shortageSilos2;
    }

    public void setShortageSilos2(float shortageSilos2) {
        this.shortageSilos2 = shortageSilos2;
    }

    public float getCountBuncker11() {
        return countBuncker11;
    }

    public void setCountBuncker11(float countBuncker11) {
        this.countBuncker11 = countBuncker11;
    }

    public float getCountBuncker12() {
        return countBuncker12;
    }

    public void setCountBuncker12(float countBuncker12) {
        this.countBuncker12 = countBuncker12;
    }

    public float getCountBuncker21() {
        return countBuncker21;
    }

    public void setCountBuncker21(float countBuncker21) {
        this.countBuncker21 = countBuncker21;
    }

    public float getCountBuncker22() {
        return countBuncker22;
    }

    public void setCountBuncker22(float countBuncker22) {
        this.countBuncker22 = countBuncker22;
    }

    public float getCountBuncker31() {
        return countBuncker31;
    }

    public void setCountBuncker31(float countBuncker31) {
        this.countBuncker31 = countBuncker31;
    }

    public float getCountBuncker32() {
        return countBuncker32;
    }

    public void setCountBuncker32(float countBuncker32) {
        this.countBuncker32 = countBuncker32;
    }

    public float getCountBuncker41() {
        return countBuncker41;
    }

    public void setCountBuncker41(float countBuncker41) {
        this.countBuncker41 = countBuncker41;
    }

    public float getCountBuncker42() {
        return countBuncker42;
    }

    public void setCountBuncker42(float countBuncker42) {
        this.countBuncker42 = countBuncker42;
    }

    public float getCountChemy1() {
        return countChemy1;
    }

    public void setCountChemy1(float countChemy1) {
        this.countChemy1 = countChemy1;
    }

    public float getCountWater1() {
        return countWater1;
    }

    public void setCountWater1(float countWater1) {
        this.countWater1 = countWater1;
    }

    public float getCountSilos1() {
        return countSilos1;
    }

    public void setCountSilos1(float countSilos1) {
        this.countSilos1 = countSilos1;
    }

    public float getCountSilos2() {
        return countSilos2;
    }

    public void setCountSilos2(float countSilos2) {
        this.countSilos2 = countSilos2;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCurrentMixCount() {
        return currentMixCount;
    }

    public void setCurrentMixCount(int currentMixCount) {
        this.currentMixCount = currentMixCount;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }

    public String getAmountConcrete() {
        return amountConcrete;
    }

    public void setAmountConcrete(String amountConcrete) {
        this.amountConcrete = amountConcrete;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getPieWater2() {
        return pieWater2;
    }

    public void setPieWater2(float pieWater2) {
        this.pieWater2 = pieWater2;
    }

    public float getCountWater2() {
        return countWater2;
    }

    public void setCountWater2(float countWater2) {
        this.countWater2 = countWater2;
    }

    public float getPieChemy2() {
        return pieChemy2;
    }

    public void setPieChemy2(float pieChemy2) {
        this.pieChemy2 = pieChemy2;
    }

    public float getCountChemy2() {
        return countChemy2;
    }

    public void setCountChemy2(float countChemy2) {
        this.countChemy2 = countChemy2;
    }

    public float getShortageChemy2() {
        return shortageChemy2;
    }

    public void setShortageChemy2(float shortageChemy2) {
        this.shortageChemy2 = shortageChemy2;
    }

    public float getShortageWater2() {
        return shortageWater2;
    }

    public void setShortageWater2(float shortageWater2) {
        this.shortageWater2 = shortageWater2;
    }

    public String getOrder() {
        return id + "|" +
                nameOrder + "|" +
                numberOrder + "|" +
                date + "|" +
                completionDate + "|" +
                organizationName + "|" +
                organizationID + "|" +
                transporter + "|" +
                transporterID + "|" +
                recepie + "|" +
                recepieID + "|" +
                totalCapacity + "|" +
                maxMixCapacity + "|" +
                totalMixCounter + "|" +
                markConcrete + "|" +
                classConcrete + "|" +
                pieBuncker11 + "|" +
                pieBuncker12 + "|" +
                pieBuncker21 + "|" +
                pieBuncker22 + "|" +
                pieBuncker31 + "|" +
                pieBuncker32 + "|" +
                pieBuncker41 + "|" +
                pieBuncker42 + "|" +
                pieChemy1 + "|" +
                pieChemy2 + "|" +
                pieWater1 + "|" +
                pieWater2 + "|" +
                pieSilos1 + "|" +
                pieSilos2 + "|" +
                shortageBuncker11 + "|" +
                shortageBuncker12 + "|" +
                shortageBuncker21 + "|" +
                shortageBuncker22 + "|" +
                shortageBuncker31 + "|" +
                shortageBuncker32 + "|" +
                shortageBuncker41 + "|" +
                shortageBuncker42 + "|" +
                shortageChemy1 + "|" +
                shortageChemy2 + "|" +
                shortageWater1 + "|" +
                shortageWater2 + "|" +
                shortageSilos1 + "|" +
                shortageSilos2 + "|" +
                countBuncker11 + "|" +
                countBuncker12 + "|" +
                countBuncker21 + "|" +
                countBuncker22 + "|" +
                countBuncker31 + "|" +
                countBuncker32 + "|" +
                countBuncker41 + "|" +
                countBuncker42 + "|" +
                countChemy1 + "|" +
                countChemy2 + "|" +
                countWater1 + "|" +
                countWater2 + "|" +
                countSilos1 + "|" +
                countSilos2 + "|" +
                state + "|" +
                currentMixCount + "|" +
                uploadAddress + "|" +
                amountConcrete + "|" +
                paymentOption + "|" +
                operator + "|" +
                comment + "|";
    }
}
