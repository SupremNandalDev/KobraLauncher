package com.kobra.launcher.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "apps_info")
public class AppInfo implements Comparable<AppInfo> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String appTitle;
    private String appPackage;
    private String iconTitle;
    private boolean hidden;

    public AppInfo(String appTitle, String appPackage, String iconTitle, boolean hidden) {
        this.appTitle = appTitle;
        this.appPackage = appPackage;
        this.iconTitle = iconTitle;
        this.hidden = hidden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getIconTitle() {
        return iconTitle;
    }

    public void setIconTitle(String iconTitle) {
        this.iconTitle = iconTitle;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int compareTo(AppInfo info) {
        return this.getIconTitle().compareTo(info.getIconTitle());
    }

    @NonNull
    @Override
    public String toString() {
        return getIconTitle();
    }
}