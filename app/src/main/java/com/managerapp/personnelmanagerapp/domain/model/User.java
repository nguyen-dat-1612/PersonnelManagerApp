package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;

public class User {
    private long id;
    private String email;
    private String fullName;
    private String numberCCCD;
    private String phoneNumber;
    private Date dob;
    private String gender;
    private String address;
    private String ethnicity;
    private String religion;
    private String taxCode;
    private String degree;
    private Status status;
    private String avatar;

    // Constructors
    public User() {
    }

    public User(long id, String email, String fullName, String numberCCCD,
                String phoneNumber, Date dob, String gender, String address,
                String ethnicity, String religion, String taxCode, String degree,
                Status status, String avatar) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.numberCCCD = numberCCCD;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.ethnicity = ethnicity;
        this.religion = religion;
        this.taxCode = taxCode;
        this.degree = degree;
        this.status = status;
        this.avatar = avatar;
    }

    // Factory method for empty user
    public static User empty() {
        return new User(0L, "", "", "", "", new Date(), "", "",
                "", "", "", "", Status.PENDING, null);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumberCCCD() {
        return numberCCCD;
    }

    public void setNumberCCCD(String numberCCCD) {
        this.numberCCCD = numberCCCD;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(fullName, user.fullName) && Objects.equals(numberCCCD, user.numberCCCD) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(dob, user.dob) && Objects.equals(gender, user.gender) && Objects.equals(address, user.address) && Objects.equals(ethnicity, user.ethnicity) && Objects.equals(religion, user.religion) && Objects.equals(taxCode, user.taxCode) && Objects.equals(degree, user.degree) && status == user.status && Objects.equals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, fullName, numberCCCD, phoneNumber, dob, gender, address, ethnicity, religion, taxCode, degree, status, avatar);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", numberCCCD='" + numberCCCD + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", religion='" + religion + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", degree='" + degree + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public enum Status {
        PENDING,
        EXPIRED,
        TERMINATED;

        public static Status fromString(String value) {
            if (value == null) return null;
            try {
                return Status.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
