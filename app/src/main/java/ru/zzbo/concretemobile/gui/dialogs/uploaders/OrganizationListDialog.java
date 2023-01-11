package ru.zzbo.concretemobile.gui.dialogs.uploaders;

import static ru.zzbo.concretemobile.utils.Constants.selectedOrg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.zzbo.concretemobile.models.Organization;

public class OrganizationListDialog extends DialogFragment {

    private List<Organization> organizationList;

    public OrganizationListDialog(List<Organization> organizationList){
        this.organizationList = organizationList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] orgNamesArray = new String[organizationList.size()];
        int i = 0;

        for (Organization org : this.organizationList) {
            orgNamesArray[i] = org.getOrganizationName();
            i++;
        }



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите клиента")
                .setItems(orgNamesArray, (dialog, which) -> {
                    Toast.makeText(getActivity(),
                            "Выбранная организация: " + orgNamesArray[which],
                            Toast.LENGTH_SHORT).show();
                    selectedOrg = orgNamesArray[which];
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
