package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;

public class Salary {
    private String id;                  // Mã lương (Salary ID)
    private String userId;              // Mã người dùng (UserEntity ID)
    private String contractId;          // Mã hợp đồng (Contract ID)
    private String salaryMonth;         // Tháng tính lương (Salary month)
    private double totalAllowances;     // Tổng số tiền trợ cấp (Total allowances)
    private double unpaidLeaveDeduction;// Khấu trừ do nghỉ không lương (Unpaid leave deduction)
    private double personalIncomeTax;   // Thuế thu nhập cá nhân (Personal income tax)
    private double totalDeductions;     // Tổng khấu trừ (Total deductions)
    private double netSalary;           // Lương được nhận (Net salary)
    private Date paymentDate;           // Ngày chi trả lương (Payment date)
    private PaymentStatus paymentStatus;// Trạng thái thanh toán (Payment status)

    // Enum for payment status
    public enum PaymentStatus {
        PENDING("Đang chờ"),
        PAID("Đã thanh toán"),
        CANCELLED("Đã hủy");

        private final String vietnameseName;

        PaymentStatus(String vietnameseName) {
            this.vietnameseName = vietnameseName;
        }

        public String getVietnameseName() {
            return vietnameseName;
        }
    }

    // Constructors
    public Salary() {
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public Salary(String id, String userId, String contractId, String salaryMonth,
                  double totalAllowances, double unpaidLeaveDeduction, double personalIncomeTax,
                  double totalDeductions, double netSalary, Date paymentDate,
                  PaymentStatus paymentStatus) {
        setId(id);
        setUserId(userId);
        setContractId(contractId);
        setSalaryMonth(salaryMonth);
        setTotalAllowances(totalAllowances);
        setUnpaidLeaveDeduction(unpaidLeaveDeduction);
        setPersonalIncomeTax(personalIncomeTax);
        setTotalDeductions(totalDeductions);
        setNetSalary(netSalary);
        setPaymentDate(paymentDate);
        setPaymentStatus(paymentStatus);
    }

    // Getters and Setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Salary ID cannot be null or empty");
        }
        this.id = id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("UserEntity ID cannot be null or empty");
        }
        this.userId = userId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId != null ? contractId.trim() : null;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(String salaryMonth) {
        if (salaryMonth == null || salaryMonth.trim().isEmpty()) {
            throw new IllegalArgumentException("Salary month cannot be null or empty");
        }
        this.salaryMonth = salaryMonth.trim();
    }

    public double getTotalAllowances() {
        return totalAllowances;
    }

    public void setTotalAllowances(double totalAllowances) {
        if (totalAllowances < 0) {
            throw new IllegalArgumentException("Total allowances cannot be negative");
        }
        this.totalAllowances = totalAllowances;
    }

    public double getUnpaidLeaveDeduction() {
        return unpaidLeaveDeduction;
    }

    public void setUnpaidLeaveDeduction(double unpaidLeaveDeduction) {
        if (unpaidLeaveDeduction < 0) {
            throw new IllegalArgumentException("Unpaid leave deduction cannot be negative");
        }
        this.unpaidLeaveDeduction = unpaidLeaveDeduction;
    }

    public double getPersonalIncomeTax() {
        return personalIncomeTax;
    }

    public void setPersonalIncomeTax(double personalIncomeTax) {
        if (personalIncomeTax < 0) {
            throw new IllegalArgumentException("Personal income tax cannot be negative");
        }
        this.personalIncomeTax = personalIncomeTax;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        if (totalDeductions < 0) {
            throw new IllegalArgumentException("Total deductions cannot be negative");
        }
        this.totalDeductions = totalDeductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        if (netSalary < 0) {
            throw new IllegalArgumentException("Net salary cannot be negative");
        }
        this.netSalary = netSalary;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus != null ? paymentStatus : PaymentStatus.PENDING;
    }

    // toString method
    @Override
    public String toString() {
        return "Salary{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", salaryMonth='" + salaryMonth + '\'' +
                ", totalAllowances=" + totalAllowances +
                ", unpaidLeaveDeduction=" + unpaidLeaveDeduction +
                ", personalIncomeTax=" + personalIncomeTax +
                ", totalDeductions=" + totalDeductions +
                ", netSalary=" + netSalary +
                ", paymentDate=" + paymentDate +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary salary = (Salary) o;
        return id.equals(salary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}