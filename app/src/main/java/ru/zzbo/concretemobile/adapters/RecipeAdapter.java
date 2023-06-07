package ru.zzbo.concretemobile.adapters;

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
import ru.zzbo.concretemobile.models.Recepie;

/**
 * Адаптер объекта "рецепт" в списке RecyclerView
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public interface DelRecipeClickListener {
        void onClick(Recepie recepie, int position);
    }

    public interface EditRecipeClickListener {
        void onClick(Recepie recepie, int position);
    }

    public interface LoadToPlcClickListener {
        void onClick(Recepie recepie, int position);
    }

    private final DelRecipeClickListener delRecipeClickListener;
    private final EditRecipeClickListener editRecipeClickListener;
    private final LoadToPlcClickListener loadToPlcClickListener;

    private final LayoutInflater inflater;
    private List<Recepie> recepies;

    public RecipeAdapter(Context context, List<Recepie> recepies, EditRecipeClickListener editRecipeClickListener,
                         LoadToPlcClickListener loadToPlcClickListener, DelRecipeClickListener delRecipeClickListener) {
        this.delRecipeClickListener = delRecipeClickListener;
        this.editRecipeClickListener = editRecipeClickListener;
        this.loadToPlcClickListener = loadToPlcClickListener;
        this.recepies = recepies;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recepie recepie = recepies.get(position);
        holder.id.setText(String.valueOf(recepie.getId()));
        holder.nameRecipe.setText(recepie.getName());
        holder.marka.setText(recepie.getMark());
        holder.description.setText(recepie.getDescription());

        holder.delRecipeBtn.setOnClickListener(v -> delRecipeClickListener.onClick(recepie, position));

        holder.editRecipeBtn.setOnClickListener(v -> editRecipeClickListener.onClick(recepie, position));

        holder.loadToPlcBtn.setOnClickListener(v -> loadToPlcClickListener.onClick(recepie, position));
    }

    @Override
    public int getItemCount() {
        return recepies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameRecipe, id, description, marka;
        final ExtendedFloatingActionButton delRecipeBtn, editRecipeBtn, loadToPlcBtn;

        ViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.id);
            nameRecipe = view.findViewById(R.id.name);
            marka = view.findViewById(R.id.marka);
            description = view.findViewById(R.id.description);
            loadToPlcBtn = view.findViewById(R.id.load_to_plc_btn);
            editRecipeBtn = view.findViewById(R.id.edit_recipe_btn);
            delRecipeBtn = view.findViewById(R.id.del_recipe_btn);
        }
    }

    public void filterList(List<Recepie> filteredList){
        recepies = filteredList;
        notifyDataSetChanged();
    }
}