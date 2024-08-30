package ru.zzbo.concretemobile.gui.dialogs.editing;

import static ru.zzbo.concretemobile.utils.Constants.editedTransporter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.zzbo.concretemobile.gui.catalogs.TransporterActivity;
import ru.zzbo.concretemobile.models.Transporter;

public class TransporterEditorDialog extends DialogFragment {

    List<Transporter> transporterList;

    public TransporterEditorDialog(List<Transporter> transporterList) {
        this.transporterList = transporterList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] showList = new String[transporterList.size() + 1];
        showList[0] = "Создать нового перевозчика";
        int i = 1;
        for (Transporter trans : transporterList) {
            showList[i] = trans.getId() + ":" + trans.getRegNumberAuto();
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите перевозчика для редактирования")
                .setItems(showList, (dialogInterface, i1) -> {
                    if (i1 > 0) {
                        Transporter selectedTrans = transporterList.get(i1 - 1);
                        editedTransporter = selectedTrans;
                        Intent intent = new Intent(getActivity(), TransporterActivity.class);
                        startActivity(intent);
                    } else {
                        editedTransporter = new Transporter(
                                0,
                                "",
                                "",
                                0,
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                        );
                        Intent intent = new Intent(getActivity().getApplicationContext(), TransporterActivity.class);
                        startActivity(intent);
                    }
                }).create();
    }
}
