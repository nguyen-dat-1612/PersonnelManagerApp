package com.managerapp.personnelmanagerapp.domain.model;

public class Position {
    private String id;  // Primary key (e.g., "GV001", "CB002")
    private String name;  // Unique, not null
    private String departmentId;  // Foreign key

    // Constructors
    public Position() {
    }

    public Position(String id, String name, String departmentId) {
        setId(id);
        setName(name);
        setDepartmentId(departmentId);
    }

    // Getters and Setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Position ID cannot be null or empty");
        }
        this.id = id.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Position name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId != null ? departmentId.trim().toUpperCase() : null;
    }

    @Override
    public String toString() {
        return name;
    }
}