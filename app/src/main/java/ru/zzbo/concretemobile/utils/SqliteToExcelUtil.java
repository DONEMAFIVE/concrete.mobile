package ru.zzbo.concretemobile.utils;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.models.Mix;

public class SqliteToExcelUtil {
    private Context context;
    private String folderReports = "/reports/";
    private List<String> dates;
    private XSSFWorkbook book;
    private XSSFSheet mixesSheet;
    private XSSFSheet partySheet;
    private XSSFSheet markSheet;
    private XSSFSheet totalSheet;
    private List<Mix> report;
    private String dateStart;
    private String dateEnd;
    private Gson gson;

    public SqliteToExcelUtil(Context context, String dateStart, String dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.context = context;
        this.dates = new DatesGenerate(dateStart, dateEnd).getLostDates();
        this.book = new XSSFWorkbook();
        this.gson = new Gson();

        File folder = new File(Environment.getExternalStorageDirectory() + folderReports);
        if (!folder.exists()) folder.mkdir();
    }

    //TODO Отчет по замесам [v]
    public void createSheetMixes(boolean mixesEmptyCementLess) {
        mixesSheet = book.createSheet("Отчет по замесам");
        List<List<String>> dataTable = new ArrayList<>();

        if (exchangeLevel == 1) {
            report = gson.fromJson(OkHttpUtil.getMixes(dateStart, dateEnd), new TypeToken<List<Mix>>() {
            }.getType());
        } else report = new DBUtilGet(context).getMixList();

        String singleRow = "ID | Заказ | № заказа | Дата | Время | Заказчик | ID Заказчика | Перевозчик | ID Перевозчика | Рецепт | ID Рецепта | Замес | Партия | Объем | Силос 1 | Силос 2 | Бункер 11 | Бункер 12 | Бункер 21 | Бункер 22 | Бункер 31 | Бункер 32 | Бункер 41 | Бункер 42 | Вода 1 | Вода 2 | ДВПЛ | Химия 1 | Химия 2 | Адрес выгрузки | Стоимость | Способ оплаты | Оператор | Время погрузки";

        List<String> headTable = Arrays.asList(singleRow.split("\\|"));
        dataTable.add(headTable);

        new Handler(Looper.getMainLooper()).post(() -> {
            List<String> dataForm;
            try {
                for (String date : dates) {
                    for (Mix mix : report) {
                        if ((mixesEmptyCementLess) && (mix.getSilos1() == 0)) continue;
                        if ((mixesEmptyCementLess) && (mix.getSilos2() == 0)) continue;

                        if (mix.getDate().equals(date)) {
                            dataForm = new ArrayList<>();
                            dataForm.add(String.valueOf(mix.getId()));
                            dataForm.add(mix.getNameOrder());
                            dataForm.add(String.valueOf(mix.getNumberOrder()));
                            dataForm.add(mix.getDate());
                            dataForm.add(mix.getTime());
                            dataForm.add(mix.getOrganization());
                            dataForm.add(String.valueOf(mix.getOrganizationID()));
                            dataForm.add(mix.getTransporter());
                            dataForm.add(String.valueOf(mix.getTransporterID()));
                            dataForm.add(mix.getRecepie());
                            dataForm.add(String.valueOf(mix.getRecepieID()));
                            dataForm.add(String.valueOf(mix.getMixCounter()));
                            dataForm.add(String.valueOf(mix.getCompleteCapacity()));
                            dataForm.add(String.valueOf(mix.getTotalCapacity()));
                            dataForm.add(String.valueOf(mix.getSilos1()));
                            dataForm.add(String.valueOf(mix.getSilos2()));
                            dataForm.add(String.valueOf(mix.getBuncker11()));
                            dataForm.add(String.valueOf(mix.getBuncker12()));
                            dataForm.add(String.valueOf(mix.getBuncker21()));
                            dataForm.add(String.valueOf(mix.getBuncker22()));
                            dataForm.add(String.valueOf(mix.getBuncker31()));
                            dataForm.add(String.valueOf(mix.getBuncker32()));
                            dataForm.add(String.valueOf(mix.getBuncker41()));
                            dataForm.add(String.valueOf(mix.getBuncker41()));
                            dataForm.add(String.valueOf(mix.getBuncker42()));
                            dataForm.add(String.valueOf(mix.getWater1()));
                            dataForm.add(String.valueOf(mix.getWater2()));
                            dataForm.add(String.valueOf(mix.getDwpl()));
                            dataForm.add(String.valueOf(mix.getChemy1()));
                            dataForm.add(String.valueOf(mix.getChemy2()));
                            dataForm.add(mix.getUploadAddress());
                            dataForm.add(String.valueOf(mix.getAmountConcrete()));
                            dataForm.add(mix.getPaymentOption());
                            dataForm.add(mix.getOperator());
                            dataForm.add(mix.getLoadingTime());

                            dataTable.add(dataForm);
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        try {
            for (int x = 0; x < dataTable.size(); x++) {
                List<String> arr = dataTable.get(x);
                XSSFRow row = mixesSheet.createRow(x);

                for (int i = 0; i < headTable.size(); i++) { //column
                    for (int s = 0; s < arr.size(); s++) {
                        String[] data = arr.get(s).split(", ");
                        XSSFCell cell = row.createCell(s);
                        for (int c = 0; c < data.length; c++) {
                            cell.setCellValue(data[c]);
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //TODO Отчет по партиям [v]
    public void createSheetParty(boolean mixesEmptyCementLess) {
        partySheet = book.createSheet("Отчет по партиям");
        List<List<String>> dataTable = new ArrayList<>();

        String singleRow = "ID | Заказ | № заказа | Дата | Время | Заказчик | ID Заказчика | Перевозчик | ID Перевозчика | Рецепт | ID Рецепта | Замес | Партия | Объем | Силос 1 | Силос 2 | Бункер 11 | Бункер 12 | Бункер 21 | Бункер 22 | Бункер 31 | Бункер 32 | Бункер 41 | Бункер 42 | Вода 1 | Вода 2 | ДВПЛ | Химия 1 | Химия 2 | Адрес выгрузки | Стоимость | Способ оплаты | Оператор | Время погрузки";
        List<String> headTable = Arrays.asList(singleRow.split("\\|"));
        dataTable.add(headTable);
        try {

            if (exchangeLevel == 1) {
                report = gson.fromJson(OkHttpUtil.getMixes(dateStart, dateEnd), new TypeToken<List<Mix>>() {
                }.getType());
            } else report = new DBUtilGet(context).getMixList();

            new Handler(Looper.getMainLooper()).post(() -> {
                List<String> dataForm;

                List<Mix> currentReport = new ArrayList<>();
                List<Mix> readyPartyReport = new ArrayList<>();

                for (String date : dates) {
                    for (Mix mixParty : report) {
                        if (mixParty.getDate().equals(date)) {
                            currentReport.add(mixParty);
                            readyPartyReport = buildPartyList(currentReport);
                        }
                    }
                }

                for (Mix mix : readyPartyReport) {
                    dataForm = new ArrayList<>();
                    dataForm.add(String.valueOf(mix.getId()));
                    dataForm.add(mix.getNameOrder());
                    dataForm.add(String.valueOf(mix.getNumberOrder()));
                    dataForm.add(mix.getDate());
                    dataForm.add(mix.getTime());
                    dataForm.add(mix.getOrganization());
                    dataForm.add(String.valueOf(mix.getOrganizationID()));
                    dataForm.add(mix.getTransporter());
                    dataForm.add(String.valueOf(mix.getTransporterID()));
                    dataForm.add(mix.getRecepie());
                    dataForm.add(String.valueOf(mix.getRecepieID()));
                    dataForm.add(String.valueOf(mix.getMixCounter()));
                    dataForm.add(String.valueOf(mix.getCompleteCapacity()));
                    dataForm.add(String.valueOf(mix.getTotalCapacity()));
                    dataForm.add(String.valueOf(mix.getSilos1()));
                    dataForm.add(String.valueOf(mix.getSilos2()));
                    dataForm.add(String.valueOf(mix.getBuncker11()));
                    dataForm.add(String.valueOf(mix.getBuncker12()));
                    dataForm.add(String.valueOf(mix.getBuncker21()));
                    dataForm.add(String.valueOf(mix.getBuncker22()));
                    dataForm.add(String.valueOf(mix.getBuncker31()));
                    dataForm.add(String.valueOf(mix.getBuncker32()));
                    dataForm.add(String.valueOf(mix.getBuncker41()));
                    dataForm.add(String.valueOf(mix.getBuncker41()));
                    dataForm.add(String.valueOf(mix.getBuncker42()));
                    dataForm.add(String.valueOf(mix.getWater1()));
                    dataForm.add(String.valueOf(mix.getWater2()));
                    dataForm.add(String.valueOf(mix.getDwpl()));
                    dataForm.add(String.valueOf(mix.getChemy1()));
                    dataForm.add(String.valueOf(mix.getChemy2()));
                    dataForm.add(mix.getUploadAddress());
                    dataForm.add(String.valueOf(mix.getAmountConcrete()));
                    dataForm.add(mix.getPaymentOption());
                    dataForm.add(mix.getOperator());
                    dataForm.add(mix.getLoadingTime());

                    dataTable.add(dataForm);

                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < dataTable.size(); x++) {
            List<String> arr = dataTable.get(x);
            XSSFRow row = partySheet.createRow(x);

            for (int i = 0; i < headTable.size(); i++) { //column
                for (int s = 0; s < arr.size(); s++) {
                    String[] data = arr.get(s).split(", ");
                    XSSFCell cell = row.createCell(s);
                    for (int c = 0; c < data.length; c++) {
                        cell.setCellValue(data[c]);
                    }
                }
            }
        }
    }

    //TODO Отчет по маркам [v]
    public void createSheetMark() {
        markSheet = book.createSheet("Отчет по маркам");
        List<List<String>> dataTable = new ArrayList<>();

        //сборка заголовка
        List<String> headTable = new ArrayList<>();
        List<String> recipes = new ArrayList<>();

        if (Constants.exchangeLevel == 1) {
            try {
                report = gson.fromJson(OkHttpUtil.getMixes(dateStart, dateEnd), new TypeToken<List<Mix>>() {}.getType());
                for (Mix mix : report) recipes.add(mix.getRecepie());
                Set<String> set = new HashSet<>(recipes);
                recipes.clear();
                recipes.addAll(set);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            recipes = new DBUtilGet(context).getDistinctRecipesMixes(dates).stream().collect(Collectors.toList());

        headTable.add("Дата/Марка");
        for (String string : recipes) headTable.add(string);
        headTable.add("Итого");
        dataTable.add(headTable);

//        List<Mix> report = new ArrayList<>();

        try {
            float totalSum = 0;
            float sum = 0;

            for (int j = 0; j < dates.size(); j++) {
//                if (Constants.exchangeLevel == 1) {
//                    report.addAll(gson.fromJson(OkHttpUtil.getMixes(dates.get(j), dates.get(j)), new TypeToken<List<Mix>>() {}.getType()));
//                } else {
//                    report.addAll(new DBUtilGet(context).getMixListForDate(dates.get(j)));
//                }

                List<String> dataForm = new ArrayList<>();
                dataForm.add(dates.get(j));

                for (int i = 0; i < recipes.size(); i++) {
                    for (int m = 0; m < report.size(); m++) {
                        if (report.get(m).getDate().equals(dates.get(j))) {
                            if (report.get(m).getRecepie().equals(recipes.get(i))) {
                                sum += report.get(m).getCompleteCapacity();
                            }
                        }
                    }

                    totalSum += sum;
                    dataForm.add(String.valueOf(sum));
                    sum = 0;
                }

                if (totalSum != 0.0f) {
                    dataForm.add(String.valueOf(totalSum));
                    dataTable.add(dataForm);
                }
                totalSum = 0;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        for (int x = 0; x < dataTable.size(); x++) {
            List<String> arr = dataTable.get(x);
            XSSFRow row = markSheet.createRow(x);

            for (int i = 0; i < headTable.size(); i++) { //column
                for (int s = 0; s < arr.size(); s++) {
                    String[] data = arr.get(s).split(", ");
                    XSSFCell cell = row.createCell(s); //i
                    for (int c = 0; c < data.length; c++) {
                        cell.setCellValue(data[c]);
                    }
                }
            }
        }
    }

    //TODO Итоговый отчет [v]
    public void createSheetTotal() {
        totalSheet = book.createSheet("Итоговый отчет");
        List<List<String>> dataTable = new ArrayList<>();

        //сборка заголовка
        List<String> headTable = new ArrayList<>();
        List<String> recipes = new ArrayList<>();
        Gson gson = new Gson();

        headTable.add("Дата");
        headTable.add("[Рецепт]:Объём,м3");

        int tableHeadSize = 10;
        if (Constants.exchangeLevel == 1) {
            try {
                report = new Gson().fromJson(OkHttpUtil.getMixes(dateStart, dateEnd), new TypeToken<List<Mix>>() {}.getType());
                for (Mix mix: report) recipes.add(mix.getRecepie());
                Set<String> set = new HashSet<>(recipes);
                recipes.clear();
                recipes.addAll(set);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else recipes = new DBUtilGet(context).getDistinctRecipesMixes(dates).stream().collect(Collectors.toList());

        if (recipes.size() > tableHeadSize) tableHeadSize = recipes.size();
        for (int i = 2; i < tableHeadSize; i++) headTable.add("  "); //String.valueOf(i)
        dataTable.add(headTable);

        List<Mix> mixes = new ArrayList<>();

        try {
            float sum = 0;

            for (int j = 0; j < dates.size(); j++) {
                if (Constants.exchangeLevel == 1) {
                    String req = OkHttpUtil.getMixes(dates.get(j), dates.get(j));
                    if (!req.trim().equals("Empty")) mixes.addAll(new Gson().fromJson(req, new TypeToken<List<Mix>>() {}.getType()));
                } else mixes.addAll(new DBUtilGet(context).getMixListForDate(dates.get(j)));

                List<String> dataForm = new ArrayList<>();
                dataForm.add(dates.get(j));

                for (int r = 0; r < recipes.size(); r++) {
                    for (int d = 0; d < mixes.size(); d++) {
                        if (mixes.get(d).getDate().equals(dates.get(j))) {
                            if (mixes.get(d).getRecepie().equals(recipes.get(r))) {
                                sum += mixes.get(d).getCompleteCapacity();
                            }
                        }
                    }
                    if (sum != 0.0f) dataForm.add("[" + recipes.get(r) + "]:" + sum);
                    sum = 0;
                }

                if (dataForm.size() != 1) dataTable.add(dataForm);
            }

            if (mixes.size() != 0) {
                List<String> dataForm = new ArrayList<>();
                dataForm.add("  ");
                dataTable.add(dataForm);

                dataForm = new ArrayList<>();
                dataForm.add("Рецепт");
                dataForm.add("Объем,м3");
                dataTable.add(dataForm);

                sum = 0;
                float totalSum = 0;

                for (int j = 0; j < recipes.size(); j++) {
                    dataForm = new ArrayList<>();
                    dataForm.add(recipes.get(j));

                    for (int d = 0; d < dates.size(); d++) {
                        if (Constants.exchangeLevel == 1) {
                            String req = OkHttpUtil.getMixes(dates.get(d), dates.get(d));
                            if (!req.trim().equals("Empty")) mixes = gson.fromJson(req, new TypeToken<List<Mix>>() {}.getType());
                        } else mixes = new DBUtilGet(context).getMixListForDate(dates.get(d));

                        for (int m = 0; m < mixes.size(); m++) {
                            if (mixes.get(m).getDate().equals(dates.get(d))) {
                                if (mixes.get(m).getRecepie().equals(recipes.get(j))) {
                                    sum += mixes.get(m).getCompleteCapacity();
                                }
                            }
                        }
                    }

                    if (sum != 0.0f) {
                        dataForm.add(String.valueOf(sum));
                        dataTable.add(dataForm);
                        totalSum += sum;
                        sum = 0;
                    }

                }

                dataForm = new ArrayList<>();
                dataForm.add("Итого:");
                dataForm.add(String.valueOf(totalSum));
                dataTable.add(dataForm);

                dataForm = new ArrayList<>();
                dataForm.add("  ");
                dataTable.add(dataForm);

                for (int j = 0; j < dates.size(); j++) {
                    if (Constants.exchangeLevel == 1) {
                        String req = OkHttpUtil.getMixes(dates.get(j), dates.get(j));
                        if (!req.trim().equals("Empty")) mixes.addAll(gson.fromJson(req, new TypeToken<List<Mix>>() {}.getType()));
                    } else mixes.addAll(new DBUtilGet(context).getMixListForDate(dates.get(j)));
                }

                /**
                 * Бункер 1		Бункер 2	Бункер 3	Цемент		Вода		Химия
                 * 1123     	1040    	1177    	1318    	1048    	500
                 */
                dataForm = new ArrayList<>();
                dataForm.add("Бункер 1");
                dataForm.add("Бункер 2");
                dataForm.add("Бункер 3");
                dataForm.add("Бункер 4");
                dataForm.add("Силос 1");
                dataForm.add("Силос 2");
                dataForm.add("Вода 1");
                dataForm.add("Вода 2");
                dataForm.add("Химия 1");
                dataForm.add("Химия 2");
                dataTable.add(dataForm);

                BigDecimal bunker11 = new BigDecimal("0");
                BigDecimal bunker21 = new BigDecimal("0");
                BigDecimal bunker31 = new BigDecimal("0");
                BigDecimal bunker41 = new BigDecimal("0");
                BigDecimal silos1 = new BigDecimal("0");
                BigDecimal silos2 = new BigDecimal("0");
                BigDecimal water1 = new BigDecimal("0");
                BigDecimal water2 = new BigDecimal("0");
                BigDecimal chemy1 = new BigDecimal("0");
                BigDecimal chemy2 = new BigDecimal("0");

                //Итоговый отчет, инертные бункера 11 12 ... считаются единым бункером 11 + 12 ...
                for (int m = 0; m < mixes.size(); m++) {
                    bunker11 = bunker11.add(BigDecimal.valueOf(mixes.get(m).getBuncker11() + mixes.get(m).getBuncker12()));
                    bunker21 = bunker21.add(BigDecimal.valueOf(mixes.get(m).getBuncker21() + mixes.get(m).getBuncker22()));
                    bunker31 = bunker31.add(BigDecimal.valueOf(mixes.get(m).getBuncker31() + mixes.get(m).getBuncker32()));
                    bunker41 = bunker41.add(BigDecimal.valueOf(mixes.get(m).getBuncker41() + mixes.get(m).getBuncker42()));
                    silos1 = silos1.add(BigDecimal.valueOf(mixes.get(m).getSilos1()));
                    silos2 = silos2.add(BigDecimal.valueOf(mixes.get(m).getSilos2()));
                    water1 = water1.add(BigDecimal.valueOf(mixes.get(m).getWater1()));
                    water2 = water2.add(BigDecimal.valueOf(mixes.get(m).getWater2()));
                    chemy1 = chemy1.add(BigDecimal.valueOf(mixes.get(m).getChemy1()));
                    chemy2 = chemy2.add(BigDecimal.valueOf(mixes.get(m).getChemy2()));
                }

                dataForm = new ArrayList<>();
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(bunker11))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(bunker21))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(bunker31))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(bunker41))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(silos1))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(silos2))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(water1))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(water2))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(chemy1))));
                dataForm.add(String.valueOf(Float.parseFloat(String.valueOf(chemy2))));
                dataTable.add(dataForm);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        for (int x = 0; x < dataTable.size(); x++) {
            List<String> arr = dataTable.get(x);
            XSSFRow row = totalSheet.createRow(x);

            for (int i = 0; i < headTable.size(); i++) { //column
                for (int s = 0; s < arr.size(); s++) {
                    String[] data = arr.get(s).split(", ");
                    XSSFCell cell = row.createCell(s); //i
                    for (int c = 0; c < data.length; c++) {
                        cell.setCellValue(data[c]);
                    }
                }
            }
        }
    }

    public boolean exportExcel() {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
            String fileName = "Отчёт " + dates.get(0) + " - " + dates.get(dates.size() - 1) + "(" + timeFormat.format(new Date()) + ").xlsx";
            FileOutputStream fileOut = new FileOutputStream(Environment.getExternalStorageDirectory() + folderReports + fileName);
            book.write(fileOut);
            fileOut.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Mix> buildPartyList(List<Mix> mixList) {
        BigDecimal totalCapacity = new BigDecimal(0);
        BigDecimal totalBuncker11 = new BigDecimal(0);
        BigDecimal totalBuncker12 = new BigDecimal(0);
        BigDecimal totalBuncker21 = new BigDecimal(0);
        BigDecimal totalBuncker22 = new BigDecimal(0);
        BigDecimal totalBuncker31 = new BigDecimal(0);
        BigDecimal totalBuncker32 = new BigDecimal(0);
        BigDecimal totalBuncker41 = new BigDecimal(0);
        BigDecimal totalBuncker42 = new BigDecimal(0);
        BigDecimal totalWater1 = new BigDecimal(0);
        BigDecimal totalWater2 = new BigDecimal(0);
        int totalDwpl = 0;
        BigDecimal totalChemy1 = new BigDecimal(0);
        BigDecimal totalChemy2 = new BigDecimal(0);
        BigDecimal totalSilos1 = new BigDecimal(0);
        BigDecimal totalSilos2 = new BigDecimal(0);

        String loadingTime = "00:00:00";
        float amountConcrete = 0;
        Mix currentMix;
        List<Mix> result = new ArrayList<>();

        ArrayList<String> times = new ArrayList<>();
        times.add("00:00:00");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        for (int i = 0; i < mixList.size() - 1; i++) {
            if (false) {   //Проверка на наличие галочки "Не учитывать замесы без цемента"
                if (!(mixList.get(i).getSilos1() == 0.0 && mixList.get(i).getSilos2() == 0.0)) {
                    times.add(mixList.get(i).getLoadingTime());
                    amountConcrete += mixList.get(i).getAmountConcrete();
                    totalCapacity = totalCapacity.add(BigDecimal.valueOf(mixList.get(i).getCompleteCapacity()));
                    totalBuncker11 = totalBuncker11.add(BigDecimal.valueOf(mixList.get(i).getBuncker11()));
                    totalBuncker12 = totalBuncker12.add(BigDecimal.valueOf(mixList.get(i).getBuncker12()));
                    totalBuncker21 = totalBuncker21.add(BigDecimal.valueOf(mixList.get(i).getBuncker21()));
                    totalBuncker22 = totalBuncker22.add(BigDecimal.valueOf(mixList.get(i).getBuncker22()));
                    totalBuncker31 = totalBuncker31.add(BigDecimal.valueOf(mixList.get(i).getBuncker31()));
                    totalBuncker32 = totalBuncker32.add(BigDecimal.valueOf(mixList.get(i).getBuncker32()));
                    totalBuncker41 = totalBuncker41.add(BigDecimal.valueOf(mixList.get(i).getBuncker41()));
                    totalBuncker42 = totalBuncker42.add(BigDecimal.valueOf(mixList.get(i).getBuncker42()));
                    totalWater1 = totalWater1.add(BigDecimal.valueOf(mixList.get(i).getWater1()));
                    totalWater2 = totalWater2.add(BigDecimal.valueOf(mixList.get(i).getWater2()));
                    totalDwpl += mixList.get(i).getDwpl();
                    totalChemy1 = totalChemy1.add(BigDecimal.valueOf(mixList.get(i).getChemy1()));
                    totalSilos1 = totalSilos1.add(BigDecimal.valueOf(mixList.get(i).getSilos1()));
                    totalSilos2 = totalSilos2.add(BigDecimal.valueOf(mixList.get(i).getSilos2()));
                } else {
                    continue;
                }
            } else {
                times.add(mixList.get(i).getLoadingTime());
                amountConcrete += mixList.get(i).getAmountConcrete();
                totalCapacity = totalCapacity.add(BigDecimal.valueOf(mixList.get(i).getCompleteCapacity()));
                totalBuncker11 = totalBuncker11.add(BigDecimal.valueOf(mixList.get(i).getBuncker11()));
                totalBuncker12 = totalBuncker12.add(BigDecimal.valueOf(mixList.get(i).getBuncker12()));
                totalBuncker21 = totalBuncker21.add(BigDecimal.valueOf(mixList.get(i).getBuncker21()));
                totalBuncker22 = totalBuncker22.add(BigDecimal.valueOf(mixList.get(i).getBuncker22()));
                totalBuncker31 = totalBuncker31.add(BigDecimal.valueOf(mixList.get(i).getBuncker31()));
                totalBuncker32 = totalBuncker32.add(BigDecimal.valueOf(mixList.get(i).getBuncker32()));
                totalBuncker41 = totalBuncker41.add(BigDecimal.valueOf(mixList.get(i).getBuncker41()));
                totalBuncker42 = totalBuncker42.add(BigDecimal.valueOf(mixList.get(i).getBuncker42()));
                totalWater1 = totalWater1.add(BigDecimal.valueOf(mixList.get(i).getWater1()));
                totalWater2 = totalWater2.add(BigDecimal.valueOf(mixList.get(i).getWater2()));
                totalDwpl += mixList.get(i).getDwpl();
                totalChemy1 = totalChemy1.add(BigDecimal.valueOf(mixList.get(i).getChemy1()));
                totalSilos1 = totalSilos1.add(BigDecimal.valueOf(mixList.get(i).getSilos1()));
                totalSilos2 = totalSilos2.add(BigDecimal.valueOf(mixList.get(i).getSilos2()));
            }

            try {
                loadingTime = time.format(time.parse(new DateTimeUtil().sumTimes(times)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            currentMix = new Mix(
                    mixList.get(i).getId(),
                    mixList.get(i).getNameOrder(),
                    mixList.get(i).getNumberOrder(),
                    mixList.get(i).getDate(),
                    mixList.get(i).getTime(),
                    mixList.get(i).getUploadAddress(),
                    mixList.get(i).getAmountConcrete(),
                    mixList.get(i).getPaymentOption(),
                    mixList.get(i).getOperator(),
                    mixList.get(i).getOrganization(),
                    mixList.get(i).getOrganizationID(),
                    mixList.get(i).getTransporter(),
                    mixList.get(i).getTransporterID(),
                    mixList.get(i).getRecepie(),
                    mixList.get(i).getRecepieID(),
                    mixList.get(i).getMixCounter() + 1,       //+1 делается потомучто из прошивки замесы начинаются с нуля
                    totalCapacity.floatValue(),
                    mixList.get(i).getTotalCapacity(),
                    totalBuncker11.floatValue(),
                    totalBuncker12.floatValue(),
                    totalBuncker21.floatValue(),
                    totalBuncker22.floatValue(),
                    totalBuncker31.floatValue(),
                    totalBuncker32.floatValue(),
                    totalBuncker41.floatValue(),
                    totalBuncker42.floatValue(),
                    totalSilos1.floatValue(),
                    totalSilos2.floatValue(),
                    totalWater1.floatValue(),
                    totalWater2.floatValue(),
                    totalDwpl,
                    totalChemy1.floatValue(),
                    totalChemy2.floatValue(),
                    loadingTime
            );

            if (mixList.get(i + 1).getMixCounter() == 0) { //если следующий элемент партии 1 или 0 считаю это концом текущей партии
                result.add(currentMix);

                totalCapacity = new BigDecimal(0);
                totalBuncker11 = new BigDecimal(0);
                totalBuncker12 = new BigDecimal(0);
                totalBuncker21 = new BigDecimal(0);
                totalBuncker22 = new BigDecimal(0);
                totalBuncker31 = new BigDecimal(0);
                totalBuncker32 = new BigDecimal(0);
                totalWater1 = new BigDecimal(0);
                totalWater2 = new BigDecimal(0);
                totalChemy1 = new BigDecimal(0);
                totalChemy2 = new BigDecimal(0);
                totalSilos1 = new BigDecimal(0);
                totalSilos2 = new BigDecimal(0);

                times.clear();
                times.add("00:00:00");
                amountConcrete = 0;
            }

            if (i == mixList.size() - 2) { //если это последняя строчка и последний замес партии
                if ((mixList.get(i + 1).getMixCounter() != 0) || (mixList.get(i + 1).getMixCounter() != 1)) {
                    times.add(mixList.get(i + 1).getLoadingTime());
                    try {
                        loadingTime = time.format(time.parse(new DateTimeUtil().sumTimes(times)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    totalCapacity = totalCapacity.add(BigDecimal.valueOf(mixList.get(i + 1).getCompleteCapacity()));
                    totalBuncker11 = totalBuncker11.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker11()));
                    totalBuncker12 = totalBuncker12.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker12()));
                    totalBuncker21 = totalBuncker21.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker21()));
                    totalBuncker22 = totalBuncker22.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker22()));
                    totalBuncker31 = totalBuncker31.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker31()));
                    totalBuncker32 = totalBuncker32.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker32()));
                    totalBuncker41 = totalBuncker41.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker41()));
                    totalBuncker42 = totalBuncker42.add(BigDecimal.valueOf(mixList.get(i + 1).getBuncker42()));
                    totalWater1 = totalWater1.add(BigDecimal.valueOf(mixList.get(i + 1).getWater1()));
                    totalChemy1 = totalChemy1.add(BigDecimal.valueOf(mixList.get(i + 1).getChemy1()));
                    totalChemy2 = totalChemy2.add(BigDecimal.valueOf(mixList.get(i + 1).getChemy2()));
                    totalSilos1 = totalSilos1.add(BigDecimal.valueOf(mixList.get(i + 1).getSilos1()));
                    totalSilos2 = totalSilos2.add(BigDecimal.valueOf(mixList.get(i + 1).getSilos2()));
                    totalDwpl += mixList.get(i + 1).getDwpl();

                    currentMix = new Mix(
                            mixList.get(i + 1).getId(),
                            mixList.get(i + 1).getNameOrder(),
                            mixList.get(i + 1).getNumberOrder(),
                            mixList.get(i + 1).getDate(),
                            mixList.get(i + 1).getTime(),
                            mixList.get(i + 1).getUploadAddress(),
                            mixList.get(i + 1).getAmountConcrete(),
                            mixList.get(i + 1).getPaymentOption(),
                            mixList.get(i + 1).getOperator(),
                            mixList.get(i + 1).getOrganization(),
                            mixList.get(i + 1).getOrganizationID(),
                            mixList.get(i + 1).getTransporter(),
                            mixList.get(i + 1).getTransporterID(),
                            mixList.get(i + 1).getRecepie(),
                            mixList.get(i + 1).getRecepieID(),
                            mixList.get(i + 1).getMixCounter() + 1,
                            totalCapacity.floatValue(),
                            mixList.get(i + 1).getTotalCapacity(),
                            totalBuncker11.floatValue(),
                            totalBuncker12.floatValue(),
                            totalBuncker21.floatValue(),
                            totalBuncker22.floatValue(),
                            totalBuncker31.floatValue(),
                            totalBuncker32.floatValue(),
                            totalBuncker41.floatValue(),
                            totalBuncker42.floatValue(),
                            totalSilos1.floatValue(),
                            totalSilos2.floatValue(),
                            totalWater1.floatValue(),
                            totalWater2.floatValue(),
                            totalDwpl,
                            totalChemy1.floatValue(),
                            totalChemy2.floatValue(),
                            loadingTime
                    );
                    result.add(currentMix);
                    break;
                }
            }
        }
        return result;
    }
}
