package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.adapter.FriendAdapter;
import com.author.toan.adapter.FriendRequestAdapter;
import com.author.toan.databinding.ActivityFriendBinding;
import com.author.toan.databinding.ActivityFriendRequestBinding;
import com.author.toan.models.User;
import com.author.toan.response.RequestAddFriend;
import com.author.toan.viewmodels.ChatViewModel;
import com.author.toan.views.editprofile.UserActivity;

import java.util.List;

public class FriendRequestActivity extends AppCompatActivity implements FriendRequestAdapter.OnItemClickListener {
    FriendRequestAdapter friendRequestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFriendRequestBinding activityFriendRequestBinding = DataBindingUtil.setContentView(this, R.layout.activity_friend_request);
        ChatViewModel chatViewModel = ChatViewModel.getInstance();
        activityFriendRequestBinding.setLifecycleOwner(this);
        activityFriendRequestBinding.setChatViewModel(chatViewModel);

        chatViewModel.getRequestFriends();

        chatViewModel.getRequestAddFriends().observe(this, new Observer<List<RequestAddFriend>>() {
            @Override
            public void onChanged(List<RequestAddFriend> requestAddFriends) {
                if (requestAddFriends != null) {
                    friendRequestAdapter = new FriendRequestAdapter(requestAddFriends);
                    activityFriendRequestBinding.rvFriendRequests.setLayoutManager(new LinearLayoutManager(FriendRequestActivity.this));
                    activityFriendRequestBinding.rvFriendRequests.setAdapter(friendRequestAdapter);
                    friendRequestAdapter.setOnItemClickListener(FriendRequestActivity.this);
                }
            }
        });

    }

    @Override
    public void itemClick(RequestAddFriend requestAddFriend) {

    }
}