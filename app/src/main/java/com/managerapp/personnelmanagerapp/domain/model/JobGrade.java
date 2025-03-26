package com.managerapp.personnelmanagerapp.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class JobGrade {
    private String id;  // Mã ngạch (primary key)
    private String name;  // Tên ngạch (unique, not null)
    private String groupName;  // Nhóm mã ngạch (part of composite primary key)
    private BigDecimal salaryCoefficient;  // Hệ số lương
    private String description;  // Chi tiết

    // Constructors
    public JobGrade() {
    }

    public JobGrade(String id, String name, String groupName,
                    BigDecimal salaryCoefficient, String description) {
        setId(id);
        setName(name);
        setGroupName(groupName);
        setSalaryCoefficient(salaryCoefficient);
        setDescription(description);
    }

    // Getters and Setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Job grade ID cannot be null or empty");
        }
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Job grade name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }
        this.groupName = groupName.trim();
    }

    public BigDecimal getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(BigDecimal salaryCoefficient) {
        if (salaryCoefficient == null || salaryCoefficient.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Salary coefficient must be positive");
        }
        this.salaryCoefficient = salaryCoefficient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    // toString method
    @Override
    public String toString() {
        return "JobGrade{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                ", salaryCoefficient=" + salaryCoefficient +
                ", description='" + description + '\'' +
                '}';
    }

    // equals and hashCode based on composite key (id and groupName)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobGrade jobGrade = (JobGrade) o;
        return id.equals(jobGrade.id) &&
                groupName.equals(jobGrade.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName);
    }
}