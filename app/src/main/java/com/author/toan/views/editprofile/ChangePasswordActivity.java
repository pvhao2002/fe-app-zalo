package com.author.toan.views.editprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityChangePasswordBinding;
import com.author.toan.databinding.ActivityForgetPasswordBinding;
import com.author.toan.viewmodels.EditAccountViewModel;
import com.author.toan.viewmodels.ForgetPasswordViewModel;
import com.author.toan.views.chat.ChatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChangePasswordBinding changePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        EditAccountViewModel editAccountViewModel = EditAccountViewModel.getInstance();
        changePasswordBinding.setLifecycleOwner(this);
        changePasswordBinding.setEditAccountViewModel(editAccountViewModel);

        editAccountViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.CHAT) {
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    editAccountViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });

        editAccountViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.isEmpty()) {
                    changePasswordBinding.tvError.setVisibility(View.VISIBLE);
                }
            }
        });

        editAccountViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    changePasswordBinding.progressBar.setVisibility(View.VISIBLE);
                    changePasswordBinding.btnUpdate.setVisibility(View.GONE);
                } else {
                    changePasswordBinding.btnUpdate.setVisibility(View.VISIBLE);
                    changePasswordBinding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}