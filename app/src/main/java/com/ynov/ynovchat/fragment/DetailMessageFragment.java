package com.ynov.ynovchat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ynov.ynovchat.R;
import com.ynov.ynovchat.bo.Message;
import com.ynov.ynovchat.databinding.FragmentDetailMessageBinding;

public class DetailMessageFragment extends Fragment {
    FragmentDetailMessageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_detail_message,
                container,
                false
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Message msg = DetailMessageFragmentArgs.fromBundle(getArguments()).getMessage();
        binding.setMessage(msg);
        Toast.makeText(view.getContext(), msg.getContent(), Toast.LENGTH_SHORT).show();
    }
}