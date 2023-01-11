package ru.zzbo.concretemobile.reporting;

import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.models.Current;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.reflections.ReflectionRetrieval;
import ru.zzbo.concretemobile.utils.Constants;

public class ReportRecordingUtil {

    public void recordWeights(Context context,
                              String nameOrder,
                              int numberOrder,
                              String uploadingAddress,
                              String operatorName,
                              String organization,
                              int organizationID,
                              String numberAuto,
                              int transporterID,
                              String currentRecepie,
                              float capacityMixer,
                              int recipeID,
                              float totalWeight) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        DecimalFormat decFormat = new DecimalFormat("#.##");

        ReflectionRetrieval retrieval = new ReflectionRetrieval();
        retrieval.getValues();

        if (!Constants.globalFactoryState) nameOrder = "Ручной замес";
        String calcTimeMix = "empty";

        Order selectedOrder = null;
        try {
            Current current = new DBUtilGet(context).getCurrent();
            selectedOrder = new DBUtilGet(context).getOrderForID(current.getOrderID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!Constants.selectedOrder.equals("Не указано")) nameOrder = selectedOrder.getNameOrder();

        float amountConcrete = 0;
        String paymentOption = "-";

        if (selectedOrder != null) {
            amountConcrete = Float.parseFloat(selectedOrder.getAmountConcrete());
            paymentOption = selectedOrder.getPaymentOption();
        }

        //округление строго обязательно
        Mix mix = new Mix(
                nameOrder,
                numberOrder,
                dateFormat.format(new Date()),
                timeFormat.format(new Date()),
                uploadingAddress,
                amountConcrete,
                paymentOption,
                operatorName,
                organization,
                organizationID,
                numberAuto,
                transporterID,
                currentRecepie,
                recipeID,
                retrieval.getMixCounterValue(),
                capacityMixer,
                totalWeight,
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper11Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper12Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper21Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper22Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper31Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper32Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper41Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseHopper42Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseSilos1Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseSilos2Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseWaterValue()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseWater2Value()).replaceAll(",", ".")),
                retrieval.getWaterPumpCounterValue() * 10,//*10 потому что 1 цикл счетчика - это 10 литров
                Float.parseFloat(decFormat.format(retrieval.getDoseChemy1Value()).replaceAll(",", ".")),
                Float.parseFloat(decFormat.format(retrieval.getDoseChemy2Value()).replaceAll(",", ".")),
                calcTimeMix
        );

        // путь первый передача в локальную sql базу
        new DBUtilInsert(context).insertIntoMix(mix);
        //обязательная установка флага, чтобы завод продолжил свою работу
        new CommandDispatcher(tagListManual.get(26)).writeSingleRegisterWithValue(false); //метка веса сохранены

    }

}
