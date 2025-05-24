package com.managerapp.personnelmanagerapp.utils.manager;

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

// LocalDataManager.java
@Singleton
public class LocalDataManager {
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String USER_DATA_FILE_NAME = "user_data.json"; // File để lưu thông tin người dùng

    private final SharedPreferences sharedPrefs;
    private final Gson gson;
    private final Context context;

    @Inject
    public LocalDataManager(@ApplicationContext Context context) {
        this.sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
        this.context = context;
    }

    // Lưu userId vào SharedPreferences
    public void saveUserId(Long userId) {
        sharedPrefs.edit()
                .putLong(KEY_USER_ID, userId)
                .apply();
    }

    // Lấy userId từ SharedPreferences
    public Long getUserId() {
        return sharedPrefs.getLong(KEY_USER_ID, 0);
    }

    public void saveUserInfo(UserProfileResponse user) {
        String userJson = gson.toJson(user);
        FileOutputStream outputStream;
        try {
            File userFile = new File(context.getFilesDir(), USER_DATA_FILE_NAME);
            outputStream = new FileOutputStream(userFile);
            outputStream.write(userJson.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lấy thông tin người dùng từ file (user_data.json)
    public UserProfileResponse getUserInfo() {
        FileInputStream inputStream;
        try {
            File userFile = new File(context.getFilesDir(), USER_DATA_FILE_NAME);
            inputStream = new FileInputStream(userFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String userJson = new String(buffer);
            return gson.fromJson(userJson, UserProfileResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Single<Long> getUserIdAsync() {
        return Single.fromCallable(() -> {
            Long userId = getUserId(); // Gọi hàm cũ
            if (userId == 0) {
                throw new IllegalStateException("User ID is null");
            }
            return userId;
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }


    // Xóa thông tin người dùng và userId
    public void clearUserData() {
        sharedPrefs.edit()
                .remove(KEY_USER_ID)
                .apply();

        File userFile = new File(context.getFilesDir(), USER_DATA_FILE_NAME);
        if (userFile.exists()) {
            userFile.delete();
        }
    }
}
