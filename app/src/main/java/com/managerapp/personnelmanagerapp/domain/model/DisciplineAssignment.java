package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;

public class DisciplineAssignment {
    private int id;                     // Mã bản ghi quyết định kỷ luật (auto-incremented)
    private int disciplineDecisionId;   // Số quyết định kỷ luật (foreign key)
    private int userId;                 // Mã người dùng bị kỷ luật (foreign key)
    private Date disciplineDate;        // Ngày bị kỷ luật

    // Constructors
    public DisciplineAssignment() {
    }

    public DisciplineAssignment(int id, int disciplineDecisionId, int userId,
                                Date disciplineDate) {
        setId(id);
        setDisciplineDecisionId(disciplineDecisionId);
        setUserId(userId);
        setDisciplineDate(disciplineDate);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDisciplineDecisionId() {
        return disciplineDecisionId;
    }

    public void setDisciplineDecisionId(int disciplineDecisionId) {
        this.disciplineDecisionId = disciplineDecisionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserEntity ID must be positive");
        }
        this.userId = userId;
    }

    public Date getDisciplineDate() {
        return disciplineDate;
    }

    public void setDisciplineDate(Date disciplineDate) {
        if (disciplineDate == null) {
            throw new IllegalArgumentException("Discipline date cannot be null");
        }
        this.disciplineDate = disciplineDate;
    }

    // toString method
    @Override
    public String toString() {
        return "DisciplineAssignment{" +
                "id=" + id +
                ", disciplineDecisionId=" + disciplineDecisionId +
                ", userId=" + userId +
                ", disciplineDate=" + disciplineDate +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisciplineAssignment that = (DisciplineAssignment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}