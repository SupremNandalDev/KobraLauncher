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

    @Query("DELETE FROM apps_info")
    void removeAppsData();

    @Query("SELECT * FROM apps_info")
    List<AppInfo> getAll();

    @Query("SELECT * FROM apps_info WHERE hidden=0")
    List<AppInfo> getAllVisible();

    @Query("SELECT * FROM apps_info WHERE hidden=1")
    List<AppInfo> getAllHidden();

    @Query("SELECT hidden FROM apps_info WHERE appPackage=:packageName")
    boolean isAppHidden(String packageName);

    @Query("UPDATE apps_info SET hidden=:b WHERE appPackage=:packageName")
    void updateHideStatus(String packageName, boolean b);

    @Query("SELECT * FROM apps_info WHERE appPackage=:packageName")
    AppInfo getAppInfoByPackage(String packageName);

    @Query("DELETE FROM apps_info WHERE appPackage=:packageName")
    void removeApp(String packageName);

}
