package com.example.githubtrainingappjava.ViewModel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.githubtrainingappjava.Repository;
import com.example.githubtrainingappjava.models.Owner;

public class OwnerViewModel extends ViewModel {

private LiveData<Owner> ownerLiveData;
private String mAuth;
private Repository mRepository;

public OwnerViewModel (Repository repository, String auth){
    mRepository = repository;
    mAuth = auth;


}

    public LiveData<Owner> getOwnerLiveData() {
        return mRepository.getOwnerLiveData(mAuth);
    }
}
