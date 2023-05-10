package com.author.toan.views.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
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

public class InputPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInputPassword2Binding activityInputPassword2Binding = DataBindingUtil.setContentView(this, R.layout.activity_input_password2);
        ForgetPasswordViewModel forgetPasswordViewModel = ForgetPasswordViewModel.getInstance();
        activityInputPassword2Binding.setLifecycleOwner(this);
        activityInputPassword2Binding.setForgetPasswordViewModel(forgetPasswordViewModel);

        forgetPasswordViewModel.getGotoNextStep().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE STATE) {
                if (STATE == STATE.LOGIN) {
                    Intent intent = new Intent(InputPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        forgetPasswordViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activityInputPassword2Binding.tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}