package com.author.toan.views.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityInputPasswordBinding;
import com.author.toan.viewmodels.RegisterViewModel;

public class InputPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputPasswordBinding activityInputPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_input_password);
        RegisterViewModel registerViewModel = RegisterViewModel.getInstance();
        activityInputPasswordBinding.setLifecycleOwner(this);
        activityInputPasswordBinding.setRegisterViewModel(registerViewModel);

        registerViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.INPUT_OTP ) {
                    startActivity(new Intent(getApplicationContext(), InputOTPActivity.class));
                    registerViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });

        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.isEmpty()) {
                    activityInputPasswordBinding.tvError.setVisibility(View.VISIBLE);
                }
            }
        });

        registerViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    activityInputPasswordBinding.progressBar.setVisibility(View.VISIBLE);
                    activityInputPasswordBinding.btnContinue.setVisibility(View.GONE);
                } else {
                    activityInputPasswordBinding.btnContinue.setVisibility(View.VISIBLE);
                    activityInputPasswordBinding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}