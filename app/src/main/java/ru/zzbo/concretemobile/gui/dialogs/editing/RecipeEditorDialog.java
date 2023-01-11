package ru.zzbo.concretemobile.gui.dialogs.editing;

import static ru.zzbo.concretemobile.utils.Constants.editedRecipe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.zzbo.concretemobile.gui.catalogs.EditRecipeActivity;
import ru.zzbo.concretemobile.models.Recipe;

public class RecipeEditorDialog extends DialogFragment {
    private List<Recipe> recipeList;
    private int menuItem = 1;

    public RecipeEditorDialog(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] listForShow = new String[recipeList.size() + menuItem];  //верхним элементом будет пункт "Добавить новый рецепт", поэтому делаем к размеру списка рецептов +1
        listForShow[0] = "----------- Создать новый рецепт -----------";

        int i = menuItem;
        for (Recipe recipe : recipeList) {
            listForShow[i] = recipe.getId() + ":" + recipe.getName() + " [" + recipe.getMark() + "]";
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Выберите рецепт для редактирования").setItems(listForShow, (dialogInterface, i1) -> {
            if (i1 > 0) {
                Recipe rec = recipeList.get(i1 - 1);
                editedRecipe = rec;
                Toast.makeText(getActivity(), rec.getMark(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), EditRecipeActivity.class);
                startActivity(intent);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                editedRecipe = new Recipe(
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
