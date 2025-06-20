package com.managerapp.personnelmanagerapp.domain.model;

public class ContractExpireReport {
    private int stt;
    private String fullName;
    private String email;
    private String departmentName;
    private String positionName;
    private String contractTypeName;
    private String endDate;
    private int remainingDays;
    private ContractStatusEnum contractStatus;

    public ContractExpireReport(int stt, String fullName, String email, String departmentName,
                                String positionName, String contractTypeName, String endDate,
                                ContractStatusEnum contractStatus, int remainingDays) {
        this.stt = stt;
        this.fullName = fullName;
        this.email = email;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.contractTypeName = contractTypeName;
        this.endDate = endDate;
        this.contractStatus = contractStatus;
        this.remainingDays = remainingDays;
    }

    public int getStt() {
        return stt;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public ContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    // === Builder ===
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int stt;
        private String fullName;
        private String email;
        private String departmentName;
        private String positionName;
        private String contractTypeName;
        private String endDate;
        private int remainingDays;
        private ContractStatusEnum contractStatus;

        public Builder stt(int stt) {
            this.stt = stt;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder departmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public Builder positionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public Builder contractTypeName(String contractTypeName) {
            this.contractTypeName = contractTypeName;
            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder contractStatus(ContractStatusEnum contractStatus) {
            this.contractStatus = contractStatus;
            return this;
        }

        public Builder remainingDays(int remainingDays) {
            this.remainingDays = remainingDays;
            return this;
        }

        public ContractExpireReport build() {
            return new ContractExpireReport(stt, fullName, email, departmentName, positionName,
                    contractTypeName, endDate, contractStatus, remainingDays);
        }
    }
}
