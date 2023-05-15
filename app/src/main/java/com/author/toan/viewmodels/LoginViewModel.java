package com.author.toan.viewmodels;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;
import com.author.toan.models.Avatar;
import com.author.toan.models.User;
import com.author.toan.remote.SharedPrefManager;
import com.author.toan.routes.APIUserService;
import com.author.toan.routes.UserClient;
import com.author.toan.views.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private APIUserService apiUserService;
    public MutableLiveData<User> mUser;
    private MutableLiveData<Boolean> loading;
    public MutableLiveData<String> error;
    public MutableLiveData<String> phoneNumber;
    public MutableLiveData<String> password;
    private MutableLiveData<STATE> gotoScreen;
    private static LoginViewModel instance;

    public static LoginViewModel getInstance() {
        if (instance == null) {
            instance = new LoginViewModel();
        }
        return instance;
    }

    private LoginViewModel() {
        mUser = new MutableLiveData<>();
        loading = new MutableLiveData<>(false);
        error = new MutableLiveData<>("");
        phoneNumber = new MutableLiveData<>("");
        password = new MutableLiveData<>("");
        gotoScreen = new MutableLiveData<>();
        apiUserService = UserClient.getInstance();
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<STATE> getGotoScreen() {
        return gotoScreen;
    }
    public void setGotoScreen(STATE state) {
        gotoScreen.setValue(state);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }
    public MutableLiveData<User> getUser() {
        return mUser;
    }

    public void login() {
        loading.setValue(true);
        String phone = phoneNumber.getValue();
        String password = this.password.getValue();
        HashMap<String, String> email_password = new HashMap<>();
        email_password.put("phone", phone);
        email_password.put("password", password);

        apiUserService.login(email_password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        JSONObject obj = new JSONObject(response.body().string());
                        JSONObject avatarJson = obj.getJSONObject("avatar");

                        Avatar avatar = new Avatar(
                                avatarJson.getString("url"),
                                avatarJson.getString("public_id")
                        );

                        User user = new User(
                                obj.getString("_id"),
                                obj.getString("name"),
                                obj.getString("phone"),
                                obj.getString("token"),
                                obj.getBoolean("isVerified"),
                                avatar
                        );
                        mUser.setValue(user);
                        gotoScreen.setValue(STATE.CHAT);
                        Log.e("token: ", user.getToken());
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        error.setValue(obj.getString("error"));
                    }
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void logout() {
    }

    public void recoverPassword() {
        gotoScreen.setValue(STATE.FORGOT_PASSWORD);
    }
}
