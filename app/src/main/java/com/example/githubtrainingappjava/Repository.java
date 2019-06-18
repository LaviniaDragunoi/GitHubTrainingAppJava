package com.example.githubtrainingappjava;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.githubtrainingappjava.data.ApiInterface;
import com.example.githubtrainingappjava.database.AppRoomDatabase;
import com.example.githubtrainingappjava.database.OwnerDao;
import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static final Object LOCK = new Object();
    private static Repository sInstance;
    private OwnerDao mOwnerDao;
    private LiveData<Owner> ownerLiveData;
    private ApiInterface mApiInterface;
    private AppExecutors mAppExecutors;
    private AppRoomDatabase mAppRoomDatabase;

    public Repository(AppExecutors appExecutors, AppRoomDatabase appRoomDatabase, OwnerDao ownerDao,
                      ApiInterface apiInterface){
        mAppExecutors = appExecutors;
        mAppRoomDatabase = appRoomDatabase;
        mOwnerDao = ownerDao;
        mApiInterface = apiInterface;

    }

    public synchronized static Repository getsInstance(AppExecutors appExecutors,
                                                          AppRoomDatabase appRoomDatabase, OwnerDao ownerDao,
                                                          ApiInterface apiInterface) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository(appExecutors, appRoomDatabase, ownerDao,apiInterface);
            }
        }
        return sInstance;
    }



    public LiveData<Owner> getRetrofitOwnerResult(String auth){
        final MutableLiveData<Owner> ownerResponse = new MutableLiveData<>();
        mApiInterface.getOwner(auth).enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.isSuccessful()) {

                    ownerResponse.setValue(response.body());
                    addOwnerToDB(response.body());
                } else {
                    ownerResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {

                ownerResponse.setValue(null);
            }
        });

        return ownerResponse;
    }

    public LiveData<List<GitHubRepo>> getRetrofitReposResult(String auth){
        MutableLiveData<List<GitHubRepo>> reposResponse = new MutableLiveData<>();
        mApiInterface.getRepos(auth).enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                if (response.isSuccessful()) {
                    reposResponse.setValue(response.body());
                    addReposToDb(response.body());
                } else {
                    reposResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                reposResponse.setValue(null);
            }
        });

        return reposResponse;
    }

    private void addOwnerToDB(Owner ownerLiveData){
        if(ownerLiveData != null){

            mAppExecutors.networkIO().execute(() ->{
                deleteOwnerFromDb();
                mOwnerDao.insertOwner(ownerLiveData);

            });
        }
    }

private void addReposToDb(List<GitHubRepo> repoList){
        if(repoList != null){
            mAppExecutors.networkIO().execute(() ->{
                deleteRepoFromDb();
                mOwnerDao.insertRepoList(repoList);

            });
        }
}
    //Clean up the database owner_table
    public void deleteOwnerFromDb() {
        mOwnerDao.deleteOwner();
    }

    //Clean up the database github_table
    public void deleteRepoFromDb() {
        mOwnerDao.deleteRepos();
    }

    public LiveData<Owner> getOwnerFromDb(){
        return mOwnerDao.getOwnerDetails();
    }

    public LiveData<List<GitHubRepo>> getReposFromDb(){
        return mOwnerDao.getRepos();
    }
}
