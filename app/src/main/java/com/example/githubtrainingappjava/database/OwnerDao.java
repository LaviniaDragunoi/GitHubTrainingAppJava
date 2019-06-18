package com.example.githubtrainingappjava.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;

import java.util.List;

@Dao
public abstract class OwnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOwner(Owner ownerEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRepoList(List<GitHubRepo> repoList);

    @Query("SELECT * FROM owner_table")
    public abstract LiveData<Owner> getOwnerDetails();

    @Query("SELECT * FROM gitHubRepo_table")
    public abstract LiveData<List<GitHubRepo>> getRepos();

    @Query("DELETE FROM owner_table")
    public abstract void deleteOwner();

    @Query("DELETE FROM gitHubRepo_table")
    public abstract void deleteRepos();


}
