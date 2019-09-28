package com.kobra.launcher.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferences {

    private Context context;

    public UserPreferences(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences("com.kobra.launcher.preferences", MODE_PRIVATE);
    }

    public void clearPreferences() {
        getPreferences().edit().clear().apply();
    }

    public int getLayoutSpan() {
        return getPreferences().getInt("rec_span", 4);
    }

    public void setLayoutSpan(int span) {
        getPreferences().edit().putInt("rec_span", span).apply();
    }


}
