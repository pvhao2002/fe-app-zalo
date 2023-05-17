package com.author.toan.routes;

import com.author.toan.models.Message;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIMessageService {
    @POST("get-messages")
    Call<List<Message>> getMessages (@Body HashMap<String, String> chatId, @Header("Authorization") String token);

    @POST("send-message")
    Call<ResponseBody> sendMessage (@Body HashMap<String, String> chat_content, @Header("Authorization") String token);
}
