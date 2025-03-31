package com.managerapp.personnelmanagerapp.ui.activities;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.managerapp.personnelmanagerapp.ui.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_URL = "http://192.168.179.165:8080/save-token"; // Đồng bộ với server
    private NavController navController;
    private ActivityMainBinding binding;
    private ChipNavigationBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting MainActivity");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: UI initialized");

        // Yêu cầu quyền thông báo cho Android 13+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission("android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
                Log.d(TAG, "onCreate: Requesting POST_NOTIFICATIONS permission");
            }
        }

        // Lấy FCM Token và gửi lên server
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Log.d(TAG, "FCM Token retrieved: " + token);
                sendTokenToServer(token, "user123");
            } else {
                String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                Log.e(TAG, "FCM Token Error: " + error);
                showToast("Failed to get FCM token: " + error);
            }
        });

        // Lấy NavController từ NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            Log.d(TAG, "onCreate: NavController initialized");
        } else {
            Log.e(TAG, "onCreate: NavHostFragment is null");
        }

        binding.bottomBar.setItemSelected(R.id.home_fragment, true);
        setupBottomNavigation();
        Log.d(TAG, "onCreate: Bottom navigation setup completed");
    }

    private void setupBottomNavigation() {
        bottomBar = findViewById(R.id.bottomBar);
        if (bottomBar != null) {
            bottomBar.setOnItemSelectedListener(id -> {
                Log.d(TAG, "BottomNav: Selected item ID: " + id);
                if (id == R.id.home_fragment) {
                    navController.navigate(R.id.home_fragment);
                } else if (id == R.id.notifications_fragment) {
                    navController.navigate(R.id.notifications_fragment);
                } else if (id == R.id.schedule_fragment) {
                    navController.navigate(R.id.schedule_fragment);
                } else if (id == R.id.profile_fragment) {
                    navController.navigate(R.id.profile_fragment);
                }
            });
            Log.d(TAG, "setupBottomNavigation: Listener attached");
        } else {
            Log.e(TAG, "setupBottomNavigation: bottomBar is null");
        }
    }

    private void showToast(String msg) {
        runOnUiThread(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            Log.d(TAG, "showToast: " + msg);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        boolean result = navController != null && (navController.navigateUp() || super.onSupportNavigateUp());
        Log.d(TAG, "onSupportNavigateUp: Result = " + result);
        return result;
    }

    public void sendTokenToServer(String token, String userId) {
        Log.d(TAG, "sendTokenToServer: Starting for userId=" + userId);
        new SendTokenTask(this).execute(token, userId);
    }

    private static class SendTokenTask extends AsyncTask<String, Void, String> {
        private MainActivity activity;

        SendTokenTask(MainActivity activity) {
            this.activity = activity;
            Log.d(TAG, "SendTokenTask: Initialized");
        }

        @Override
        protected String doInBackground(String... params) {
            String token = params[0];
            String userId = params[1];
            String result = "";

            Log.d(TAG, "SendTokenTask: Starting HTTP request");
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", userId);
                jsonBody.put("fcm_token", token);
                Log.d(TAG, "SendTokenTask: JSON payload = " + jsonBody.toString());

                URL url = new URL(SERVER_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                Log.d(TAG, "SendTokenTask: Connection configured");

                OutputStream os = conn.getOutputStream();
                os.write(jsonBody.toString().getBytes("UTF-8"));
                os.flush();
                os.close();
                Log.d(TAG, "SendTokenTask: Data sent to server");

                int responseCode = conn.getResponseCode();
                Log.d(TAG, "SendTokenTask: Response code = " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    result = response.toString();
                    Log.d(TAG, "SendTokenTask: Server response = " + result);
                } else {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    errorReader.close();
                    result = "Error: " + responseCode + " - " + errorResponse.toString();
                    Log.e(TAG, "SendTokenTask: Error response = " + result);
                }
                conn.disconnect();
            } catch (Exception e) {
                result = "Exception: " + e.getMessage();
                Log.e(TAG, "SendTokenTask: Exception = " + e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "SendTokenTask: onPostExecute result = " + result);
            if (activity != null) {
                activity.showToast("Server response: " + result);
            }
        }
    }
}