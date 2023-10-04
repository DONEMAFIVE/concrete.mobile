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
import ru.zzbo.concretemobile.models.Transporter;

/**
 * Адаптер объекта в списке RecyclerView
 */
public class TransporterAdapter extends RecyclerView.Adapter<TransporterAdapter.ViewHolder> {

    public interface DelOrgClickListener {
        void onClick(Transporter transporter, int position);
    }

    public interface EditOrgClickListener {
        void onClick(Transporter transporter, int position);
    }

    public interface LoadToPlcClickListener {
        void onClick(Transporter transporter, int position);
    }

    private final DelOrgClickListener delClickListener;
    private final EditOrgClickListener editClickListener;
    private final LoadToPlcClickListener setClickListener;

    private final LayoutInflater inflater;
    private List<Transporter> transporters;

    public TransporterAdapter(Context context, List<Transporter> transporters, EditOrgClickListener editClickListener,
                              LoadToPlcClickListener setClickListener, DelOrgClickListener delClickListener) {
        this.delClickListener = delClickListener;
        this.editClickListener = editClickListener;
        this.setClickListener = setClickListener;
        this.transporters = transporters;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trans_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transporter transporter = transporters.get(position);
        holder.id.setText(String.valueOf(transporter.getId()));
        holder.nameOrg.setText(transporter.getOrganizationName());
        holder.regNumberAuto.setText(transporter.getRegNumberAuto());
        holder.comment.setText(transporter.getComment());

        if (accessLevel == 0) {
            holder.setBtn.setVisibility(View.VISIBLE);
            holder.editBtn.setVisibility(View.GONE);
        }
        if (accessLevel == 1) {
            holder.setBtn.setVisibility(View.GONE);
            holder.editBtn.setVisibility(View.VISIBLE);
        }

        holder.delBtn.setOnClickListener(v -> delClickListener.onClick(transporter, position));

        holder.editBtn.setOnClickListener(v -> editClickListener.onClick(transporter, position));

        holder.setBtn.setOnClickListener(v -> setClickListener.onClick(transporter, position));
    }

    @Override
    public int getItemCount() {
        return transporters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameOrg, id, comment, regNumberAuto;
        final ExtendedFloatingActionButton delBtn, editBtn, setBtn;

        ViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.id);
            nameOrg = view.findViewById(R.id.name);
            regNumberAuto = view.findViewById(R.id.reg_number_auto);
            comment = view.findViewById(R.id.description);
            setBtn = view.findViewById(R.id.set_btn);
            editBtn = view.findViewById(R.id.edit_btn);
            delBtn = view.findViewById(R.id.del_btn);
        }
    }

    public void filterList(List<Transporter> filteredList){
        transporters = filteredList;
        notifyDataSetChanged();
    }
}