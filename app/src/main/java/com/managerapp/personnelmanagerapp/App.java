package com.managerapp.personnelmanagerapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.managerapp.personnelmanagerapp.manager.SettingsManager;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.components.SingletonComponent;

@HiltAndroidApp
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SettingsManager settingsManager = EntryPointAccessors.fromApplication(this, SettingsManagerEntryPoint.class).getSettingsManager();
        String languageTag = settingsManager.getSelectedLanguage();
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(languageTag);
        AppCompatDelegate.setApplicationLocales(appLocale);
        boolean isDark = settingsManager.isDarkModeEnabled();
        AppCompatDelegate.setDefaultNightMode(isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    @EntryPoint
    @InstallIn(SingletonComponent.class)
    public interface SettingsManagerEntryPoint {
        SettingsManager getSettingsManager();
    }
}
