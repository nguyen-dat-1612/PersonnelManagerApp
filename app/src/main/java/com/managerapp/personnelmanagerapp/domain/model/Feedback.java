package com.managerapp.personnelmanagerapp.domain.model;

// Class đóng góp phản hồi
public class Feedback {
    private String id;
    private int employeeId;
    private String content;
    private String sendDate;
    private String status;
    private int processorId;

    public Feedback(int processorId, String status, String sendDate, String content, int employeeId, String id) {
        this.processorId = processorId;
        this.status = status;
        this.sendDate = sendDate;
        this.content = content;
        this.employeeId = employeeId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public int getProcessorId() {
        return processorId;
    }

    public void setProcessorId(int processorId) {
        this.processorId = processorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
