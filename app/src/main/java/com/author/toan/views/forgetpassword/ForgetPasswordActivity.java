package com.author.toan.views.forgetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityForgetPasswordBinding;
import com.author.toan.databinding.ActivityInputOtp2Binding;
import com.author.toan.viewmodels.ForgetPasswordViewModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgetPasswordBinding activityForgetPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        ForgetPasswordViewModel forgetPasswordViewModel = ForgetPasswordViewModel.getInstance();
        activityForgetPasswordBinding.setLifecycleOwner(this);
        activityForgetPasswordBinding.setForgetPasswordViewModel(forgetPasswordViewModel);

        forgetPasswordViewModel.getGotoNextStep().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE STATE) {
                if (STATE == STATE.INPUT_OTP) {

                }
            }
        });
    }
}