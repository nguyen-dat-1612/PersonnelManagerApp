package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class UserSummaryResponse {
    @SerializedName("id")
    private long id;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("numberCCCD")
    private String numberCCCD;

    @SerializedName("dob")
    private String dob;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
