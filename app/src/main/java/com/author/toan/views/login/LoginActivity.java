package com.author.toan.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.author.toan.R;
import com.author.toan.databinding.ActivityLoginBinding;
import com.author.toan.viewmodels.ForgetPasswordViewModel;
import com.author.toan.viewmodels.LoginViewModel;
import com.author.toan.views.forgetpassword.ForgetPasswordActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        activityLoginBinding.setLifecycleOwner(this);
        activityLoginBinding.setLoginViewModel(loginViewModel);

        loginViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    activityLoginBinding.progressBar.setVisibility(View.VISIBLE);
                    activityLoginBinding.btnLogin.setVisibility(View.GONE);
                } else {
                    activityLoginBinding.btnLogin.setVisibility(View.VISIBLE);
                    activityLoginBinding.progressBar.setVisibility(View.GONE);
                }
            }
        });

        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activityLoginBinding.tvError.setVisibility(View.VISIBLE);
            }
        });

        loginViewModel.getGotoRecoverPassword().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gotoScreen) {
                if (gotoScreen) {
                    Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                    startActivity(intent);
                }

            }
        });


    }
}