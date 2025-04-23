package com.managerapp.personnelmanagerapp.data.remote.request;

import androidx.annotation.NonNull;

public class ChangePasswordRequest {
    @NonNull
    private final long userId;
    @NonNull
    private final String oldPass;
    @NonNull
    private final String newPass;

    public ChangePasswordRequest(@NonNull long userId, @NonNull String oldPass, @NonNull String newPass) {
        this.userId = userId;
        this.oldPass = oldPass;
        this.newPass = newPass;
    }
}
