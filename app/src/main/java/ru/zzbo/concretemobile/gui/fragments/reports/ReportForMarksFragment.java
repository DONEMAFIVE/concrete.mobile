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

public class ReportForMarksFragment extends Fragment {

    private String dateFirst;
    private String dateEnd;
    private List<String> dates;
    private TableView tableView;
    private List<Mix> report;

    public ReportForMarksFragment(String dateFirst, String dateEnd) {
        this.dateFirst = dateFirst;
        this.dateEnd = dateEnd;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_marks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableView = view.findViewById(R.id.marksReportTableView);

        if (this.dateFirst.equals("")) this.dateFirst = this.dateEnd;
        dates = new DatesGenerate(this.dateFirst, this.dateEnd).getLostDates();

        new Thread(()->{ buildMarksReport();}).start();
    }

    public void buildMarksReport() {
        try {
            //сборка заголовка
            List<String> headTable = new ArrayList<>();
            List<String> recipes = new ArrayList<>();

            if (Constants.exchangeLevel == 1) {
                try {
                    String req = OkHttpUtil.getMixes(dateFirst, dateEnd);
                    if (!req.trim().equals("Empty")) {
                        report = new Gson().fromJson(req, new TypeToken<List<Mix>>() {}.getType());
                        for (Mix mix: report) recipes.add(mix.getRecepie());
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

            headTable.add("Дата/Марка");
            for (String string : recipes) headTable.add(string);
            headTable.add("Итого");

            new Handler(Looper.getMainLooper()).post(() -> tableView.addHeader(headTable));

            List<Mix> mixes = new ArrayList<>();
            float totalSum = 0;
            float sum = 0;

            for (int j = 0; j < dates.size(); j++) {
                if (Constants.exchangeLevel == 1) {
                    try {
                        String req = OkHttpUtil.getMixes(dates.get(j), dates.get(j));
                        if (!req.trim().equals("Empty")) {
                            mixes.addAll(new Gson().fromJson(req, new TypeToken<List<Mix>>() {}.getType()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mixes.addAll(new DBUtilGet(getContext()).getMixListForDate(dates.get(j)));
                }

                List<String> dataForm = new ArrayList<>();
                dataForm.add(dates.get(j));

                for (int i = 0; i < recipes.size(); i++) {
                    for (int m = 0; m < mixes.size(); m++) {
                        if (mixes.get(m).getDate().equals(dates.get(j))) {
                            if (mixes.get(m).getRecepie().equals(recipes.get(i))) {
                                sum += mixes.get(m).getCompleteCapacity();
                            }
                        }
                    }

                    totalSum += sum;
                    dataForm.add(String.valueOf(sum));
                    sum = 0;
                }

                if (totalSum != 0.0f) {
                    dataForm.add(String.valueOf(totalSum));
                    new Handler(Looper.getMainLooper()).post(() -> tableView.addRow(dataForm));
                }
                totalSum = 0;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
