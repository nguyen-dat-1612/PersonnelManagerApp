package com.managerapp.personnelmanagerapp.domain.model;

public class Employee {
    private int employeeID;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String permanentAddress;
    private int ethnicityID;
    private int religionID;
    private String email;
    private String phoneNumber;

    // Constructor
    public Employee(int employeeID, String fullName, String gender, String dateOfBirth,
                    String permanentAddress, int ethnicityID, int religionID, String email,
                    String phoneNumber) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.permanentAddress = permanentAddress;
        this.ethnicityID = ethnicityID;
        this.religionID = religionID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters và Setters
    public int getEmployeeID() { return employeeID; }
    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }

    public int getEthnicityID() { return ethnicityID; }
    public void setEthnicityID(int ethnicityID) { this.ethnicityID = ethnicityID; }

    public int getReligionID() { return religionID; }
    public void setReligionID(int religionID) { this.religionID = religionID; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
