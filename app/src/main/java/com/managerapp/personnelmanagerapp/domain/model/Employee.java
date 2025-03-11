package com.managerapp.personnelmanagerapp.domain.model;

public class Employee {
    private int id;
    private int accountId;
    private String fullName;
    private String identityNumber;
    private String placeOfIssue;
    private String issueDate;
    private String gender;
    private String birthDate;
    private String email;
    private String phoneNumber;
    private String permanentAddress;
    private String healthStatus;
    private String jobTitleId;
    private String positionId;
    private String degreeId;

    public Employee(int id, int accountId, String fullName, String identityNumber, String issueDate, String placeOfIssue, String birthDate, String gender, String email, String phoneNumber, String permanentAddress, String healthStatus, String jobTitleId, String positionId, String degreeId) {
        this.id = id;
        this.accountId = accountId;
        this.fullName = fullName;
        this.identityNumber = identityNumber;
        this.issueDate = issueDate;
        this.placeOfIssue = placeOfIssue;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.permanentAddress = permanentAddress;
        this.healthStatus = healthStatus;
        this.jobTitleId = jobTitleId;
        this.positionId = positionId;
        this.degreeId = degreeId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
