package com.managerapp.personnelmanagerapp.data.repository;

import android.net.Uri;

import com.managerapp.personnelmanagerapp.data.remote.api.FileApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileRepository {

    private final FileApiService fileApiService;


    @Inject
    public FileRepository(FileApiService fileApiService) {
        this.fileApiService = fileApiService;
    }

    public Single<String> uploadPdf(MultipartBody.Part file) {
        return RxResultHandler.handle(fileApiService.uploadPdf(file));
    }
}
