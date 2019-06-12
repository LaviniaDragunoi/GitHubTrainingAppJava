package com.example.githubtrainingappjava.database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.githubtrainingappjava.models.Plan;

public abstract class PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOwner(Plan planEntity);

    @Query("SELECT * FROM plan_table")
    public abstract Plan getOwnerDetails();
}
