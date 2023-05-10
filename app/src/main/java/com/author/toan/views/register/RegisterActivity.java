package com.author.toan.views.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityRegisterBinding;
import com.author.toan.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        RegisterViewModel registerViewModel = RegisterViewModel.getInstance();
        activityRegisterBinding.setLifecycleOwner(this);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);

        registerViewModel.getGotoNextStep().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE STATE) {
                if (STATE == STATE.INPUT_PHONE_NUMBER) {
                    Intent intent = new Intent(RegisterActivity.this, InputPhoneNumberActivity.class);
                    startActivity(intent);
                }
            }
        });
        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activityRegisterBinding.tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}