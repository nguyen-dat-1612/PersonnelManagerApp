package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;

public interface FileRepository {
    Single<String> uploadPdf(MultipartBody.Part file);
}
