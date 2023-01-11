package ru.zzbo.concretemobile.gui.catalogs;

import static ru.zzbo.concretemobile.utils.Constants.editedTransporter;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class TransporterActivity extends AppCompatActivity {

    private EditText transporterIDField;
    private CheckBox personaChecker;
    private EditText regNumberAutoField;
    private EditText orgNameField;
    private EditText innField;
    private EditText driverNameField;
    private EditText markAutoField;
    private EditText phoneDriverField;
    private EditText addressOrgField;
    private EditText commentField;
    private Button saveBtn;
    private Button delBtn;
    private Button closeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transporters_activity);
        initialUI();
        initialFieldsUI();
        startListeners();
    }

    private void startListeners() {
        personaChecker.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "При активации опции Физическое лицо заполняется только поле - Регистрационный номер авто", Toast.LENGTH_SHORT).show();
        });
        saveBtn.setOnClickListener(view -> {
            int persona = 0;
            if (personaChecker.isChecked()) persona = 1;
            Transporter trans;

            if (!personaChecker.isChecked()) {
                //пользователь хочет сохранить водителя со всем набором полей
                if (!transporterIDField.equals("") ||
                        !regNumberAutoField.equals("") ||
                        !orgNameField.equals("") ||
                        !innField.equals("") ||
                        !driverNameField.equals("") ||
                        !markAutoField.equals("") ||
                        !phoneDriverField.equals("") ||
                        !addressOrgField.equals("") ||
                        !commentField.equals("")
                ) {
                    //сохраняем водителя со всеми полями

                    trans = new Transporter(
                            Integer.parseInt(String.valueOf(transporterIDField.getText())),
                            String.valueOf(regNumberAutoField.getText()),
                            String.valueOf(orgNameField.getText()),
                            persona,
                            String.valueOf(innField.getText()),
                            String.valueOf(driverNameField.getText()),
                            String.valueOf(markAutoField.getText()),
                            String.valueOf(phoneDriverField.getText()),
                            String.valueOf(addressOrgField.getText()),
                            String.valueOf(commentField.getText())
                    );

                    new Thread(() -> {
                        if (exchangeLevel == 1) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            if (editedTransporter.getId() != 0)
                                OkHttpUtil.updTransporters(gson.toJson(trans));
                            else OkHttpUtil.newTransporters(gson.toJson(trans));
                        } else {
                            if (editedTransporter.getId() != 0)
                                new DBUtilUpdate(getApplicationContext()).updateTransporter(trans);
                            else new DBUtilInsert(getApplicationContext()).insertIntoTrans(trans);
                        }
                    }).start();
                    super.onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "Одно или несколько полей не заполнены!", Toast.LENGTH_SHORT).show();
                }

                //пользователь хочет сохранить организацию как физ лицо, заполняется в этом случае только поле название организации
            } else {
                //читаем только поле название организации, все остальное заполняем сами
                if (!orgNameField.equals("")) {
                    if (personaChecker.isChecked()) persona = 1;
                    trans = new Transporter(
                            Integer.parseInt(String.valueOf(transporterIDField.getText())),
                            String.valueOf(regNumberAutoField.getText()),
                            String.valueOf(orgNameField.getText()),
                            persona,
                            "-",
                            "-",
                            "-",
                            "-",
                            "-",
                            "-"
                    );

                    new Thread(() -> {
                        if (exchangeLevel == 1) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            if (editedTransporter.getId() != 0)
                                OkHttpUtil.updTransporters(gson.toJson(trans));
                            else OkHttpUtil.newTransporters(gson.toJson(trans));
                        } else {
                            if (editedTransporter.getId() != 0)
                                new DBUtilUpdate(getApplicationContext()).updateTransporter(trans);
                            else new DBUtilInsert(getApplicationContext()).insertIntoTrans(trans);
                        }
                    }).start();
                    super.onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "Заполните поле - Регистрационный номер авто", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delBtn.setOnClickListener(view -> {
            new Thread(() -> {
                if (exchangeLevel == 1) {
                    OkHttpUtil.delTransporters(editedTransporter.getId());
                } else {
                    new DBUtilDelete(getApplicationContext()).deleteTrans(editedTransporter.getId());
                }
            }).start();
            Toast.makeText(getApplicationContext(), "Перевозчик " + editedTransporter.getOrganizationName() + " удален", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        });
        closeBtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }

    private void initialUI() {

        transporterIDField = findViewById(R.id.transporterIDField);
        personaChecker = findViewById(R.id.personaChecker);
        regNumberAutoField = findViewById(R.id.regNumberAutoField);
        orgNameField = findViewById(R.id.orgNameField);
        innField = findViewById(R.id.innField);
        driverNameField = findViewById(R.id.driverNameField);
        markAutoField = findViewById(R.id.markAutoField);
        phoneDriverField = findViewById(R.id.phoneDriverField);
        addressOrgField = findViewById(R.id.addressOrgField);
        commentField = findViewById(R.id.commentField);
        saveBtn = findViewById(R.id.saveTrans);
        delBtn = findViewById(R.id.delTrans);
        closeBtn = findViewById(R.id.closeTrans);
        if (editedTransporter.getId() == 0) delBtn.setVisibility(View.INVISIBLE);

    }

    private void initialFieldsUI() {

        transporterIDField.setText(String.valueOf(editedTransporter.getId()));
        regNumberAutoField.setText(String.valueOf(editedTransporter.getRegNumberAuto()));
        orgNameField.setText(String.valueOf(editedTransporter.getOrganizationName()));
        innField.setText(String.valueOf(editedTransporter.getInn()));
        driverNameField.setText(String.valueOf(editedTransporter.getDriverName()));
        markAutoField.setText(String.valueOf(editedTransporter.getMarkAuto()));
        phoneDriverField.setText(String.valueOf(editedTransporter.getPhone()));
        addressOrgField.setText(String.valueOf(editedTransporter.getAddress()));
        commentField.setText(String.valueOf(editedTransporter.getComment()));

        personaChecker.setChecked(editedTransporter.getPersona() == 1);

    }

}
