package com.managerapp.personnelmanagerapp.domain.model;
import java.util.Objects;

public class UserSummary {
    private long id;
    private String fullName;
    private String numberCCCD;
    private String dateOfBirth;
    private String phoneNumber;
    private String nationality;
    private String email;
    private String address;

    private UserSummary(Builder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.numberCCCD = builder.numberCCCD;
        this.dateOfBirth = builder.dateOfBirth;
        this.phoneNumber = builder.phoneNumber;
        this.nationality = builder.nationality;
        this.email = builder.email;
        this.address = builder.address;
    }
    public static class Builder{
        private long id;
        private String fullName;
        private String numberCCCD;
        private String dateOfBirth;
        private String phoneNumber;
        private String nationality;
        private String email;
        private String address;

        public Builder id(long id) {
            this.id = id;
            return this;
        }
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder numberCCCD(String numberCCCD) {
            this.numberCCCD = numberCCCD;
            return this;
        }

        public Builder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder address(String address) {
            this.address = address;
            return this;
        }
        public UserSummary build() {
            return new UserSummary(this);
        }
    }

    public long getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public String getNumberCCCD() {
        return numberCCCD;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getNationality() {
        return nationality;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "UserSummary{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", numberCCCD='" + numberCCCD + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserSummary that = (UserSummary) o;
        return id == that.id && Objects.equals(fullName, that.fullName) && Objects.equals(numberCCCD, that.numberCCCD) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(nationality, that.nationality) && Objects.equals(email, that.email) && Objects.equals(address, that.address);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, numberCCCD, dateOfBirth, phoneNumber, nationality, email, address);
    }
}
