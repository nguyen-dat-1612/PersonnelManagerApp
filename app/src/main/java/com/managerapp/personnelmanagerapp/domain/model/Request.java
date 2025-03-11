package com.managerapp.personnelmanagerapp.domain.model;

// Class đơn từ
public class Request {
    private String id;
    private int senderId;
    private String requestTypeId;
    private String sendDate;
    private String applyDate;
    private String endDate;
    private String approvalDate;
    private String status;
    private String content;
    private String response;
    private int approverId;

    public Request(String id, int senderId, String requestTypeId, String sendDate, String applyDate, String endDate, String approvalDate, String status, String content, int approverId, String response) {
        this.id = id;
        this.senderId = senderId;
        this.requestTypeId = requestTypeId;
        this.sendDate = sendDate;
        this.applyDate = applyDate;
        this.endDate = endDate;
        this.approvalDate = approvalDate;
        this.status = status;
        this.content = content;
        this.approverId = approverId;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(String requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }
}

