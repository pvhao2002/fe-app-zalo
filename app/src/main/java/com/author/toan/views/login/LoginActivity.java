package com.author.toan.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityLoginBinding;
import com.author.toan.models.User;
import com.author.toan.remote.SharedPrefManager;
import com.author.toan.viewmodels.LoginViewModel;
import com.author.toan.views.chat.ChatActivity;
import com.author.toan.views.forgetpassword.ForgetPasswordActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = LoginViewModel.getInstance();
        activityLoginBinding.setLifecycleOwner(this);
        activityLoginBinding.setLoginViewModel(loginViewModel);

        loginViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                Log.e("STATE - login: ", state.toString());
                if (state == STATE.FORGOT_PASSWORD) {
                    startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                    loginViewModel.setGotoScreen(STATE.MAIN);
                }
                else if (state == STATE.CHAT){
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    loginViewModel.setGotoScreen(STATE.MAIN);
                    finish();
                }
            }
        });

        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.isEmpty()) {
                    activityLoginBinding.tvError.setVisibility(View.VISIBLE);
                }
            }
        });
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

        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user.getVerified()){
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                }
            }
        }) ;
    }
}