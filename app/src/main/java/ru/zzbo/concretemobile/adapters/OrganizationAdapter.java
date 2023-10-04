package ru.zzbo.concretemobile.adapters;

import static ru.zzbo.concretemobile.utils.Constants.accessLevel;
import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.models.Organization;

/**
 * Адаптер объекта в списке RecyclerView
 */
public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder> {

    public interface DelOrgClickListener {
        void onClick(Organization organization, int position);
    }

    public interface EditOrgClickListener {
        void onClick(Organization organization, int position);
    }

    public interface LoadToPlcClickListener {
        void onClick(Organization organization, int position);
    }

    private final DelOrgClickListener delOrgClickListener;
    private final EditOrgClickListener editOrgClickListener;
    private final LoadToPlcClickListener setOrgClickListener;

    private final LayoutInflater inflater;
    private List<Organization> organizations;

    public OrganizationAdapter(Context context, List<Organization> organizations, EditOrgClickListener editOrgClickListener,
                               LoadToPlcClickListener setOrgClickListener, DelOrgClickListener delOrgClickListener) {
        this.delOrgClickListener = delOrgClickListener;
        this.editOrgClickListener = editOrgClickListener;
        this.setOrgClickListener = setOrgClickListener;
        this.organizations = organizations;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.org_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Organization organization = organizations.get(position);
        holder.id.setText(String.valueOf(organization.getId()));
        holder.nameOrg.setText(organization.getOrganizationName());
        holder.headNameOrg.setText(organization.getOrganizationHeadName());
        holder.comment.setText(organization.getComment());

        if (accessLevel == 0) {
            holder.setOrgBtn.setVisibility(View.VISIBLE);
            holder.editOrgBtn.setVisibility(View.GONE);
        }

        if (accessLevel == 1) {
            holder.setOrgBtn.setVisibility(View.GONE);
            holder.editOrgBtn.setVisibility(View.VISIBLE);
        }

        holder.delOrgBtn.setOnClickListener(v -> delOrgClickListener.onClick(organization, position));

        holder.editOrgBtn.setOnClickListener(v -> editOrgClickListener.onClick(organization, position));

        holder.setOrgBtn.setOnClickListener(v -> setOrgClickListener.onClick(organization, position));
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameOrg, id, comment, headNameOrg;
        final ExtendedFloatingActionButton delOrgBtn, editOrgBtn, setOrgBtn;

        ViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.id);
            nameOrg = view.findViewById(R.id.name);
            headNameOrg = view.findViewById(R.id.marka);
            comment = view.findViewById(R.id.description);
            setOrgBtn = view.findViewById(R.id.load_to_plc_btn);
            editOrgBtn = view.findViewById(R.id.edit_recipe_btn);
            delOrgBtn = view.findViewById(R.id.del_recipe_btn);
        }
    }

    public void filterList(List<Organization> filteredList){
        organizations = filteredList;
        notifyDataSetChanged();
    }
}