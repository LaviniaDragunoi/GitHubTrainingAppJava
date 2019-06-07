package com.example.githubtrainingappjava.data;

import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("user")
    Call<Owner> getOwner(@Header("Authorization") String authHeader);

    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> listRepos(@Header("Authorization") String authHeader);
}
