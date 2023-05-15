package com.author.toan.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.author.toan.STATE;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> gotoLoginScreen;
    private static MainViewModel instance;

    public static MainViewModel getInstance() {
        if (instance == null) {
            instance = new MainViewModel();
        }
        return instance;
    }

    private MainViewModel() {
        gotoLoginScreen = new MutableLiveData<>();
    }
    public MutableLiveData<Boolean> getGotoLoginScreen() {
        return gotoLoginScreen;
    }

    public void login() {
        gotoLoginScreen.setValue(true);
    }

    public void register() {
        gotoLoginScreen.setValue(false);
    }
}
