package com.managerapp.personnelmanagerapp.domain.usecase.file;

import com.managerapp.personnelmanagerapp.data.repository.FileRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.FileRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;

public class UploadPdfUseCase {
    private final FileRepository repository;

    @Inject
    public UploadPdfUseCase(FileRepository repository) {
        this.repository = repository;
    }

    public Single<String> execute(MultipartBody.Part file) {
        return repository.uploadPdf(file);
    }
}
