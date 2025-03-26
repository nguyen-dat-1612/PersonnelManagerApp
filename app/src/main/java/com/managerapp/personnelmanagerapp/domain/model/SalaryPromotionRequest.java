package com.managerapp.personnelmanagerapp.domain.model;

import java.time.LocalDate;

public class SalaryPromotionRequest {
    private String id;                      // Mã đề nghị (e.g., DN001, DN002,...)
    private int userId;                    // Mã nhân sự gửi đề nghị
    private String requestType;             // Tiêu đề đề nghị
    private String currentJobGradeId;       // Mã ngạch lương hiện tại
    private String requestedJobGradeId;     // Mã ngạch lương đề xuất
    private LocalDate requestDate;         // Ngày gửi đề nghị
    private ApprovalStatus approvalStatus;  // Trạng thái phê duyệt
    private String notes;                  // Ghi chú

    // Enum for approval status
    public enum ApprovalStatus {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected");

        private final String displayName;

        ApprovalStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors
    public SalaryPromotionRequest() {
        this.approvalStatus = ApprovalStatus.PENDING;
    }

    public SalaryPromotionRequest(String id, int userId, String requestType,
                                  String currentJobGradeId, String requestedJobGradeId,
                                  LocalDate requestDate, ApprovalStatus approvalStatus,
                                  String notes) {
        this.id = id;
        this.userId = userId;
        this.requestType = requestType;
        this.currentJobGradeId = currentJobGradeId;
        this.requestedJobGradeId = requestedJobGradeId;
        this.requestDate = requestDate;
        this.approvalStatus = approvalStatus != null ? approvalStatus : ApprovalStatus.PENDING;
        this.notes = notes;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        if (requestType == null || requestType.trim().isEmpty()) {
            throw new IllegalArgumentException("Request type cannot be null or empty");
        }
        this.requestType = requestType;
    }

    public String getCurrentJobGradeId() {
        return currentJobGradeId;
    }

    public void setCurrentJobGradeId(String currentJobGradeId) {
        if (currentJobGradeId == null || currentJobGradeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Current job grade ID cannot be null or empty");
        }
        this.currentJobGradeId = currentJobGradeId;
    }

    public String getRequestedJobGradeId() {
        return requestedJobGradeId;
    }

    public void setRequestedJobGradeId(String requestedJobGradeId) {
        if (requestedJobGradeId == null || requestedJobGradeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Requested job grade ID cannot be null or empty");
        }
        this.requestedJobGradeId = requestedJobGradeId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        if (requestDate == null) {
            throw new IllegalArgumentException("Request date cannot be null");
        }
        this.requestDate = requestDate;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus != null ? approvalStatus : ApprovalStatus.PENDING;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // toString method
    @Override
    public String toString() {
        return "SalaryPromotionRequest{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", requestType='" + requestType + '\'' +
                ", currentJobGradeId='" + currentJobGradeId + '\'' +
                ", requestedJobGradeId='" + requestedJobGradeId + '\'' +
                ", requestDate=" + requestDate +
                ", approvalStatus=" + approvalStatus +
                ", notes='" + notes + '\'' +
                '}';
    }
}