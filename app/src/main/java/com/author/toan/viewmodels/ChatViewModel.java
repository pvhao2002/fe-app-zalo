package com.author.toan.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;
import com.author.toan.models.Chat;
import com.author.toan.models.Message;
import com.author.toan.models.User;
import com.author.toan.remote.SharedPrefManager;
import com.author.toan.response.RequestAddFriend;
import com.author.toan.routes.APIChatService;
import com.author.toan.routes.APIMessageService;
import com.author.toan.routes.APIUserService;
import com.author.toan.routes.ChatClient;
import com.author.toan.routes.MessageClient;
import com.author.toan.routes.UserClient;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<STATE> gotoScreen;
    private MutableLiveData<List<Chat>> chat;
    private MutableLiveData<List<User>> friends;
    private MutableLiveData<List<RequestAddFriend>> requestAddFriends;
    private APIChatService apiChatService;
    private APIUserService apiUserService;
    private static ChatViewModel instance;

    private ChatViewModel() {
        loading = new MutableLiveData<>(false);
        gotoScreen = new MutableLiveData<>();
        chat = new MutableLiveData<>();
        friends = new MutableLiveData<>();
        requestAddFriends = new MutableLiveData<>();
        apiChatService = ChatClient.getInstance();
        apiUserService = UserClient.getInstance();
    }

    public static ChatViewModel getInstance() {
        if (instance == null) {
            instance = new ChatViewModel();
        }
        return instance;
    }

    public MutableLiveData<STATE> getGotoScreen() {
        return gotoScreen;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setGotoScreen(STATE state) {
        gotoScreen.setValue(state);
    }

    public void viewAccount() {
        gotoScreen.setValue(STATE.VIEW_ACCOUNT);
    }

    public void viewTimeLines() {
        gotoScreen.setValue(STATE.VIEW_TIME_LINE);
    }

    public void viewGroups() {
        gotoScreen.setValue(STATE.VIEW_GROUP);
    }

    public void viewMessages() {
        gotoScreen.setValue(STATE.VIEW_MESSAGE);
    }

    public void getFriendRequest() {
        gotoScreen.setValue(STATE.VIEW_FRIEND_REQUEST);
    }

    public void viewFriends() {
        gotoScreen.setValue(STATE.VIEW_FRIEND);
    }
    public MutableLiveData<List<Chat>> getChat() {
        return chat;
    }
    public MutableLiveData<List<RequestAddFriend>> getRequestAddFriends() {
        return requestAddFriends;
    }
    public MutableLiveData<List<User>> getFriends() {
        return friends;
    }
    public void getChats() {
        String token = SharedPrefManager.getUser().getToken();
        loading.setValue(true);
        apiChatService.getChats("Bearer " + token).enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                loading.setValue(false);
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        List<Chat> chatList = response.body();
                        chat.setValue(chatList);
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        Log.e("Error-backend", obj.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Log.e("Error-call", t.getMessage());
            }
        });
    }

    public void getFriend() {
        String token = SharedPrefManager.getUser().getToken();
        loading.setValue(true);

        apiUserService.getFriends("Bearer " + token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                loading.setValue(false);
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        List<User> userList = response.body();
                        friends.setValue(userList);
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        Log.e("Error-backend", obj.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Error-call", t.getMessage());
            }
        });
    }

    public void getRequestFriends() {
        String token = SharedPrefManager.getUser().getToken();
        loading.setValue(true);

        apiUserService.getFriendRequest("Bearer " + token).enqueue(new Callback<List<RequestAddFriend>>() {
            @Override
            public void onResponse(Call<List<RequestAddFriend>> call, Response<List<RequestAddFriend>> response) {
                loading.setValue(false);
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        List<RequestAddFriend> requestAddFriend = response.body();
                        requestAddFriends.setValue(requestAddFriend);
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        Log.e("Error-backend", obj.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<List<RequestAddFriend>> call, Throwable t) {
                Log.e("Error-call", t.getMessage());
            }
        });
    }

    public void answerRequestAddFriend(String requestId ,String answer) {

    }
}
