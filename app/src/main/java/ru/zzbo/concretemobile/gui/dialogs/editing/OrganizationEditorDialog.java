package ru.zzbo.concretemobile.gui.dialogs.editing;

import static ru.zzbo.concretemobile.utils.Constants.editedOrganization;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.zzbo.concretemobile.gui.catalogs.OrganizationActivity;
import ru.zzbo.concretemobile.models.Organization;

public class OrganizationEditorDialog extends DialogFragment {

    List<Organization> organizationList;

    public OrganizationEditorDialog(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] showList = new String[organizationList.size() + 1];
        showList[0] = "Создать новую организацию";

        int i = 1;
        for (Organization org : organizationList) {
            showList[i] = org.getId() + ":" + org.getOrganizationHeadName();
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите организацию для редактирования")
                .setItems(showList, (dialogInterface, i1) -> {
                    if (i1 > 0) {
                        Organization selectedOrg = organizationList.get(i1 - 1);
                        editedOrganization = selectedOrg;
                        Toast.makeText(getActivity(), selectedOrg.getOrganizationName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), OrganizationActivity.class);
                        startActivity(intent);
                    } else {
                        editedOrganization = new Organization(
                                0,
                                "",
                                "",
                                0,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                        );
                        Intent intent = new Intent(getActivity().getApplicationContext(), OrganizationActivity.class);
                        startActivity(intent);
                    }
                }).create();
    }
}
