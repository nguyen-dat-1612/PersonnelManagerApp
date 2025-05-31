package com.managerapp.personnelmanagerapp.domain.usecase.user;

import com.managerapp.personnelmanagerapp.data.repository.UserRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class SearchUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public SearchUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<List<UserSummary>> execute(String query) {
        return userRepository.searchUser(query);

    }
}
