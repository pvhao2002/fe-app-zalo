package com.author.toan.routes;

import com.author.toan.models.Chat;
import com.author.toan.models.User;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIChatService {
    @GET("get-chat")
    Call<List<Chat>> getChats (@Header("Authorization") String token);

    @POST("access-chat")
    Call<ResponseBody> accessChat (@Body HashMap<String, String> chatId, @Header("Authorization") String token);

    @POST("create-chat")
    Call<ResponseBody> createChat (@Body HashMap<String, String> userId, @Header("Authorization") String token);

}
