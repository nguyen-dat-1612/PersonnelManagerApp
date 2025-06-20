package com.managerapp.personnelmanagerapp.domain.model;

public class Department {
    private String id;
    private String name;
    private String description;
    public Department(String id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Department ID cannot be null or empty");
        }
        this.id = id.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    @Override
    public String toString() {
        return name;
    }
}