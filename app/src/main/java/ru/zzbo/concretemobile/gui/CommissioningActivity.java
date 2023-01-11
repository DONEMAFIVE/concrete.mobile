package ru.zzbo.concretemobile.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.utils.Constants;

public class CommissioningActivity extends AppCompatActivity {

    private Button configBtn;
    private Button settingsBtn;
    private Button calibrationBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioning);

        configBtn = findViewById(R.id.configBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        calibrationBtn = findViewById(R.id.calibrationBtn);

        initActions();
    }

    private void initActions() {
        configBtn.setOnClickListener(e -> {
            Intent intent = new Intent(getApplicationContext(), SystemConfigActivity.class);
            startActivity(intent);
        });

        settingsBtn.setOnClickListener(e -> {
            if (Constants.exchangeLevel != 1) {
                Intent intent = new Intent(getApplicationContext(), FactoryConfigActivity.class);
                startActivity(intent);
            }
        });

        calibrationBtn.setOnClickListener(e -> {
            if (Constants.exchangeLevel != 1) {
                Intent intent = new Intent(getApplicationContext(), CalibrateWeightsActivity.class);
                startActivity(intent);
            }
        });
    }
}