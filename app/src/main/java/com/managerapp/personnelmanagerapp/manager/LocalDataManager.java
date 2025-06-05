package com.managerapp.personnelmanagerapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class LocalDataManager {
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_USER_ID = "user_id";
    private final SharedPreferences sharedPrefs;

    @Inject
    public LocalDataManager(@ApplicationContext Context context) {
        this.sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserId(Long userId) {
        sharedPrefs.edit()
                .putLong(KEY_USER_ID, userId)
                .apply();
    }

    public Long getUserId() {
        return sharedPrefs.getLong(KEY_USER_ID, 0);
    }

    public Single<Long> getUserIdAsync() {
        return Single.fromCallable(() -> {
            Long userId = getUserId();
            if (userId == 0) {
                throw new IllegalStateException("User ID is null");
            }
            return userId;
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }
}
