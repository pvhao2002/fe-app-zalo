package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.author.toan.R;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.databinding.ActivityMessageBinding;
import com.author.toan.viewmodels.ChatViewModel;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityMessageBinding activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        ChatViewModel chatViewModel = ChatViewModel.getInstance();
        activityMessageBinding.setLifecycleOwner(this);
        activityMessageBinding.setChatViewModel(chatViewModel);
    }
}