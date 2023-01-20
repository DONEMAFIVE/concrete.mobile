package ru.zzbo.concretemobile.protocol;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.tagListMain;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

import android.content.Context;

import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.protocol.profinet.collectors.DynamicTagCollector;

/**
 * назначить слой для сбора данных
 */
public class DataManager {

    //todo: доступные варианты параметр exchange_level:
    // 0 - берем и пишем данные из ПЛК напрямую
    // 1 - берем и пишем все в скаду
    // 2 - получаем данные из rest сервера, на процесс производства влиять не можем
    // 3 - читаем и пишем только в rest сервер

    private Context context;

    public DataManager(Context context) {
        this.context = context;
    }

    public void runCollector() {
        tagListMain = new DBTags(context).getTags("tags_main");
        tagListManual = new DBTags(context).getTags("tags_manual");

        DynamicTagCollector service = DynamicTagCollector.getTagCollector();

        switch (exchangeLevel) {
            case 0: new Thread(() -> service.getValuesFromPLC()).start(); break;  //PLC
            case 1: new Thread(() -> service.getValuesFromPC(context)).start(); break;   //PC
        }

    }

}
