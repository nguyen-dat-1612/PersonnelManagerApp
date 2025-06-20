package com.managerapp.personnelmanagerapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Objects;

public class JobGrade {
    private String id;
    private String name;
    private String coefficient;
    private String description;

    public JobGrade(String id, String name, String coefficient, String description) {
        this.id = id;
        this.name = name;
        this.coefficient = coefficient;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}