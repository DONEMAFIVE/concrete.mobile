package ru.zzbo.concretemobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.models.Users;

/**
 * Адаптер объекта "рецепт" в списке RecyclerView
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public interface DelClickListener {
        void onClick(Users user, int position);
    }

    public interface EditClickListener {
        void onClick(Users user, int position);
    }

//    private final DelClickListener delClickListener;
    private final EditClickListener editClickListener;

    private final LayoutInflater inflater;
    private List<Users> users;

    public UserAdapter(Context context, List<Users> users, EditClickListener editClickListener) {
        this.editClickListener = editClickListener;
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Users user = users.get(position);
        holder.id.setText(String.valueOf(user.getId()));
        holder.name.setText(user.getUserName());
        holder.login.setText(user.getLogin());

        switch (user.getAccessLevel()){
            case 0: holder.level.setText("Оператор"); break;
            case 1: holder.level.setText("Диспетчер"); break;
            case 2: holder.level.setText("Инженер"); break;
            case 3: holder.level.setText("Администратор");break;
        }

        holder.editBtn.setOnClickListener(v -> editClickListener.onClick(user, position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, id, level, login;
        final ExtendedFloatingActionButton editBtn;

        ViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.id);
            name = view.findViewById(R.id.name);
            login = view.findViewById(R.id.login);
            level = view.findViewById(R.id.level);

            editBtn = view.findViewById(R.id.edit_btn);
        }
    }

    public void filterList(List<Users> filteredList){
        users = filteredList;
        notifyDataSetChanged();
    }
}