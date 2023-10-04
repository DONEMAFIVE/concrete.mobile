package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.APP_PREFERENCES;
import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.mSettings;
import static ru.zzbo.concretemobile.utils.Constants.marksDone;
import static ru.zzbo.concretemobile.utils.Constants.mixesDone;
import static ru.zzbo.concretemobile.utils.Constants.partyDone;
import static ru.zzbo.concretemobile.utils.Constants.totalDone;
import static ru.zzbo.concretemobile.utils.DateTimeUtil.getDateFromDatePicker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForMarksFragment;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForMixesFragment;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForPartyFragment;
import ru.zzbo.concretemobile.gui.fragments.reports.ReportForTotalFragment;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.ConnectionUtil;
import ru.zzbo.concretemobile.utils.DateTimeUtil;
import ru.zzbo.concretemobile.utils.FileUtil;
import ru.zzbo.concretemobile.utils.SqliteToExcelUtil;
import ru.zzbo.concretemobile.utils.ftp.EZFtpServer;
import ru.zzbo.concretemobile.utils.ftp.FtpConfig;
import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUser;
import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUserPermission;

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

    private FloatingActionButton mAddFab, mAddFiltersFab, mAddSaveFab, mAddOpenFab, mSyncHMIFab;
    private TextView addSaveActionText, addFilterActionText, addOpenActionText, syncActionText;
    private Boolean isAllFabsVisible = false;
    private String startDate;
    private String endDate;
    private DrawerLayout progressLoading;
    private TextView textInfoLoading;
    public EZFtpServer ftpServer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initUI();
        firstRun();
        initActions();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (marksDone || mixesDone || partyDone || totalDone) {
                        runOnUiThread(()->{
                            progressLoading.setVisibility(View.GONE);
                            textInfoLoading.setText("Сохранение...");
                        });
                        marksDone = false;
                        totalDone = false;
                        mixesDone = false;
                        partyDone = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

                //todo: прежде чем двигаться дальше поставить проверку на то, что там написал пользователь в полях дат периода
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

                fabVisible(true);

//                progressLoading.setVisibility(View.VISIBLE);
//                textInfoLoading.setText("Формирование отчета...");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //Меню
        mAddFab.setOnClickListener(view -> fabVisible(isAllFabsVisible));

        //Снятие отчета
        mSyncHMIFab.setOnClickListener(view -> {
            //todo проверить подключение к панели
            if (!ConnectionUtil.isIpConnected(configList.getHmiIP())) {
                runOnUiThread(()-> Toast.makeText(getApplicationContext(), "Отсутствует соединение с HMI", Toast.LENGTH_LONG).show());
                return;
            }
            //todo проверить адрес планшета
            if (!ConnectionUtil.getIpDevice(getApplicationContext()).equals("192.168.250.123")) {
                runOnUiThread(()-> Toast.makeText(getApplicationContext(), "Не верный адрес устройства", Toast.LENGTH_LONG).show());
                return;
            }

            File dir = new File(Environment.getExternalStorageDirectory() + "/IP_"+configList.getHmiIP()+"/datalog/");
            //todo проверить наличие папки с отчетами
            if (dir.exists()) {
                String str = mSettings.getString("lastDateTimeUnloadReportFromHMI",
                        "На устройстве была обнаружена папка с отчетами из панели. Открыть папку или снять отчёт?");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Снятие отчета");
                builder.setMessage("Дата последней выгрузки: " + str);
                builder.setIcon(R.drawable.download_report);
                builder.setNeutralButton("Отмена", (dialog, id) -> dialog.dismiss());
                builder.setPositiveButton("Снять отчет", (dialog, id) -> unloadReportsFromHMI(dir));
                builder.setNegativeButton("Открыть папку", (dialog, id) -> {
                    Intent intent = new Intent(getApplicationContext(), ReportHMIActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else unloadReportsFromHMI(dir);
        });

        mAddOpenFab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ReportDeviceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // Сохранение
        mAddSaveFab.setOnClickListener(view -> {
            checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
            //todo: сохранение excel
            exportExcelBtn.setOnClickListener(e -> {
                try {
                    new Thread(() -> {
                        runOnUiThread(() -> progressLoading.setVisibility(View.VISIBLE));

                        SqliteToExcelUtil sqliteToExcelUtil = new SqliteToExcelUtil(this, startDate, endDate);

                        //todo: выбор отчета
                        if (mixesReportChecker.isChecked())
                            sqliteToExcelUtil.createSheetMixes(cementLessFilter.isChecked());
                        if (partyReportChecker.isChecked())
                            sqliteToExcelUtil.createSheetParty(cementLessFilter.isChecked());
                        if (marksReportChecker.isChecked()) sqliteToExcelUtil.createSheetMark();
                        if (totalReportChecker.isChecked()) sqliteToExcelUtil.createSheetTotal();


                        if (sqliteToExcelUtil.exportExcel()) {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Сохранение выполенно успешно!");
                                builder.setMessage("Открыть папку с выгруженными отчетами?");

                                builder.setPositiveButton("Да", (dialog, id) -> {
                                    Intent intent = new Intent(getApplicationContext(), ReportHMIActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                });
                                builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Ошибка экспорта", Toast.LENGTH_SHORT).show();
                            });
                        }
                        runOnUiThread(() -> progressLoading.setVisibility(View.GONE));

                    }).start();

                    mAddFiltersFab.hide();
                    mAddSaveFab.hide();
                    mAddOpenFab.hide();
                    mSyncHMIFab.hide();
                    syncActionText.setVisibility(View.GONE);
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

        // Фильтр
        mAddFiltersFab.setOnClickListener(view -> filterDialog.show());

        // Применить фильтр
        applyReportBtn.setOnClickListener(e -> {
            progressLoading.setVisibility(View.VISIBLE);
            textInfoLoading.setText("Формирование отчета...");

            fragment = null;
            startDate = sdf.format(getDateFromDatePicker(dateBeginWidget));
            endDate = sdf.format(getDateFromDatePicker(dateEndWidget));

            if (DateTimeUtil.startLongerEnd(startDate, endDate)) {
                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.warning);
                    builder.setTitle("Уведомление").setMessage("Указан неверный диапазон дат!");
                    builder.setPositiveButton("ОК", (dialog, id) -> {
                        dialog.dismiss();
                    });
                    builder.show();
                });

                return;
            }

            //todo: прежде чем двигаться дальше поставить проверку на то, что там написал пользователь в полях дат периода
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

            fabVisible(true);

            filterDialog.hide();
        });

        // Закрыть фильтр
        closeDialog.setOnClickListener(e -> filterDialog.hide());

    }

    public void unloadReportsFromHMI(File dir) {
        new Thread(() -> {
            //todo удаляем папку если она есть
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                fabVisible(true);
                progressLoading.setVisibility(View.VISIBLE);
                textInfoLoading.setText("Загрузка...");
            });

            //todo запускаем сервер
            try {
                ftpServer = EZFtpServer.init();
                ftpServer.start();

                Thread.sleep(1000);

                Tag tag = new Tag();
                tag.setArea(S7.S7AreaDB);
                tag.setDbNumber(15);
                tag.setStart(18);
                tag.setBit(7);
                tag.setTypeTag("Bool");
                tag.setBoolValueIf(false);

                //todo проверка если при каких либо обстоятельствах тригер не был установлен в false, то мы отправляем false
                if (new CommandDispatcher(tag).readSingleRegister().isBoolValueIf()) {
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(false);
                }
                Thread.sleep(1000);

                //todo отправляем импульсный сигнал true = false
                new CommandDispatcher(tag).writeSingleInvertedBoolRegister();

                boolean chkLoadedData = true;
                //todo каждую секунду опрашиваем наличие загруженой папки
                while (chkLoadedData) {
                    Thread.sleep(1000);
                    if (dir.exists() && dir.isDirectory()) {
                        //todo если появилась папка, то через 5 сек, закрывем опрос и останавливаем сервер.
                        Thread.sleep(5000);
                        ftpServer.stop();
                        chkLoadedData = false;
                    }
                    System.out.println("Проверка наличия папки..." + dir.exists());
                }

                for (File file : dir.listFiles()) {
                    //todo переименовываем файлы в формат даты из zzbo_report_01012023 в 01.01.2023
                    String name = file.getName().replaceAll("zzbo_report_", "");
                    char[] fileName = name.toCharArray();
                    name = fileName[0] + "" + fileName[1] + "." +
                            fileName[2] + "" + fileName[3] + "." +
                            fileName[4] + "" + fileName[5] + "" + fileName[6] + "" + fileName[7];
                    File rename = new File(dir + "/" + name);
                    file.renameTo(rename);

                    //todo переводим CSV в XLSX
                    csvToXLSX(name, dir);
                    rename.delete();
                    System.out.println("Файл " + file + " переименован в " + name);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
                SharedPreferences.Editor prefEditor = mSettings.edit();
                prefEditor.putString("lastDateTimeUnloadReportFromHMI", sdf.format(new Date()));
                prefEditor.apply();

                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Снятие отчета");
                    builder.setIcon(R.drawable.download_report);
                    builder.setMessage("Выгрузка выполнена успешно! Открыть папку с отчетами?");
                    builder.setPositiveButton("Да", (dialog, id) -> {
                        new FileUtil().openFolder("IP_" + configList.getHmiIP() + "/datalog/");
                    });
                    builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> {
                    progressLoading.setVisibility(View.GONE);
                    textInfoLoading.setText("Сохранение...");
                });
            }

        }).start();
    }

    public static void csvToXLSX(String file, File dir) {
        try {
            String csvFileAddress = dir.getPath() + "/" + file; // + ".csv";
            String xlsxFileAddress = dir.getPath() + "/" + file + ".xlsx";
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine;
            int RowNum = 0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow = sheet.createRow(RowNum);
                for (int i = 0; i < str.length; i++) {
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String[]> read(InputStream inputStream) {
        List<String[]> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return resultList;
    }

    private void fabVisible(boolean visible) {
        if (!visible) {
            mAddFiltersFab.show();
            mAddSaveFab.show();
            mAddOpenFab.show();
            mSyncHMIFab.show();
            syncActionText.setVisibility(View.VISIBLE);
            addSaveActionText.setVisibility(View.VISIBLE);
            addFilterActionText.setVisibility(View.VISIBLE);
            addOpenActionText.setVisibility(View.VISIBLE);

            isAllFabsVisible = true;
        } else {
            mAddFiltersFab.hide();
            mAddSaveFab.hide();
            mAddOpenFab.hide();
            mSyncHMIFab.hide();
            syncActionText.setVisibility(View.GONE);
            addSaveActionText.setVisibility(View.GONE);
            addFilterActionText.setVisibility(View.GONE);
            addOpenActionText.setVisibility(View.GONE);

            isAllFabsVisible = false;
        }
    }

    private void initUI() {
        mAddFab = findViewById(R.id.add_fab);
        textInfoLoading = findViewById(R.id.text_info);
        progressLoading = findViewById(R.id.progress_loading);

        // FAB кнопки
        mAddFiltersFab = findViewById(R.id.filter_fab);
        mAddSaveFab = findViewById(R.id.save_fab);
        mAddOpenFab = findViewById(R.id.open_fab);
        mSyncHMIFab = findViewById(R.id.sync_fab);

        // Текст названия действия для всех FABs.
        syncActionText = findViewById(R.id.sync_text);
        addSaveActionText = findViewById(R.id.save_text);
        addFilterActionText = findViewById(R.id.filter_text);
        addOpenActionText = findViewById(R.id.open_text);

        mAddFiltersFab.setVisibility(View.GONE);
        mAddSaveFab.setVisibility(View.GONE);
        mAddOpenFab.setVisibility(View.GONE);
        mSyncHMIFab.setVisibility(View.GONE);
        syncActionText.setVisibility(View.GONE);
        addSaveActionText.setVisibility(View.GONE);
        addFilterActionText.setVisibility(View.GONE);
        addOpenActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        tabLayout = findViewById(R.id.reportTabController);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Итоговый отчет");
        tabLayout.addTab(firstTab);

        TabLayout.Tab mixTab = tabLayout.newTab();
        mixTab.setText("По замесам");
        tabLayout.addTab(mixTab);

        TabLayout.Tab partyTab = tabLayout.newTab();
        partyTab.setText("По партиям");
        tabLayout.addTab(partyTab);

        TabLayout.Tab marksTab = tabLayout.newTab();
        marksTab.setText("По маркам");
        tabLayout.addTab(marksTab);

        mFilterBuilder = new AlertDialog.Builder(this);
        mFilterView = getLayoutInflater().inflate(R.layout.dialog_filter, null);
        mFilterBuilder.setView(mFilterView);
        filterDialog = mFilterBuilder.create();

        applyReportBtn = mFilterView.findViewById(R.id.applyBtn);
        closeDialog = mFilterView.findViewById(R.id.closeDialog);
        dateBeginWidget = mFilterView.findViewById(R.id.dateBeginWidget);
        dateEndWidget = mFilterView.findViewById(R.id.dateEndWidget);
        cementLessFilter = mFilterView.findViewById(R.id.autoCorrectionShnekSelector);

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

    // Функция проверки и запроса разрешения
    public void checkPermission(Activity activity, String permission, int requestCode) {
        // Проверка, если разрешение не выдано
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

}