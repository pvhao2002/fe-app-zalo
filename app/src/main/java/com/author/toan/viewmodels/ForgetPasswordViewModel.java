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
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>("");
    public MutableLiveData<String> otp = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>("");
    public MutableLiveData<String> error = new MutableLiveData<>("");
    public MutableLiveData<STATE> gotoNextStep;
    private APIUserService apiUserService = UserClient.getInstance();
    private static ForgetPasswordViewModel instance;

    private ForgetPasswordViewModel() {
    }

    public static ForgetPasswordViewModel getInstance() {
        if (instance == null) {
            instance = new ForgetPasswordViewModel();
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
            gotoNextStep.setValue(STATE.INPUT_OTP);
            HashMap<String, String> phone_otp = new HashMap<>();
            phone_otp.put("phone", phoneNumber.getValue());
            apiUserService.forgotPassword(phone_otp).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                           if (response.code() == 200) {
                               JSONObject obj = new JSONObject(response.body().string());
                               userId = obj.getString("id");
                               Log.e("Id: ", userId);

                           }
                           else {
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
            } );

        } else {
            error.setValue("Invalid phone number");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setValue(false);
            }
        }, 3000);
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
                    if (response.code() == 200) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean correctOTP = jsonObject.getBoolean("valid");
                        if (correctOTP) {
                            gotoNextStep.setValue(STATE.INPUT_PASSWORD);
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setValue(false);
            }
        }, 3000);
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
                            if (response.code() == 200) {
                                JSONObject obj = new JSONObject(response.body().string());
                                String message = obj.getString("message");
                                Log.e("Message: ", message);
                                gotoNextStep.setValue(STATE.LOGIN);
                            }
                            else {
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setValue(false);
            }
        }, 3000);
    }
}
