package com.managerapp.personnelmanagerapp.service;

import android.util.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class RxWebSocketClient {

    private static final String TAG = "RxWebSocketClient";

    private final OkHttpClient client;
    private WebSocket webSocket;
    private final PublishSubject<String> messageSubject = PublishSubject.create();

    public RxWebSocketClient() {
        client = new OkHttpClient();
    }

    public void connect(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket ws, Response response) {
                Log.d(TAG, "WebSocket opened");
            }

            @Override
            public void onMessage(WebSocket ws, String text) {
                Log.d(TAG, "Received text: " + text);
                messageSubject.onNext(text);
            }

            @Override
            public void onMessage(WebSocket ws, ByteString bytes) {
                Log.d(TAG, "Received bytes");
                messageSubject.onNext(bytes.hex());
            }

            @Override
            public void onClosing(WebSocket ws, int code, String reason) {
                Log.d(TAG, "Closing: " + code + " / " + reason);
                ws.close(code, reason);
                messageSubject.onComplete();
            }

            @Override
            public void onFailure(WebSocket ws, Throwable t, Response response) {
                Log.e(TAG, "Error: " + t.getMessage());
                messageSubject.onError(t);
            }
        });
    }

    public Observable<String> observeMessages() {
        return messageSubject;
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
    }

    public void disconnect() {
        if (webSocket != null) {
            webSocket.close(1000, "User closed");
        }
    }
}
