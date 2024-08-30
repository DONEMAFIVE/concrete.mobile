package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.editedOrganization;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrg;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.adapters.OrganizationAdapter;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.gui.catalogs.OrganizationActivity;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.utils.Constants;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class OrganizationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton createRecipeBtn, setOrgBtn, setEmptyBtn;
    private List<Organization> organizations = new ArrayList<>();
    private ConstraintLayout orgsNotFound;
    private OrganizationAdapter adapter;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);

        initFieldUI();
        initData();
        initActions();
    }

    private void filter(String text){
        List<Organization> filteredList = new ArrayList<>();

        for (Organization organization : organizations){
            if (
                    String.valueOf(organization.getId()).toLowerCase().contains(text.toLowerCase()) ||
                    organization.getOrganizationName().toLowerCase().contains(text.toLowerCase()) ||
                    organization.getOrganizationHeadName().toLowerCase().contains(text.toLowerCase())
            ) filteredList.add(organization);
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            editedOrganization = new Organization(
                    0,
                    "Руководитель организации",
                    "Новая организация",
                    0,
                    "ИНН",
                    "КПП",
                    "ОКПО",
                    "-",
                    "-", 
                    "-", 
                    "-", 
                    "-");
            finish();
            Intent intent = new Intent(getApplicationContext(), OrganizationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        setEmptyBtn.setOnClickListener(e->{
            selectedOrg = "Не указано";
            new Thread(()-> OkHttpUtil.updOrgDispatcherStates("empty")).start();
            finish();
        });

        //Удаление 
        OrganizationAdapter.DelOrgClickListener delOrgClickListener = (org, position) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Удаление");
            builder.setMessage("Вы действительно хотите удалить организацию?");
            builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
            builder.setPositiveButton("Да", (dialog, id) -> {
                editedOrganization = organizations.get(position);
                new DBUtilDelete(getApplicationContext()).deleteOrg(editedOrganization.getId());
                Toast.makeText(getApplicationContext(), "Организация удалена!", Toast.LENGTH_LONG).show();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        };

        // Редактирование 
        OrganizationAdapter.EditOrgClickListener editOrgClickListener = (org, position) -> {
            editedOrganization = organizations.get(position);
            finish();
            Intent intent = new Intent(getApplicationContext(), OrganizationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        };

        // установка
        OrganizationAdapter.LoadToPlcClickListener loadToPlcClickListener = (org, position) -> {
            Toast.makeText(getApplicationContext(), "Организация установлена  - " + org.getOrganizationName(), Toast.LENGTH_LONG).show();
            selectedOrg = org.getOrganizationName();
            new Thread(()-> OkHttpUtil.updOrgDispatcherStates(String.valueOf(org.getId()))).start();
            finish();
        };

        // создаем адаптер
        adapter = new OrganizationAdapter(this, organizations, editOrgClickListener, loadToPlcClickListener, delOrgClickListener);

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
            organizations.clear();
            if (exchangeLevel == 1) {
                organizations.addAll(new Gson().fromJson(OkHttpUtil.getOrganization(), new TypeToken<List<Organization>>() {}.getType()));
            }else {
                organizations.addAll(new DBUtilGet(this).getOrgs());
            }

            if (organizations.size() == 0) orgsNotFound.setVisibility(View.VISIBLE);
            else orgsNotFound.setVisibility(View.GONE);

        }).start();
    }
}