package com.author.toan.viewmodels;

import android.os.Handler;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;
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

public class RegisterViewModel extends ViewModel {
    private String userId;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>("");
    public MutableLiveData<String> otp = new MutableLiveData<>("");
    public MutableLiveData<STATE> gotoNextStep;
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>("");
    public MutableLiveData<String> error = new MutableLiveData<>("");
    private APIUserService apiUserService = UserClient.getInstance();
    private static RegisterViewModel instance;

    private RegisterViewModel() {
    }

    public static RegisterViewModel getInstance() {
        if (instance == null) {
            instance = new RegisterViewModel();
        }
        return instance;
    }

    public MutableLiveData<STATE> getGotoNextStep() {
        if (gotoNextStep == null) {
            gotoNextStep = new MutableLiveData<>(STATE.INIT);
        }
        return gotoNextStep;
    }

    public MutableLiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<>(false);
        }
        return loading;
    }

    public void verifyPhone() {
        loading.setValue(true);
        HashMap<String, String> user_otp = new HashMap<>();
        Log.e("UserId", userId);
        Log.e("OTP", otp.getValue());
        user_otp.put("userId", userId);
        user_otp.put("OTP", otp.getValue());

        apiUserService.verifyPhone(user_otp).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        JSONObject obj = new JSONObject(response.body().string());
                        JSONObject userJson = obj.getJSONObject("user");
                        User user = new User(
                                userJson.getString("id"),
                                userJson.getString("name"),
                                userJson.getString("phone"),
                                userJson.getString("role"),
                                userJson.getString("token"),
                                userJson.getBoolean("isVerified")
                        );
                        gotoNextStep.setValue(STATE.CHAT);
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        error.setValue(obj.getString("error"));
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
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

    public boolean comparePassword() {
        if (password.getValue().equals(confirmPassword.getValue())) {
            return true;
        }
        error.setValue("Password and Confirm Password must be the same");
        return false;
    }

    public void isValidName() {
        if (name.getValue().length() < 2) {
            error.setValue("Name must be at least 2 characters");
        } else {
            gotoNextStep.setValue(STATE.INPUT_PHONE_NUMBER);
        }
    }

    public void isValidPhone() {
        if (Patterns.PHONE.matcher(phoneNumber.getValue()).matches()) {
            gotoNextStep.setValue(STATE.INPUT_PASSWORD);
        } else {
            error.setValue("Invalid phone number");
        }
    }

    public void createUser() {
        if (comparePassword()) {
            loading.setValue(true);
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name.getValue());
            map.put("phone", phoneNumber.getValue());
            map.put("password", password.getValue());

            apiUserService.signup(map).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 201) {
                            JSONObject obj = new JSONObject(response.body().string());
                            JSONObject userJson = obj.getJSONObject("user");
                            userId = userJson.getString("id");
                            User user = new User(
                                    userId,
                                    userJson.getString("name"),
                                    userJson.getString("phone")

                            );
                            gotoNextStep.setValue(STATE.INPUT_OTP);
                        } else {
                            JSONObject obj = new JSONObject(response.errorBody().string());
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
    }
}
