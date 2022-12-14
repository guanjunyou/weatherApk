package com.example.pro2.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public String city; // 城市名称

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }
    public LiveData<String> getText() {
        return mText;
    }
}