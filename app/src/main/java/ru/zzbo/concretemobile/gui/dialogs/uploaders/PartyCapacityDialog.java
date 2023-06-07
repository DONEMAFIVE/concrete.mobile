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

public class PartyCapacityDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText partyCapacity = new EditText(getActivity());
        partyCapacity.setText("1");
        partyCapacity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setTitle("Укажите параметры партии")
                .setView(partyCapacity)
                .setPositiveButton("Принять", (dialogInterface, i) -> {

                    new Thread(() -> {
                        tagListManual = new DBTags(getContext()).getTags("tags_manual");
                        try {
                            float totalWeightParty = Float.valueOf(partyCapacity.getText().toString());

                            if (exchangeLevel == 1) {
                                new CommandDispatcher(64).writeValue(String.valueOf(totalWeightParty));
                            } else {
                                Tag weightPartyTag = Constants.tagListManual.get(64);
                                weightPartyTag.setRealValueIf(totalWeightParty);
                                new CommandDispatcher(weightPartyTag).writeSingleRegisterWithLock();
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
                        new CommandDispatcher(tagListManual.get(71)).writeSingleFrontBoolRegister(2000);
                    }).start();

                    Toast.makeText(getActivity(), "Партия: " + partyCapacity.getText(), Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
