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

        registerViewModel.getGotoNextStep().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE STATE) {
                if (STATE == STATE.INPUT_OTP ) {
                    Intent intent = new Intent(InputPasswordActivity.this, InputOTPActivity.class);
                    startActivity(intent);
                }
            }
        });

        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activityInputPasswordBinding.tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}