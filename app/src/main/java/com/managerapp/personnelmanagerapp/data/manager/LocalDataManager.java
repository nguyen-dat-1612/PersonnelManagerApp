package com.managerapp.personnelmanagerapp.data.manager;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

// LocalDataManager.java
@Singleton
public class LocalDataManager {
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_USER_ID = "user_id";

    private final SharedPreferences sharedPrefs;

    @Inject
    public LocalDataManager(@ApplicationContext Context context) {
        this.sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserId(String userId) {
        sharedPrefs.edit()
                .putString(KEY_USER_ID, userId)
                .apply();
    }

    public String getUserId() {
        return sharedPrefs.getString(KEY_USER_ID, null);
    }

    public void clearUserId() {
        sharedPrefs.edit()
                .remove(KEY_USER_ID)
                .apply();
    }
}