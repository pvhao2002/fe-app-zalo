package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.adapter.ChatAdapter;
import com.author.toan.adapter.UserAdapter;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.databinding.RowChatBinding;
import com.author.toan.models.Chat;
import com.author.toan.models.User;
import com.author.toan.remote.SocketClient;
import com.author.toan.viewmodels.ChatViewModel;
import com.author.toan.views.editprofile.UserActivity;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.OnItemClickListener, UserAdapter.OnItemClickListener {
    ChatViewModel chatViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityChatBinding activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        chatViewModel = ChatViewModel.getInstance();
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
                else if (state == STATE.VIEW_FRIEND) {
                    startActivity(new Intent(getApplicationContext(), FriendActivity.class));
                    chatViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });

        chatViewModel.getListUsers("9");
        activityChatBinding.searchView.clearFocus();
        UserAdapter userAdapter = new UserAdapter(chatViewModel.getUsers().getValue());
        activityChatBinding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    // If the search query is empty, show the rvChat and hide the rvUser
                    activityChatBinding.rvChat.setVisibility(View.VISIBLE);
                    activityChatBinding.rvUser.setVisibility(View.GONE);
                } else {
                    activityChatBinding.rvChat.setVisibility(View.GONE);
                    activityChatBinding.rvUser.setVisibility(View.VISIBLE);
                    chatViewModel.getListUsers(s);
                    userAdapter.setListner(chatViewModel.getUsers().getValue());
                }
                return false;
            }
        });
        chatViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    UserAdapter userAdapter1 = new UserAdapter(users);
                    activityChatBinding.rvUser.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    activityChatBinding.rvUser.setAdapter(userAdapter1);
                    userAdapter1.setOnItemClickListener(ChatActivity.this);
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
        SocketClient.getInstance().getSocket().emit("join chat", chat.getId());
        Log.e("join chat", chat.getId());
        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", chat.getId());
        bundle.putString("name", chat.getName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void itemClick(User user) {
        chatViewModel.startChat(user).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String chatId) {
                Log.e("chatId-acti", chatId);
                SocketClient.getInstance().getSocket().emit("join chat", chatId);
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", chatId);
                bundle.putString("name", user.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}