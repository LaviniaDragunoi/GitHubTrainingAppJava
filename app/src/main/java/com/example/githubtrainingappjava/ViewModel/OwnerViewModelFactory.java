package com.example.githubtrainingappjava.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.support.annotation.NonNull;

import com.example.githubtrainingappjava.Repository;

public class OwnerViewModelFactory extends ViewModelProvider.NewInstanceFactory {
private Repository mRepository;
private  String mAuth;

public OwnerViewModelFactory(Repository repository, String auth){
    mRepository = repository;
    mAuth = auth;
}
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new OwnerViewModel(mRepository, mAuth);
    }
}
