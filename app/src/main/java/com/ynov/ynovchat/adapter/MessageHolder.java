package com.ynov.ynovchat.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ynov.ynovchat.databinding.RowLayoutMessageBinding;

/**
 * Created by quentin for YnovChat on 17/12/2021.
 */
class MessageHolder extends RecyclerView.ViewHolder {
    RowLayoutMessageBinding binding;

    public MessageHolder(@NonNull RowLayoutMessageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
