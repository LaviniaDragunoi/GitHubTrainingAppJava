package com.example.githubtrainingappjava.database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.githubtrainingappjava.models.Owner;

public abstract class OwnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOwner(Owner ownerEntity);

    @Query("SELECT * FROM owner_table")
    public abstract Owner getOwnerDetails();

}
