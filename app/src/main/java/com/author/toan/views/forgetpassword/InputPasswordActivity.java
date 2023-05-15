package com.author.toan.views.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityInputPassword2Binding;
import com.author.toan.databinding.ActivityInputPasswordBinding;
import com.author.toan.viewmodels.ForgetPasswordViewModel;
import com.author.toan.viewmodels.RegisterViewModel;
import com.author.toan.views.login.LoginActivity;
import com.author.toan.views.main.MainActivity;
import com.author.toan.views.register.RegisterActivity;

public class InputPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputPassword2Binding activityInputPassword2Binding = DataBindingUtil.setContentView(this, R.layout.activity_input_password2);
        ForgetPasswordViewModel forgetPasswordViewModel = ForgetPasswordViewModel.getInstance();
        activityInputPassword2Binding.setLifecycleOwner(this);
        activityInputPassword2Binding.setForgetPasswordViewModel(forgetPasswordViewModel);

        forgetPasswordViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.LOGIN) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    forgetPasswordViewModel.setGotoScreen(STATE.MAIN);
                    forgetPasswordViewModel.setError("");
                }
            }
        });

        forgetPasswordViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.isEmpty()) {
                    activityInputPassword2Binding.tvError.setVisibility(View.VISIBLE);
                }
            }
        });

        forgetPasswordViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    activityInputPassword2Binding.tvError.setVisibility(View.GONE);
                    activityInputPassword2Binding.progressBar.setVisibility(View.VISIBLE);
                    activityInputPassword2Binding.btnContinue.setVisibility(View.GONE);
                } else {
                    activityInputPassword2Binding.btnContinue.setVisibility(View.VISIBLE);
                    activityInputPassword2Binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}