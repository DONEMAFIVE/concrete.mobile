package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.editedUser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.adapters.UserAdapter;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.gui.catalogs.EditUserActivity;
import ru.zzbo.concretemobile.models.Users;

public class UsersActivity  extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton createRecipeBtn;
    private List<Users> users = new ArrayList<>();
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        
        initFieldUI();
        initData();
        initActions();
    }

    private void initActions() {
        // определяем слушателя нажатия элемента FAB
        createRecipeBtn.setOnClickListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            editedUser = new Users(
                    0,
                    "ФИО",
                    sdf.format(new Date()),
                    "Имя пользователя",
                    "",
                    0);
            finish();
            Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        //Удаление рецепта
//        UserAdapter.DelClickListener delClickListener = (user, position) -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Удаление");
//            builder.setMessage("Вы действительно хотите удалить пользователя?");
//            builder.setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
//            builder.setPositiveButton("Да", (dialog, id) -> {
//                editedUser = users.get(position);
//                new DBUtilDelete(getApplicationContext()).deleteUser(editedUser.getId());
//                Toast.makeText(getApplicationContext(), "Пользователь удален!", Toast.LENGTH_LONG).show();
//            });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        };

        // Редактирование рецепта
        UserAdapter.EditClickListener editClickListener = (user, position) -> {
            editedUser = users.get(position);
            Toast.makeText(getApplicationContext(), user.getLogin(), Toast.LENGTH_SHORT).show();
//            finish();
            Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        };


        // создаем адаптер
        adapter = new UserAdapter(this, users, editClickListener);

        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void initFieldUI() {
        recyclerView = findViewById(R.id.recyclerView);
        createRecipeBtn = findViewById(R.id.createBtn);
    }

    private void initData() {
        new Thread(() -> {
            users.clear();
            users.addAll(new DBUtilGet(this).getUsers());
        }).start();
    }
}
