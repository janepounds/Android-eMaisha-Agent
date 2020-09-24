package com.cabraltech.emaishaagentsapp.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OffersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OffersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("The Offers Cards have been designed.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}