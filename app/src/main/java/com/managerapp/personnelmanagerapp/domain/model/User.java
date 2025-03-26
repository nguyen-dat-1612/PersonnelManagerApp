package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;

public class User {
    private int id;  // Primary key, auto-increment
    private Integer accountId;  // Foreign key (nullable)
    private String fullName;
    private String cccd;  // Citizen ID (12 chars)
    private Gender gender;
    private Date dateOfBirth;
    private String permanentAddress;
    private String ethnicity;  // Foreign key
    private String religion;
    private String email;  // Unique
    private String status;
    private String phoneNumber;  // Unique
    private String academicDegree;

    public enum Gender {
        MALE("Nam"),
        FEMALE("Nữ"),
        OTHER("Khác");

        private final String vietnameseName;

        Gender(String vietnameseName) {
            this.vietnameseName = vietnameseName;
        }

        public String getVietnameseName() {
            return vietnameseName;
        }
    }

    // Constructors
    public User() {
    }

    public User(int id, Integer accountId, String fullName, String cccd,
                Gender gender, Date dateOfBirth, String permanentAddress,
                String ethnicity, String religion, String email,
                String status, String phoneNumber, String academicDegree) {
        setId(id);
        setAccountId(accountId);
        setFullName(fullName);
        setCccd(cccd);
        setGender(gender);
        setDateOfBirth(dateOfBirth);
        setPermanentAddress(permanentAddress);
        setEthnicity(ethnicity);
        setReligion(religion);
        setEmail(email);
        setStatus(status);
        setPhoneNumber(phoneNumber);
        setAcademicDegree(academicDegree);
    }

    // Getters and Setters with validation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName != null ? fullName.trim() : null;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        if (cccd != null && cccd.length() != 12) {
            throw new IllegalArgumentException("CCCD must be 12 characters");
        }
        this.cccd = cccd;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress != null ? permanentAddress.trim() : null;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion != null ? religion.trim() : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email != null ? email.trim() : null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status != null ? status.trim() : null;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree != null ? academicDegree.trim() : null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}