package ru.zzbo.concretemobile.gui.dialogs.uploaders;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;
import static ru.zzbo.concretemobile.utils.Constants.hydroGateOption;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

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

public class MixCapacityDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText mixCapacity = new EditText(getActivity());
        mixCapacity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setTitle("Укажите максимальный объем замеса")
                .setView(mixCapacity)
                .setPositiveButton("Принять", (dialogInterface, i) -> {
                    new Thread(() -> {
                        tagListManual = new DBTags(getContext()).getTags("tags_manual");
                        try {
                            float singleMixWeight = Float.valueOf(mixCapacity.getText().toString());

                            if (exchangeLevel == 1) {
                                new CommandDispatcher(62).writeValue(String.valueOf(singleMixWeight));
                            } else {
                                Tag weightMixTag = Constants.tagListManual.get(62);
                                weightMixTag.setRealValueIf(singleMixWeight);
                                new CommandDispatcher(weightMixTag).writeSingleRegisterWithLock();
                            }

                            if (hydroGateOption) {
                                Thread.sleep(100);
                                new CommandDispatcher(Constants.tagListManual.get(21)).writeSingleRegisterWithValue(false);
                                Thread.sleep(100);
                                new CommandDispatcher(Constants.tagListManual.get(82)).writeSingleRegisterWithValue(false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                    Toast.makeText(getActivity(), "Замес: " + mixCapacity.getText(), Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
