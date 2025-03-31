package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;

public class RewardAssignment {
    private int id;                     // Mã bản ghi quyết định khen thưởng (auto-incremented)
    private int rewardDecisionId;       // Số quyết định khen thưởng (foreign key)
    private int userId;                 // Mã người dùng được khen thưởng (foreign key)
    private Date rewardDate;            // Ngày áp dụng khen thưởng

    // Constructors
    public RewardAssignment() {
    }

    public RewardAssignment(int id, int rewardDecisionId, int userId, Date rewardDate) {
        setId(id);
        setRewardDecisionId(rewardDecisionId);
        setUserId(userId);
        setRewardDate(rewardDate);
    }

    // Getters and Setters with validation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRewardDecisionId() {
        return rewardDecisionId;
    }

    public void setRewardDecisionId(int rewardDecisionId) {
        this.rewardDecisionId = rewardDecisionId;
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

    public Date getRewardDate() {
        return rewardDate;
    }

    public void setRewardDate(Date rewardDate) {
        if (rewardDate == null) {
            throw new IllegalArgumentException("Reward date cannot be null");
        }
        this.rewardDate = rewardDate;
    }

    // toString method
    @Override
    public String toString() {
        return "RewardAssignment{" +
                "id=" + id +
                ", rewardDecisionId=" + rewardDecisionId +
                ", userId=" + userId +
                ", rewardDate=" + rewardDate +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RewardAssignment that = (RewardAssignment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}