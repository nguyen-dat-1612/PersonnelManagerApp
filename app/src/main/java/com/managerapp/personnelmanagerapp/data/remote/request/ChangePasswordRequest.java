package com.managerapp.personnelmanagerapp.data.remote.request;

import androidx.annotation.NonNull;

public class ChangePasswordRequest {
    @NonNull
    private long userId;
    @NonNull
    private String oldPass;
    @NonNull
    private String newPass;

    public ChangePasswordRequest(@NonNull long userId, @NonNull String oldPass, @NonNull String newPass) {
        this.userId = userId;
        this.oldPass = oldPass;
        this.newPass = newPass;
    }
}
