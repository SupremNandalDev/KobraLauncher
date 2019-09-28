package com.kobra.launcher.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kobra.launcher.model.AppInfo;

@Database(entities = AppInfo.class, version = 1)
public abstract class AppInfoDB extends RoomDatabase {

    private static final String DATABASE_NAME = "app_info_data";
    private static AppInfoDB instance;

    public static AppInfoDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppInfoDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract AppInfoDao getDataAccessObject();

}
