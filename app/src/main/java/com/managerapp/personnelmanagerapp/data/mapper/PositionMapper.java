package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.PositionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Position;

import java.util.List;
import java.util.stream.Collectors;

public class PositionMapper {
    public static Position toPosition(PositionResponse positionResponse) {
        return new Position.Builder()
                .id(positionResponse.getId())
                .name(positionResponse.getName())
                .description(positionResponse.getDescription())
                .department(DepartmentMapper.toDepartment(positionResponse.getDepartment()))
                .role(RoleMapper.toRole(positionResponse.getRole()))
                .build();
    }

    public static List<Position> toPositions(List<PositionResponse> positionResponses) {
        return positionResponses.stream()
                .map(PositionMapper::toPosition)
                .collect(Collectors.toList());
    }
}
