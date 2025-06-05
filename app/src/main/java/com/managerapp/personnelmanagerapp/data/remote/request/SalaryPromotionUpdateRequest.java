package com.managerapp.personnelmanagerapp.data.remote.request;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;

import org.jetbrains.annotations.NotNull;

public class SalaryPromotionUpdateRequest {
    @NotNull
    private Long signerId;

    @NotNull
    private FormStatusEnum formStatus;

    @NotNull
    private String reason;

    public SalaryPromotionUpdateRequest(@NotNull Long signerId, @NotNull FormStatusEnum formStatus, @NotNull String reason) {
        this.signerId = signerId;
        this.formStatus = formStatus;
        this.reason = reason;
    }
}
