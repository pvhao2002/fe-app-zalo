package com.author.toan.viewmodels;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;
import com.author.toan.models.User;
import com.author.toan.remote.SharedPrefManager;
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

public class EditAccountViewModel extends ViewModel {
    private MutableLiveData<Boolean> loading;
    public MutableLiveData<String> currentPassword;
    public MutableLiveData<String> newPassword;
    public MutableLiveData<String> confirmNewPassword;
    public MutableLiveData<String> error;
    public MutableLiveData<STATE> gotoScreen;
    private APIUserService apiUserService;
    private static EditAccountViewModel instance;

    private EditAccountViewModel() {
        loading = new MutableLiveData<>(false);
        currentPassword = new MutableLiveData<>("");
        newPassword = new MutableLiveData<>("");
        confirmNewPassword = new MutableLiveData<>("");
        error = new MutableLiveData<>("");
        apiUserService = UserClient.getInstance();
        gotoScreen = new MutableLiveData<>();
    }

    public static EditAccountViewModel getInstance() {
        if (instance == null) {
            instance = new EditAccountViewModel();
        }
        return instance;
    }

    public MutableLiveData<STATE> getGotoScreen() {
        return gotoScreen;
    }
    public void setGotoScreen(STATE state) {
        gotoScreen.setValue(state);
    }
    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public boolean comparePassword() {
        if (newPassword.getValue().equals(confirmNewPassword.getValue())) {
            return true;
        }
        error.setValue("New password and confirm new password must be the same");
        return false;
    }

    public void changePassword(){
        gotoScreen.setValue(STATE.CHANGE_PASSWORD);
    }

    public void viewProfile() {
        gotoScreen.setValue(STATE.VIEW_PROFILE);
    }
    public void viewPrivacy() {
        gotoScreen.setValue(STATE.VIEW_PRIVACY);
    }

    public void editProfile() {
        gotoScreen.setValue(STATE.EDIT_PROFILE);
    }

    public void deleteAccount() {}

    public void update() {
        String token = SharedPrefManager.getUser().getToken();
        if (comparePassword()) {
            loading.setValue(true);
            HashMap<String, String> user_password = new HashMap<>();
            user_password.put("currentPassword", currentPassword.getValue());
            user_password.put("newPassword", newPassword.getValue());

            apiUserService.changePassword(user_password, "Bearer " + token).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        loading.setValue(false);
                        if (response.code() == 200) {
                            JSONObject obj = new JSONObject(response.body().string());
                            String message = obj.getString("message");
                            Log.e("Message: ", message);
                            gotoScreen.setValue(STATE.CHAT);
                        } else {
                            JSONObject obj = new JSONObject(response.errorBody().string());
                            Log.e("Error-", obj.getString("error"));
                            error.setValue(obj.getString("error"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Error: ", t.getMessage());
                }
            });
        }
    }

}
