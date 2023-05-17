package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.adapter.ChatAdapter;
import com.author.toan.adapter.FriendAdapter;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.databinding.ActivityFriendBinding;
import com.author.toan.models.Chat;
import com.author.toan.models.User;
import com.author.toan.viewmodels.ChatViewModel;

import java.util.List;

public class FriendActivity extends AppCompatActivity implements FriendAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFriendBinding activityFriendBinding = DataBindingUtil.setContentView(this, R.layout.activity_friend);
        ChatViewModel chatViewModel = ChatViewModel.getInstance();
        activityFriendBinding.setLifecycleOwner(this);
        activityFriendBinding.setChatViewModel(chatViewModel);

        chatViewModel.getFriend();

        chatViewModel.getFriends().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    FriendAdapter friendAdapter = new FriendAdapter(users);
                    activityFriendBinding.rvFriends.setLayoutManager(new LinearLayoutManager(FriendActivity.this));
                    activityFriendBinding.rvFriends.setAdapter(friendAdapter);
                    friendAdapter.setOnItemClickListener(FriendActivity.this);
                }
            }
        });

        chatViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.VIEW_FRIEND_REQUEST) {
                    startActivity(new Intent(getApplicationContext(), FriendRequestActivity.class));
                    chatViewModel.setGotoScreen(STATE.MAIN);
                }

            }
        });
    }

    @Override
    public void itemClick(User user) {
        Toast.makeText(this, user.getName(), Toast.LENGTH_SHORT).show();
    }
}