package com.managerapp.personnelmanagerapp.data.remote.api;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;

import io.reactivex.rxjava3.core.Single;

public class RxResultHandler {
    public static <T> Single<T> handle(Single<BaseResponse<T>> source) {
        return source.flatMap(response -> {
            if (response.getCode() == 200 && response.getData() != null) {
                Log.d("RxResultHandler", "Success: " + response.getData());
                return Single.just(response.getData());
            } else {
                Log.e("RxResultHandler", "API failed: " + response.getMessage());
                return Single.error(new Exception(response.getMessage()));
            }
        }).onErrorResumeNext(throwable -> {
            if (throwable instanceof JsonSyntaxException) {
                Log.e("RxResultHandler", "JSON parse error", throwable);
                return Single.error(new Exception("Data format error"));
            }
            Log.e("RxResultHandler", "API error", throwable);
            return Single.error(throwable);
        });
    }
}
