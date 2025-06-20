package com.managerapp.personnelmanagerapp.data.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.utils.ApiException;
import com.managerapp.personnelmanagerapp.utils.ErrorMapper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.HttpException;

@Singleton
public class RxResultHandler {

    private final ErrorMapper errorMapper;

    @Inject
    public RxResultHandler(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    public <T> Single<T> handleSingle(Single<BaseResponse<T>> source) {
        return source.flatMap(response -> {
            if (response.getCode() == 200) {
                return Single.just(response.getData());
            }
            String errorMsg = errorMapper.getMessage(response.getCode());
            return Single.error(new ApiException(response.getCode(), errorMsg));
        }).onErrorResumeNext(throwable -> {
            if (throwable instanceof JsonSyntaxException) {
                Log.d("Lỗi định dạng dữ liệu: ", throwable.getMessage());
                return Single.error(new Exception("Data format error"));
            }
            else if (throwable instanceof retrofit2.adapter.rxjava3.HttpException) {
                retrofit2.adapter.rxjava3.HttpException httpEx = (retrofit2.adapter.rxjava3.HttpException) throwable;
                int statusCode = httpEx.code();

                try {
                    String errorBody = httpEx.response().errorBody().string();
                    BaseResponse<?> errorResponse = new Gson().fromJson(errorBody, BaseResponse.class);
                    String errorMsg = errorMapper.getMessage(errorResponse.getCode());
                    return Single.error(new ApiException(statusCode, errorMsg));
                } catch (Exception e) {
                    String errorMsg = errorMapper.getMessage(statusCode);
                    return Single.error(new ApiException(statusCode, errorMsg));
                }
            }
            else if (throwable instanceof java.net.SocketTimeoutException) {
                Log.d("Lỗi timeout", throwable.getMessage());
                return Single.error(new Exception("Request timeout"));
            }
            else if (throwable instanceof java.net.UnknownHostException) {
                Log.d("Lỗi mạng", throwable.getMessage());
                return Single.error(new Exception("No internet connection"));
            }
            else if (throwable instanceof IOException) {
                Log.d("Lỗi IO", throwable.getMessage());
                return Single.error(new Exception("Network error"));
            }

            Log.d("Lỗi khác", throwable.toString());
            return Single.error(throwable);
        });
    }

    // Xử lý Observable<BaseResponse<T>> -> Observable<T>
    public <T> Observable<T> handleObservable(Observable<BaseResponse<T>> source) {
        return source.flatMap(response -> {
            if (response.getCode() == 200 && response.getData() != null) {
                return Observable.just(response.getData());
            } else {
                String errorMsg = errorMapper.getMessage(response.getCode());
                return Observable.error(new Exception(errorMsg));
            }
        }).onErrorResumeNext(throwable -> {
            if (throwable instanceof JsonSyntaxException) {
                return Observable.error(new Exception("Data format error"));
            }
            return Observable.error(throwable);
        });
    }

    // Xử lý Maybe<BaseResponse<T>> -> Maybe<T>
    public <T> Maybe<T> handleMaybe(Maybe<BaseResponse<T>> source) {
        return source.flatMap(response -> {
            if (response.getCode() == 200 && response.getData() != null) {
                return Maybe.just(response.getData());
            } else {
                String errorMsg = errorMapper.getMessage(response.getCode());
                return Maybe.error(new Exception(errorMsg));
            }
        }).onErrorResumeNext(throwable -> {
            if (throwable instanceof JsonSyntaxException) {
                return Maybe.error(new Exception("Data format error"));
            }
            return Maybe.error(throwable);
        });
    }

    // Xử lý Completable (không có dữ liệu trả về, chỉ xử lý lỗi)
    public Completable handleCompletable(Completable source) {
        return source.onErrorResumeNext(throwable -> {
            if (throwable instanceof JsonSyntaxException) {
                return Completable.error(new Exception("Data format error"));
            }
            return Completable.error(throwable);
        });
    }
}
