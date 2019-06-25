package com.example.githubtrainingappjava.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

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

    public LiveData<List<GitHubRepo>> getReposByCreatedDate() {
        return mRepository.getCreateRepos();
    }

    public Owner getOwner(){
    return mRepository.getOwnerDb();
    }

    public LiveData<List<GitHubRepo>> getReposByUpdatedDate() {
        return mRepository.getUpdateRepos();
    }

    public LiveData<List<GitHubRepo>> getReposByPushedDate() {
    return mRepository.getReposPushedDate();
    }

    public LiveData<List<GitHubRepo>> getReposByName() {
        return mRepository.getSortedByName();
    }

    public LiveData<List<GitHubRepo>> getOwnedRepos() {
    return mRepository.getPersonalRepos();
    }
}
