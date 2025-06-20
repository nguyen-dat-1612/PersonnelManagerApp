package com.managerapp.personnelmanagerapp.presentation.base;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.managerapp.personnelmanagerapp.utils.LocaleHelper;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {
    private BaseViewModel viewModel;
    private Snackbar snackbar;
    private static final String TAG = "BaseActivity";
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        viewModel = new ViewModelProvider(this).get(BaseViewModel.class);
        observeNetworkStatus();
        checkNotificationStatus();
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        // Áp dụng ngôn ngữ trước khi attach context
        sharedPreferences = newBase.getSharedPreferences("app_settings", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "vi");
        super.attachBaseContext(updateBaseContextLocale(newBase, language));
    }

    private Context updateBaseContextLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        return context.createConfigurationContext(configuration);
    }

    private void checkNotificationStatus() {
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", true);
        if (!notificationsEnabled) {
            disableNotifications();
        }
    }
    protected void enableNotifications() {
        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Subscribed to notifications");
                    }
                });
    }

    protected void disableNotifications() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("all")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Unsubscribed from notifications");
                    }
                });

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void observeNetworkStatus() {
        viewModel.getNetworkStatus().observe(this, isConnected -> {
            if (!isConnected) {
                showNoInternetSnackbar();
            } else {
                hideNoInternetSnackbar();
            }
        });
    }

    private void showNoInternetSnackbar() {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Không có kết nối mạng!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Thử lại", v -> recreate());
            snackbar.show();
        }
    }

    private void hideNoInternetSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private final BroadcastReceiver forceLogoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sharedPreferences.edit().clear().apply();
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(forceLogoutReceiver, new IntentFilter("force_logout"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(forceLogoutReceiver);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        hideNoInternetSnackbar();
    }
}
