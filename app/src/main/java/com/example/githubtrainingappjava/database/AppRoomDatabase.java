package com.example.githubtrainingappjava.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.githubtrainingappjava.models.GitHubRepo;
import com.example.githubtrainingappjava.models.Owner;
import com.example.githubtrainingappjava.models.Plan;

@Database(entities = {GitHubRepo.class, Owner.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "github.table.db";
    private static final Object LOCK = new Object();
    private static Builder<AppRoomDatabase> sInstance;


    public static AppRoomDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppRoomDatabase.class, AppRoomDatabase.DATABASE_NAME);
            }
        }
        return sInstance.build();
    }

    public abstract OwnerDao ownerDao();


}
