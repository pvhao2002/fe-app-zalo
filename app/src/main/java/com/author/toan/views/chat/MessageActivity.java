package com.author.toan.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.adapter.ChatAdapter;
import com.author.toan.adapter.MessageAdapter;
import com.author.toan.databinding.ActivityChatBinding;
import com.author.toan.databinding.ActivityMessageBinding;
import com.author.toan.models.Avatar;
import com.author.toan.models.Message;
import com.author.toan.models.User;
import com.author.toan.remote.SocketClient;
import com.author.toan.viewmodels.ChatViewModel;
import com.author.toan.viewmodels.MessageViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.socket.emitter.Emitter;

public class MessageActivity extends AppCompatActivity {

    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityMessageBinding activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        MessageViewModel messageViewModel = MessageViewModel.getInstance();
        activityMessageBinding.setLifecycleOwner(this);
        activityMessageBinding.setMessageViewModel(messageViewModel);

        SocketClient.getInstance().getSocket().on("message received", onNewMessage_Send);


        String chatId = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        activityMessageBinding.tvName.setText(name);
        messageViewModel.getAllMessage(chatId);

        messageViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                if (messages != null) {
                    messageAdapter = new MessageAdapter(messages);
                    activityMessageBinding.rvMessage.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                    activityMessageBinding.rvMessage.setAdapter(messageAdapter);
                }
            }
        });

        messageViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.CHAT) {
                    SocketClient.getInstance().getSocket().emit("leave chat", chatId);
                    finish();
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    messageViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });
    }

    private Emitter.Listener onNewMessage_Send = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject userJson = data.getJSONObject("sender");
                        JSONObject avatarJson = userJson.getJSONObject("avatar");
                        Avatar avatar = new Avatar(
                                avatarJson.getString("url"),
                                avatarJson.getString("public_id")
                        );
                        User user = new User(
                                userJson.getString("_id"),
                                userJson.getString("name"),
                                userJson.getString("phone"),
                                userJson.getBoolean("isVerified"),
                                avatar
                        );
                        Message message = new Message(
                                data.getString("_id"),
                                data.getString("content"),
                                user
                        );
                        messageAdapter.addMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}