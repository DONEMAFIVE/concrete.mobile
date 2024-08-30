package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.editedTransporter;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.selectedTrans;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.adapters.TransporterAdapter;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.gui.catalogs.TransporterActivity;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class TransportersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton createRecipeBtn, setOrgBtn, setEmptyBtn;
    private List<Transporter> transporters = new ArrayList<>();
    private ConstraintLayout orgsNotFound;
    private TransporterAdapter adapter;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporters);

        initFieldUI();
        initData();
        initActions();
    }

    private void filter(String text){
        List<Transporter> filteredList = new ArrayList<>();

        for (Transporter transporter : transporters){
            if (
                    String.valueOf(transporter.getId()).toLowerCase().contains(text.toLowerCase()) ||
                    transporter.getOrganizationName().toLowerCase().contains(text.toLowerCase()) ||
                    transporter.getMarkAuto().toLowerCase().contains(text.toLowerCase()) ||
                    transporter.getRegNumberAuto().toLowerCase().contains(text.toLowerCase())
            ) filteredList.add(transporter);
        }
        adapter.filterList(filteredList);
    }
    private void initActions() {
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        // определяем слушателя нажатия элемента FAB
        createRecipeBtn.setOnClickListener(e -> {
            editedTransporter = new Transporter(
                    0,
                    "Рег. номер",
                    "Новая организация",
                    0,
                    "ИНН",
                    "-",
                    "-",
                    "-",
                    "-", 
                    "-");
            finish();
            Intent intent = new Intent(getApplicationContext(), TransporterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        setEmptyBtn.setOnClickListener(e->{
            selectedTrans = "Не указано";
            new Thread(()-> OkHttpUtil.updTransDispatcherStates("empty")).start();
            finish();
        });

        //Удаление 
        TransporterAdapter.DelOrgClickListener delOrgClickListener = (org, position) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Удаление");
            builder.setMessage("Вы действительно хотите удалить грузоперевозчика?");
            builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
            builder.setPositiveButton("Да", (dialog, id) -> {
                editedTransporter = transporters.get(position);
                new DBUtilDelete(getApplicationContext()).deleteOrg(editedTransporter.getId());
                Toast.makeText(getApplicationContext(), "Организация удалена!", Toast.LENGTH_LONG).show();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        };

        // Редактирование 
        TransporterAdapter.EditOrgClickListener editOrgClickListener = (org, position) -> {
            editedTransporter = transporters.get(position);
            finish();
            Intent intent = new Intent(getApplicationContext(), TransporterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        };

        // установка
        TransporterAdapter.LoadToPlcClickListener loadToPlcClickListener = (org, position) -> {
            Toast.makeText(getApplicationContext(), "Грузоперевозчик установлен  - " + org.getRegNumberAuto(), Toast.LENGTH_LONG).show();
            selectedTrans = org.getRegNumberAuto();
            new Thread(()-> OkHttpUtil.updTransDispatcherStates(String.valueOf(org.getId()))).start();
            finish();
        };

        // создаем адаптер
        adapter = new TransporterAdapter(this, transporters, editOrgClickListener, loadToPlcClickListener, delOrgClickListener);

        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void initFieldUI() {
        searchField = findViewById(R.id.searchField);
        recyclerView = findViewById(R.id.recyclerView);
        createRecipeBtn = findViewById(R.id.createBtn);
        setEmptyBtn = findViewById(R.id.setEmptyBtn);
        setOrgBtn = findViewById(R.id.setBtn);
        orgsNotFound = findViewById(R.id.not_found);

        if (exchangeLevel != 0) {
            setOrgBtn.setVisibility(View.GONE);
//            createRecipeBtn.setVisibility(View.GONE);
        }
    }

    private void initData() {
        new Thread(() -> {
            transporters.clear();
            if (exchangeLevel == 1) {
                transporters.addAll(new Gson().fromJson(OkHttpUtil.getTransporters(), new TypeToken<List<Transporter>>() {}.getType()));
            }else {
                transporters.addAll(new DBUtilGet(this).getTrans());
            }

            if (transporters.size() == 0) orgsNotFound.setVisibility(View.VISIBLE);
            else orgsNotFound.setVisibility(View.GONE);

        }).start();
    }
}