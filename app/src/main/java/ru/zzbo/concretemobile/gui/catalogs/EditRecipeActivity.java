package ru.zzbo.concretemobile.gui.catalogs;

import static ru.zzbo.concretemobile.utils.Constants.accessLevel;
import static ru.zzbo.concretemobile.utils.Constants.editedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class EditRecipeActivity extends AppCompatActivity {

    private EditText recipeIDField;
    private EditText dateCreateField;
    private EditText recipeNameField;
    private EditText recipeMarkField;
    private EditText recipeClassField;
    private EditText uniNumberField;
    private EditText descriptionField;
    private EditText mixTimeField;
    private EditText weightBuncker11;
    private EditText shortageBuncker11;
    private EditText humidityBuncker11;
    private EditText weightBuncker12;
    private EditText shortageBuncker12;
    private EditText humidityBuncker12;
    private EditText weightBuncker21;
    private EditText shortageBuncker21;
    private EditText humidityBuncker21;
    private EditText weightBuncker22;
    private EditText shortageBuncker22;
    private EditText humidityBuncker22;
    private EditText weightBuncker31;
    private EditText shortageBuncker31;
    private EditText humidityBuncker31;
    private EditText weightBuncker32;
    private EditText shortageBuncker32;
    private EditText humidityBuncker32;
    private EditText weightBuncker41;
    private EditText shortageBuncker41;
    private EditText humidityBuncker41;
    private EditText weightBuncker42;
    private EditText shortageBuncker42;
    private EditText humidityBuncker42;
    private EditText weightChemy1;
    private EditText shortageChemy1;
    private EditText weightChemy2;
    private EditText shortageChemy2;
    private EditText weightSilos1;
    private EditText shortageSilos1;
    private EditText weightSilos2;
    private EditText shortageSilos2;
    private EditText weightWater;
    private EditText shortageWater;
    private EditText weightWater2;
    private EditText shortageWater2;
    private EditText pathToHumidityField;
    private Button saveRecipe;
    private Button delRecipe;
    private Button closeWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recepie_activity);

        initUI();
        initFieldForRecipe();
        initActions();
    }

    private void initActions() {
        saveRecipe.setOnClickListener(view -> {
            try {
                if (
                        (!recipeNameField.equals("")) &&
                                (!recipeMarkField.equals("")) &&
                                (!uniNumberField.equals("")) &&
                                (!mixTimeField.equals("")) &&
                                (!weightBuncker11.equals("")) &&
                                (!shortageBuncker11.equals("")) &&
                                (!humidityBuncker11.equals("")) &&
                                (!weightBuncker12.equals("")) &&
                                (!shortageBuncker12.equals("")) &&
                                (!humidityBuncker12.equals("")) &&
                                (!weightBuncker21.equals("")) &&
                                (!shortageBuncker21.equals("")) &&
                                (!humidityBuncker21.equals("")) &&
                                (!weightBuncker22.equals("")) &&
                                (!shortageBuncker22.equals("")) &&
                                (!humidityBuncker22.equals("")) &&
                                (!weightBuncker31.equals("")) &&
                                (!shortageBuncker31.equals("")) &&
                                (!humidityBuncker31.equals("")) &&
                                (!weightBuncker32.equals("")) &&
                                (!shortageBuncker32.equals("")) &&
                                (!humidityBuncker32.equals("")) &&
                                (!weightBuncker41.equals("")) &&
                                (!shortageBuncker41.equals("")) &&
                                (!humidityBuncker41.equals("")) &&
                                (!weightBuncker42.equals("")) &&
                                (!shortageBuncker42.equals("")) &&
                                (!humidityBuncker42.equals("")) &&
                                (!weightChemy1.equals("")) &&
                                (!shortageChemy1.equals("")) &&
                                (!weightChemy2.equals("")) &&
                                (!shortageChemy2.equals("")) &&
                                (!weightSilos1.equals("")) &&
                                (!shortageSilos1.equals("")) &&
                                (!weightSilos2.equals("")) &&
                                (!shortageSilos2.equals("")) &&
                                (!weightWater.equals("")) &&
                                (!shortageWater.equals(""))
                ) {
                    SimpleDateFormat datePattern = new SimpleDateFormat("dd.MM.yyyy");
                    SimpleDateFormat timePattern = new SimpleDateFormat("HH:mm:ss");

                    Recepie recepie = new Recepie(
                            Integer.parseInt(String.valueOf(recipeIDField.getText())),
                            datePattern.format(new Date()),
                            timePattern.format(new Date()),
                            String.valueOf(recipeNameField.getText()),
                            String.valueOf(recipeMarkField.getText()),
                            String.valueOf(recipeClassField.getText()),
                            String.valueOf(descriptionField.getText()),
                            Float.valueOf(String.valueOf(weightBuncker11.getText())),
                            Float.valueOf(String.valueOf(weightBuncker12.getText())),
                            Float.valueOf(String.valueOf(weightBuncker21.getText())),
                            Float.valueOf(String.valueOf(weightBuncker22.getText())),
                            Float.valueOf(String.valueOf(weightBuncker31.getText())),
                            Float.valueOf(String.valueOf(weightBuncker32.getText())),
                            Float.valueOf(String.valueOf(weightBuncker41.getText())),
                            Float.valueOf(String.valueOf(weightBuncker42.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker11.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker12.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker21.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker22.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker31.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker32.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker41.getText())),
                            Float.valueOf(String.valueOf(shortageBuncker42.getText())),
                            Float.valueOf(String.valueOf(weightChemy1.getText())),
                            Float.valueOf(String.valueOf(shortageChemy1.getText())),
                            Float.valueOf(String.valueOf(shortageChemy2.getText())),
                            Float.valueOf(String.valueOf(weightWater.getText())),
                            Float.valueOf(String.valueOf(weightWater2.getText())),
                            Float.valueOf(String.valueOf(shortageWater.getText())),
                            Float.valueOf(String.valueOf(shortageWater2.getText())),
                            Float.valueOf(String.valueOf(weightSilos1.getText())),
                            Float.valueOf(String.valueOf(weightSilos2.getText())),
                            Float.valueOf(String.valueOf(shortageSilos1.getText())),
                            Float.valueOf(String.valueOf(shortageSilos2.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker11.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker12.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker21.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker22.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker31.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker32.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker41.getText())),
                            Float.valueOf(String.valueOf(humidityBuncker42.getText())),
                            String.valueOf(uniNumberField.getText()),
                            Integer.parseInt(String.valueOf(mixTimeField.getText())),
                            Float.parseFloat(String.valueOf(weightChemy2.getText())),
                            0,
                            Float.parseFloat(String.valueOf(shortageChemy2.getText())),
                            0,
                            0,
                            100,
                            0,
                            0,
                            0
                    );
                    if (recepie.getId() != 0) {
                        if (exchangeLevel == 1) {
                            new Thread(() -> {
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                OkHttpUtil.updRecipe(gson.toJson(recepie));
                            }).start();
                        } else new DBUtilUpdate(getApplicationContext()).updateRecipe(recepie);
                    } else {
                        if (exchangeLevel == 1) {
                            new Thread(() -> {
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                OkHttpUtil.newRecipe(gson.toJson(recepie));
                            }).start();
                        } else new DBUtilInsert(getApplicationContext()).insertIntoRecipe(recepie);
                    }

                    Toast.makeText(getApplicationContext(), "Рецепт записан", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(null, "Проверьте ввод, одно или несколько полей не заполнено!", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(null, "Проверьте ввод, некорректное заполнение полей!", Toast.LENGTH_LONG).show();
            }
        });
        delRecipe.setOnClickListener(view -> {
            new Thread(() -> {
                if (exchangeLevel == 1) OkHttpUtil.delRecipe(editedRecepie.getId());
                else new DBUtilDelete(getApplicationContext()).deleteRecipe(editedRecepie.getId());
            }).start();

            Toast.makeText(getApplicationContext(), "Рецепт удален!", Toast.LENGTH_LONG).show();
            finish();
        });
        closeWindow.setOnClickListener(view -> finish());
    }

    private void initFieldForRecipe() {
        recipeIDField.setText(String.valueOf(editedRecepie.getId()));
        dateCreateField.setText(String.valueOf(editedRecepie.getDate()));
        recipeNameField.setText(String.valueOf(editedRecepie.getName()));
        recipeMarkField.setText(String.valueOf(editedRecepie.getMark()));
        recipeClassField.setText(String.valueOf(editedRecepie.getClassPie()));
        uniNumberField.setText(String.valueOf(editedRecepie.getUniNumber()));
        descriptionField.setText(String.valueOf(editedRecepie.getDescription()));
        mixTimeField.setText(String.valueOf(editedRecepie.getTimeMix()));
        weightBuncker11.setText(String.valueOf(editedRecepie.getBunckerRecepie11()));
        shortageBuncker11.setText(String.valueOf(editedRecepie.getBunckerShortage11()));
        humidityBuncker11.setText(String.valueOf(editedRecepie.getHumidity11()));
        weightBuncker12.setText(String.valueOf(editedRecepie.getBunckerRecepie12()));
        shortageBuncker12.setText(String.valueOf(editedRecepie.getBunckerShortage12()));
        humidityBuncker12.setText(String.valueOf(editedRecepie.getHumidity12()));
        weightBuncker21.setText(String.valueOf(editedRecepie.getBunckerRecepie21()));
        shortageBuncker21.setText(String.valueOf(editedRecepie.getBunckerShortage21()));
        humidityBuncker21.setText(String.valueOf(editedRecepie.getHumidity21()));
        weightBuncker22.setText(String.valueOf(editedRecepie.getBunckerRecepie22()));
        shortageBuncker22.setText(String.valueOf(editedRecepie.getBunckerShortage22()));
        humidityBuncker22.setText(String.valueOf(editedRecepie.getHumidity22()));
        weightBuncker31.setText(String.valueOf(editedRecepie.getBunckerRecepie31()));
        shortageBuncker31.setText(String.valueOf(editedRecepie.getBunckerShortage31()));
        humidityBuncker31.setText(String.valueOf(editedRecepie.getHumidity31()));
        weightBuncker32.setText(String.valueOf(editedRecepie.getBunckerRecepie32()));
        shortageBuncker32.setText(String.valueOf(editedRecepie.getBunckerShortage32()));
        humidityBuncker32.setText(String.valueOf(editedRecepie.getHumidity32()));
        weightBuncker41.setText(String.valueOf(editedRecepie.getBunckerRecepie41()));
        shortageBuncker41.setText(String.valueOf(editedRecepie.getBunckerShortage41()));
        humidityBuncker41.setText(String.valueOf(editedRecepie.getHumidity41()));
        weightBuncker42.setText(String.valueOf(editedRecepie.getBunckerRecepie42()));
        shortageBuncker42.setText(String.valueOf(editedRecepie.getBunckerShortage42()));
        humidityBuncker42.setText(String.valueOf(editedRecepie.getHumidity42()));
        weightChemy1.setText(String.valueOf(editedRecepie.getChemyRecepie1()));
        shortageChemy1.setText(String.valueOf(editedRecepie.getChemyShortage1()));
        weightChemy2.setText(String.valueOf(editedRecepie.getChemy2Recepie()));
        shortageChemy2.setText(String.valueOf(editedRecepie.getChemyShortage2()));
        weightSilos1.setText(String.valueOf(editedRecepie.getSilosRecepie1()));
        shortageSilos1.setText(String.valueOf(editedRecepie.getSilosShortage1()));
        weightSilos2.setText(String.valueOf(editedRecepie.getSilosRecepie2()));
        shortageSilos2.setText(String.valueOf(editedRecepie.getSilosShortage2()));
        weightWater.setText(String.valueOf(editedRecepie.getWater1Recepie()));
        shortageWater.setText(String.valueOf(editedRecepie.getWater1Shortage()));
        weightWater2.setText(String.valueOf(editedRecepie.getWater2Recepie()));
        shortageWater2.setText(String.valueOf(editedRecepie.getWater2Shortage()));
        pathToHumidityField.setText(String.valueOf(editedRecepie.getPathToHumidity()));
    }

    private void initUI() {
        recipeIDField = findViewById(R.id.recipeIDField);
        dateCreateField = findViewById(R.id.typeOrganizationField);
        recipeNameField = findViewById(R.id.recipeNameField);
        recipeMarkField = findViewById(R.id.recepieMarkField);
        recipeClassField = findViewById(R.id.recepieClassField);
        uniNumberField = findViewById(R.id.uniNumberField);
        descriptionField = findViewById(R.id.descriptionField);
        mixTimeField = findViewById(R.id.mixTimeField);
        weightBuncker11 = findViewById(R.id.weightBuncker11);
        shortageBuncker11 = findViewById(R.id.shortageBuncker11);
        humidityBuncker11 = findViewById(R.id.humidityBuncker11);
        weightBuncker12 = findViewById(R.id.weightBuncker12);
        shortageBuncker12 = findViewById(R.id.shortageBuncker12);
        humidityBuncker12 = findViewById(R.id.humidityBuncker12);
        weightBuncker21 = findViewById(R.id.weightBuncker21);
        shortageBuncker21 = findViewById(R.id.shortageBuncker21);
        humidityBuncker21 = findViewById(R.id.humidityBuncker21);
        weightBuncker22 = findViewById(R.id.weightBuncker22);
        shortageBuncker22 = findViewById(R.id.shortageBuncker22);
        humidityBuncker22 = findViewById(R.id.humidityBuncker22);
        weightBuncker31 = findViewById(R.id.weightBuncker31);
        shortageBuncker31 = findViewById(R.id.shortageBuncker31);
        humidityBuncker31 = findViewById(R.id.humidityBuncker31);
        weightBuncker32 = findViewById(R.id.weightBuncker32);
        shortageBuncker32 = findViewById(R.id.shortageBuncker32);
        humidityBuncker32 = findViewById(R.id.humidityBuncker32);
        weightBuncker41 = findViewById(R.id.weightBuncker41);
        shortageBuncker41 = findViewById(R.id.shortageBuncker41);
        humidityBuncker41 = findViewById(R.id.humidityBuncker41);
        weightBuncker42 = findViewById(R.id.weightBuncker42);
        shortageBuncker42 = findViewById(R.id.shortageBuncker42);
        humidityBuncker42 = findViewById(R.id.humidityBuncker42);
        weightChemy1 = findViewById(R.id.weightChemy1);
        shortageChemy1 = findViewById(R.id.shortageChemy1);
        weightChemy2 = findViewById(R.id.weightChemy2);
        shortageChemy2 = findViewById(R.id.shortageChemy2);
        weightSilos1 = findViewById(R.id.weightSilos1);
        shortageSilos1 = findViewById(R.id.shortageSilos1);
        weightSilos2 = findViewById(R.id.weightSilos2);
        shortageSilos2 = findViewById(R.id.shortageSilos2);
        weightWater = findViewById(R.id.weightWater);
        shortageWater = findViewById(R.id.shortageWater);
        weightWater2 = findViewById(R.id.weightWater2);
        shortageWater2 = findViewById(R.id.shortageWater2);
        pathToHumidityField = findViewById(R.id.pathToHumidityField);
        saveRecipe = findViewById(R.id.saveOrder);
        delRecipe = findViewById(R.id.delOrder);
        closeWindow = findViewById(R.id.close);
        if (editedRecepie.getId() == 0) delRecipe.setVisibility(View.INVISIBLE);

        if (accessLevel == 0) {
            delRecipe.setVisibility(View.GONE);
            saveRecipe.setVisibility(View.GONE);
        }
    }
}
