package ru.zzbo.concretemobile.gui.catalogs;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.models.Requisites;

public class RequisitesActivity extends AppCompatActivity {

    private EditText typeOrganizationField;
    private EditText nameOrganizationField;
    private EditText innField;
    private EditText addressField;
    private EditText headNameField;
    private EditText phoneField;
    private EditText faxField;
    private EditText siteField;
    private EditText emailField;
    private EditText loadAddressField;
    private EditText dispatcherNameField;
    private EditText commentField;
    private Button saveBtn;
    private Button closeBtn;

    private Requisites requisites;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requisites_activity);

        initialUI();
        startListeners();
    }

    private void initialUI() {
        typeOrganizationField = findViewById(R.id.typeOrganizationField);
        nameOrganizationField = findViewById(R.id.nameOrganizationField);
        innField = findViewById(R.id.innField);
        headNameField = findViewById(R.id.headNameField);
        addressField = findViewById(R.id.addressField);
        phoneField = findViewById(R.id.phoneField);
        faxField = findViewById(R.id.faxField);
        siteField = findViewById(R.id.siteField);
        emailField = findViewById(R.id.emailField);
        loadAddressField = findViewById(R.id.loadAddressField);
        dispatcherNameField = findViewById(R.id.dispatcherNameField);
        commentField = findViewById(R.id.commentField);

        saveBtn = findViewById(R.id.saveOrg);
        closeBtn = findViewById(R.id.closeOrg);

        requisites = new DBUtilGet(getApplicationContext()).getRequisites();

        new Handler(Looper.getMainLooper()).post(() -> {
            typeOrganizationField.setText(requisites.getOrganizationType());
            nameOrganizationField.setText(requisites.getOrganizationName());
            innField.setText(requisites.getInn());
            addressField.setText(requisites.getAddress());
            headNameField.setText(requisites.getHeadName());
            phoneField.setText(requisites.getPhone());
            faxField.setText(requisites.getFax());
            siteField.setText(requisites.getSite());
            emailField.setText(requisites.getEmail());
            commentField.setText(requisites.getComment());
            loadAddressField.setText(requisites.getLoadAddress());
            dispatcherNameField.setText(requisites.getDispatcherName());
        });


    }

    private void startListeners() {
        saveBtn.setOnClickListener(view -> {
            try {
               boolean res = new DBUtilUpdate(getApplicationContext()).updateRequisites(
                        new Requisites(
                                requisites.getId(),
                                typeOrganizationField.getText().toString(),
                                nameOrganizationField.getText().toString(),
                                innField.getText().toString(),
                                addressField.getText().toString(),
                                headNameField.getText().toString(),
                                phoneField.getText().toString(),
                                faxField.getText().toString(),
                                siteField.getText().toString(),
                                emailField.getText().toString(),
                                commentField.getText().toString(),
                                loadAddressField.getText().toString(),
                                dispatcherNameField.getText().toString()
                        )
                );
               if (res) {
                   Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_LONG).show();
                   finish();
               }

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        closeBtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }

}
