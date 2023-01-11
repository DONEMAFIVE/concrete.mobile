package ru.zzbo.concretemobile.protocol.profinet.collectors.request;

import static ru.zzbo.concretemobile.db.DBConstants.TABLE_NAME_CONFIG;
import static ru.zzbo.concretemobile.utils.Constants.configList;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.models.Parameter;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7Client;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.Constants;

/**
 * Используется только для калибровки весов, считывает аналоговый сигнал и калиброванный
 */
public class WeightCollector {

    private float weightDK;
    private float weightWater;
    private float weightCement;
    private float weightChemy;
    private float weightFibra;

    private int analogDK;
    private int analogWater;
    private int analogCement;
    private int analogChemy;
    private int analogFibra;

    private Context context;

    public WeightCollector(Context context){
        this.context = context;
        configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(context).getFromParameterTable(TABLE_NAME_CONFIG));
    }


    public void getValues(){
        List<Tag> tagListRuntime = new DBTags(context).getTags("tags_main");
        List<Tag> tagListManual = new DBTags(context).getTags("tags_manual");

        S7Client plcConnector;
        int connectonState;

        plcConnector = new S7Client();
        plcConnector.SetConnectionType(S7.S7_BASIC);
        connectonState = plcConnector.ConnectTo(configList.getPlcIP(), Constants.RACK, Constants.SLOT);
        if (connectonState == 0) {
            byte[] bufferInt = new byte[2];
            byte[] bufferReal = new byte[4];

            Tag tagWeightDK = tagListRuntime.get(93);
            Tag tagWeightWater = tagListRuntime.get(94);
            Tag tagWeightChemy = tagListRuntime.get(95);
            Tag tagWeightCement = tagListRuntime.get(96);
            Tag tagWeightFibra = tagListRuntime.get(155);

            Tag tagAnalogWeightDK = tagListManual.get(72);
            Tag tagAnalogWeightWater = tagListManual.get(73);
            Tag tagAnalogWeightCement = tagListManual.get(74);
            Tag tagAnalogWeightChemy = tagListManual.get(75);
            Tag tagAnalogWeightFibra = tagListManual.get(117);

            plcConnector.ReadArea(tagWeightDK.getArea(), tagWeightDK.getDbNumber(), tagWeightDK.getStart(), 4, bufferReal);
            weightDK = S7.GetFloatAt(bufferReal, 0);

            plcConnector.ReadArea(tagWeightWater.getArea(), tagWeightWater.getDbNumber(), tagWeightWater.getStart(), 4, bufferReal);
            weightWater = S7.GetFloatAt(bufferReal, 0);

            plcConnector.ReadArea(tagWeightCement.getArea(), tagWeightCement.getDbNumber(), tagWeightCement.getStart(), 4, bufferReal);
            weightCement = S7.GetFloatAt(bufferReal, 0);

            plcConnector.ReadArea(tagWeightChemy.getArea(), tagWeightChemy.getDbNumber(), tagWeightChemy.getStart(), 4, bufferReal);
            weightChemy = S7.GetFloatAt(bufferReal, 0);

            plcConnector.ReadArea(tagWeightFibra.getArea(), tagWeightFibra.getDbNumber(), tagWeightFibra.getStart(), 4, bufferReal);
            weightFibra = S7.GetFloatAt(bufferReal, 0);

            plcConnector.ReadArea(tagAnalogWeightWater.getArea(), tagAnalogWeightWater.getDbNumber(), tagAnalogWeightWater.getStart(), 2, bufferInt);
            analogWater = S7.GetShortAt(bufferInt, 0);

            plcConnector.ReadArea(tagAnalogWeightDK.getArea(), tagAnalogWeightDK.getDbNumber(), tagAnalogWeightDK.getStart(), 2, bufferInt);
            analogDK = S7.GetShortAt(bufferInt, 0);

            plcConnector.ReadArea(tagAnalogWeightCement.getArea(), tagAnalogWeightCement.getDbNumber(), tagAnalogWeightCement.getStart(), 2, bufferInt);
            analogCement = S7.GetShortAt(bufferInt, 0);

            plcConnector.ReadArea(tagAnalogWeightChemy.getArea(), tagAnalogWeightChemy.getDbNumber(), tagAnalogWeightChemy.getStart(), 2, bufferInt);
            analogChemy = S7.GetShortAt(bufferInt, 0);

            plcConnector.ReadArea(tagAnalogWeightFibra.getArea(), tagAnalogWeightFibra.getDbNumber(), tagAnalogWeightFibra.getStart(), 2, bufferInt);
            analogFibra = S7.GetShortAt(bufferInt, 0);

            plcConnector.Disconnect();
            try{Thread.sleep(500);}catch(InterruptedException e){e.printStackTrace();}
        } else {
            Log.i("WEIGHT COLLECTOR", "[" + new Date() + "] Weights Collector report ... PLC Connection lost!");
        }
    }

    public float getWeightDK() {
        return weightDK;
    }

    public float getWeightWater() {
        return weightWater;
    }

    public float getWeightCement() {
        return weightCement;
    }

    public int getAnalogDK() {
        return analogDK;
    }

    public int getAnalogWater() {
        return analogWater;
    }

    public int getAnalogCement() {
        return analogCement;
    }

    public float getWeightChemy() {
        return weightChemy;
    }

    public int getAnalogChemy() {
        return analogChemy;
    }

    public float getWeightFibra() {
        return weightFibra;
    }

    public int getAnalogFibra() {
        return analogFibra;
    }
}
