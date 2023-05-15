package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.adapter.ChatAdapter;
import com.author.toan.adapter.MessageAdapter;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.databinding.ActivityMessageBinding;
import com.author.toan.models.Message;
import com.author.toan.viewmodels.ChatViewModel;
import com.author.toan.viewmodels.MessageViewModel;

import java.util.List;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityMessageBinding activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        MessageViewModel messageViewModel = MessageViewModel.getInstance();
        activityMessageBinding.setLifecycleOwner(this);
        activityMessageBinding.setMessageViewModel(messageViewModel);

        String chatId = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        activityMessageBinding.tvName.setText(name);
        messageViewModel.getAllMessage(chatId);

        messageViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                if (messages != null) {
                    MessageAdapter messageAdapter = new MessageAdapter(messages);
                    activityMessageBinding.rvMessage.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                    activityMessageBinding.rvMessage.setAdapter(messageAdapter);
                }
            }
        });

        messageViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.CHAT) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    messageViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });
    }
}