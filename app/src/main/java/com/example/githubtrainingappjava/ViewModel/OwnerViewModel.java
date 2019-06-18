package com.example.githubtrainingappjava.ViewModel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.githubtrainingappjava.GithubDao;
import com.example.githubtrainingappjava.Repository;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.List;

public class OwnerViewModel extends ViewModel {

private LiveData<Owner> ownerLiveData;
private LiveData<List<GitHubRepo>> reposListLiveData;
private String mAuth;
private Repository mRepository;

public OwnerViewModel (Repository repository, String auth){
    mRepository = repository;
    mAuth = auth;
    ownerLiveData = mRepository.getRetrofitOwnerResult(mAuth);
    reposListLiveData = mRepository.getRetrofitReposResult(mAuth);

}

    public LiveData<Owner> getOwnerLiveData() {
        return mRepository.getOwnerFromDb();
    }

    public LiveData<List<GitHubRepo>> getReposLiveData() {
        return mRepository.getReposFromDb();
    }

    public void deleteDatabase(){
    mRepository.deleteOwnerFromDb();
    mRepository.deleteRepoFromDb();
    }
}
