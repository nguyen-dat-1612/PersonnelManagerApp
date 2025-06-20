package com.managerapp.personnelmanagerapp.domain.usecase.user;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.data.repository.UserRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetWorkLogUseCase {
    private final UserRepository userRepository;

    @Inject
    public GetWorkLogUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<List<WorkLogResponse>> getWorkLogs() {
        return userRepository.getWorkLog();
    }
}
