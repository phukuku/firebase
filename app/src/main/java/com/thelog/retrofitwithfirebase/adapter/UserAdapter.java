package com.thelog.retrofitwithfirebase.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thelog.retrofitwithfirebase.R;
import com.thelog.retrofitwithfirebase.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.thelog.retrofitwithfirebase.R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.txtUsernameRC.setText(user.getUsername());
        holder.txtEmailRC.setText(user.getEmail());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUsernameRC, txtEmailRC;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsernameRC = itemView.findViewById(com.thelog.retrofitwithfirebase.R.id.txtUsernameRC);
            txtEmailRC = itemView.findViewById(com.thelog.retrofitwithfirebase.R.id.txtEmailRC);
        }
    }
}
