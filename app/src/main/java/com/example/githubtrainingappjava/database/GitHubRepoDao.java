package com.example.githubtrainingappjava.database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.githubtrainingappjava.models.GitHubRepo;


public abstract class GitHubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOwner(GitHubRepo gitHubRepoEntity);

    @Query("SELECT * FROM gitHubRepo_table")
    public abstract GitHubRepo getOwnerDetails();
}
