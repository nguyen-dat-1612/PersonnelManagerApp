package com.managerapp.personnelmanagerapp.data.remote.request;

import org.jetbrains.annotations.NotNull;

public class DecisionApproveRequest {
    private String attachment;
    @NotNull
    private long signerId;

    public DecisionApproveRequest(String attachment, @NotNull long signerId) {
        this.attachment = attachment;
        this.signerId = signerId;
    }
}
