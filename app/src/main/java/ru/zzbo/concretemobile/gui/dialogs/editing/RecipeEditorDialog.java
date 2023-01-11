package ru.zzbo.concretemobile.gui.dialogs.editing;

import static ru.zzbo.concretemobile.utils.Constants.editedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.gui.catalogs.EditRecipeActivity;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class RecipeEditorDialog extends DialogFragment {
    private List<Recepie> recepieList = new ArrayList<>();
    private int menuItem = 1;

    public RecipeEditorDialog(List<Recepie> recepieList) {
        this.recepieList = recepieList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] listForShow = new String[recepieList.size() + menuItem];  //верхним элементом будет пункт "Добавить новый рецепт", поэтому делаем к размеру списка рецептов +1
        listForShow[0] = "----------- Создать новый рецепт -----------";

        int i = menuItem;
        for (Recepie recepie : recepieList) {
                listForShow[i] = recepie.getId() + ":" + recepie.getName() + " [" + recepie.getMark() + "]";
                i++;
            }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Выберите рецепт для редактирования").setItems(listForShow, (dialogInterface, i1) -> {
            if (i1 > 0) {
                Recepie rec = recepieList.get(i1 - 1);
                editedRecepie = rec;
                Toast.makeText(getActivity(), rec.getMark(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), EditRecipeActivity.class);
                startActivity(intent);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                editedRecepie = new Recepie(
                        0,
                        sdf.format(new Date()),
                        "",
                        "",
                        "",
                        "",
                        "",
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
                Intent intent = new Intent(getActivity().getApplicationContext(), EditRecipeActivity.class);
                startActivity(intent);

            }
        }).create();

    }
}
