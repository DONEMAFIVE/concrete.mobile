package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.answer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.protocol.profinet.collectors.DynamicTagBuilder;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.BlockMultiple;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
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
                new Thread(() -> {
                    initAnswer();
                    Intent intent = new Intent(getApplicationContext(), FactoryConfigActivity.class);
                    startActivity(intent);
                }).start();
            }
        });

        calibrationBtn.setOnClickListener(e -> {
            if (Constants.exchangeLevel != 1) {
                Intent intent = new Intent(getApplicationContext(), CalibrateWeightsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initAnswer() {
        CommandDispatcher commandDispatcher = new CommandDispatcher();

        List<Tag> getTagListAdditionalOptions = new DBTags(this).getTags("tags_additional_options");
        DynamicTagBuilder dynamicTagCollector = new DynamicTagBuilder(getTagListAdditionalOptions);
        dynamicTagCollector.buildSortedTags();

        List<BlockMultiple> tagIntAnswer = dynamicTagCollector.getTagIntAnswer();
        List<BlockMultiple> tagDIntAnswer = dynamicTagCollector.getTagDIntAnswer();
        List<BlockMultiple> tagRealAnswer = dynamicTagCollector.getTagRealAnswer();

        answer = new ArrayList<>();
        answer.addAll(commandDispatcher.readMultipleIntRegister(tagIntAnswer, getTagListAdditionalOptions));
        answer.addAll(commandDispatcher.readMultipleDIntRegister(tagDIntAnswer, getTagListAdditionalOptions));
        answer.addAll(commandDispatcher.readMultipleRealRegister(tagRealAnswer, getTagListAdditionalOptions));
    }
}