package com.ynov.ynovchat.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ynov.ynovchat.R;
import com.ynov.ynovchat.bo.User;
import com.ynov.ynovchat.databinding.RowLayoutUserBinding;

import java.util.ArrayList;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    ArrayList<User> arrayListUsers;

    public UserAdapter() {
        this.arrayListUsers = new ArrayList<>();
    }

    public void setArrayListUsers(ArrayList<User> arrayListUsers) {
        this.arrayListUsers = arrayListUsers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutUserBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_layout_user,
                parent,
                false
        );
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User userToDisplay = arrayListUsers.get(position);
        holder.binding.setUser(userToDisplay);
    }

    @Override
    public int getItemCount() {
        return arrayListUsers.size();
    }

}
