package com.author.toan.routes;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIUserService {
    @POST("sign-in")
    Call<ResponseBody> login (@Body HashMap<String, String> phone_password);

    @POST("create")
    Call<ResponseBody> signup (@Body HashMap<String, String> name_phone_password);

    @POST("verify-phone")
    Call<ResponseBody> verifyPhone (@Body HashMap<String, String> phone);

    @POST("forget-password")
    Call<ResponseBody> forgotPassword (@Body HashMap<String, String> phone);

    @POST("verify-pass-reset-token")
    Call<ResponseBody> verifyOTP (@Body HashMap<String, String> user_otp);

    @POST("reset-password")
    Call<ResponseBody> resetPassword (@Body HashMap<String, String> otp_password);

    @POST("change-password")
    Call<ResponseBody> changePassword (@Body HashMap<String, String> user_password, @Header("Authorization") String token);

    @POST("upload-image")
    @Multipart
    Call<ResponseBody> uploadImage (@Part MultipartBody.Part avatar, @Header("Authorization") String token);
}
