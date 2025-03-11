package com.managerapp.personnelmanagerapp.domain.model;

public class RewardDiscipline {
    private String id;
    private String name;
    private String type; // Reward or Discipline
    private String description;
    private double value;

    public RewardDiscipline(String id, String name, String type, String description, double value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
