package com.managerapp.personnelmanagerapp.data.remote.request;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class SalaryPromotionRequest {

    @SerializedName("reason")
    private String reason;

    @SerializedName("currentJobGradeId")
    @NotNull
    private String currentJobGradeId;

    @SerializedName("requestJobGradeId")
    @NotNull
    private String requestJobGradeId;

    @NotNull
    private Long userId;

    public SalaryPromotionRequest(String reason, @NotNull String currentJobGradeId, @NotNull String requestJobGradeId, @NotNull Long userId) {
        this.reason = reason;
        this.currentJobGradeId = currentJobGradeId;
        this.requestJobGradeId = requestJobGradeId;
        this.userId = userId;
    }
}
