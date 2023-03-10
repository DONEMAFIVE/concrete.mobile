package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.DateTimeUtils.getDateFromDatePicker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.text.SimpleDateFormat;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForMarksFragment;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForMixesFragment;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForPartyFragment;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForTotalFragment;
import ru.zzbo.concretemobile.utils.DateTimeUtils;
import ru.zzbo.concretemobile.utils.SqliteToExcel;

public class ReportsActivity extends AppCompatActivity {

    private DatePicker dateBeginWidget;
    private DatePicker dateEndWidget;
    private CheckBox cementLessFilter;
    private TabLayout tabLayout;
    private Fragment fragment;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private Button applyReportBtn;
    private Button closeDialog;
    private Button closeSaveDialog;
    private Button exportExcelBtn;
    private CheckBox totalReportChecker, mixesReportChecker, partyReportChecker, marksReportChecker;

    private AlertDialog.Builder mFilterBuilder;
    private AlertDialog filterDialog;
    private View mFilterView;

    private AlertDialog.Builder mSaveBuilder;
    private AlertDialog saveDialog;
    private View mSaveView;

    private FloatingActionButton mAddFab, mAddFiltersFab, mAddSaveFab, mAddOpenFab;
    private TextView addSaveActionText, addFilterActionText, addOpenActionText;
    private Boolean isAllFabsVisible;
    private String startDate;
    private String endDate;
    private DrawerLayout progressLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        initUI();
        firstRun();
        initActions();

    }

    private void firstRun() {
        checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
        startDate = sdf.format(getDateFromDatePicker(dateBeginWidget));
        endDate = sdf.format(getDateFromDatePicker(dateEndWidget));

        fragment = new ReportForTotalFragment(startDate, endDate);
        totalReportChecker.setChecked(true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.tabReportFrame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void initActions() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment = null;

                //todo: ???????????? ?????? ?????????????????? ???????????? ?????????????????? ???????????????? ???? ????, ?????? ?????? ?????????????? ???????????????????????? ?? ?????????? ?????? ??????????????
                switch (tab.getPosition()) {
                    case 0: {
                        fragment = new ReportForTotalFragment(startDate, endDate);
                        totalReportChecker.setChecked(true);
                        break;
                    }
                    case 1: {
                        fragment = new ReportForMixesFragment(startDate, endDate, cementLessFilter.isChecked());
                        mixesReportChecker.setChecked(true);
                        break;
                    }
                    case 2: {
                        fragment = new ReportForPartyFragment(startDate, endDate, cementLessFilter.isChecked());
                        partyReportChecker.setChecked(true);
                        break;
                    }
                    case 3: {
                        fragment = new ReportForMarksFragment(startDate, endDate);
                        marksReportChecker.setChecked(true);
                        break;
                    }
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.tabReportFrame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

                mAddFiltersFab.hide();
                mAddSaveFab.hide();
                mAddOpenFab.hide();
                addSaveActionText.setVisibility(View.GONE);
                addFilterActionText.setVisibility(View.GONE);
                addOpenActionText.setVisibility(View.GONE);

                isAllFabsVisible = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // ????????
        mAddFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                mAddFiltersFab.show();
                mAddSaveFab.show();
                mAddOpenFab.show();
                addSaveActionText.setVisibility(View.VISIBLE);
                addFilterActionText.setVisibility(View.VISIBLE);
                addOpenActionText.setVisibility(View.VISIBLE);

                isAllFabsVisible = true;
            } else {
                mAddFiltersFab.hide();
                mAddSaveFab.hide();
                mAddOpenFab.hide();
                addSaveActionText.setVisibility(View.GONE);
                addFilterActionText.setVisibility(View.GONE);
                addOpenActionText.setVisibility(View.GONE);

                isAllFabsVisible = false;
            }
        });

        mAddOpenFab.setOnClickListener(view -> {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/reports/");
            Uri uri;

            if (Build.VERSION.SDK_INT < 24) uri = Uri.fromFile(file);
            else uri = Uri.parse(file.getPath());

            Intent viewFile = new Intent(Intent.ACTION_VIEW);
            viewFile.setDataAndType(uri, "*/*");
            startActivity(viewFile);
        });

        // ????????????????????
        mAddSaveFab.setOnClickListener(view -> {
            checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
            //todo: ???????????????????? excel
            exportExcelBtn.setOnClickListener(e -> {
                try {
                    new Thread(() -> {
                        runOnUiThread(() -> progressLoading.setVisibility(View.VISIBLE));

                        SqliteToExcel sqliteToExcel = new SqliteToExcel(this, startDate, endDate);

                        //todo: ?????????? ????????????
                        if (mixesReportChecker.isChecked())
                            sqliteToExcel.createSheetMixes(cementLessFilter.isChecked());
                        if (partyReportChecker.isChecked())
                            sqliteToExcel.createSheetParty(cementLessFilter.isChecked());
                        if (marksReportChecker.isChecked()) sqliteToExcel.createSheetMark();
                        if (totalReportChecker.isChecked()) sqliteToExcel.createSheetTotal();

                        if (sqliteToExcel.exportExcel()) {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "??????????????", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("???????????????????? ?????????????????? ??????????????!");
                                builder.setMessage("?????????????? ?????????? ?? ???????????????????????? ?????????????????");

                                builder.setPositiveButton("????", (dialog, id) -> {
                                    File file = new File(Environment.getExternalStorageDirectory().toString() + "/reports/");
                                    Uri uri;

                                    if (Build.VERSION.SDK_INT < 24) uri = Uri.fromFile(file);
                                    else uri = Uri.parse(file.getPath());

                                    Intent viewFile = new Intent(Intent.ACTION_VIEW);
                                    viewFile.setDataAndType(uri, "*/*");
                                    startActivity(viewFile);
                                });
                                builder.setNegativeButton("??????", (dialog, id) -> dialog.dismiss());

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "???????????? ????????????????", Toast.LENGTH_SHORT).show();
                            });
                        }
                        runOnUiThread(() -> progressLoading.setVisibility(View.GONE));

                    }).start();

                    mAddFiltersFab.hide();
                    mAddSaveFab.hide();
                    mAddOpenFab.hide();
                    addSaveActionText.setVisibility(View.GONE);
                    addFilterActionText.setVisibility(View.GONE);
                    addOpenActionText.setVisibility(View.GONE);

                    isAllFabsVisible = false;


                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                saveDialog.hide();
            });

            closeSaveDialog.setOnClickListener(e -> saveDialog.hide());

            saveDialog.show();
        });

        // ????????????
        mAddFiltersFab.setOnClickListener(view -> filterDialog.show());

        // ?????????????????? ????????????
        applyReportBtn.setOnClickListener(e -> {
            fragment = null;
            startDate = sdf.format(getDateFromDatePicker(dateBeginWidget));
            endDate = sdf.format(getDateFromDatePicker(dateEndWidget));

            if (DateTimeUtils.startLongerEnd(startDate, endDate)) {
                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("??????????????????????").setMessage("???????????? ???????????????? ???????????????? ??????!");
                    builder.setPositiveButton("????", (dialog, id) -> {
                        dialog.dismiss();
                    });
                    builder.show();
                });

                return;
            }

            //todo: ???????????? ?????? ?????????????????? ???????????? ?????????????????? ???????????????? ???? ????, ?????? ?????? ?????????????? ???????????????????????? ?? ?????????? ?????? ??????????????
            switch (tabLayout.getSelectedTabPosition()) {
                case 0: {
                    fragment = new ReportForTotalFragment(startDate, endDate);
                    break;
                }
                case 1: {
                    fragment = new ReportForMixesFragment(startDate, endDate, cementLessFilter.isChecked());
                    break;
                }
                case 2: {
                    fragment = new ReportForPartyFragment(startDate, endDate, cementLessFilter.isChecked());
                    break;
                }
                case 3: {
                    fragment = new ReportForMarksFragment(startDate, endDate);
                    break;
                }
            }

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.tabReportFrame, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            mAddFiltersFab.hide();
            mAddSaveFab.hide();
            mAddOpenFab.hide();
            addSaveActionText.setVisibility(View.GONE);
            addFilterActionText.setVisibility(View.GONE);
            addOpenActionText.setVisibility(View.GONE);

            isAllFabsVisible = false;

            filterDialog.hide();
        });

        // ?????????????? ????????????
        closeDialog.setOnClickListener(e -> filterDialog.hide());

    }

    private void initUI() {
        mAddFab = findViewById(R.id.add_fab);
        progressLoading = findViewById(R.id.progress_loading);

        // FAB ????????????
        mAddFiltersFab = findViewById(R.id.filter_fab);
        mAddSaveFab = findViewById(R.id.save_fab);
        mAddOpenFab = findViewById(R.id.open_fab);

        // ?????????? ???????????????? ???????????????? ?????? ???????? FABs.
        addSaveActionText = findViewById(R.id.save_text);
        addFilterActionText = findViewById(R.id.filter_text);
        addOpenActionText = findViewById(R.id.open_text);

        mAddFiltersFab.setVisibility(View.GONE);
        mAddSaveFab.setVisibility(View.GONE);
        mAddOpenFab.setVisibility(View.GONE);
        addSaveActionText.setVisibility(View.GONE);
        addFilterActionText.setVisibility(View.GONE);
        addOpenActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        tabLayout = findViewById(R.id.reportTabController);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("???????????????? ??????????");
        tabLayout.addTab(firstTab);

        TabLayout.Tab mixTab = tabLayout.newTab();
        mixTab.setText("???? ??????????????");
        tabLayout.addTab(mixTab);

        TabLayout.Tab partyTab = tabLayout.newTab();
        partyTab.setText("???? ??????????????");
        tabLayout.addTab(partyTab);

        TabLayout.Tab marksTab = tabLayout.newTab();
        marksTab.setText("???? ????????????");
        tabLayout.addTab(marksTab);

        mFilterBuilder = new AlertDialog.Builder(this);
        mFilterView = getLayoutInflater().inflate(R.layout.dialog_filter, null);
        mFilterBuilder.setView(mFilterView);
        filterDialog = mFilterBuilder.create();

        applyReportBtn = mFilterView.findViewById(R.id.applyBtn);
        closeDialog = mFilterView.findViewById(R.id.closeDialog);
        dateBeginWidget = mFilterView.findViewById(R.id.dateBeginWidget);
        dateEndWidget = mFilterView.findViewById(R.id.dateEndWidget);
        cementLessFilter = mFilterView.findViewById(R.id.cementLessFilter);

        mSaveBuilder = new AlertDialog.Builder(this);
        mSaveView = getLayoutInflater().inflate(R.layout.dialog_save_report, null);
        mSaveBuilder.setView(mSaveView);
        saveDialog = mSaveBuilder.create();

        exportExcelBtn = mSaveView.findViewById(R.id.exportExcelBtn);
        closeSaveDialog = mSaveView.findViewById(R.id.closeSaveDialog);
        totalReportChecker = mSaveView.findViewById(R.id.totalReportChecker);
        mixesReportChecker = mSaveView.findViewById(R.id.mixesReportChecker);
        partyReportChecker = mSaveView.findViewById(R.id.partyReportChecker);
        marksReportChecker = mSaveView.findViewById(R.id.marksReportChecker);
    }

    @Override
    public void onBackPressed() {
        saveDialog.dismiss();
        filterDialog.dismiss();
        super.onBackPressed();
    }

    // ?????????????? ???????????????? ?? ?????????????? ????????????????????
    public void checkPermission(Activity activity, String permission, int requestCode) {
        // ????????????????, ???????? ???????????????????? ???? ????????????
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

}