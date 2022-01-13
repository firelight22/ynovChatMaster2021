package com.ynov.ynovchat.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.ynov.ynovchat.databinding.RowLayoutUserBinding;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {
    RowLayoutUserBinding binding;
    public UserViewHolder(RowLayoutUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
