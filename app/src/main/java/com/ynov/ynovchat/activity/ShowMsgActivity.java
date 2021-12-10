package com.ynov.ynovchat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.ynov.ynovchat.R;
import com.ynov.ynovchat.bo.Message;
import com.ynov.ynovchat.bo.User;
import com.ynov.ynovchat.databinding.ActivityShowMsgBinding;

public class ShowMsgActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShowMsgBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_msg);
        String millions = "";
        for (int i =0;  i<9999;i++){
            millions+="Bonjour le monde";
        }
        Message monMessage = new Message(
                millions,
            new User("moi", "unemail@oui.Fr"),
            "Aujourd'hui"
        );
        binding.setMessage(monMessage);
    }
}