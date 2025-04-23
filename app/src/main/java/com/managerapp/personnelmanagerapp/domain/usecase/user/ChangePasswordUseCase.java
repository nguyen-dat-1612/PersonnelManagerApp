package com.managerapp.personnelmanagerapp.domain.usecase.user;

import com.managerapp.personnelmanagerapp.data.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ChangePasswordUseCase {
    private final UserRepository userRepository;

    @Inject
    public ChangePasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<Boolean> excute(String oldPass, String newPass) {
        return userRepository.changePasswordUser(oldPass, newPass);
    }
}
