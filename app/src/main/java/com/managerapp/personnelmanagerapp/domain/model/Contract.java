package com.managerapp.personnelmanagerapp.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Contract {
    private String id;  // Mã hợp đồng (primary key)
    private int userId;  // Mã người dùng (foreign key)
    private String contractTypeId;  // Mã loại hợp đồng (foreign key)
    private Date startDate;  // Ngày bắt đầu hợp đồng
    private Date endDate;  // Ngày kết thúc hợp đồng (nullable)
    private ContractStatus status;  // Trạng thái hợp đồng
    private String jobGradeId;  // Mã ngạch (foreign key)
    private BigDecimal baseSalary;  // Lương cơ bản
    private String positionId;  // Mã chức vụ (foreign key)

    // Enum for contract status
    public enum ContractStatus {
        INACTIVE("Chưa có hiệu lực"),
        ACTIVE("Đang hiệu lực"),
        EXPIRED("Hết hạn"),
        TERMINATED("Chấm dứt");

        private final String vietnameseName;

        ContractStatus(String vietnameseName) {
            this.vietnameseName = vietnameseName;
        }

        public String getVietnameseName() {
            return vietnameseName;
        }
    }

    // Constructors
    public Contract() {
        this.status = ContractStatus.INACTIVE;
    }

    public Contract(String id, int userId, String contractTypeId,
                    Date startDate, Date endDate,
                    ContractStatus status, String jobGradeId,
                    BigDecimal baseSalary, String positionId) {
        setId(id);
        setUserId(userId);
        setContractTypeId(contractTypeId);
        setStartDate(startDate);
        setEndDate(endDate);
        setStatus(status);
        setJobGradeId(jobGradeId);
        setBaseSalary(baseSalary);
        setPositionId(positionId);
    }

    // Getters and Setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Contract ID cannot be null or empty");
        }
        this.id = id.trim();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        this.userId = userId;
    }

    public String getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId != null ? contractTypeId.trim() : null;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null");
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null && startDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        this.endDate = endDate;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status != null ? status : ContractStatus.INACTIVE;
    }

    public String getJobGradeId() {
        return jobGradeId;
    }

    public void setJobGradeId(String jobGradeId) {
        this.jobGradeId = jobGradeId != null ? jobGradeId.trim() : null;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        if (baseSalary == null || baseSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Base salary must be positive");
        }
        this.baseSalary = baseSalary;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId != null ? positionId.trim() : null;
    }

    // Business logic methods
    public boolean isActive() {
        return status == ContractStatus.ACTIVE;
    }

    public boolean isExpired() {
        return endDate != null && new Date().after(endDate);
    }

    // toString method
    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", contractTypeId='" + contractTypeId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", jobGradeId='" + jobGradeId + '\'' +
                ", baseSalary=" + baseSalary +
                ", positionId='" + positionId + '\'' +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id.equals(contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}