package ru.zzbo.concretemobile.utils;

import java.util.ArrayList;
import java.util.List;

public class DatesGenerate {

    private String dateBegin;
    private String dateEnd;
    private List<String> lostDates = new ArrayList<>();

    public DatesGenerate(String dateBegin, String dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        gen();
    }

    private void gen() {
        //получить последнюю дату
        String dateBegin = this.dateBegin;

        //узнать какое сегодня число
        String dateEnd = this.dateEnd;

        //проверка на совпадения
        if (dateEnd.equals(dateBegin)){
            System.out.println("Даты совпадают, ничего делать не нужно");
            this.lostDates.add(dateBegin);
            return;
        }
        this.lostDates.add(dateBegin);

        System.out.println("dateEnd: " + dateEnd);
        System.out.println("dateBegin: " + dateBegin);

        char[] parseDateLast = dateEnd.toCharArray();
        char[] parseDateToday = dateBegin.toCharArray();;

        String yearEnd = String.valueOf(parseDateLast[6]) + String.valueOf(parseDateLast[7]) + String.valueOf(parseDateLast[8]) + String.valueOf(parseDateLast[9]);
        String monthEnd = String.valueOf(parseDateLast[3]) + String.valueOf(parseDateLast[4]);
        String dayEnd = String.valueOf(parseDateLast[0]) + String.valueOf(parseDateLast[1]);

        String yearBegin = String.valueOf(parseDateToday[6]) + String.valueOf(parseDateToday[7]) + String.valueOf(parseDateToday[8]) + String.valueOf(parseDateToday[9]);
        String monthBegin = String.valueOf(parseDateToday[3]) + String.valueOf(parseDateToday[4]);
        String dayBegint = String.valueOf(parseDateToday[0]) + String.valueOf(parseDateToday[1]);

        int yearLastInt = Integer.valueOf(yearEnd);
        int monthLastInt = Integer.valueOf(monthEnd);
        int dayLastInt = Integer.valueOf(dayEnd);

        int yearTodayInt = Integer.valueOf(yearBegin);
        int monthTodayInt = Integer.valueOf(monthBegin);
        int dayTodayInt = Integer.valueOf(dayBegint);

        String tmpDay;
        if (yearLastInt >= yearTodayInt){
            for (int i = dayTodayInt; i <= 366; i++) {

                dayTodayInt++;
                if (monthTodayInt < 10){
                    String tmpMonth = "0" + monthTodayInt;
                    if (dayTodayInt<10) {
                        tmpDay = "0" + dayTodayInt;
                    } else {
                        tmpDay = String.valueOf(dayTodayInt);
                    }
                    lostDates.add(tmpDay + "." + tmpMonth + "." + yearTodayInt);
                }

                if (monthTodayInt>=10){
                    if (dayTodayInt<10) {
                        tmpDay = "0" + dayTodayInt;
                    } else {
                        tmpDay = String.valueOf(dayTodayInt);
                    }
                    lostDates.add(tmpDay + "." + monthTodayInt + "." + yearTodayInt);
                }

                if (monthTodayInt < 7) { //проверка с января по июнь
                    if (dayTodayInt == 31) {
                        if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;
                        if (monthTodayInt % 2 == 1) {
                            dayTodayInt = 0;
                            monthTodayInt++;
                        }
                    }
                    if (dayTodayInt == 30) {
                        if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;
                        if (monthTodayInt % 2 == 0) {
                            dayTodayInt = 0;
                            monthTodayInt++;
                        }
                    }

                    if ((dayTodayInt == 28) && (monthTodayInt == 2)) { //проверка на високосный год
                        if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;

                        if ( (yearTodayInt == yearLastInt) && (monthTodayInt == monthLastInt)) {
                            if ((dayLastInt == 29) ||(dayLastInt == 30) || (dayLastInt == 31)) break;
                        }

                        if (yearTodayInt % 4 != 0) { //год не високосный
                            dayTodayInt = 0;
                            monthTodayInt++;
                        }
                    } else if ((dayTodayInt == 29) && (monthTodayInt == 2)) {
                        if ( (yearTodayInt == yearLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;

                        if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt)) {
                            if ((dayLastInt == 30) || (dayLastInt == 31)) break;
                        }

                        if (yearTodayInt % 4 == 0) {
                            if ((yearTodayInt % 100 != 0) || (yearTodayInt % 400 == 0)) {
                                dayTodayInt = 0;
                                monthTodayInt++;
                            }
                        }
                    }
                } else if (monthTodayInt != 7) { //проверка с июня по декабрь
                    if (dayTodayInt == 30) {
                        if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;
                        if (monthTodayInt % 2 == 1) {
                            dayTodayInt = 0;
                            monthTodayInt++;
                        }
                    }
                    if (dayTodayInt == 31) {
                        if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;
                        if (monthTodayInt % 2 == 0) {
                            dayTodayInt = 0;
                            monthTodayInt++;
                        }
                    }
                } else if (dayTodayInt == 31) {
                    dayTodayInt = 0;
                    monthTodayInt++;
                }
                if (monthTodayInt > 12){
                    monthTodayInt = 1;
                    dayTodayInt=0;
                    yearTodayInt++;
                }
                if ( (dayTodayInt == dayLastInt) && (monthTodayInt == monthLastInt) && (yearTodayInt == yearLastInt) ) break;

            }
        } else {
            this.lostDates = null;
        }
    }

    public List<String> getLostDates() {
        for (int i = 0; i < lostDates.size(); i++) {
            System.out.println(lostDates.get(i));
        }
        return lostDates;
    }

}
