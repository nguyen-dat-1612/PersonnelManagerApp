package com.managerapp.personnelmanagerapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SettingsManager {

    private static final String PREF_NAME = "app_settings";
    private final SharedPreferences prefs;

    @Inject
    public SettingsManager(@ApplicationContext Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isDarkModeEnabled() {
        return prefs.getBoolean("dark_mode", false);
    }

    public void setDarkModeEnabled(boolean isEnabled) {
        prefs.edit().putBoolean("dark_mode", isEnabled).apply();
    }

    public boolean isNotificationEnabled() {
        return prefs.getBoolean("notifications", true);
    }

    public void setNotificationEnabled(boolean isEnabled) {
        prefs.edit().putBoolean("notifications", isEnabled).apply();
    }

    public String getSelectedLanguage() {
        return prefs.getString("language", "vi");
    }

    public void setSelectedLanguage(String languageCode) {
        prefs.edit().putString("language", languageCode).apply();
    }
}
