package com.managerapp.personnelmanagerapp.domain.model;

import com.managerapp.personnelmanagerapp.data.remote.response.DepartmentResponse;
import com.managerapp.personnelmanagerapp.domain.model.Role;

public class Position {
    private String id;
    private String name;
    private String description;
    private Department department;
    private Role role;

    public Position() {
    }

    public Position(String id, String name, String description, Department department, Role role) {
        setId(id);
        setName(name);
        setDescription(description);
        setDepartment(department);
        setRole(role);
    }

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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Builder {
        private String id;
        private String name;
        private String departmentId;
        private String description;
        private Department department;
        private Role role;

        public Builder id(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Position ID cannot be null or empty");
            }
            this.id = id.trim().toUpperCase();
            return this;
        }

        public Builder name(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Position name cannot be null or empty");
            }
            this.name = name.trim();
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Position build() {
            Position position = new Position();
            position.setId(this.id);
            position.setName(this.name);
            position.setDescription(this.description);
            position.setDepartment(this.department);
            position.setRole(this.role);
            return position;
        }
    }
}
