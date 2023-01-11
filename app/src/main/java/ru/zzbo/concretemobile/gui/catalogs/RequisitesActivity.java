package ru.zzbo.concretemobile.gui.catalogs;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.zzbo.concretemobile.R;

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
    private Button delBtn;
    private Button closeBtn;

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
        headNameField = findViewById(R.id.nameOrganizationField);
        addressField = findViewById(R.id.addressOrgField);
        phoneField = findViewById(R.id.phoneField);
        faxField = findViewById(R.id.faxField);
        siteField = findViewById(R.id.siteField);
        emailField = findViewById(R.id.emailField);
        loadAddressField = findViewById(R.id.loadAddressField);
        dispatcherNameField = findViewById(R.id.dispatcherNameField);
        commentField = findViewById(R.id.commentField);

        saveBtn = findViewById(R.id.saveOrg);
        delBtn = findViewById(R.id.delOrg);
        closeBtn = findViewById(R.id.closeOrg);
    }

    private void startListeners() {
        saveBtn.setOnClickListener(view -> {

        });

        delBtn.setOnClickListener(view -> {

        });

        closeBtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }

}
