package com.author.toan.viewmodels;

import android.os.Handler;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;
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

public class ForgetPasswordViewModel extends ViewModel {
    private String userId;
    private MutableLiveData<Boolean> loading;
    public MutableLiveData<String> phoneNumber;
    public MutableLiveData<String> otp;
    public MutableLiveData<String> password;
    public MutableLiveData<String> confirmPassword;
    public MutableLiveData<String> error;
    public MutableLiveData<STATE> gotoScreen;
    private APIUserService apiUserService;
    private static ForgetPasswordViewModel instance;

    private ForgetPasswordViewModel() {
        loading = new MutableLiveData<>(false);
        phoneNumber = new MutableLiveData<>("");
        otp = new MutableLiveData<>("");
        password = new MutableLiveData<>("");
        confirmPassword = new MutableLiveData<>("");
        error = new MutableLiveData<>("");
        gotoScreen = new MutableLiveData<>();
        apiUserService = UserClient.getInstance();
    }

    public static ForgetPasswordViewModel getInstance() {
        if (instance == null) {
            instance = new ForgetPasswordViewModel();
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
    public void setError(String mError) {
        error.setValue(mError);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public boolean comparePassword() {
        if (password.getValue().equals(confirmPassword.getValue())) {
            return true;
        }
        error.setValue("Password and Confirm Password must be the same");
        return false;
    }

    public void isValidPhone() {
        loading.setValue(true);
        if (Patterns.PHONE.matcher(phoneNumber.getValue()).matches()) {
            HashMap<String, String> phone_otp = new HashMap<>();
            phone_otp.put("phone", phoneNumber.getValue());
            apiUserService.forgotPassword(phone_otp).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        loading.setValue(false);
                        if (response.code() == 200) {
                            JSONObject obj = new JSONObject(response.body().string());
                            userId = obj.getString("_id");
                            Log.e("Id: ", userId);
                            gotoScreen.setValue(STATE.INPUT_OTP);
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

        } else {
            error.setValue("Invalid phone number");
        }
    }

    public void isValidOTP() {
        loading.setValue(true);
        HashMap<String, String> user_otp = new HashMap<>();
        user_otp.put("userId", userId);
        user_otp.put("token", otp.getValue());

        apiUserService.verifyOTP(user_otp).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    loading.setValue(false);
                    if (response.code() == 200) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean correctOTP = jsonObject.getBoolean("valid");
                        if (correctOTP) {
                            gotoScreen.setValue(STATE.INPUT_PASSWORD);
                        } else {
                            error.setValue("Invalid OTP");
                        }
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        error.setValue(obj.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void recoverPassword() {
        if (comparePassword()) {
            loading.setValue(true);
            HashMap<String, String> user_password = new HashMap<>();
            user_password.put("token", otp.getValue());
            user_password.put("userId", userId);
            user_password.put("newPassword", password.getValue());

            apiUserService.resetPassword(user_password).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        loading.setValue(false);
                        if (response.code() == 200) {
                            JSONObject obj = new JSONObject(response.body().string());
                            String message = obj.getString("message");
                            Log.e("Message: ", message);
                            gotoScreen.setValue(STATE.LOGIN);
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

                }
            });
        }
    }
}
