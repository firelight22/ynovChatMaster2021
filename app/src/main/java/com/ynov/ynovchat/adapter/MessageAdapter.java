package com.ynov.ynovchat.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ynov.ynovchat.R;
import com.ynov.ynovchat.bo.Message;
import com.ynov.ynovchat.databinding.RowLayoutMessageBinding;
import com.ynov.ynovchat.fragment.ListMessageFragmentDirections;

import java.util.ArrayList;

/**
 * Created by quentin for YnovChat on 17/12/2021.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {
    ArrayList<Message> messageArrayList;

    public MessageAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutMessageBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_layout_message,
                parent,
                false
        );
        return new MessageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message msg = messageArrayList.get(position);
        ListMessageFragmentDirections.Liste2Detail action =
                ListMessageFragmentDirections.liste2Detail(msg);
        holder.itemView.setOnClickListener(
                (view)-> Navigation.findNavController(holder.itemView)
                .navigate((NavDirections) action)
        );
        holder.binding.setMessage(msg);
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
