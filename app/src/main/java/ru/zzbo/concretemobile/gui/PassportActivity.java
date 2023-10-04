package ru.zzbo.concretemobile.gui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.models.Requisites;

public class PassportActivity extends AppCompatActivity {
    private EditText breakstoneField;
    private EditText capacityConcreteMixField;
    private EditText cementField;
    private EditText chiefNameField;
    private EditText compositionNumberField;
    private Spinner classMarkaField;
    private Spinner consumerField;
    private Spinner hoursShipmentConcreteMix;
    private Button createPassportQualityBtn;
    private EditText dateIssuePicker;
    private EditText dateShipmentConcreteMix;
    private EditText issuedDatePicker;
    private Spinner minuteShipmentConcreteMix;
    private EditText mixNField;
    private EditText operatorNameField;
    private EditText percentField;
    private EditText placeholderSizeField;
    private EditText requiredStrengthField;
    private EditText sandField;
    private EditText stackabilityField;
    private EditText typeMixField;
    private EditText weightCementField;
    private Requisites requisites;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
        initialUI();
    }

    private void initialUI() {
        breakstoneField = findViewById(R.id.breakstoneField);
        classMarkaField = findViewById(R.id.classMarkaField);
        capacityConcreteMixField = findViewById(R.id.capacityConcreteMixField);
        cementField = findViewById(R.id.cementField);
        chiefNameField = findViewById(R.id.chiefNameField);
        compositionNumberField = findViewById(R.id.compositionNumberField);
        consumerField = findViewById(R.id.consumerField);
        hoursShipmentConcreteMix = findViewById(R.id.hoursShipmentConcreteMix);
        minuteShipmentConcreteMix = findViewById(R.id.minuteShipmentConcreteMix);
        dateIssuePicker = findViewById(R.id.dateIssuePicker);
        dateShipmentConcreteMix = findViewById(R.id.dateShipmentConcreteMix);
        issuedDatePicker = findViewById(R.id.issuedDatePicker);
        mixNField = findViewById(R.id.mixNField);
        operatorNameField = findViewById(R.id.operatorNameField);
        percentField = findViewById(R.id.percentField);
        placeholderSizeField = findViewById(R.id.placeholderSizeField);
        requiredStrengthField = findViewById(R.id.requiredStrengthField);
        sandField = findViewById(R.id.sandField);
        stackabilityField = findViewById(R.id.stackabilityField);
        typeMixField = findViewById(R.id.typeMixField);
        weightCementField = findViewById(R.id.weightCementField);
//        createPassportQualityBtn = findViewById(R.id.createPassportQualityBtn);

        requisites = new DBUtilGet(getApplicationContext()).getRequisites();

        new Handler(Looper.getMainLooper()).post(() -> {

        });


    }
}
