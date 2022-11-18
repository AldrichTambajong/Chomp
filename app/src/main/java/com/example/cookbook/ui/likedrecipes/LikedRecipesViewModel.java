package com.example.cookbook.ui.likedrecipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LikedRecipesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public LikedRecipesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is liked recipes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}