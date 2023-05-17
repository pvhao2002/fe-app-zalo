package com.author.toan.views.editprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityEditAccountBinding;
import com.author.toan.viewmodels.EditAccountViewModel;
import com.author.toan.views.login.LoginActivity;

public class EditAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditAccountBinding activityEditAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_account);
        EditAccountViewModel editAccountViewModel = EditAccountViewModel.getInstance();
        activityEditAccountBinding.setLifecycleOwner(this);
        activityEditAccountBinding.setEditAccountViewModel(editAccountViewModel);

        editAccountViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.CHANGE_PASSWORD) {
                    startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                    editAccountViewModel.setGotoScreen(STATE.MAIN);
                }
                else if (state == STATE.LOGIN) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    editAccountViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });
    }
}