package ru.zzbo.concretemobile.gui.catalogs;

import static ru.zzbo.concretemobile.utils.Constants.editedUser;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilDelete;
import ru.zzbo.concretemobile.db.DBUtilInsert;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.models.Users;
import ru.zzbo.concretemobile.utils.CryptoUtil;

public class EditUserActivity extends AppCompatActivity {

    private EditText IDField;
    private EditText dateCreateField;
    private EditText nameField;
    private EditText loginField;
    private EditText passwordField;
    private Spinner levelSpinner;
    private Button saveBtn;
    private Button delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        initUI();
        initLoginList();
        initField();
        initActions();
    }

    private void enterPassSaveDialog() {
        EditText passwordEditText = new EditText(EditUserActivity.this);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        AlertDialog dialog = new AlertDialog.Builder(EditUserActivity.this)
                .setTitle("Введите пароль")
                .setView(passwordEditText)
                .setPositiveButton("Ок", (dialog1, which) -> {
                    String pass = String.valueOf(passwordEditText.getText());
                    if (pass.equals("2280")) save();
                    else runOnUiThread(()->Toast.makeText(EditUserActivity.this, "Не вверный пароль!", Toast.LENGTH_LONG).show());
                })
                .setCancelable(false)
                .setNegativeButton("Отмена", null)
                .create();
        dialog.show();

    }
    private void enterPassDelDialog() {
        EditText passwordEditText = new EditText(EditUserActivity.this);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        AlertDialog dialog = new AlertDialog.Builder(EditUserActivity.this)
                .setTitle("Введите пароль")
                .setView(passwordEditText)
                .setPositiveButton("Ок", (dialog1, which) -> {
                    String pass = String.valueOf(passwordEditText.getText());
                    if (pass.equals("2280")) delete();
                    else runOnUiThread(()->Toast.makeText(EditUserActivity.this, "Не вверный пароль!", Toast.LENGTH_LONG).show());
                })
                .setCancelable(false)
                .setNegativeButton("Отмена", null)
                .create();
        dialog.show();
    }
    private void initActions() {
        saveBtn.setOnClickListener(view -> {
            try {
                if ((!nameField.equals("")) && (!loginField.equals("")) && (!passwordField.equals(""))) {
                    if (levelSpinner.getSelectedItemPosition() == 2 || levelSpinner.getSelectedItemPosition() == 3) enterPassSaveDialog();
                    else save();
                } else {
                    Toast.makeText(null, "Проверьте ввод, одно или несколько полей не заполнено!", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(null, "Проверьте ввод, некорректное заполнение полей!", Toast.LENGTH_LONG).show();
            }
        });
        delBtn.setOnClickListener(view -> {
            if (levelSpinner.getSelectedItemPosition() == 2 || levelSpinner.getSelectedItemPosition() == 3) enterPassDelDialog();
            else delete();
        });
    }
    private void delete() {
        new Thread(() -> {
           if (exchangeLevel == 1) System.err.println("Не работает!");
           else new DBUtilDelete(getApplicationContext()).deleteUser(editedUser.getId());
        }).start();
        finish();
        Toast.makeText(getApplicationContext(), "Пользователь удален!", Toast.LENGTH_LONG).show();
    }

    private void save() {
        SimpleDateFormat datePattern = new SimpleDateFormat("dd.MM.yyyy");

        String cryptPass = new CryptoUtil(passwordField.getText().toString()).encrypt();
        Users users = new Users(
                Integer.parseInt(String.valueOf(IDField.getText())),
                nameField.getText().toString(),
                datePattern.format(new Date()),
                loginField.getText().toString(),
                cryptPass,
                levelSpinner.getSelectedItemPosition()
        );

        if (users.getId() != 0) {
            if (exchangeLevel == 1) {
                new Thread(() -> {
//                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                                OkHttpUtil.updUser(gson.toJson(users));
                    System.err.println("Не работает с пк!");
                }).start();
            } else new DBUtilUpdate(getApplicationContext()).updateUser(users);
        } else {
            if (exchangeLevel == 1) {
                new Thread(() -> {
//                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                                OkHttpUtil.newRecipe(gson.toJson(users));
                    System.err.println("Не работает с пк!");
                }).start();
            } else new DBUtilInsert(getApplicationContext()).insertIntoUser(users);
        }

        Toast.makeText(getApplicationContext(), "Пользователь сохранен", Toast.LENGTH_LONG).show();
        finish();
    }
    private void initField() {
        IDField.setText(String.valueOf(editedUser.getId()));
        dateCreateField.setText(String.valueOf(editedUser.getDateCreation()));
        nameField.setText(String.valueOf(editedUser.getUserName()));
        loginField.setText(String.valueOf(editedUser.getLogin()));
        levelSpinner.setSelection(editedUser.getAccessLevel());

        if (editedUser.getId() == 0) delBtn.setVisibility(View.GONE);

    }

    private void initUI() {
        IDField = findViewById(R.id.IDField);
        dateCreateField = findViewById(R.id.dateField);
        nameField = findViewById(R.id.nameField);
        loginField = findViewById(R.id.loginField);
        passwordField = findViewById(R.id.passwordField);
        levelSpinner = findViewById(R.id.levelSpinner);
        
        saveBtn = findViewById(R.id.save);
        delBtn = findViewById(R.id.del);
    }

    private void initLoginList() {
        ArrayList<String> login = new ArrayList<>();
        ArrayAdapter<String> adapter;
        login.add("Оператор");
        login.add("Диспетчер");
        login.add("Инженер");
        login.add("Администратор");

        adapter = new ArrayAdapter<>(this, R.layout.spinner, login);
        levelSpinner.setAdapter(adapter);
    }
}
