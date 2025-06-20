package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.PositionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Position;

import java.util.List;
import io.reactivex.rxjava3.core.Observable;

public interface PositionRepository {
    Observable<List<Position>> getPositions();
}
