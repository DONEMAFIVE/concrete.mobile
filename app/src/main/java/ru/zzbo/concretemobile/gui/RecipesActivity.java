package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.editedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.selectedRecepie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.adapters.RecipeAdapter;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.db.dbStructures.DatabaseHelper;
import ru.zzbo.concretemobile.gui.catalogs.EditRecipeActivity;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.commands.SetRecipe;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.ConnectionUtil;
import ru.zzbo.concretemobile.utils.OkHttpUtil;
import ru.zzbo.concretemobile.utils.ftp.EZFtpServer;

public class RecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton createRecipeBtn, downloadFromPcBtn, unloadRecipesHMIBtn;

    private List<Recepie> recepies = new ArrayList<>();
    private ProgressBar loadRecipeToPLC;
    private TextView loadTitle;
    private DrawerLayout blockTouchLayout;
    private ConstraintLayout recipesNotFound;
    private RecipeAdapter adapter;
    private EditText searchField;

    public EZFtpServer ftpServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        initFieldUI();
        initData();
        initActions();
    }

    private void filter(String text) {
        List<Recepie> filteredList = new ArrayList<>();

        for (Recepie recepie : recepies) {
            if (
                    String.valueOf(recepie.getId()).toLowerCase().contains(text.toLowerCase()) ||
                            recepie.getName().toLowerCase().contains(text.toLowerCase()) ||
                            recepie.getMark().toLowerCase().contains(text.toLowerCase()) ||
                            recepie.getClassPie().toLowerCase().contains(text.toLowerCase())
            ) filteredList.add(recepie);
        }
        adapter.filterList(filteredList);
    }

    @SuppressLint("Range")
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

        unloadRecipesHMIBtn.setOnClickListener(e -> {
            try {
                File dir = new File(Environment.getExternalStorageDirectory() + "/IP_" + configList.getHmiIP() + "/recipe/");
                unloadRecipesFromHMI(dir);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // определяем слушателя нажатия элемента FAB
        createRecipeBtn.setOnClickListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            editedRecepie = new Recepie(
                    0,
                    sdf.format(new Date()),
                    "",
                    "Новый рецепт",
                    "Марка",
                    "Класс",
                    "Описание",
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
                    100,
                    0,
                    0,
                    0
            );
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
            if (exchangeLevel == 1) {
                new Thread(() -> {
                    OkHttpUtil.uplRecipe(recipe.getId());
                    OkHttpUtil.updCurrent(recipe.getId(), 0);
                }).start();
            } else {
                new Thread(() -> new SetRecipe().sendRecipeToPLC(recipe)).start();
            }

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

    @SuppressLint("Range")
    public void unloadRecipesFromHMI(File dir) {
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        new Thread(() -> {
            //todo проверить подключение к панели
            if (!ConnectionUtil.isIpConnected(configList.getHmiIP())) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Отсутствует соединение с HMI", Toast.LENGTH_LONG).show());
                return;
            }

            //todo проверить адрес планшета (FTP address)
            if (!ConnectionUtil.getIpDevice(getApplicationContext()).equals("192.168.250.123")) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Не верный адрес устройства", Toast.LENGTH_LONG).show());
                return;
            }

            //todo удалить все выгруженные HMI рецепты из основной бд
            List<Recepie> recipesDB = new DBUtilGet(getApplicationContext()).getRecipes();
            for (Recepie recipe : recipesDB) {
                if (recipe.getUniNumber().equals("HMI")) {
                    new DBUtilDelete(getApplicationContext()).deleteRecipe(recipe.getId());
                }
            }

            //todo удаляем папку (recipe) если она есть
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                blockTouchLayout.setVisibility(View.VISIBLE);
                loadRecipeToPLC.setVisibility(View.GONE);
                loadTitle.setText("Выгрузка рецептов...");;
            });

            //todo запускаем сервер
            try {
                ftpServer = EZFtpServer.init();
                ftpServer.start();

                Thread.sleep(1000);

                Tag tag = new Tag();
                tag.setArea(S7.S7AreaDB);
                tag.setDbNumber(15);
                tag.setStart(20);
                tag.setBit(0);
                tag.setTypeTag("Bool");
                tag.setBoolValueIf(false);

                //todo проверка если при каких либо обстоятельствах тригер не был установлен в false, то мы отправляем false
                if (new CommandDispatcher(tag).readSingleRegister().isBoolValueIf()) {
                    new CommandDispatcher(tag).writeSingleRegisterWithValue(false);
                }
                Thread.sleep(1000);

                //todo отправляем импульсный сигнал true = false
                new CommandDispatcher(tag).writeSingleInvertedBoolRegister();

                boolean chkLoadedData = true;
                //todo каждую секунду опрашиваем наличие загруженой папки

                while (chkLoadedData) {
                    Thread.sleep(1000);
                    if (dir.exists() && dir.isDirectory()) {
                        //todo если появилась папка, то через 5 сек, закрывем опрос и останавливаем сервер.
                        Thread.sleep(5000);
                        ftpServer.stop();
                        chkLoadedData = false;
                    }
                    System.out.println("Проверка наличия папки " +dir.getName() +" - " + dir.exists());
                }

                //todo подключиться к бд
                DatabaseHelper mDBHelper = new DatabaseHelper(this);
                SQLiteDatabase mDb = mDBHelper.openDataBase();

                //todo получить данные таблицы рецептов
                List<Recepie> recipesHMI = new ArrayList<>();
                Cursor cursor = mDb.query("concrete", null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    recipesHMI.add(new Recepie(
                            0,
                            date.format(new Date()),
                            time.format(new Date()),
                            cursor.getString(cursor.getColumnIndex("Name")),
                            "Марка",
                            "Класс",
                            "Загружено из панели",
                            cursor.getFloat(cursor.getColumnIndex("Bunk1_1")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk1_2")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk2_1")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk2_2")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk3_1")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk3_2")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk4_1")),
                            cursor.getFloat(cursor.getColumnIndex("Bunk4_2")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk1_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk1_2")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk2_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk2_2")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk3_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk3_2")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk4_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_bunk4_2")),
                            cursor.getFloat(cursor.getColumnIndex("Ch_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_ch_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_ch_2")),
                            cursor.getFloat(cursor.getColumnIndex("Water")),
                            0,
                            cursor.getFloat(cursor.getColumnIndex("LackOf_water")),
                            0,
                            cursor.getFloat(cursor.getColumnIndex("Cement_1")),
                            cursor.getFloat(cursor.getColumnIndex("Cement_2")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_cement_1")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_cement_2")),
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            "HMI", //Устанавливаю тэг "HMI" для идентификации выгруженого рецепта из панели
                            0,
                            cursor.getFloat(cursor.getColumnIndex("Ch_2")),
                            cursor.getFloat(cursor.getColumnIndex("Ch_3")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_ch_2")),
                            cursor.getFloat(cursor.getColumnIndex("LackOf_ch_3")),
                            0,
                            0,
                            0,
                            0,
                            0
                    ));
                }

                //todo перенести рецепты в основную базу
                for (Recepie recepie : recipesHMI) new DBUtilInsert(this).insertIntoRecipe(recepie);

                cursor.close();
                mDBHelper.close();
                mDb.close();

                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Выгрузка рецептов");
                    builder.setIcon(R.drawable.download_report);
                    builder.setMessage("Выгрузка выполнена успешно!");
                    builder.setPositiveButton("Ок", (dialog, id) -> {
                        dialog.dismiss();
                        finish();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> {
                    blockTouchLayout.setVisibility(View.GONE);
                    loadRecipeToPLC.setVisibility(View.VISIBLE);
                    loadTitle.setText("Загрузка рецепта в PLC");
                });
            }
        }).start();
    }

    private void initFieldUI() {
        searchField = findViewById(R.id.searchField);
        recyclerView = findViewById(R.id.recyclerView);
        createRecipeBtn = findViewById(R.id.createBtn);
        unloadRecipesHMIBtn = findViewById(R.id.syncRecipeHMIBtn);
        downloadFromPcBtn = findViewById(R.id.setBtn);
        loadRecipeToPLC = findViewById(R.id.load_to_plc);
        loadTitle = findViewById(R.id.load_title);
        blockTouchLayout = findViewById(R.id.touchlock);
        recipesNotFound = findViewById(R.id.not_found);

        if (exchangeLevel != 0) {
            downloadFromPcBtn.setVisibility(View.GONE);
//            createRecipeBtn.setVisibility(View.GONE);
        }
    }

    private void initData() {
        new Thread(() -> {
            recepies.clear();
            if (exchangeLevel == 1) {
                recepies.addAll(new Gson().fromJson(OkHttpUtil.getRecipes(), new TypeToken<List<Recepie>>() {
                }.getType()));
            } else recepies.addAll(new DBUtilGet(this).getRecipes());

            if (recepies.size() == 0) recipesNotFound.setVisibility(View.VISIBLE);
            else recipesNotFound.setVisibility(View.GONE);

        }).start();
    }
}