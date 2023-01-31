package ru.zzbo.concretemobile.gui.fragments.reports;

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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.DatesGenerate;
import ru.zzbo.concretemobile.utils.OkHttpUtil;
import ru.zzbo.concretemobile.utils.TableView;

public class ReportForMixesFragment extends Fragment {

    private String dateFirst;
    private String dateEnd;
    private boolean mixesEmptyCementLess;   //установлена галочка "Не учитывать замесы без цемента"
    private TableView mixReportTableView;
    private List<String> dates;
    private List<Mix> report;

    public ReportForMixesFragment(String dateFirst, String dateEnd, boolean mixesEmptyCementLess) {
        this.dateFirst = dateFirst;
        this.dateEnd = dateEnd;
        this.mixesEmptyCementLess = mixesEmptyCementLess;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_mix, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (this.dateFirst.equals("")) this.dateFirst = this.dateEnd;
        dates = new DatesGenerate(this.dateFirst, this.dateEnd).getLostDates();
        mixReportTableView = view.findViewById(R.id.mixReportTableView);

        new Thread(() -> {
            if (Constants.exchangeLevel == 1) {
                try {
                    String req = OkHttpUtil.getMixes(dateFirst, dateEnd);
                    if (!req.trim().equals("Empty")){
                        report = new Gson().fromJson(req, new TypeToken<List<Mix>>() {}.getType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else report = new DBUtilGet(getContext()).getMixList();

            if (report != null) new Handler(Looper.getMainLooper()).post(() -> buildMixesReport());
        }).start();

    }

    public void buildMixesReport() {
        try {
            String singleRow = "ID | Заказ | № заказа | Дата | Время | Заказчик | ID Заказчика | Перевозчик | ID Перевозчика | Рецепт | ID Рецепта | Замес | Объем | Партия | Силос 1 | Силос 2 | Бункер 11 | Бункер 12 | Бункер 21 | Бункер 22 | Бункер 31 | Бункер 32 | Бункер 41 | Бункер 42 | Вода 1 | Вода 2 | ДВПЛ | Химия 1 | Химия 2 | Адрес выгрузки | Стоимость | Способ оплаты | Оператор | Время погрузки";
            List<String> tableHeader = Arrays.asList(singleRow.split("\\|"));

            mixReportTableView.addHeader(tableHeader);

            LinkedHashMap<Integer, List<String>> rows = new LinkedHashMap<>();

            for (String date : dates) {
                for (Mix mix : report) {
                    if ((this.mixesEmptyCementLess) && (mix.getSilos1() == 0)) continue;
                    if ((this.mixesEmptyCementLess) && (mix.getSilos2() == 0)) continue;
                    if (mix.getDate().equals(date)) {
                        singleRow =
                                mix.getId() + " | " +
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
                }
            }
            mixReportTableView.addRows(rows);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
