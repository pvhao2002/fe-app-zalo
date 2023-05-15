package com.author.toan.views.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityInputOtp2Binding;
import com.author.toan.viewmodels.ForgetPasswordViewModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class InputOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputOtp2Binding activityInputOtp2Binding = DataBindingUtil.setContentView(this, R.layout.activity_input_otp2);
        ForgetPasswordViewModel forgetPasswordViewModel = ForgetPasswordViewModel.getInstance();
        activityInputOtp2Binding.setLifecycleOwner(this);
        activityInputOtp2Binding.setForgetPasswordViewModel(forgetPasswordViewModel);

        activityInputOtp2Binding.tvPhone.setText(forgetPasswordViewModel.phoneNumber.getValue());
        forgetPasswordViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.INPUT_PASSWORD) {
                    startActivity(new Intent(getApplicationContext(), InputPasswordActivity.class));
                    forgetPasswordViewModel.setGotoScreen(STATE.MAIN);
                    forgetPasswordViewModel.setError("");
                }
            }
        });

        forgetPasswordViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    activityInputOtp2Binding.progressBar.setVisibility(View.VISIBLE);
                    activityInputOtp2Binding.btnContinue.setVisibility(View.GONE);
                } else {
                    activityInputOtp2Binding.btnContinue.setVisibility(View.VISIBLE);
                    activityInputOtp2Binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}