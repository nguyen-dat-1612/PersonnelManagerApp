package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Objects;

public class LeaveType {
    private int id;
    private String name;

    public LeaveType(int id, String name) {
        this.id = id;
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Leave type name cannot be null or empty");
        }
        if (name.length() > 200) {
            throw new IllegalArgumentException("Leave type name cannot exceed 100 characters");
        }
        this.name = name.trim();
    }

    @Override
    public String toString() {
        return "LeaveType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveType leaveType = (LeaveType) o;
        return id == leaveType.id && name.equals(leaveType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}