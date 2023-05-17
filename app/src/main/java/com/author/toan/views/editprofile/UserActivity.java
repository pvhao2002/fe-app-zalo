package com.author.toan.views.editprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import com.author.toan.R;
import com.author.toan.STATE;
import com.author.toan.databinding.ActivityUserBinding;
import com.author.toan.remote.SharedPrefManager;
import com.author.toan.viewmodels.EditAccountViewModel;
import com.author.toan.views.login.LoginActivity;
import com.bumptech.glide.Glide;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserBinding activityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        EditAccountViewModel editAccountViewModel = EditAccountViewModel.getInstance();
        activityUserBinding.setLifecycleOwner(this);
        activityUserBinding.setEditAccountViewModel(editAccountViewModel);

        activityUserBinding.tvName.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
        Glide.with(getApplicationContext())
                .load(SharedPrefManager.getInstance(getApplicationContext()).getImageUser())
                .circleCrop()
                .into(activityUserBinding.ivAvatar);
        editAccountViewModel.getGotoScreen().observe(this, new Observer<STATE>() {
            @Override
            public void onChanged(STATE state) {
                if (state == STATE.VIEW_PROFILE) {
                    startActivity(new Intent(getApplicationContext(), EditAccountActivity.class));
                    editAccountViewModel.setGotoScreen(STATE.MAIN);
                }
                else if (state == STATE.EDIT_PROFILE) {
                    startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                    editAccountViewModel.setGotoScreen(STATE.MAIN);
                }
                else if (state == STATE.LOGIN) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    editAccountViewModel.setGotoScreen(STATE.MAIN);
                }
            }
        });
    }
}