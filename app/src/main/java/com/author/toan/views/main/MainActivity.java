package com.author.toan.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.author.toan.R;
import com.author.toan.databinding.ActivityMainBinding;
import com.author.toan.viewmodels.MainViewModel;
import com.author.toan.views.login.LoginActivity;
import com.author.toan.views.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = MainViewModel.getInstance();
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setMainViewModel(mainViewModel);

        mainViewModel.getGotoLoginScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gotoLoginScreen) {
                Log.e("gotoLoginScreen", gotoLoginScreen.toString());
                if (gotoLoginScreen) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            }
        });
    }
}