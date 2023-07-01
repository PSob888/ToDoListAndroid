package com.example.todolist.CategoryPackage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryViewModelFactory implements ViewModelProvider.Factory{
    private final Application application;
    public CategoryViewModelFactory(Application myApplication) {
        application = myApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CategoryViewModel(application);
    }
}
