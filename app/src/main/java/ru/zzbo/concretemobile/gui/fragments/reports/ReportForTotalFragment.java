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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.models.Mix;
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.DatesGenerate;
import ru.zzbo.concretemobile.utils.OkHttpUtil;
import ru.zzbo.concretemobile.utils.TableView;

public class ReportForTotalFragment extends Fragment {

    private String dateFirst;
    private String dateEnd;
    private TableView tableView;
    private List<String> dates;
    private List<Mix> report;

    public ReportForTotalFragment(String dateFirst, String dateEnd) {
        this.dateFirst = dateFirst;
        this.dateEnd = dateEnd;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_total, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableView = view.findViewById(R.id.totalReportTableView);

        if (dateFirst.equals("")) dateFirst = dateEnd;
        dates = new DatesGenerate(dateFirst, dateEnd).getLostDates();

        new Thread(() -> buildTotalReport()).start();
    }

    public void buildTotalReport() {
        //сборка заголовка
        List<String> headTable = new ArrayList<>();
        List<String> recipes = new ArrayList<>();
        Gson gson = new Gson();

        headTable.add("Дата");
        headTable.add("[Рецепт]:Объём,м3");

        int tableHeadSize = 10;
        if (Constants.exchangeLevel == 1) {
            try {
                String req = OkHttpUtil.getMixes(dateFirst, dateEnd);
                if (!req.trim().equals("Empty")) {
                    report = new Gson().fromJson(req, new TypeToken<List<Mix>>() {
                    }.getType());
                    for (Mix mix : report) recipes.add(mix.getRecipe());
                    Set<String> set = new HashSet<>(recipes);
                    recipes.clear();
                    recipes.addAll(set);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            recipes = new DBUtilGet(getContext()).getDistinctRecipesMixes(dates).stream().collect(Collectors.toList());
        }

        if (recipes.size() > tableHeadSize) tableHeadSize = recipes.size();
        for (int i = 2; i < tableHeadSize; i++) headTable.add("  "); //String.valueOf(i)
        new Handler(Looper.getMainLooper()).post(() -> tableView.addHeader(headTable));

        List<Mix> mixes = new ArrayList<>();

        try {
            float sum = 0;

            for (int j = 0; j < dates.size(); j++) {
                if (Constants.exchangeLevel == 1) {
                    String req = OkHttpUtil.getMixes(dates.get(j), dates.get(j));
                    if (!req.trim().equals("Empty")) {
                        mixes.addAll(new Gson().fromJson(req, new TypeToken<List<Mix>>() {
                        }.getType()));
                    }
                } else {
                    mixes.addAll(new DBUtilGet(getContext()).getMixListForDate(dates.get(j)));
                }
                List<String> dataForm = new ArrayList<>();
                dataForm.add(dates.get(j));

                for (int r = 0; r < recipes.size(); r++) {
                    for (int d = 0; d < mixes.size(); d++) {
                        if (mixes.get(d).getDate().equals(dates.get(j))) {
                            if (mixes.get(d).getRecipe().equals(recipes.get(r))) {
                                sum += mixes.get(d).getCompleteCapacity();
                            }
                        }
                    }
                    if (sum != 0.0f) dataForm.add("[" + recipes.get(r) + "]:" + sum);
                    sum = 0;
                }

                if (dataForm.size() != 1)
                    new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(dataForm));
            }

            if (mixes.size() != 0) {
                List<String> dataForm = new ArrayList<>();
                dataForm.add("  ");
                List<String> finalDataForm = dataForm;
                new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm));

                dataForm = new ArrayList<>();
                dataForm.add("Рецепт");
                dataForm.add("Объем,м3");

                List<String> finalDataForm1 = dataForm;
                new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm1));

                sum = 0;
                float totalSum = 0;

                for (int j = 0; j < recipes.size(); j++) {
                    dataForm = new ArrayList<>();
                    dataForm.add(recipes.get(j));

                    for (int d = 0; d < dates.size(); d++) {
                        try {
                            if (Constants.exchangeLevel == 1) {
                                String req = OkHttpUtil.getMixes(dates.get(d), dates.get(d));
                                if (!req.trim().equals("Empty")) {
                                    mixes = gson.fromJson(req, new TypeToken<List<Mix>>() {
                                    }.getType());
                                }
                            } else {
                                mixes = new DBUtilGet(getContext()).getMixListForDate(dates.get(d));
                            }

                            for (int m = 0; m < mixes.size(); m++) {
                                if (mixes.get(m).getDate().equals(dates.get(d))) {
                                    if (mixes.get(m).getRecipe().equals(recipes.get(j))) {
                                        sum += mixes.get(m).getCompleteCapacity();
                                    }
                                }
                            }
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }

                    }

                    if (sum != 0.0f) {
                        dataForm.add(String.valueOf(sum));
                        List<String> finalDataForm2 = dataForm;
                        new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm2));
                        totalSum += sum;
                        sum = 0;
                    }

                }

                dataForm = new ArrayList<>();
                dataForm.add("Итого:");
                dataForm.add(String.valueOf(totalSum));

                List<String> finalDataForm3 = dataForm;
                new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm3));

                dataForm = new ArrayList<>();
                dataForm.add("  ");

                List<String> finalDataForm4 = dataForm;
                new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm4));

                for (int j = 0; j < dates.size(); j++) {
                    if (Constants.exchangeLevel == 1) {
                        String req = OkHttpUtil.getMixes(dates.get(j), dates.get(j));
                        if (!req.trim().equals("Empty")) {
                            mixes.addAll(gson.fromJson(req, new TypeToken<List<Mix>>() {
                            }.getType()));
                        }
                    } else {
                        mixes.addAll(new DBUtilGet(getContext()).getMixListForDate(dates.get(j)));
                    }
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

                List<String> finalDataForm5 = dataForm;
                new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm5));

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

                List<String> finalDataForm6 = dataForm;
                new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(finalDataForm6));

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
