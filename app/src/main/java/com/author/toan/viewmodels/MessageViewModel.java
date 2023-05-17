package com.author.toan.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;
import com.author.toan.models.Message;
import com.author.toan.remote.SharedPrefManager;
import com.author.toan.remote.SocketClient;
import com.author.toan.routes.APIMessageService;
import com.author.toan.routes.MessageClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<List<Message>> messages;
    private APIMessageService apiMessageService;
    public MutableLiveData<String> message;
    private static MessageViewModel instance;
    private MutableLiveData<STATE> gotoScreen;
    private String chatId;
    private MessageViewModel() {
        loading = new MutableLiveData<>(false);
        message = new MutableLiveData<>();
        messages = new MutableLiveData<>();
        gotoScreen = new MutableLiveData<>();
        apiMessageService = MessageClient.getInstance();
    }

    public static MessageViewModel getInstance() {
        if (instance == null) {
            instance = new MessageViewModel();
        }
        return instance;
    }

    public MutableLiveData<List<Message>> getMessages() {
        return messages;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<STATE> getGotoScreen() {
        return gotoScreen;
    }

    public void setGotoScreen(STATE state) {
        gotoScreen.setValue(state);
    }

    public void back() {
        gotoScreen.setValue(STATE.CHAT);
    }

    public void getAllMessage(String chatId) {
        this.chatId = chatId;
        String token = SharedPrefManager.getUser().getToken();
        HashMap<String, String> id = new HashMap<>();
        id.put("chatId", chatId);
        loading.setValue(true);
        apiMessageService.getMessages(id,"Bearer " + token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                loading.setValue(false);
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        List<Message> messageList = response.body();
                        messages.setValue(messageList);
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
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("Error-call", t.getMessage());
            }
        });
    }

    public void sendMessage() {
        String token = SharedPrefManager.getUser().getToken();
        HashMap<String, String> chat_content = new HashMap<>();
        chat_content.put("chatId", chatId);
        chat_content.put("content", message.getValue());
        loading.setValue(true);

        apiMessageService.sendMessage(chat_content, "Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.setValue(false);
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        JSONObject obj = new JSONObject(response.body().string());
                        SocketClient.getInstance().getSocket().emit("new message", obj);
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error-call", t.getMessage());
            }
        });
    }
}
