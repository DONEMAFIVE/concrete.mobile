package ru.zzbo.concretemobile.gui.fragments.reports;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.utils.DateTimeUtil;
import ru.zzbo.concretemobile.utils.DatesGenerate;
import ru.zzbo.concretemobile.utils.OkHttpUtil;
import ru.zzbo.concretemobile.utils.TableView;

public class ReportForPartyFragment extends Fragment {

    private String dateFirst;
    private String dateEnd;
    private boolean flagEmptyCement;
    private TableView tableView;
    private List<String> dates;
    private List<Mix> report;

    public ReportForPartyFragment(String dateFirst, String dateEnd, boolean flagEmptyCement) {
        this.dateFirst = dateFirst;
        this.dateEnd = dateEnd;
        this.flagEmptyCement = flagEmptyCement;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_party, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tableView = view.findViewById(R.id.partyReportTableView);
        if (this.dateFirst.equals("")) this.dateFirst = this.dateEnd;
        dates = new DatesGenerate(this.dateFirst, this.dateEnd).getLostDates();

        new Thread(() -> {
            try {
                if (exchangeLevel == 1) {
                    String req = OkHttpUtil.getMixes(dateFirst, dateEnd);
                    if (!req.trim().equals("Empty")){
                        report =  new Gson().fromJson(req, new TypeToken<List<Mix>>() {}.getType());
                    }
                } else report = new DBUtilGet(getContext()).getMixList();

                if (report != null) new Handler(Looper.getMainLooper()).post(() -> buildPartyReport());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();

    }

    public void buildPartyReport() {
        try {
            String singleRow = "ID | Заказ | № заказа | Дата | Время | Заказчик | ID Заказчика | Перевозчик | ID Перевозчика | Рецепт | ID Рецепта | Замес | Объем | Партия | Силос 1 | Силос 2 | Бункер 11 | Бункер 12 | Бункер 21 | Бункер 22 | Бункер 31 | Бункер 32 | Бункер 41 | Бункер 42 | Вода 1 | Вода 2 | ДВПЛ | Химия 1 | Химия 2 | Адрес выгрузки | Стоимость | Способ оплаты | Оператор | Время погрузки";
            List<String> tableHeader = Arrays.asList(singleRow.split("\\|"));
            tableView.addHeader(tableHeader);

            LinkedHashMap<Integer, List<String>> rows = new LinkedHashMap<>();

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
                singleRow = mix.getId() + " | " +
                        mix.getNameOrder() + " | " +
                        mix.getNumberOrder() + " | " +
                        mix.getDate() + " | " +
                        mix.getTime() + " | " +
                        mix.getOrganization() + " | " +
                        mix.getOrganizationID() + " | " +
                        mix.getTransporter() + " | " +
                        mix.getTransporterID() + " | " +
                        mix.getRecepie() + " | " +
                        mix.getRecepieID() + " | " +
                        mix.getMixCounter() + " | " +
                        mix.getCompleteCapacity() + " | " +
                        mix.getTotalCapacity() + " | " +
                        mix.getSilos1() + " | " +
                        mix.getSilos2() + " | " +
                        mix.getBuncker11() + " | " +
                        mix.getBuncker12() + " | " +
                        mix.getBuncker21() + " | " +
                        mix.getBuncker22() + " | " +
                        mix.getBuncker31() + " | " +
                        mix.getBuncker32() + " | " +
                        mix.getBuncker41() + " | " +
                        mix.getBuncker42() + " | " +
                        mix.getWater1() + " | " +
                        mix.getWater2() + " | " +
                        mix.getDwpl() + " | " +
                        mix.getChemy1() + " | " +
                        mix.getChemy2() + " | " +
                        mix.getUploadAddress() + " | " +
                        mix.getAmountConcrete() + " | " +
                        mix.getPaymentOption() + " | " +
                        mix.getOperator() + " | " +
                        mix.getLoadingTime();

                rows.put(mix.getId(), Arrays.asList(singleRow.split("\\|")));

            }

            tableView.addRows(rows);
        } catch (NullPointerException e) {
            e.printStackTrace();
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

        if (mixList.size() == 1) {
            mixList.add(new Mix(
                    -1,
                    "0",
                    0,
                    "0",
                    "0",
                    "0",
                    0,
                    "0",
                    "0",
                    "0",
                    0,
                    "0",
                    0,
                    "0",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "0"
            ));
        }
        for (int i = 0; i < mixList.size() - 1; i++) {
            if (flagEmptyCement) {   //Проверка на наличие галочки "Не учитывать замесы без цемента"
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
                    if (currentMix.getId() != -1) result.add(currentMix);
                    break;
                }
            }
        }
        return result;
    }
}

