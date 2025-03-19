package com.managerapp.personnelmanagerapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";
    private static final String CHANNEL_ID = "DemoChannel";
    private static final String SERVER_URL = "http://192.168.1.118:8080/save-token"; // Đồng bộ với server

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: Message received");

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "onMessageReceived: Title = " + title + ", Body = " + body);
            showNotification(title, body);
        } else {
            Log.d(TAG, "onMessageReceived: No notification payload");
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "onNewToken: New token = " + token);
        sendTokenToServer(token, "user123");
    }

    private void showNotification(String title, String message) {
        Log.d(TAG, "showNotification: Creating notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Demo Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "showNotification: Notification channel created");
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title != null ? title : "Thông báo mới")
                .setContentText(message != null ? message : "Bạn vừa nhận được một thông báo!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
        Log.d(TAG, "showNotification: Notification displayed");
    }

    private void sendTokenToServer(String token, String userId) {
        Log.d(TAG, "sendTokenToServer: Starting for userId=" + userId);
        new SendTokenTask().execute(token, userId);
    }

    private static class SendTokenTask extends AsyncTask<String, Void, String> {
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
        }
    }
}