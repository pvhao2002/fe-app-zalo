package com.author.toan.routes;

import com.author.toan.models.Chat;

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
}
