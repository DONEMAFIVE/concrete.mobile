package ru.zzbo.concretemobile.gui.dialogs.uploaders;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.selectedOrder;
import static ru.zzbo.concretemobile.utils.Constants.selectedRecepie;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.commands.SetRecipe;
import ru.zzbo.concretemobile.utils.OkHttpUtil;

public class RecipeLoaderDialog extends DialogFragment {

    private ProgressDialog progressDialog;
    private List<Recepie> recepies;

    public RecipeLoaderDialog(List<Recepie> recepies) {
        this.recepies = recepies;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] recipeList = new String[this.recepies.size()];

        int i = 0;
        for (Recepie recepie : this.recepies) {
            recipeList[i] = recepie.getName() + " [" + recepie.getMark() + "]";
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите рецепт")
                .setItems(recipeList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Подождите, загружается рецепт - " + recepies.get(i).getMark(), Toast.LENGTH_LONG).show();
                        selectedRecepie = recepies.get(i).getMark();
                        selectedOrder = "Не указано";
                        new DBUtilUpdate(getContext()).updCurrentTable("recepieId", String.valueOf(recepies.get(i).getId()));
                        new DBUtilUpdate(getContext()).updCurrentTable("orderId", "0");

                        new Thread(() -> {
                            if (exchangeLevel == 1) {
                                OkHttpUtil.uplRecipe(recepies.get(i).getId());
                                OkHttpUtil.updCurrent(recepies.get(i).getId(), 0);
                            }
                            else new SetRecipe().sendRecipeToPLC(recepies.get(i));
                        }).start();

                        //пока грузится рецепт в контроллер покажем пользователю симуляцию загрузки
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setMax(100);
                        progressDialog.setMessage("Идет загрузка....");
                        progressDialog.setTitle("Выбран рецепт " + recepies.get(i).getMark());
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
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            new CommandDispatcher(tagListManual.get(71)).writeSingleFrontBoolRegister(2000);
                        }).start();

                    }
                }).setNegativeButton("Отмена", null).create();
    }
}
