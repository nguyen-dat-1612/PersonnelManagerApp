package com.managerapp.personnelmanagerapp.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Contract {
    private final int id;
    private final Date startDate;
    private final Date endDate;
    private final double basicSalary;
    private final String clause;
    private final ContractStatusEnum contractStatusEnum;
    private final String contractTypeName;
    private final UserSummary user;
    private final UserSummary signer;
    private final String positionName;
    private final String departmentName;
    private final double jobGradeCoefficient;

    private Contract(Builder builder) {
        this.id = builder.id;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.basicSalary = builder.basicSalary;
        this.clause = builder.clause;
        this.contractStatusEnum = builder.contractStatusEnum;
        this.contractTypeName = builder.contractTypeName;
        this.user = builder.user;
        this.signer = builder.signer;
        this.positionName = builder.positionName;
        this.departmentName = builder.departmentName;
        this.jobGradeCoefficient = builder.jobGradeCoefficient;
    }

    public int getId() {
        return id;
    }

    public String getStartDate() {
        if (startDate == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi", "VN"));
        return sdf.format(startDate);
    }

    public String getEndDate() {
        if (endDate == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi", "VN"));
        return sdf.format(endDate);
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public String getClause() {
        return clause;
    }

    public ContractStatusEnum getContractStatusEnum() {
        return contractStatusEnum;
    }

    public String getContractStatusEnumUI() {
        if (contractStatusEnum == null) return "";
        switch (contractStatusEnum) {
            case PENDING:
                return "🔵 Đang chờ";
            case EXPIRED:
                return "⚫ Hết hạn";
            case TERMINATED:
                return "🔴 Đã hủy";
            case RENEWED:
                return "🟢 Đã gia hạn";
            case SIGNED_PENDING_EFFECTIVE:
                return "🟡 Đã ký";
            case ACTIVE:
                return "🟢 Đang hiệu lực";
            default:
                return "";
        }
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public UserSummary getUser() {
        return user;
    }

    public UserSummary getSigner() {
        return signer;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public double getJobGradeCoefficient() {
        return jobGradeCoefficient;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", basicSalary=" + basicSalary +
                ", clause='" + clause + '\'' +
                ", contractStatusEnum=" + contractStatusEnum +
                ", contractTypeName='" + contractTypeName + '\'' +
                ", user=" + user +
                ", signer=" + signer +
                ", positionName='" + positionName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", jobGradeCoefficient=" + jobGradeCoefficient +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id == contract.id && Double.compare(basicSalary, contract.basicSalary) == 0 && Double.compare(jobGradeCoefficient, contract.jobGradeCoefficient) == 0 && Objects.equals(startDate, contract.startDate) && Objects.equals(endDate, contract.endDate) && Objects.equals(clause, contract.clause) && contractStatusEnum == contract.contractStatusEnum && Objects.equals(contractTypeName, contract.contractTypeName) && Objects.equals(user, contract.user) && Objects.equals(signer, contract.signer) && Objects.equals(positionName, contract.positionName) && Objects.equals(departmentName, contract.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, basicSalary, clause, contractStatusEnum, contractTypeName, user, signer, positionName, departmentName, jobGradeCoefficient);
    }

    public static class Builder {
        private int id;
        private Date startDate;
        private Date endDate;
        private double basicSalary;
        private String clause;
        private ContractStatusEnum contractStatusEnum;
        private String contractTypeName;
        private UserSummary user;
        private UserSummary signer;
        private String positionName;
        private String departmentName;
        private double jobGradeCoefficient;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder basicSalary(double basicSalary) {
            this.basicSalary = basicSalary;
            return this;
        }

        public Builder clause(String clause) {
            this.clause = clause;
            return this;
        }

        public Builder contractStatusEnum(ContractStatusEnum contractStatusEnum) {
            this.contractStatusEnum = contractStatusEnum;
            return this;
        }

        public Builder contractTypeName(String contractTypeName) {
            this.contractTypeName = contractTypeName;
            return this;
        }

        public Builder user(UserSummary user) {
            this.user = user;
            return this;
        }

        public Builder signer(UserSummary signer) {
            this.signer = signer;
            return this;
        }

        public Builder positionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public Builder departmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public Builder jobGradeCoefficient(double jobGradeCoefficient) {
            this.jobGradeCoefficient = jobGradeCoefficient;
            return this;
        }

        public Contract build() {
            return new Contract(this);
        }
    }
}
