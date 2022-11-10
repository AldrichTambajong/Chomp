package com.example.cookbook.ui.cookbooks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CookbooksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CookbooksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Cookbooks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}