package ru.zzbo.concretemobile.gui.dialogs.uploaders;

import static ru.zzbo.concretemobile.utils.Constants.selectedTrans;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.zzbo.concretemobile.models.Transporter;

public class TransporterListDialog extends DialogFragment {

    private List<Transporter> transporterList;

    public TransporterListDialog(List<Transporter> transporterList){
        this.transporterList = transporterList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] transNames = new String[transporterList.size()];
        int i = 0;

        for (Transporter trans : this.transporterList) {
            transNames[i] = trans.getRegNumberAuto();
            i++;
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите перевозчика")

                .setItems(transNames, (dialog, which) -> {
                    Toast.makeText(getActivity(),
                            "Выбранный водитель: " + transNames[which],
                            Toast.LENGTH_SHORT).show();
                    selectedTrans = transNames[which];
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
