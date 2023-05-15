package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.adapter.ChatAdapter;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.databinding.RowChatBinding;
import com.author.toan.models.Chat;
import com.author.toan.viewmodels.ChatViewModel;
import com.author.toan.views.editprofile.UserActivity;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityChatBinding activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        ChatViewModel chatViewModel = ChatViewModel.getInstance();
        activityChatBinding.setLifecycleOwner(this);
        activityChatBinding.setChatViewModel(chatViewModel);

        chatViewModel.getChats();
        chatViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.VIEW_ACCOUNT) {
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    chatViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });

        chatViewModel.getChat().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                if (chats != null) {
                    ChatAdapter chatAdapter = new ChatAdapter(chats);
                    activityChatBinding.rvChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    activityChatBinding.rvChat.setAdapter(chatAdapter);
                    chatAdapter.setOnItemClickListener(ChatActivity.this);
                }
            }
        });
    }

    @Override
    public void itemClick(Chat chat) {
        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", chat.getId());
        bundle.putString("name", chat.getName());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}