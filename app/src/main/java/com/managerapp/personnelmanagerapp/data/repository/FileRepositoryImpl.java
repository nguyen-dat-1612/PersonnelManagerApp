package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.FileApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.repository.FileRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;

public class FileRepositoryImpl implements FileRepository {
    private final FileApiService fileApiService;
    private final RxResultHandler rxResultHandler;
    @Inject
    public FileRepositoryImpl(FileApiService fileApiService, RxResultHandler rxResultHandler) {
        this.fileApiService = fileApiService;
        this.rxResultHandler = rxResultHandler;
    }
    public Single<String> uploadPdf(MultipartBody.Part file) {
        return rxResultHandler.handleSingle(fileApiService.uploadPdf(file));
    }
}
