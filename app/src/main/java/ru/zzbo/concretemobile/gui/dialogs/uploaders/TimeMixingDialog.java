package ru.zzbo.concretemobile.gui.dialogs.uploaders;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.hydroGateOption;
import static ru.zzbo.concretemobile.utils.Constants.retrieval;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.Constants;

public class TimeMixingDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText timeMixMixer = new EditText(getActivity());
        timeMixMixer.setText(String.valueOf((int) retrieval.getMixingTimeValue() / 1000));
        timeMixMixer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setTitle("Укажите время перемешивания в автомате")
                .setView(timeMixMixer)
                .setPositiveButton("Принять", (dialogInterface, i) -> {

                    new Thread(() -> {
                        tagListManual = new DBTags(getContext()).getTags("tags_manual");
                        try {
                            long timeMixing = Long.valueOf(timeMixMixer.getText().toString()) * 1000;

                            if (exchangeLevel == 1) {
                                new CommandDispatcher(65).writeValue(String.valueOf(timeMixing));
                            } else {
                                Tag timeMixingTag = Constants.tagListManual.get(65);
                                timeMixingTag.setDIntValueIf(timeMixing);
                                timeMixingTag.setTypeTag("DInt");
                                new CommandDispatcher(timeMixingTag).writeSingleRegisterWithThread();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                    Toast.makeText(getActivity(), "Время перемешивания: " + timeMixMixer.getText(), Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
