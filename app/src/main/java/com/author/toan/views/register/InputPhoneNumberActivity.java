package com.author.toan.views.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityInputPhoneNumberBinding;
import com.author.toan.viewmodels.RegisterViewModel;

public class InputPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputPhoneNumberBinding activityInputPhoneNumberBinding = DataBindingUtil.setContentView(this, R.layout.activity_input_phone_number);
        RegisterViewModel registerViewModel = RegisterViewModel.getInstance();
        activityInputPhoneNumberBinding.setLifecycleOwner(this);
        activityInputPhoneNumberBinding.setRegisterViewModel(registerViewModel);

        registerViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.INPUT_PASSWORD && activityInputPhoneNumberBinding.checkBox.isChecked()) {
                    startActivity(new Intent(getApplicationContext(), InputPasswordActivity.class));
                    registerViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });

        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.isEmpty()) {
                    activityInputPhoneNumberBinding.tvError.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}