package com.managerapp.personnelmanagerapp.domain.usecase.position;

import com.managerapp.personnelmanagerapp.domain.model.Position;
import com.managerapp.personnelmanagerapp.domain.repository.PositionRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetPositionUseCase {
    private final PositionRepository positionRepository;

    @Inject
    public GetPositionUseCase(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Observable<List<Position>> execute() {
        return positionRepository.getPositions();
    }
}
