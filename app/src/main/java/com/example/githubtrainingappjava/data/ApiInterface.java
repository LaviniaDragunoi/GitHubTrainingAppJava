package com.example.githubtrainingappjava.data;

import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterface {

    @GET("user")
    Call<Owner> getOwner(@Header("Authorization") String authHeader);

    @GET("user/repos")
    Call<List<GitHubRepo>> getRepos(@Header("Authorization") String authHeader);
}
