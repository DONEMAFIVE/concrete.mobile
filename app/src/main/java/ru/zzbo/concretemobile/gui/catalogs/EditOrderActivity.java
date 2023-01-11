package ru.zzbo.concretemobile.gui.catalogs;


import static ru.zzbo.concretemobile.utils.Constants.editedOrder;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.operatorLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recipe;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.utils.CalcUtil;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class EditOrderActivity extends AppCompatActivity {

    private EditText numberOrderField;
    private EditText dateOrderField;
    private EditText nameOrderField;
    private EditText commentOrderField;
    private EditText addressUploadField;
    private EditText amountConcreteField;
    private EditText partyCapacityField;
    private EditText mixCapacityField;
    private Spinner organizationSpinner;
    private Spinner driverSpinner;
    private Spinner recipeSpinner;
    private Spinner paymentSpinner;
    private Button saveOrder;
    private Button close;

    private List<Organization> org = new ArrayList<>();
    private List<Transporter> trans = new ArrayList<>();
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        initUI();
        initActions();
    }

    public void initUI() {
        numberOrderField = findViewById(R.id.numberOrderField);
        dateOrderField = findViewById(R.id.dateOrderField);
        nameOrderField = findViewById(R.id.nameOrderField);
        commentOrderField = findViewById(R.id.commentOrderField);
        addressUploadField = findViewById(R.id.addressUploadField);
        amountConcreteField = findViewById(R.id.amountConcreteField);
        partyCapacityField = findViewById(R.id.partyCapacityField);
        mixCapacityField = findViewById(R.id.mixCapacityField);

        organizationSpinner = findViewById(R.id.organizationSpinner);
        driverSpinner = findViewById(R.id.driverSpinner);
        recipeSpinner = findViewById(R.id.recipeSpinner);
        paymentSpinner = findViewById(R.id.paymentSpinner);

        saveOrder = findViewById(R.id.saveOrder);
        close = findViewById(R.id.close);

        numberOrderField.setText(String.valueOf(editedOrder.getNumberOrder()));
        dateOrderField.setText(editedOrder.getDate());
        nameOrderField.setText(editedOrder.getNameOrder());
        commentOrderField.setText(editedOrder.getComment());
        addressUploadField.setText(editedOrder.getUploadAddress());
        amountConcreteField.setText(editedOrder.getAmountConcrete());
        partyCapacityField.setText(String.valueOf(editedOrder.getTotalCapacity()));
        mixCapacityField.setText(String.valueOf(editedOrder.getMaxMixCapacity()));

        new Thread(() -> {
            List<String> nameOrg = new ArrayList<>();
            List<String> nameTrans = new ArrayList<>();
            List<String> nameRecipe = new ArrayList<>();
            List<String> namePayment = new ArrayList<>();
            namePayment.add("Счёт");
            namePayment.add("Терминал");

            if (exchangeLevel == 1) {
                org.addAll(new Gson().fromJson(OkHttpUtil.getOrganization(), new TypeToken<List<Organization>>() {
                }.getType()));
                trans.addAll(new Gson().fromJson(OkHttpUtil.getTransporters(), new TypeToken<List<Transporter>>() {
                }.getType()));
                recipes.addAll(new Gson().fromJson(OkHttpUtil.getRecipes(), new TypeToken<List<Recipe>>() {
                }.getType()));
            } else {
                org.addAll(new DBUtilGet(this).getOrgs());
                trans.addAll(new DBUtilGet(this).getTrans());
                recipes.addAll(new DBUtilGet(this).getRecepies());
            }

            for (Organization organization : org)
                nameOrg.add(organization.getId() + ":" + organization.getOrganizationName());
            for (Transporter transporter : trans)
                nameTrans.add(transporter.getId() + ":" + transporter.getDriverName());
            for (Recipe recipe : recipes) nameRecipe.add(recipe.getId() + ":" + recipe.getName());


            ArrayAdapter orgAdapter = new ArrayAdapter(this, R.layout.spinner, nameOrg);
            ArrayAdapter transAdapter = new ArrayAdapter(this, R.layout.spinner, nameTrans);
            ArrayAdapter recipeAdapter = new ArrayAdapter(this, R.layout.spinner, nameRecipe);
            ArrayAdapter paymentAdapter = new ArrayAdapter(this, R.layout.spinner, namePayment);

            runOnUiThread(() -> organizationSpinner.setAdapter(orgAdapter));
            runOnUiThread(() -> driverSpinner.setAdapter(transAdapter));
            runOnUiThread(() -> recipeSpinner.setAdapter(recipeAdapter));
            runOnUiThread(() -> paymentSpinner.setAdapter(paymentAdapter));
            runOnUiThread(() -> {
                organizationSpinner.setSelection(getIndex(organizationSpinner, editedOrder.getOrganizationID()));
                recipeSpinner.setSelection(getIndex(recipeSpinner, editedOrder.getRecipeID()));
                driverSpinner.setSelection(getIndex(driverSpinner, editedOrder.getTransporterID()));
            });

        }).start();

        if (editedOrder.getPaymentOption().equals("Терминал")) paymentSpinner.setSelection(1);

        if (editedOrder.getNumberOrder() == 0) {
            String numberOrder = String.valueOf(numberOrder());
            numberOrderField.setText(numberOrder);
            nameOrderField.setText("Новый заказ " + numberOrder);
        }

    }

    public void initActions() {
        saveOrder.setOnClickListener(view -> {
            Recipe recipe = recipes.get(recipeSpinner.getSelectedItemPosition());
            Organization organization = org.get(organizationSpinner.getSelectedItemPosition());
            Transporter transporter = trans.get(driverSpinner.getSelectedItemPosition());

            CalcUtil calc = new CalcUtil();
            calc.cycleCalcCounter(Float.valueOf(partyCapacityField.getText().toString()), Float.valueOf(mixCapacityField.getText().toString()));
            int cycleSum = calc.getCycleSum();
            float capacityMix = calc.getCapacityMix();

            float countBuncker11 = recipe.getBunckerRecepie11() * capacityMix;
            float countBuncker12 = recipe.getBunckerRecepie12() * capacityMix;
            float countBuncker21 = recipe.getBunckerRecepie21() * capacityMix;
            float countBuncker22 = recipe.getBunckerRecepie22() * capacityMix;
            float countBuncker31 = recipe.getBunckerRecepie31() * capacityMix;
            float countBuncker32 = recipe.getBunckerRecepie32() * capacityMix;
            float countBuncker41 = recipe.getBunckerRecepie41() * capacityMix;
            float countBuncker42 = recipe.getBunckerRecepie42() * capacityMix;

            float countChemy1 = recipe.getChemyRecepie1() * capacityMix;
            float countChemy2 = recipe.getChemy2Recepie() * capacityMix;
            float countWater1 = recipe.getWater1Recepie() * capacityMix;
            float countWater2 = recipe.getWater2Recepie() * capacityMix;
            float countSilos1 = recipe.getSilosRecepie1() * capacityMix;
            float countSilos2 = recipe.getSilosRecepie2() * capacityMix;

            float totalCountBuncker11 = countBuncker11 * cycleSum;
            float totalCountBuncker12 = countBuncker12 * cycleSum;
            float totalCountBuncker21 = countBuncker21 * cycleSum;
            float totalCountBuncker22 = countBuncker22 * cycleSum;
            float totalCountBuncker31 = countBuncker31 * cycleSum;
            float totalCountBuncker32 = countBuncker32 * cycleSum;
            float totalCountBuncker41 = countBuncker41 * cycleSum;
            float totalCountBuncker42 = countBuncker42 * cycleSum;
            float totalCountChemy1 = countChemy1 * cycleSum;
            float totalCountChemy2 = countChemy2 * cycleSum;
            float totalCountWater1 = countWater1 * cycleSum;
            float totalCountWater2 = countWater2 * cycleSum;
            float totalCountSilos1 = countSilos1 * cycleSum;
            float totalCountSilos2 = countSilos2 * cycleSum;

            Order order = new Order(
                    editedOrder.getId(),
                    nameOrderField.getText().toString(),
                    Integer.parseInt(numberOrderField.getText().toString()),
                    dateOrderField.getText().toString(),
                    "",
                    organization.getOrganizationName(),
                    organization.getId(),
                    transporter.getDriverName(),
                    transporter.getId(),
                    recipe.getName(),
                    recipe.getId(),
                    Float.valueOf(partyCapacityField.getText().toString()),
                    Float.valueOf(mixCapacityField.getText().toString()),
                    cycleSum,
                    recipe.getMark(),
                    recipe.getClassPie(),
                    recipe.getBunckerRecepie11(),
                    recipe.getBunckerRecepie12(),
                    recipe.getBunckerRecepie21(),
                    recipe.getBunckerRecepie22(),
                    recipe.getBunckerRecepie31(),
                    recipe.getBunckerRecepie32(),
                    recipe.getBunckerRecepie41(),
                    recipe.getBunckerRecepie42(),
                    recipe.getChemyRecepie1(),
                    recipe.getChemy2Recepie(),
                    recipe.getWater1Recepie(),
                    recipe.getWater2Recepie(),
                    recipe.getSilosRecepie1(),
                    recipe.getSilosRecepie2(),
                    recipe.getBunckerShortage11(),
                    recipe.getBunckerShortage12(),
                    recipe.getBunckerShortage21(),
                    recipe.getBunckerShortage22(),
                    recipe.getBunckerShortage31(),
                    recipe.getBunckerShortage32(),
                    recipe.getBunckerShortage41(),
                    recipe.getBunckerShortage42(),
                    recipe.getChemyShortage1(),
                    recipe.getChemyShortage2(),
                    recipe.getWater1Shortage(),
                    recipe.getWater2Shortage(),
                    recipe.getSilosShortage1(),
                    recipe.getSilosShortage2(),
                    totalCountBuncker11,
                    totalCountBuncker12,
                    totalCountBuncker21,
                    totalCountBuncker22,
                    totalCountBuncker31,
                    totalCountBuncker32,
                    totalCountBuncker41,
                    totalCountBuncker42,
                    totalCountChemy1,
                    totalCountChemy2,
                    totalCountWater1,
                    totalCountWater2,
                    totalCountSilos1,
                    totalCountSilos2,
                    0,
                    0,
                    addressUploadField.getText().toString(),
                    amountConcreteField.getText().toString(),
                    paymentSpinner.getSelectedItem().toString(),
                    operatorLogin,
                    commentOrderField.getText().toString()
            );

            new Thread(() -> {
                if (exchangeLevel == 1) {
                    Gson gson = new GsonBuilder()
                            .serializeSpecialFloatingPointValues()
                            .setPrettyPrinting()
                            .create();
                    if (editedOrder.getNumberOrder() != 0) OkHttpUtil.updOrder(gson.toJson(order));
                    else OkHttpUtil.newOrder(gson.toJson(order));
                } else {
                    if (editedOrder.getNumberOrder() != 0)
                        new DBUtilUpdate(getApplicationContext()).updateOrder(order);
                    else new DBUtilInsert(this).insertIntoOrder(order);
                }
            }).start();

            Toast.makeText(getApplicationContext(), "Заявка сохранена", Toast.LENGTH_LONG).show();
            onBackPressed();
        });
        close.setOnClickListener(view -> onBackPressed());
    }

    private int getIndex(Spinner spinner, int s) {
        for (int i = 0; i < spinner.getCount(); i++) {
            String id = spinner.getItemAtPosition(i).toString().split(":")[0];
            if (id.equalsIgnoreCase(String.valueOf(s))) return i;
        }
        return 0;
    }

    private int numberOrder() {
        try {
            List<Order> order = new DBUtilGet(getApplicationContext()).getOrders();
            int numberOrder = 0;
            if (order.size() > 0) numberOrder = order.get(order.size() - 1).getNumberOrder();
            return numberOrder + 1;
        } catch (ArrayIndexOutOfBoundsException exc) {
            return 1;
        }
    }
}