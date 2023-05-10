package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.author.toan.R;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.viewmodels.LoginViewModel;
import com.author.toan.viewmodels.RegisterViewModel;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityChatBinding activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
    }
}