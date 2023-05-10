package com.author.toan.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

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
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setMainViewModel(mainViewModel);

        mainViewModel.getGotoLoginScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gotoLoginScreen) {
                if (gotoLoginScreen) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}