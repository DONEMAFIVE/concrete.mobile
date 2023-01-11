package ru.zzbo.concretemobile.utils;

import android.widget.DatePicker;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    public static SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static SimpleDateFormat fullTimeFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Проверка - дата начала периода не может быть больше даты его окончания
     *
     * @param start
     * @param end
     * @return если true то дата начала больше
     */
    public static boolean startLongerEnd(String start, String end) {
        try {
            Date a = fullDateFormat.parse(start);
            Date b = fullDateFormat.parse(end);
            return b.compareTo(a) < 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Суммирование времени
     *
     * @param time список со временем
     */
    public String sumTimes(ArrayList<String> time) {
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .minimumPrintedDigits(2)
                .printZeroAlways()
                .appendHours()
                .appendLiteral(":")
                .appendMinutes()
                .appendLiteral(":")
                .appendSeconds()
                .toFormatter();

        ArrayList<Period> periods = new ArrayList<>();
        String res = null;
        for (int i = 0; i < time.size(); i++) {
            String checkedString = time.get(i);
            if (chkStringForTimeFormat(checkedString))
                periods.add(formatter.parsePeriod(time.get(i)));
            else periods.add(formatter.parsePeriod("00:00:00"));
        }

        for (int j = 1; j < time.size(); j++) {
            res = formatter.print(periods.get(j).plus(periods.get(j - 1)));
            periods.set(j, formatter.parsePeriod(res));
        }

        if (res == null) return "00:00:00";
        return res;
    }

    /**
     * Проверка является строка датой формата HH:mm:ss
     * @param inDate
     * @return
     */
    public boolean chkStringForTimeFormat(String inDate) {
        fullTimeFormat.setLenient(false);
        try {
            fullTimeFormat.parse(inDate.trim());
        } catch (Exception pe) {
            return false;
        }
        return true;
    }

    public static Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
