package com.author.toan.viewmodels;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.models.User;
import com.author.toan.routes.APIUserService;
import com.author.toan.routes.UserClient;

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
    public MutableLiveData<User> mUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public MutableLiveData<String> error = new MutableLiveData<>("");
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    private MutableLiveData<Boolean> gotoRecoverPassword;
    public MutableLiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public MutableLiveData<Boolean> getGotoRecoverPassword() {
        if (gotoRecoverPassword == null) {
            gotoRecoverPassword = new MutableLiveData<>(false);
        }
        return gotoRecoverPassword;
    }

    public MutableLiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<>(false);
        }
        return loading;
    }

    public void login() {
        loading.setValue(true);
        String phone = phoneNumber.getValue();
        String password = this.password.getValue();
        HashMap<String, String> email_password = new HashMap<>();
        email_password.put("phone", phone);
        email_password.put("password", password);

        apiUserService = UserClient.getInstance();
        apiUserService.login(email_password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        JSONObject obj = new JSONObject(response.body().string());
                        User user = new User(
                                obj.getString("id"),
                                obj.getString("name"),
                                obj.getString("phone"),
                                obj.getString("role"),
                                obj.getString("token"),
                                obj.getBoolean("isVerified")
                        );
                        mUser.setValue(user);
                        gotoRecoverPassword.setValue(true);
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setValue(false);
            }
        }, 3000);
    }

    public void logout() {
    }

    public void recoverPassword() {
        gotoRecoverPassword.setValue(true);
    }
}
