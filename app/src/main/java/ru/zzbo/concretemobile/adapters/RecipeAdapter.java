package ru.zzbo.concretemobile.adapters;

import static ru.zzbo.concretemobile.utils.Constants.exchangeLevel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.utils.Constants;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    public interface DelRecipeClickListener {
        void onClick(Recepie recipe, int position);
    }

    public interface EditRecipeClickListener {
        void onClick(Recepie recipe, int position);
    }

    public interface LoadToPlcClickListener {
        void onClick(Recepie recipe,int position);
    }

    private final DelRecipeClickListener delRecipeClickListener;
    private final EditRecipeClickListener editRecipeClickListener;
    private final LoadToPlcClickListener loadToPlcClickListener;

    private final LayoutInflater inflater;
    private List<Recepie> recipes;

    public RecipeAdapter(Context context, List<Recepie> recipes, EditRecipeClickListener editRecipeClickListener, LoadToPlcClickListener loadToPlcClickListener, DelRecipeClickListener delRecipeClickListener) {
        this.delRecipeClickListener = delRecipeClickListener;
        this.editRecipeClickListener = editRecipeClickListener;
        this.loadToPlcClickListener = loadToPlcClickListener;
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recepie recipe = recipes.get(position);
        holder.id.setText(String.valueOf(recipe.getId()));
        holder.nameRecipe.setText(recipe.getName());
        holder.marka.setText(recipe.getMark());
        holder.description.setText(recipe.getDescription());

        holder.delRecipeBtn.setOnClickListener(v -> {
            delRecipeClickListener.onClick(recipe, position);
        });

        holder.editRecipeBtn.setOnClickListener(v -> {
            editRecipeClickListener.onClick(recipe, position);
        });

        holder.loadToPlcBtn.setOnClickListener(v -> {
            loadToPlcClickListener.onClick(recipe, position);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void filterList(List<Recepie> filteredList){
        recipes = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameRecipe, id, description, marka;
        final ExtendedFloatingActionButton delRecipeBtn, editRecipeBtn, loadToPlcBtn;
        ViewHolder(View view){
            super(view);
            id = view.findViewById(R.id.id);
            nameRecipe = view.findViewById(R.id.name);
            marka = view.findViewById(R.id.marka);
            description = view.findViewById(R.id.description);
            loadToPlcBtn = view.findViewById(R.id.load_to_plc_btn);
            editRecipeBtn = view.findViewById(R.id.edit_recipe_btn);
            delRecipeBtn = view.findViewById(R.id.del_recipe_btn);

            if (exchangeLevel != 0) editRecipeBtn.setVisibility(View.GONE);
        }
    }
}