package ru.zzbo.concretemobile.protocol.profinet.commands;

import static ru.zzbo.concretemobile.utils.Constants.REQUEST_TAG_SLEEP;
import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.tagListMain;
import static ru.zzbo.concretemobile.utils.OkHttpUtil.sendGet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Request;
import okhttp3.Response;
import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7Client;
import ru.zzbo.concretemobile.protocol.profinet.models.BlockBool;
import ru.zzbo.concretemobile.protocol.profinet.models.BlockMultiple;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.Constants;

public class CommandDispatcher {
    private Tag tag;
    private List<Tag> listTag;
    private String query;

    private S7Client plcConnector;
    private int connectionState;

    public CommandDispatcher() {
    }

    /**
     * метод writeSingleRegister работает с тем тэгом, который был передан в конструктор,
     * а блочным считываниям (методы read...) в конструктор ничего передавать не надо, им передается блок тэгов в поле метода,
     * поэтому в классе CommandDispatcher объявлен конструктор без параметров. TODO: добавить проверку на инициализированный тэг при выполнение write...?
     *
     * @param tag
     */
    public CommandDispatcher(Tag tag) {
        this.tag = tag;
        query = "cmd?tag=" + tag.getId();
//        System.out.println(tag);
    }

    public CommandDispatcher(int tagId) {
        try {
            query = "cmd?tag=" + tagId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeValue(String value) {
        try {
            sendGet(query + "&value=" + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeInverted() {
        try {
            //query = "cmd?tag=" + tag.getId();
            sendGet(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandDispatcher(List<Tag> listTag) {
        this.listTag = listTag;
    }

    private void createConnection() {
        plcConnector = new S7Client();
        plcConnector.SetConnectionType(S7.S7_BASIC);
        connectionState = plcConnector.ConnectTo(configList.getPlcIP(), 0, 1);
    }

    private void closeConnection() {
        plcConnector.Disconnect();
    }

    private void checkConnection() {
        while (connectionState != 0) {  //проверка на подключение
            System.err.println("PLC connection lost ...");
            closeConnection();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createConnection();
            break;
        }
    }

    //для работы только с логическими и только когда нужно инвертировать состояние
    public void writeSingleInvertedBoolRegister() {
        if (chkZeroZone(tag)) return;
        new Thread(()->{
            if (exchangeLevel == 1) writeInverted();
            else {
                try {
                    Constants.lockStateRequests = true;
                    Thread.sleep(REQUEST_TAG_SLEEP);
                    createConnection();
                    checkConnection();
                    if (connectionState == 0) {
                        byte[] buffer = new byte[1];    //для логических достаточно буфера размером в 1 байт
                        plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                        boolean state = S7.GetBitAt(buffer, 0, tag.getBit());
                        if (state) {
                            S7.SetBitAt(buffer, 0, tag.getBit(), false);
                            plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                        } else {
                            S7.SetBitAt(buffer, 0, tag.getBit(), true);
                            plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                    Constants.lockStateRequests = false;
                }
            }
        }).start();
    }

    //для работы только с логическими, только когда нужна отправка команды по фронту - отправить true, через timer милисекунд false
    public void writeSingleFrontBoolRegister(int timer) {
        if (chkZeroZone(tag)) return;
        new Thread(() -> {
            try {
                Constants.lockStateRequests = true;
                Thread.sleep(REQUEST_TAG_SLEEP);
                createConnection();
                checkConnection();
                if (connectionState == 0) {
                    byte[] buffer = new byte[1];    //для логических достаточно буфера размером в 1 байт
                    plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                    S7.SetBitAt(buffer, 0, tag.getBit(), true);
                    plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                    try {
                        Thread.sleep(timer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                    S7.SetBitAt(buffer, 0, tag.getBit(), false);
                    plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnection();
                Constants.lockStateRequests = false;
            }
        }).start();
    }

    //только для логических переменных, и только когда нужно отправить конкретное состояние value вкл или выкл
    public void writeSingleRegisterWithValue(boolean value) {
        if (chkZeroZone(tag)) return;

        new Thread(()->{
            if (exchangeLevel == 1) writeValue(String.valueOf(value));
            else {
                try {
                    Constants.lockStateRequests = true;
                    Thread.sleep(REQUEST_TAG_SLEEP);
                    createConnection();
                    checkConnection();
                    if (connectionState == 0) {
                        byte[] buffer = new byte[1];    //для логических достаточно буфера размером в 1 байт
                        plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                        S7.SetBitAt(buffer, 0, tag.getBit(), value);
                        plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                    Constants.lockStateRequests = false;
                }
            }
        }).start();
    }

    /**
     * здесь внимательно - этот метод используется когда блокировка чтения потока вызывается снаружи
     * пример использования - загрузка рецепта в панель, нужно выполнить с десяток операций записи в контроллер
     * поток чтения пока идет запись должен спать.
     * Ответственность при использовании этого метода ложиться на программиста, блокировки lockStateRequest устанавливаются вручную, извне
     */
    public void writeSingleRegisterWithoutLock() {
        if (chkZeroZone(tag)) return;
        try {
            Thread.sleep(REQUEST_TAG_SLEEP);
            createConnection();
            checkConnection();
            byte[] buffer;
            if (tag.getTypeTag().equals("Bool")) {   //обрабатываем переменную логического типа
                buffer = new byte[1];
                plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                S7.SetBitAt(buffer, 0, tag.getBit(), tag.isBoolValueIf());
            }
            if (tag.getTypeTag().equals("Real")) {   //число с плавающей точкой
                buffer = new byte[4];
                S7.SetFloatAt(buffer, 0, tag.getRealValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
            }
            if (tag.getTypeTag().equals("Int")) {    //целочисленные
                buffer = new byte[2];
                S7.SetShortAt(buffer, 0, tag.getIntValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 2, buffer);
            }
            if (tag.getTypeTag().equals("DInt")) {   //большие целочисленные
                buffer = new byte[4];
                S7.SetDIntAt(buffer, 0, (int) tag.getDIntValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
            }
            Thread.sleep(REQUEST_TAG_SLEEP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }

    //для отправки значения любого типа, тэг передается в конструктор
    public void writeSingleRegisterWithLock() {

        if (chkZeroZone(tag)) return;

        try {
            Constants.lockStateRequests = true;
            try {
                Thread.sleep(REQUEST_TAG_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createConnection();
            checkConnection();
            byte[] buffer;
            if (tag.getTypeTag().equals("Bool")) {   //обрабатываем переменную логического типа
                buffer = new byte[1];
                plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                S7.SetBitAt(buffer, 0, tag.getBit(), tag.isBoolValueIf());
            }
            if (tag.getTypeTag().equals("Real")) {   //число с плавающей точкой
                buffer = new byte[4];
                S7.SetFloatAt(buffer, 0, tag.getRealValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
            }
            if (tag.getTypeTag().equals("Int")) {    //целочисленные
                buffer = new byte[2];
                S7.SetShortAt(buffer, 0, tag.getIntValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 2, buffer);
            }
            if (tag.getTypeTag().equals("DInt")) {   //большие целочисленные
                buffer = new byte[4];
                S7.SetDIntAt(buffer, 0, (int) tag.getDIntValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
            }
            if (tag.getTypeTag().equals("String")) {
                buffer = new byte[100];
                S7.setS7StringAt(buffer, 0, 100, tag.getStringValueIf());
                plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 100, buffer);
            }
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
            Constants.lockStateRequests = false;
        }
    }

    /**
     * работает точно также как writeSingleRegister, с разницей в том, что запись производиттся в параллельном некотролируемом потоке.
     * применяется только там, где нужно обновлять UI без ожидания окончания работы записи команды
     */
    public void writeSingleRegisterWithThread() {

        if (chkZeroZone(tag)) return;

        new Thread(() -> {
            try {
                Constants.lockStateRequests = true;
                try {
                    Thread.sleep(REQUEST_TAG_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createConnection();
                checkConnection();
                byte[] buffer;
                checkConnection();
                if (tag.getTypeTag().equals("Bool")) {   //обрабатываем переменную логического типа
                    buffer = new byte[1];
                    plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
                    S7.SetBitAt(buffer, 0, tag.getBit(), tag.isBoolValueIf());
                }
                if (tag.getTypeTag().equals("Real")) {   //число с плавающей точкой
                    buffer = new byte[4];
                    S7.SetFloatAt(buffer, 0, tag.getRealValueIf());
                    plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
                }
                if (tag.getTypeTag().equals("Int")) {    //целочисленные
                    buffer = new byte[2];
                    S7.SetShortAt(buffer, 0, tag.getIntValueIf());
                    plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 2, buffer);
                }
                if (tag.getTypeTag().equals("DInt")) {   //большие целочисленные
                    buffer = new byte[4];
                    S7.SetDIntAt(buffer, 0, (int) tag.getDIntValueIf());
                    plcConnector.WriteArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnection();
                Constants.lockStateRequests = false;
            }
        }).start();
    }

    public Tag readSingleRegister() {

        checkStateWrite("readSingleRegister");
        createConnection();
        checkConnection();

        if (connectionState != 0) {  //проверка на подключение
            closeConnection();
            return null;
        }

        byte[] buffer;
        if (connectionState != 0) return null;  //проверка на подключение

        if (tag.getTypeTag().equals("Bool")) {   //обрабатываем переменную логического типа
            buffer = new byte[1];
            plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 1, buffer);
            tag.setBoolValueIf(S7.GetBitAt(buffer, 0, tag.getBit()));
            closeConnection();
            return tag;
        }
        if (tag.getTypeTag().equals("Real")) {   //число с плавающей точкой
            buffer = new byte[4];
            plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
            tag.setRealValueIf(S7.GetFloatAt(buffer, 0));
            closeConnection();
            return tag;
        }
        if (tag.getTypeTag().equals("Int")) {    //целочисленные
            buffer = new byte[2];
            plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 2, buffer);
            tag.setIntValueIf(S7.GetShortAt(buffer, 0));
            closeConnection();
            return tag;
        }
        if (tag.getTypeTag().equals("DInt")) {   //большие целочисленные
            buffer = new byte[4];
            plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 4, buffer);
            tag.setDIntValueIf(S7.GetDIntAt(buffer, 0));
            closeConnection();
            return tag;
        }
        if (tag.getTypeTag().equals("String")) {
            buffer = new byte[100];
            plcConnector.ReadArea(tag.getArea(), tag.getDbNumber(), tag.getStart(), 98, buffer);
            tag.setStringValueIf(S7.GetStringAt(buffer, 2,98));
            closeConnection();
            return tag;
        }
        closeConnection();
        return null;
    }

    /**
     * с чтением большой области памяти где есть длинна length, есть ньюансы -
     * объект plcConnector запросит данные указанной длинны, для int, real, DInt ограничений в общем нет, проси сколько хочешь,
     * у bool есть биты, за запрос больше 8 бит, без сдвига, забирать нельзя. Текущая реализация алгоритма формируя пакет bool на запрос
     * не использует больше 8 бит.
     */
    public List<Tag> readMultipleBoolRegister(List<BlockBool> boolList) {
        //прочитать пул регистров, получить значения
        //выставить соответствие с основным листом тэгов
        //вернуть лист тэгов
        //найти все DB зоны в входящем списке тэгов

        checkStateWrite("readMultipleBoolRegister");

        List<Tag> resultList = new ArrayList<>();

        createConnection();
        byte[] singleRegisterBuffer;
        boolean[] readedSection;
        for (BlockBool tag : boolList) {
            singleRegisterBuffer = new byte[tag.getLength()];
            plcConnector.ReadArea(tag.getDbArea(), tag.getNumber(), tag.getStart(), tag.getLength(), singleRegisterBuffer);
            readedSection = S7.GetBitAtValues(singleRegisterBuffer, 0, tag.getStartBit(), tag.getLength());

            for (int i = tag.getStartBit(), boolIndex = 0; i < tag.getStartBit() + tag.getLength(); i++, boolIndex++) {
                resultList.add(new Tag(
                        tag.getDbArea(),
                        tag.getNumber(),
                        tag.getStart(),
                        i,
                        "Bool",
                        readedSection[boolIndex],
                        0,
                        0,
                        0,
                        "",
                        "",
                        0
                ));
            }
        }

        List<Tag> tableTags = tagListMain;

        for (Tag tag : resultList) {
            for (Tag tableTag : tableTags) {
                if ((tableTag.getArea() == tag.getArea()) &&
                        (tableTag.getDbNumber() == tag.getDbNumber()) &&
                        (tableTag.getStart() == tag.getStart()) &&
                        (tableTag.getBit() == tag.getBit())
                ) {
                    tag.setId(tableTag.getId());
                    tag.setDescription(tableTag.getDescription());
                }
            }
        }

        closeConnection();
        return resultList;

    }

    /**
     * Написана только блочная обработка для Real типа данных
     *
     * @param tags
     * @return
     */
    public List<Tag> readMultipleRealRegister(List<BlockMultiple> tags, List<Tag> tableTags) {

        checkStateWrite("readMultipleRealRegister");

        List<Tag> resultList = new ArrayList<>();
        createConnection();

        byte[] singleRegisterBuffer;
        float[] answerArray;
        for (BlockMultiple tag : tags) {
            singleRegisterBuffer = new byte[tag.getLength() * 4];
            plcConnector.ReadArea(tag.getDbArea(), tag.getNumber(), tag.getStartValue(), tag.getLength() * 4, singleRegisterBuffer);
            answerArray = S7.GetFloatAtValues(singleRegisterBuffer, 0, tag.getLength());

            int lengthAnswer = tag.getLength() * 4;
            lengthAnswer += tag.getStartValue();

            for (int i = tag.getStartValue(), realIndex = 0; i < lengthAnswer; i += tag.getStep(), realIndex++) {
                resultList.add(new Tag(
                        tag.getDbArea(),
                        tag.getNumber(),
                        i,
                        0,
                        "Real",
                        false,
                        0,
                        0,
                        answerArray[realIndex],
                        "",
                        "",
                        0
                ));
            }
        }

        for (Tag tag : resultList) {
            for (Tag tableTag : tableTags) {
                if ((tableTag.getArea() == tag.getArea()) &&
                        (tableTag.getDbNumber() == tag.getDbNumber()) &&
                        (tableTag.getStart() == tag.getStart())
                ) {
                    tag.setId(tableTag.getId());
                    tag.setDescription(tableTag.getDescription());
                }
            }
        }

        closeConnection();
        return resultList;
    }

    public List<Tag> readMultipleIntRegister(List<BlockMultiple> tags, List<Tag> tableTags) {
        checkStateWrite("readMultipleIntRegister");

        List<Tag> resultList = new ArrayList<>();

        createConnection();

        byte[] singleRegisterBuffer;
        int[] answerArray;
        for (BlockMultiple tag : tags) {
            singleRegisterBuffer = new byte[tag.getLength() * 2];
            plcConnector.ReadArea(tag.getDbArea(), tag.getNumber(), tag.getStartValue(), tag.getLength() * 2, singleRegisterBuffer);
            answerArray = S7.GetIntMultipleRegister(singleRegisterBuffer, 0, tag.getLength());

            int lengthAnswer = tag.getLength() * 2;
            lengthAnswer += tag.getStartValue();

            for (int i = tag.getStartValue(), intIndex = 0; i < lengthAnswer; i += tag.getStep(), intIndex++) {
                resultList.add(new Tag(
                        tag.getDbArea(),
                        tag.getNumber(),
                        i,
                        0,
                        "Int",
                        false,
                        answerArray[intIndex],
                        0,
                        0,
                        "",
                        "",
                        0
                ));
            }
        }

        for (Tag tag : resultList) {
            for (Tag tableTag : tableTags) {
                if ((tableTag.getArea() == tag.getArea()) &&
                        (tableTag.getDbNumber() == tag.getDbNumber()) &&
                        (tableTag.getStart() == tag.getStart())
                ) {
                    tag.setId(tableTag.getId());
                    tag.setDescription(tableTag.getDescription());
                }
            }
        }

        closeConnection();

        return resultList;
    }

    public List<Tag> readMultipleDIntRegister(List<BlockMultiple> tags, List<Tag> tableTags) {
        checkStateWrite("readMultipleDIntRegister");
        List<Tag> resultList = new ArrayList<>();
        createConnection();

        byte[] singleRegisterBuffer;
        int[] answerArray;
        for (BlockMultiple tag : tags) {
            singleRegisterBuffer = new byte[tag.getLength() * 4];
            plcConnector.ReadArea(tag.getDbArea(), tag.getNumber(), tag.getStartValue(), tag.getLength() * 4, singleRegisterBuffer);
            answerArray = S7.get4bitMultipleRegister(singleRegisterBuffer, 0, tag.getLength());

            int lengthAnswer = tag.getLength() * 4;
            lengthAnswer += tag.getStartValue();

            for (int i = tag.getStartValue(), dIntIndex = 0; i < lengthAnswer; i += tag.getStep(), dIntIndex++) {
                resultList.add(new Tag(
                        tag.getDbArea(),
                        tag.getNumber(),
                        i,
                        0,
                        "DInt",
                        false,
                        0,
                        answerArray[dIntIndex],
                        0,
                        "",
                        "",
                        0
                ));
            }
        }

        for (Tag tag : resultList) {
            for (Tag tableTag : tableTags) {
                if ((tableTag.getArea() == tag.getArea()) &&
                        (tableTag.getDbNumber() == tag.getDbNumber()) &&
                        (tableTag.getStart() == tag.getStart())
                ) {
                    tag.setId(tableTag.getId());
                    tag.setDescription(tableTag.getDescription());
                }
            }
        }

        closeConnection();

        return resultList;
    }

    /**
     * если в данный момент идет завершение команды чтения то ждать указанное время
     */
    public void checkStateWrite(String whoAreYou) {
        if (Constants.lockStateRequests) {
            System.out.println("[INFO] " + whoAreYou + " lock thread read state");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //защита от дурака - проверка на DB0.0.0 адрес, ничего туда записывать нельзя
    private boolean chkZeroZone(Tag tag) {
        if ((tag.getStart() == 0) && (tag.getDbNumber() == 0) && (tag.getBit() == 0)) return true;
        return false;
    }
}
