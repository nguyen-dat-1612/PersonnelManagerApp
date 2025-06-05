package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.PositionApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.PositionResponse;
import com.managerapp.personnelmanagerapp.domain.repository.PositionRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PositionRepositoryImpl implements PositionRepository {
    private final PositionApiService positionApiService;

    @Inject
    public PositionRepositoryImpl(PositionApiService positionApiService) {
        this.positionApiService = positionApiService;
    }

    public Observable<List<PositionResponse>> getPositions() {
        return positionApiService.getPositions()
                .map(response -> response.getData())
                .onErrorResumeNext(throwable -> {
                    return Observable.error(new Throwable(throwable.getMessage()));
                });
    }

}
