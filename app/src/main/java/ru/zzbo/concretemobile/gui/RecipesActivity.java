package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.editedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.selectedRecepie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.adapters.RecipeAdapter;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.gui.catalogs.EditRecipeActivity;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.protocol.profinet.commands.SetRecipe;

public class RecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton createRecipeBtn, downloadFromPcBtn;
    private List<Recepie> recepies = new ArrayList<>();
    private ProgressBar loadRecipeToPLC;
    private DrawerLayout blockTouchLayout;
    private ConstraintLayout recipesNotFound;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        initFieldUI();
        initData();
        initActions();
    }

    private void initActions() {
        // определяем слушателя нажатия элемента FAB
        createRecipeBtn.setOnClickListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            editedRecepie = new Recepie(
                    0,
                    sdf.format(new Date()),
                    "",
                    "",
                    "",
                    "",
                    "Локальный",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "0",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    100);
            finish();
            Intent intent = new Intent(getApplicationContext(), EditRecipeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        //Удаление рецепта
        RecipeAdapter.DelRecipeClickListener delRecipeClickListener = (recipe, position) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Удаление");
            builder.setMessage("Вы действительно хотите удалить рецепт?");
            builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
            builder.setPositiveButton("Да", (dialog, id) -> {
                editedRecepie = recepies.get(position);
                new DBUtilDelete(getApplicationContext()).deleteRecipe(editedRecepie.getId());
                Toast.makeText(getApplicationContext(), "Рецепт удален!", Toast.LENGTH_LONG).show();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        };

        // Редактирование рецепта
        RecipeAdapter.EditRecipeClickListener editRecipeClickListener = (recipe, position) -> {
            editedRecepie = recepies.get(position);
            Toast.makeText(getApplicationContext(), recipe.getMark(), Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(getApplicationContext(), EditRecipeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        };

        // Передача рецепта в plc
        RecipeAdapter.LoadToPlcClickListener loadToPlcClickListener = (recipe, position) -> {
            Toast.makeText(getApplicationContext(), "Подождите, загружается рецепт - " + recipe.getName(), Toast.LENGTH_LONG).show();
            blockTouchLayout.setVisibility(View.VISIBLE);
            loadRecipeToPLC.setVisibility(View.VISIBLE);

            selectedRecepie = recipe.getName(); //Уст. тек. рецепт
            new SetRecipe().sendRecipeToPLC(recipe);

            Handler handle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    loadRecipeToPLC.incrementProgressBy(2);
                }
            };

            new Thread(() -> {
                try {
                    while (loadRecipeToPLC.getProgress() <= loadRecipeToPLC.getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (loadRecipeToPLC.getProgress() == loadRecipeToPLC.getMax()) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                blockTouchLayout.setVisibility(View.INVISIBLE);
                                finish();
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        };

        // создаем адаптер
        adapter = new RecipeAdapter(this, recepies, editRecipeClickListener, loadToPlcClickListener, delRecipeClickListener);

        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void initFieldUI() {
        recyclerView = findViewById(R.id.recyclerView);
        createRecipeBtn = findViewById(R.id.createBtn);
        downloadFromPcBtn = findViewById(R.id.loadBtn);
        loadRecipeToPLC = findViewById(R.id.load_to_plc);
        blockTouchLayout = findViewById(R.id.touchlock);
        recipesNotFound = findViewById(R.id.recipes_not_found);

        if (exchangeLevel != 0) {
            downloadFromPcBtn.setVisibility(View.GONE);
            createRecipeBtn.setVisibility(View.GONE);
        }
    }

    private void initData() {
        new Thread(() -> {
            recepies.clear();
            recepies.addAll(new DBUtilGet(this).getRecipes());

            if (recepies.size() == 0) recipesNotFound.setVisibility(View.VISIBLE);
            else recipesNotFound.setVisibility(View.GONE);

        }).start();
    }
}