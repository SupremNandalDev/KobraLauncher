package com.kobra.launcher.model;

import androidx.annotation.NonNull;

public class AppInfo implements Comparable<AppInfo>{

    private String appTitle;
    private String appPackage;
    private String appVersion;

    public AppInfo(String appTitle, String appPackage, String appVersion) {
        this.appTitle = appTitle;
        this.appPackage = appPackage;
        this.appVersion = appVersion;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public int compareTo(AppInfo info) {
        return this.getAppTitle().compareTo(info.getAppTitle());
    }

    @NonNull
    @Override
    public String toString() {
        return getAppTitle();
    }
}