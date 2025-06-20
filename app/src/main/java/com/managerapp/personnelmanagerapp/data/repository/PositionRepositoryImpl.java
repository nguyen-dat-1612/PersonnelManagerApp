package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.PositionMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.PositionApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.PositionResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.Position;
import com.managerapp.personnelmanagerapp.domain.repository.PositionRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PositionRepositoryImpl implements PositionRepository {
    private final PositionApiService positionApiService;

    @Inject
    public PositionRepositoryImpl(PositionApiService positionApiService, RxResultHandler rxResultHandler) {
        this.positionApiService = positionApiService;
    }

    public Observable<List<Position>> getPositions() {
        return positionApiService.getPositions()
                .map(response ->
                        PositionMapper.toPositions(response.getData())
                )
                .onErrorResumeNext(throwable -> {
                    return Observable.error(new Throwable(throwable.getMessage()));
                });
    }

}
