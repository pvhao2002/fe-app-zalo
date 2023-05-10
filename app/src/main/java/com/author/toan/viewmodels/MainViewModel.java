package com.author.toan.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> gotoLoginScreen;
    public MutableLiveData<Boolean> getGotoLoginScreen() {
        if (gotoLoginScreen == null) {
            gotoLoginScreen = new MutableLiveData<>();
        }
        return gotoLoginScreen;
    }

    public void login() {
        gotoLoginScreen.setValue(true);
    }

    public void register() {
        gotoLoginScreen.setValue(false);
    }
}
