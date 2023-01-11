package ru.zzbo.concretemobile.gui.dialogs.editing;

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

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.gui.OrdersActivity;
import ru.zzbo.concretemobile.models.Organization;
import ru.zzbo.concretemobile.models.Recipe;
import ru.zzbo.concretemobile.models.Transporter;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class CatalogMenuDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] catalogList = {"Рецепты", "Оганизации", "Грузоперевозчики", "Заказы"}; //"Данные производителя"
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите каталог")
                .setItems(catalogList, (dialog, i) -> {
                    Toast.makeText(getActivity(), "Выбран каталог " + catalogList[i] + " " + i, Toast.LENGTH_SHORT).show();

                    switch (i) {
                        case 0: {
                            new Thread(() -> {
                                List<Recipe> recipeList = new ArrayList<>();
                                if (exchangeLevel == 1) {
                                    recipeList.addAll(new Gson().fromJson(OkHttpUtil.getRecipes(), new TypeToken<List<Recipe>>() {}.getType()));
                                } else recipeList = new DBUtilGet(getContext()).getRecepies();

                                RecipeEditorDialog recipeEditorDialog = new RecipeEditorDialog(recipeList);
                                recipeEditorDialog.show(getActivity().getSupportFragmentManager(), "custom");
                            }).start();
                            break;
                        }
                        case 1: {
                            new Thread(() -> {
                                List<Organization> organizationList = new ArrayList<>();
                                if (exchangeLevel == 1) {
                                    organizationList.addAll(new Gson().fromJson(OkHttpUtil.getOrganization(), new TypeToken<List<Organization>>() {}.getType()));
                                } else organizationList = new DBUtilGet(getContext()).getOrgs();

                                OrganizationEditorDialog organizationEditorDialog = new OrganizationEditorDialog(organizationList);
                                organizationEditorDialog.show(getActivity().getSupportFragmentManager(), "custom");
                            }).start();
                            break;
                        }
                        case 2: {
                            new Thread(() -> {
                                List<Transporter> transporterList = new ArrayList<>();
                                if (exchangeLevel == 1) {
                                    transporterList.addAll(new Gson().fromJson(OkHttpUtil.getTransporters(), new TypeToken<List<Transporter>>() {}.getType()));
                                } else transporterList = new DBUtilGet(getContext()).getTrans();

                                TransporterEditorDialog transporterEditorDialog = new TransporterEditorDialog(transporterList);
                                transporterEditorDialog.show(getActivity().getSupportFragmentManager(), "custom");
                            }).start();
                            break;
                        }
                        case 3: {
                            Intent intent = new Intent(getContext(), OrdersActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            break;
                        }
                        case 4: {
                            Toast.makeText(getActivity(), "В разработке", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }).create();
    }
}
