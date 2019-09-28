package com.kobra.launcher.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kobra.launcher.model.AppInfo;

import java.util.List;

@Dao
public interface AppInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppInfo(AppInfo appInfo);

    @Query("SELECT * FROM apps_info")
    List<AppInfo> getAll();

}
