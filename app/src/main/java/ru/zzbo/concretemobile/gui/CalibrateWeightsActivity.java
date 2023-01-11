package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.tagListMain;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.protocol.profinet.collectors.request.WeightCollector;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

public class CalibrateWeightsActivity extends AppCompatActivity {
    private boolean threadState = true;

    private Button calibrateDWBtn;
    private Button calibrateDKBtn;
    private Button calibrateDChBtn;
    private Button calibrateDCBtn;
    private Button calibrateDFBtn;

    private Button nullWeightDWBtn;
    private Button nullWeightDKBtn;
    private Button nullWeightDFBtn;
    private Button nullWeightDChBtn;
    private Button nullWeightDCBtn;

    private TextView analogWeightDWView;
    private TextView analogWeightDKView;
    private TextView analogWeightCementView;
    private TextView analogWeightChemyView;
    private TextView analogWeightFibraView;

    private TextView currentWeightDWView;
    private TextView currentWeightDKView;
    private TextView currentWeightDCView;
    private TextView currentWeightDChView;
    private TextView currentWeightDFView;

    private EditText acceptCalibrateValueDW;
    private EditText acceptCalibrateValueDK;
    private EditText acceptCalibrateValueDC;
    private EditText acceptCalibrateValueDCh;
    private EditText acceptCalibrateValueDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate_weights);

        tagListMain = new DBTags(getApplicationContext()).getTags("tags_main");
        tagListManual = new DBTags(getApplicationContext()).getTags("tags_manual");

        analogWeightDWView = findViewById(R.id.inputAnalogDW);
        analogWeightDKView = findViewById(R.id.inputAnalogDK);
        analogWeightCementView = findViewById(R.id.inputAnalogDC);
        analogWeightChemyView = findViewById(R.id.inputAnalogDCH);
        analogWeightFibraView = findViewById(R.id.inputAnalogDF);

        currentWeightDWView = findViewById(R.id.currentWeightDW);
        currentWeightDKView = findViewById(R.id.currentWeightDK);
        currentWeightDCView = findViewById(R.id.currentWeightDC);
        currentWeightDChView = findViewById(R.id.currentWeightDCH);
        currentWeightDFView = findViewById(R.id.currentWeightDF);

        calibrateDWBtn = findViewById(R.id.calibrateDW);
        calibrateDKBtn = findViewById(R.id.calibrateDK);
        calibrateDChBtn = findViewById(R.id.calibrateDCH);
        calibrateDCBtn = findViewById(R.id.calibrateDC);
        calibrateDFBtn = findViewById(R.id.calibrateDF);

        nullWeightDWBtn = findViewById(R.id.nullDW);
        nullWeightDKBtn = findViewById(R.id.nullDK);
        nullWeightDFBtn = findViewById(R.id.nullDF);
        nullWeightDChBtn = findViewById(R.id.nullDCH);
        nullWeightDCBtn = findViewById(R.id.nullDC);

        acceptCalibrateValueDW = findViewById(R.id.currentWeightCalibrateDW);
        acceptCalibrateValueDK = findViewById(R.id.currentWeightCalibrateDK);
        acceptCalibrateValueDC = findViewById(R.id.currentWeightCalibrateDC);
        acceptCalibrateValueDCh = findViewById(R.id.currentWeightCalibrateDCH);
        acceptCalibrateValueDF = findViewById(R.id.currentWeightCalibrateDF);

        initActions();

        new Thread(() -> {
            WeightCollector weightCollector = new WeightCollector(this);
            while (threadState) {
                weightCollector.getValues();
                runOnUiThread(() -> {
                    analogWeightDWView.setText("Сигнал входа: " + weightCollector.getAnalogWater());
                    analogWeightDKView.setText("Сигнал входа: " + weightCollector.getAnalogDK());
                    analogWeightChemyView.setText("Сигнал входа: " + weightCollector.getAnalogChemy());
                    analogWeightCementView.setText("Сигнал входа: " + weightCollector.getAnalogCement());
                    analogWeightFibraView.setText("Сигнал входа: " + weightCollector.getAnalogFibra());

                    currentWeightDWView.setText("Тек: " + weightCollector.getWeightWater());
                    currentWeightDKView.setText("Тек: " + weightCollector.getWeightDK());
                    currentWeightDChView.setText("Тек: " + weightCollector.getWeightChemy());
                    currentWeightDCView.setText("Тек: " + weightCollector.getWeightCement());
                    currentWeightDFView.setText("Тек: " + weightCollector.getWeightFibra());
                });

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread busy ...");
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        threadState = false;
        super.onBackPressed();
    }

    public void initActions() {
        /**
         * обработчики нажатий кнопок
         * у обработчиков кнопок считывается аналоговый сигнал не поле ввода
         */

        //дозатор воды
        nullWeightDWBtn.setOnClickListener(view -> {
            try {
                if (!analogWeightDWView.getText().equals("")) {
                    Tag calAnalogWeightTag = new DBTags(this).getTagForNum(29, "tags_manual");
                    new CommandDispatcher(calAnalogWeightTag).writeSingleFrontBoolRegister(1000);
                    Toast.makeText(getApplicationContext(), "Нулевое значение дозатора минеральных записано", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Недопустимые символы", Toast.LENGTH_SHORT).show();
            }
        });
        calibrateDWBtn.setOnClickListener(view -> {
            try {
                float currentWeight = Float.valueOf(String.valueOf(acceptCalibrateValueDW.getText()));
                if (currentWeight > 0) {
                    Tag calibrateWeight = new DBTags(this).getTagForNum(68, "tags_manual");
                    calibrateWeight.setRealValueIf(currentWeight);

                    Tag calibrateWeightWithAnalogTag = new DBTags(this).getTagForNum(30, "tags_manual");
                    new Thread(() -> {
                        new CommandDispatcher(calibrateWeight).writeSingleRegisterWithLock();                                                                   //запись веса кал груза
                        new CommandDispatcher(calibrateWeightWithAnalogTag).writeSingleFrontBoolRegister(1000);                                             //запись аналогового с грузом
                    }).start();
                    Toast.makeText(getApplicationContext(), "Новое калибровочное значение вес Доз Вод (аналог) + вес гири (кг) [" + calibrateWeight.getRealValueIf() + "] записано", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Возможны только положительные значения", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Некорректный ввод калибровочный вес гири ДВ.", Toast.LENGTH_SHORT).show();
            }
        });

        //дозирующий комплекс
        nullWeightDKBtn.setOnClickListener(view -> {
            try {
                if (!analogWeightDKView.getText().equals("")) {
                    Tag calAnalogWeightTag = new DBTags(this).getTagForNum(27, "tags_manual");
                    new CommandDispatcher(calAnalogWeightTag).writeSingleFrontBoolRegister(1000);
                    Toast.makeText(getApplicationContext(), "Нулевое значение ДК", Toast.LENGTH_SHORT).show();

                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Недопустимые символы", Toast.LENGTH_SHORT).show();
            }
        });
        calibrateDKBtn.setOnClickListener(view -> {
            try {
                float currentWeight = Float.valueOf(String.valueOf(acceptCalibrateValueDK.getText()));
                if (currentWeight > 0) {
                    Tag calibrateWeight = new DBTags(this).getTagForNum(67, "tags_manual");
                    calibrateWeight.setRealValueIf(currentWeight);

                    Tag calibrateWeightWithAnalogTag = new DBTags(this).getTagForNum(28, "tags_manual");
                    new Thread(() -> {
                        new CommandDispatcher(calibrateWeight).writeSingleRegisterWithLock();                                                                   //запись веса кал груза
                        new CommandDispatcher(calibrateWeightWithAnalogTag).writeSingleFrontBoolRegister(1000);                                                      //запись аналогового с грузом
                    }).start();
                    Toast.makeText(getApplicationContext(), "Новое калибровочное значение вес ДК (аналог) + вес гири (кг) [" + calibrateWeight.getRealValueIf() + "] записано", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Возможны только положительные значения", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Некорректный ввод калибровочный вес гири ДК.", Toast.LENGTH_SHORT).show();
            }
        });

        //дозатор химии
        nullWeightDChBtn.setOnClickListener(view -> {
            try {
                if (!analogWeightChemyView.getText().equals("")) {
                    Tag calAnalogWeightTag = new DBTags(this).getTagForNum(33, "tags_manual");
                    new CommandDispatcher(calAnalogWeightTag).writeSingleFrontBoolRegister(1000);
                    Toast.makeText(getApplicationContext(), "Нулевое значение ДХ", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Недопустимые символы", Toast.LENGTH_SHORT).show();
            }
        });
        calibrateDChBtn.setOnClickListener(view -> {
            try {
                float currentWeight = Float.valueOf(String.valueOf(acceptCalibrateValueDCh.getText()));
                if (currentWeight > 0) {
                    Tag calibrateWeight = new DBTags(this).getTagForNum(70, "tags_manual");
                    calibrateWeight.setRealValueIf(currentWeight);

                    Tag calibrateWeightWithAnalogTag = new DBTags(this).getTagForNum(34, "tags_manual");
                    new Thread(() -> {
                        new CommandDispatcher(calibrateWeight).writeSingleRegisterWithLock();                                                                   //запись веса кал груза
                        new CommandDispatcher(calibrateWeightWithAnalogTag).writeSingleFrontBoolRegister(1000);                                    //запись аналогового с грузом
                    }).start();

                    Toast.makeText(getApplicationContext(), "Новое калибровочное значение вес Доз Хим (аналог) + вес гири (кг) [" + calibrateWeight.getRealValueIf() + "] записано", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Возможны только положительные значения", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Некорректный ввод калибровочный вес гири ДХ.", Toast.LENGTH_SHORT).show();
            }
        });

        //дозатор цемента
        nullWeightDCBtn.setOnClickListener(view -> {
            try {
                if (!analogWeightCementView.getText().equals("")) {
                    Tag calAnalogWeightTag = new DBTags(this).getTagForNum(31, "tags_manual");
                    new CommandDispatcher(calAnalogWeightTag).writeSingleFrontBoolRegister(1000);
                    Toast.makeText(getApplicationContext(), "Нулевое значение ДЦ", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Недопустимые символы", Toast.LENGTH_SHORT).show();
            }
        });
        calibrateDCBtn.setOnClickListener(view -> {
            try {
                float currentWeight = Float.valueOf(String.valueOf(acceptCalibrateValueDC.getText()));
                if (currentWeight > 0) {
                    Tag calibrateWeight = new DBTags(this).getTagForNum(69, "tags_manual");
                    calibrateWeight.setRealValueIf(currentWeight);

                    Tag calibrateWeightWithAnalogTag = new DBTags(this).getTagForNum(32, "tags_manual");
                    new Thread(() -> {
                        new CommandDispatcher(calibrateWeight).writeSingleRegisterWithLock();                                                                   //запись веса кал груза
                        new CommandDispatcher(calibrateWeightWithAnalogTag).writeSingleFrontBoolRegister(1000);                                           //запись аналогового с грузом
                    }).start();
                    Toast.makeText(getApplicationContext(), "Новое калибровочное значение вес Доз Цем (аналог) + вес гири (кг) [" + calibrateWeight.getRealValueIf() + "] записано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Возможны только положительные значения", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Некорректный ввод калибровочный вес гири ДЦ.", Toast.LENGTH_SHORT).show();
            }
        });

        //дозатор фибры
        nullWeightDFBtn.setOnClickListener(view -> {
            try {
                if (!analogWeightFibraView.getText().equals("")) {
                    Tag calAnalogWeightTag = new DBTags(this).getTagForNum(118, "tags_manual");
                    new CommandDispatcher(calAnalogWeightTag).writeSingleFrontBoolRegister(1000);
                    Toast.makeText(getApplicationContext(), "Нулевое значение ДФ", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Недопустимые символы", Toast.LENGTH_SHORT).show();
            }
        });
        calibrateDFBtn.setOnClickListener(view -> {
            try {
                float currentWeight = Float.valueOf(String.valueOf(acceptCalibrateValueDF.getText()));
                if (currentWeight > 0) {
                    Tag calibrateWeight = new DBTags(this).getTagForNum(120, "tags_manual");
                    calibrateWeight.setRealValueIf(currentWeight);

                    Tag calibrateWeightWithAnalogTag = new DBTags(this).getTagForNum(119, "tags_manual");
                    new Thread(() -> {
                        new CommandDispatcher(calibrateWeight).writeSingleRegisterWithLock();                                                                   //запись веса кал груза
                        new CommandDispatcher(calibrateWeightWithAnalogTag).writeSingleFrontBoolRegister(1000);                                           //запись аналогового с грузом
                    }).start();
                    Toast.makeText(getApplicationContext(), "Новое калибровочное значение вес Доз Цем (аналог) + вес гири (кг) [" + calibrateWeight.getRealValueIf() + "] записано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Возможны только положительные значения", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Некорректный ввод калибровочный вес гири ДЦ.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}