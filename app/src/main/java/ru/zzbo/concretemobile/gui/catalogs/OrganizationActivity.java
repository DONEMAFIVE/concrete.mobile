package ru.zzbo.concretemobile.gui.catalogs;

import static ru.zzbo.concretemobile.utils.Constants.editedOrganization;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.os.Bundle;
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
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class OrganizationActivity extends AppCompatActivity {

    private EditText organizationIDField;
    private CheckBox personaChecker;
    private EditText dateCreateField;
    private EditText headNameField;
    private EditText orgNameField;
    private EditText innField;
    private EditText kppField;
    private EditText okpoField;
    private EditText phoneField;
    private EditText addressField;
    private EditText contactNameField;
    private EditText contactPhoneField;
    private EditText commentField;
    private Button saveBtn;
    private Button delBtn;
    private Button closeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_activity);

        initUI();
        initFieldsUI();
        initActions();
    }

    private void initActions() {
        personaChecker.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),
                    "При активации опции Физическое лицо заполняется только поле - Название организации",
                    Toast.LENGTH_SHORT).show();
        });

        saveBtn.setOnClickListener(view -> {
            Organization org;
            int persona = 0;
            if (personaChecker.isChecked()) persona = 1;

            if (!personaChecker.isChecked()) {
                //пользователь хочет сохранить организацию со всем набором полей
                if (!organizationIDField.equals("") ||
                        !personaChecker.equals("") ||
                        !dateCreateField.equals("") ||
                        !headNameField.equals("") ||
                        !orgNameField.equals("") ||
                        !innField.equals("") ||
                        !kppField.equals("") ||
                        !okpoField.equals("") ||
                        !phoneField.equals("") ||
                        !addressField.equals("") ||
                        !contactNameField.equals("") ||
                        !contactPhoneField.equals("")
                ) {
                    //сохраняем организацию со всеми полями
                    org = new Organization(
                            Integer.parseInt(String.valueOf(organizationIDField.getText())),
                            String.valueOf(headNameField.getText()),
                            String.valueOf(orgNameField.getText()),
                            persona,
                            String.valueOf(innField.getText()),
                            String.valueOf(kppField.getText()),
                            String.valueOf(okpoField.getText()),
                            String.valueOf(phoneField.getText()),
                            String.valueOf(addressField.getText()),
                            String.valueOf(commentField.getText()),
                            String.valueOf(contactNameField.getText()),
                            String.valueOf(contactPhoneField.getText())
                    );
                    new Thread(() -> {
                        if (exchangeLevel == 1) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            if (editedOrganization.getId() != 0)
                                OkHttpUtil.updOrganization(gson.toJson(org));
                            else OkHttpUtil.newOrganization(gson.toJson(org));
                        } else {
                            if (editedOrganization.getId() != 0)
                                new DBUtilUpdate(getApplicationContext()).updateOrganization(org);
                            else new DBUtilInsert(getApplicationContext()).insertIntoOrgs(org);
                        }
                    }).start();
                    super.onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "Одно или несколько полей не заполнены!", Toast.LENGTH_SHORT).show();
                }

                //пользователь хочет сохранить организацию как физ лицо, заполняется в этом случае только поле название организации
            } else {
                //todo: читаем только поле название организации, все остальное заполняем сами
                if (!orgNameField.equals("")) {
                    org = new Organization(
                            Integer.parseInt(String.valueOf(organizationIDField.getText())),
                            "-",
                            String.valueOf(orgNameField.getText()),
                            persona,
                            "-",
                            "-",
                            "-",
                            "-",
                            "-",
                            "-",
                            "-",
                            "-"
                    );
                    if (editedOrganization.getId() != 0) {
                        new DBUtilUpdate(getApplicationContext()).updateOrganization(org);
                    } else {
                        new DBUtilInsert(getApplicationContext()).insertIntoOrgs(org);
                    }
                    super.onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "Заполните поле Название организации", Toast.LENGTH_SHORT).show();
                }
            }

        });
        delBtn.setOnClickListener(view -> {
            new Thread(() -> {
                if (exchangeLevel == 1) {
                    OkHttpUtil.delOrganization(editedOrganization.getId());
                } else {
                    new DBUtilDelete(getApplicationContext()).deleteOrg(editedOrganization.getId());
                }
            }).start();

            super.onBackPressed();
            Toast.makeText(getApplicationContext(),
                    "Организация " + editedOrganization.getOrganizationName() + " удалена",
                    Toast.LENGTH_SHORT).show();
        });
        closeBtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }

    private void initUI() {
        organizationIDField = findViewById(R.id.organizationIDField);
        personaChecker = findViewById(R.id.personaChecker);
        dateCreateField = findViewById(R.id.typeOrganizationField);
        headNameField = findViewById(R.id.nameOrganizationField);
        orgNameField = findViewById(R.id.orgNameField);
        innField = findViewById(R.id.innField);
        kppField = findViewById(R.id.kppField);
        okpoField = findViewById(R.id.okpoField);
        phoneField = findViewById(R.id.phoneField);
        addressField = findViewById(R.id.addressField);
        contactNameField = findViewById(R.id.contactNameField);
        contactPhoneField = findViewById(R.id.contactPhoneField);
        commentField = findViewById(R.id.commentField);

        saveBtn = findViewById(R.id.saveOrg);
        delBtn = findViewById(R.id.delOrg);
        closeBtn = findViewById(R.id.closeOrg);

        if (editedOrganization.getId() == 0) delBtn.setVisibility(View.INVISIBLE);

    }

    private void initFieldsUI() {
        organizationIDField.setText(String.valueOf(editedOrganization.getId()));
        headNameField.setText(String.valueOf(editedOrganization.getOrganizationHeadName()));
        orgNameField.setText(String.valueOf(editedOrganization.getOrganizationName()));
        innField.setText(String.valueOf(editedOrganization.getInn()));
        kppField.setText(String.valueOf(editedOrganization.getKpp()));
        okpoField.setText(String.valueOf(editedOrganization.getOkpo()));
        phoneField.setText(String.valueOf(editedOrganization.getPhone()));
        addressField.setText(String.valueOf(editedOrganization.getAddress()));
        contactNameField.setText(String.valueOf(editedOrganization.getContactName()));
        contactPhoneField.setText(String.valueOf(editedOrganization.getContactPhone()));
        commentField.setText(String.valueOf(editedOrganization.getId()));

        personaChecker.setChecked(editedOrganization.getPersona() == 1);
    }
}
