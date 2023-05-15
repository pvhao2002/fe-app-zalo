package com.author.toan.views.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityInputOtpBinding;
import com.author.toan.viewmodels.RegisterViewModel;
import com.author.toan.views.login.LoginActivity;
import com.author.toan.views.main.MainActivity;

public class InputOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputOtpBinding activityInputOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_input_otp);
        RegisterViewModel registerViewModel = RegisterViewModel.getInstance();
        activityInputOtpBinding.setLifecycleOwner(this);
        activityInputOtpBinding.setRegisterViewModel(registerViewModel);

        activityInputOtpBinding.tvPhone.setText(registerViewModel.phoneNumber.getValue());
        registerViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.LOGIN) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    registerViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });

        registerViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    activityInputOtpBinding.progressBar.setVisibility(View.VISIBLE);
                    activityInputOtpBinding.btnRegister.setVisibility(View.GONE);
                } else {
                    activityInputOtpBinding.btnRegister.setVisibility(View.VISIBLE);
                    activityInputOtpBinding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}