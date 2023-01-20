package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.editedOrder;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.operatorLogin;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrder;
import static ru.zzbo.concretemobile.utils.Constants.selectedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.DateTimeUtils.getDateFromDatePicker;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.gui.catalogs.EditOrderActivity;
import ru.zzbo.concretemobile.models.Order;
import ru.zzbo.concretemobile.models.Recipe;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.commands.SetRecipe;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.DateTimeUtils;
import ru.zzbo.concretemobile.utils.DatesGenerate;
import ru.zzbo.concretemobile.utils.OkHttpUtil;
import ru.zzbo.concretemobile.utils.TableView;

public class OrdersActivity extends AppCompatActivity {
    private DatePicker dateBeginWidget;
    private DatePicker dateEndWidget;
    private CheckBox completeOrders;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private Button applyBtn;
    private Button closeDialog;

    private AlertDialog.Builder mFilterBuilder;
    private AlertDialog filterDialog;
    private View mFilterView;

    private FloatingActionButton mAddFab, mAddFiltersFab, mAddNewFab;
    private TextView addFilterActionText, addNewActionText;
    private Boolean isAllFabsVisible;
    private String startDate;
    private String endDate;
    private DrawerLayout progressLoading;
    private TableView listOrdersTableView;

    private List<String> dates = new ArrayList<>();
    private List<Order> order = new ArrayList<>();
    private Order currentOrder = new Order();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initUI();
        firstRun();
        initActions();
    }

    @Override
    protected void onResume() {
        buildListOrders();
        super.onResume();
    }

    private void firstRun() {
        checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
        startDate = sdf.format(getDateFromDatePicker(dateBeginWidget));
        endDate = sdf.format(getDateFromDatePicker(dateEndWidget));
        dates = new DatesGenerate(startDate, endDate).getLostDates();
    }

    private void initActions() {
        listOrdersTableView.setTableViewListener(new TableView.OnTableViewListener() {
            @Override
            public void onRowClicked(@NonNull TableRow tableRow, int position, List<String> row) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), tableRow);
                popupMenu.getMenuInflater().inflate(R.menu.popup_order, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    if (exchangeLevel == 1) {
                        for (Order ord : order)
                            if (ord.getId() == Integer.valueOf(row.get(0))) currentOrder = ord;
                    } else {
                        currentOrder = new DBUtilGet(getApplicationContext()).getOrderForID(Integer.valueOf(row.get(0)));
                    }

                    if (currentOrder.equals(null))
                        Toast.makeText(getApplicationContext(), "Ошибка NULL", Toast.LENGTH_LONG).show();

                    switch (menuItem.getItemId()) {
                        case R.id.upload: {
                            if (currentOrder.getState() == 1) {
                                Toast.makeText(getApplicationContext(), "Заказ уже выполнен. Запуск в работу не возможен.", Toast.LENGTH_LONG).show();
                                return true;
                            }
                            EditText partyCapacity = new EditText(OrdersActivity.this);
                            partyCapacity.setText("1");
                            partyCapacity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrdersActivity.this);
                            builder
                                    .setTitle("Укажите параметры партии").setView(partyCapacity)
                                    .setPositiveButton("Принять", (dialogInterface, i) -> {

                                        if (partyCapacity.getText().toString().equals("")) {
                                            Toast.makeText(OrdersActivity.this, "Заполните поле объема партии", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        selectedRecepie = currentOrder.getMarkConcrete();
                                        selectedOrder = currentOrder.getNameOrder();
                                        editedOrder = currentOrder;
                                        new DBUtilUpdate(getApplicationContext()).updCurrentTable("orderId", String.valueOf(currentOrder.getId()));
                                        new DBUtilUpdate(getApplicationContext()).updCurrentTable("recepieId", String.valueOf(currentOrder.getRecipeID()));
                                        new Thread(() -> {
                                            if (exchangeLevel == 1) {
                                                OkHttpUtil.uplOrder(currentOrder.getId(), Float.parseFloat(partyCapacity.getText().toString()));
                                            } else {
                                                Recipe recipe = new DBUtilGet(getApplicationContext()).getRecepieForID(currentOrder.getRecipeID());
                                                if (new SetRecipe().sendRecipeToPLC(recipe)) {
                                                    Tag weightPartyTag = tagListManual.get(64);
                                                    Tag weightSingleMixTag = tagListManual.get(62);

                                                    weightPartyTag.setRealValueIf(Float.valueOf(partyCapacity.getText().toString()));
                                                    weightSingleMixTag.setRealValueIf(currentOrder.getMaxMixCapacity()); //mixSingleWeight
                                                    new CommandDispatcher(weightSingleMixTag).writeSingleRegisterWithLock();
                                                    new CommandDispatcher(weightPartyTag).writeSingleRegisterWithLock();
                                                }
                                                new DBUtilUpdate(getApplicationContext()).updCurrentTable("orderId", String.valueOf(currentOrder.getId()));
                                            }

                                        }).start();

                                        //пока грузится рецепт в контроллер покажем пользователю симуляцию загрузки
                                        ProgressDialog progressDialog = new ProgressDialog(OrdersActivity.this);
                                        progressDialog.setCancelable(false);
                                        progressDialog.setMax(100);
                                        progressDialog.setMessage("Идет загрузка....");
                                        progressDialog.setTitle("Выбран заказ: " + currentOrder.getNameOrder());
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                        progressDialog.show();

                                        Handler handle = new Handler() {
                                            @Override
                                            public void handleMessage(Message msg) {
                                                super.handleMessage(msg);
                                                progressDialog.incrementProgressBy(2);
                                            }
                                        };

                                        new Thread(() -> {
                                            try {
                                                while (progressDialog.getProgress() <= progressDialog.getMax()) {
                                                    Thread.sleep(250);
                                                    handle.sendMessage(handle.obtainMessage());
                                                    if (progressDialog.getProgress() == progressDialog.getMax()) {
                                                        progressDialog.dismiss();
                                                        finish();
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }).start();
                                    })
                                    .setNegativeButton("Отмена", null).create();
                            builder.show();
                            break;
                        }
                        case R.id.edit: {
                            if (currentOrder.getState() == 1) { // row.get(58)
                                Toast.makeText(getApplicationContext(), "Заказ находится в работе. Редактирование не возможно.", Toast.LENGTH_LONG).show();
                                return true;
                            }
                            editedOrder = currentOrder;
                            Intent intent = new Intent(getApplicationContext(), EditOrderActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            break;
                        }
                        case R.id.repeat: {
                            if (currentOrder.getState() == 1) {
                                EditText partyCapacity = new EditText(OrdersActivity.this);
                                partyCapacity.setText("1");
                                partyCapacity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrdersActivity.this);
                                builder
                                        .setTitle("Укажите параметры партии").setView(partyCapacity)
                                        .setPositiveButton("Принять", (dialogInterface, i) -> {
                                            if (partyCapacity.getText().toString().equals("")) {
                                                Toast.makeText(OrdersActivity.this, "Заполните поле объема партии", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            try {
                                                new Thread(() -> {
                                                    if (exchangeLevel == 1) {
                                                        OkHttpUtil.rptOrder(currentOrder.getId(), Float.parseFloat(partyCapacity.getText().toString()));
                                                    } else {
                                                        new Handler(Looper.getMainLooper()).post(() -> {
                                                            Toast.makeText(getApplicationContext(), "В разработке", Toast.LENGTH_LONG).show();
                                                        });
                                                    }
                                                    new Handler(Looper.getMainLooper()).post(() -> {
                                                        buildListOrders();
                                                        Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_LONG).show();
                                                    });
                                                }).start();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        })
                                        .setNegativeButton("Отмена", null).create();
                                builder.show();
                            } else {
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    Toast.makeText(getApplicationContext(), "Заказ ещё не завершён.", Toast.LENGTH_LONG).show();
                                });
                            }
                            break;
                        }
                        case R.id.delete: {
                            AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
                            builder.setTitle("Удаление").setMessage("Вы действительно хотите удалить заказ?");
                            builder.setPositiveButton("Удалить", (dialog, id) -> {
                                try {
                                    new Thread(() -> {
                                        if (exchangeLevel == 1) {
                                            OkHttpUtil.delOrder(currentOrder.getId());
                                        } else {
                                            new DBUtilDelete(getApplicationContext()).deleteOrder(currentOrder.getId());
                                        }
                                        new Handler(Looper.getMainLooper()).post(() -> {
                                            buildListOrders();
                                        });

                                    }).start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            builder.setNegativeButton("Отмена", (dialog, id) -> {
                                dialog.dismiss();
                            });
                            builder.show();
                            break;
                        }
                    }
                    return true;
                });
                popupMenu.show();
            }

            @Override
            public void onLongClicked(@NonNull TableRow tableRow, int position, List<String> row) {
                if (exchangeLevel != 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
                    builder.setTitle("Удаление").setMessage("Вы действительно хотите удалить все заказы?");
                    builder.setPositiveButton("Удалить", (dialog, id) -> {
                        try {
                            new Thread(() -> {
                                for (Order curOrder : order) {
                                    new DBUtilDelete(getApplicationContext()).deleteOrder(curOrder.getId());
                                }
                                new Handler(Looper.getMainLooper()).post(() -> buildListOrders());
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    builder.setNegativeButton("Отмена", (dialog, id) -> dialog.cancel());
                    builder.show();
                }
            }
        });

        // Меню
        mAddFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                mAddFiltersFab.show();
                mAddNewFab.show();

                addFilterActionText.setVisibility(View.VISIBLE);
                addNewActionText.setVisibility(View.VISIBLE);
                isAllFabsVisible = true;
            } else {
                mAddFiltersFab.hide();
                mAddNewFab.hide();
                addFilterActionText.setVisibility(View.GONE);
                addNewActionText.setVisibility(View.GONE);
                isAllFabsVisible = false;
            }
        });

        // Новый
        mAddNewFab.setOnClickListener(view -> {
            editedOrder = new Order(
                    0,
                    "0",
                    0,
                    sdf.format(new Date()),
                    "",
                    "0",
                    0,
                    "0",
                    0,
                    "0",
                    0,
                    1,
                    1,
                    0,
                    "0",
                    "0",
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
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "-",
                    "0",
                    "0",
                    operatorLogin,
                    "-"
            );
            Intent intent = new Intent(getApplicationContext(), EditOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // Фильтр
        mAddFiltersFab.setOnClickListener(view -> {
            applyBtn.setOnClickListener(e -> {
                startDate = sdf.format(getDateFromDatePicker(dateBeginWidget));
                endDate = sdf.format(getDateFromDatePicker(dateEndWidget));
                dates = new DatesGenerate(startDate, endDate).getLostDates();

                if (DateTimeUtils.startLongerEnd(startDate, endDate)) {
                    runOnUiThread(() -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Уведомление").setMessage("Указан неверный диапазон дат!");
                        builder.setPositiveButton("ОК", (dialog, id) -> dialog.dismiss());
                        builder.show();
                    });
                    return;
                }

                buildListOrders();

                mAddFiltersFab.hide();
                mAddNewFab.hide();
                addFilterActionText.setVisibility(View.GONE);
                addNewActionText.setVisibility(View.GONE);
                addNewActionText.setVisibility(View.GONE);
                isAllFabsVisible = false;

                filterDialog.cancel();
            });

            closeDialog.setOnClickListener(e -> filterDialog.cancel());

            filterDialog.show();
        });

    }

    private void initUI() {
        listOrdersTableView = findViewById(R.id.listOrdersTableView);

        mAddFab = findViewById(R.id.add_fab);
        progressLoading = findViewById(R.id.progress_loading);

        // FAB кнопки
        mAddFiltersFab = findViewById(R.id.filter_fab);
        mAddNewFab = findViewById(R.id.new_fab);

        // Текст названия действия для всех FABs.
        addFilterActionText = findViewById(R.id.filter_text);
        addNewActionText = findViewById(R.id.new_text);

        mAddFiltersFab.setVisibility(View.GONE);
        mAddNewFab.setVisibility(View.GONE);
        addFilterActionText.setVisibility(View.GONE);
        addNewActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mFilterBuilder = new AlertDialog.Builder(this);
        mFilterView = getLayoutInflater().inflate(R.layout.dialog_order_filter, null);
        mFilterBuilder.setView(mFilterView);
        filterDialog = mFilterBuilder.create();

        applyBtn = mFilterView.findViewById(R.id.applyBtn);
        closeDialog = mFilterView.findViewById(R.id.closeDialog);
        dateBeginWidget = mFilterView.findViewById(R.id.dateBeginWidget);
        dateEndWidget = mFilterView.findViewById(R.id.dateEndWidget);
        completeOrders = mFilterView.findViewById(R.id.compliteOrders);
    }

    @Override
    public void onBackPressed() {
        filterDialog.dismiss();
        super.onBackPressed();
    }

    // Функция проверки и запроса разрешения
    public void checkPermission(Activity activity, String permission, int requestCode) {
        // Проверка, если разрешение не выдано
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    public void buildListOrders() {
        listOrdersTableView.clear();
        new Thread(() -> {
            if (exchangeLevel == 1) {
                String res = OkHttpUtil.getOrders(startDate, endDate, completeOrders.isChecked());
                order = new Gson().fromJson(res, new TypeToken<List<Order>>() {
                }.getType());
//                order = Arrays.asList(new Gson().fromJson(res, Order[].class));
            } else {
                order = new DBUtilGet(getApplicationContext()).getOrdersByRangeDate(dates, completeOrders.isChecked());
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    String[] headTable = {
                            "ID",
                            "Наименование",
                            "№ заказа",
                            "Дата",
                            "Дата закрытия",
                            "Заказчик",
                            "Заказчик id",
                            "Грузоперевозчик",
                            "Грузоперевозчик id",
                            "Рецепт",
                            "Рецепт id",
                            "Общий объем",
                            "Объем 1 замеса",
                            "Замесы",
                            "Марка",
                            "Класс",
                            "Бункер11(рец.)",
                            "Бункер12(рец.)",
                            "Бункер21(рец.)",
                            "Бункер22(рец.)",
                            "Бункер31(рец.)",
                            "Бункер32(рец.)",
                            "Бункер41(рец.)",
                            "Бункер42(рец.)",
                            "Химия1(рец.)",
                            "Химия2(рец.)",
                            "Вода1(рец.)",
                            "Вода2(рец.)",
                            "Цемент1(рец.)",
                            "Цемент2(рец.)",
                            "Бункер11 недосып",
                            "Бункер12 недосып",
                            "Бункер21 недосып",
                            "Бункер22 недосып",
                            "Бункер31 недосып",
                            "Бункер32 недосып",
                            "Бункер41 недосып",
                            "Бункер42 недосып",
                            "Химия1 недосып",
                            "Химия2 недосып",
                            "Вода1 недосып",
                            "Вода2 недосып",
                            "Цемент1 недосып",
                            "Цемент2 недосып",
                            "Бункер11(всего)",
                            "Бункер12(всего)",
                            "Бункер21(всего)",
                            "Бункер22(всего)",
                            "Бункер31(всего)",
                            "Бункер32(всего)",
                            "Бункер41(всего)",
                            "Бункер42(всего)",
                            "Химия1(всего)",
                            "Химия2(всего)",
                            "Вода1(всего)",
                            "Вода2(всего)",
                            "Цемент1(всего)",
                            "Цемент2(всего)",
                            "Статус",
                            "Выполнено циклов",
                            "Адрес выгрузки",
                            "Сумма за бетон",
                            "Вариант оплаты",
                            "Оператор",
                            "Комментарий"
                    };

                    List<String> tableHeader = new ArrayList<>(Arrays.asList(headTable));

                    listOrdersTableView.addHeader(tableHeader);
                    LinkedHashMap<Integer, List<String>> rows = new LinkedHashMap<>();

                    for (String date : dates) {
                        for (Order order : order) {
                            if (order.getDate().equals(date)) {
                                String singleRow = order.getOrder();
                                rows.put(order.getId(), Arrays.asList(singleRow.split("\\|")));
                            }
                        }
                    }

                    listOrdersTableView.addRows(rows);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
        }).start();

    }
}