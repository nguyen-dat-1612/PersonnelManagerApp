package com.managerapp.personnelmanagerapp.presentation.decision.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;

/**
 * Builder pattern for creating DecisionRequest objects
 * Provides a fluent API for constructing DecisionRequest with method chaining
 */
public class DecisionRequestBuilder {
    private String id;
    private double value;
    private String content;
    private DecisionEnum type;
    private String date;
    private String effectiveDate;
    private long userId;
    private Integer salaryPromotionId;
    private String positionId;
    private Integer seniorityAllowanceRuleId;

    /**
     * Private constructor to enforce usage through static factory method
     */
    public DecisionRequestBuilder() {
    }

    /**
     * Static factory method to create a new builder instance
     * @return new DecisionRequestBuilder instance
     */
    public static DecisionRequestBuilder newBuilder() {
        return new DecisionRequestBuilder();
    }

    /**
     * Create builder from existing DecisionRequest
     * @param request existing DecisionRequest to copy from
     * @return new DecisionRequestBuilder with values from existing request
     */
    public static DecisionRequestBuilder fromRequest(@NonNull DecisionRequest request) {
        return new DecisionRequestBuilder()
                .setId(request.getId())
                .setValue(request.getValue())
                .setContent(request.getContent())
                .setType(request.getType())
                .setDate(request.getDate())
                .setEffectiveDate(request.getEffectiveDate())
                .setUserId(request.getUserId())
                .setSalaryPromotionId(request.getSalaryPromotionId())
                .setPositionId(request.getPositionId())
                .setSeniorityAllowanceRuleId(request.getSeniorityAllowanceRuleId());
    }

    /**
     * Set decision ID
     * @param id decision identifier
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setId(@NonNull String id) {
        this.id = id;
        return this;
    }

    /**
     * Set decision value
     * @param value numeric value for the decision
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setValue(double value) {
        this.value = value;
        return this;
    }

    /**
     * Set decision content/description
     * @param content textual description of the decision
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Set decision type
     * @param type type of decision (AWARD, DISCIPLINE, PROMOTION, etc.)
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setType(@NonNull DecisionEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Set decision date
     * @param date date when decision was made
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setDate(@NonNull String date) {
        this.date = date;
        return this;
    }

    /**
     * Set effective date
     * @param effectiveDate date when decision becomes effective
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setEffectiveDate(@NonNull String effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    /**
     * Set user ID
     * @param userId ID of the user affected by this decision
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Set salary promotion ID (for INCREASE_SALARY decisions)
     * @param salaryPromotionId ID of the salary promotion
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setSalaryPromotionId(Integer salaryPromotionId) {
        this.salaryPromotionId = salaryPromotionId;
        return this;
    }

    /**
     * Set position ID (for PROMOTION decisions)
     * @param positionId ID of the new position
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setPositionId(String positionId) {
        this.positionId = positionId;
        return this;
    }

    /**
     * Set seniority allowance rule ID (for SENIORITY_ALLOWANCE decisions)
     * @param seniorityAllowanceRuleId ID of the seniority allowance rule
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder setSeniorityAllowanceRuleId(Integer seniorityAllowanceRuleId) {
        this.seniorityAllowanceRuleId = seniorityAllowanceRuleId;
        return this;
    }

    /**
     * Convenience method to set promotion-specific fields
     * @param positionId ID of the new position
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder forPromotion(String positionId) {
        this.positionId = positionId;
        this.type = DecisionEnum.PROMOTION;
        return this;
    }

    /**
     * Convenience method to set salary increase-specific fields
     * @param salaryPromotionId ID of the salary promotion
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder forSalaryIncrease(Integer salaryPromotionId) {
        this.salaryPromotionId = salaryPromotionId;
        this.type = DecisionEnum.INCREASE_SALARY;
        return this;
    }

    /**
     * Convenience method to set seniority allowance-specific fields
     * @param seniorityAllowanceRuleId ID of the seniority allowance rule
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder forSeniorityAllowance(Integer seniorityAllowanceRuleId) {
        this.seniorityAllowanceRuleId = seniorityAllowanceRuleId;
        this.type = DecisionEnum.SENIORITY_ALLOWANCE;
        return this;
    }

    /**
     * Clear all optional fields (salaryPromotionId, positionId, seniorityAllowanceRuleId)
     * Useful when switching between different decision types
     * @return this builder for method chaining
     */
    public DecisionRequestBuilder clearOptionalFields() {
        this.salaryPromotionId = null;
        this.positionId = null;
        this.seniorityAllowanceRuleId = null;
        return this;
    }

    /**
     * Validate required fields before building
     * @throws IllegalStateException if required fields are missing
     */
    private void validate() {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalStateException("Decision ID is required");
        }
        if (type == null) {
            throw new IllegalStateException("Decision type is required");
        }
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalStateException("Decision date is required");
        }
        if (effectiveDate == null || effectiveDate.trim().isEmpty()) {
            throw new IllegalStateException("Effective date is required");
        }
        if (userId <= 0) {
            throw new IllegalStateException("Valid user ID is required");
        }

        // Validate decision-specific requirements
        validateDecisionSpecificFields();
    }

    /**
     * Validate decision-specific required fields
     * @throws IllegalStateException if decision-specific required fields are missing
     */
    private void validateDecisionSpecificFields() {
        switch (type) {
            case PROMOTION:
                if (positionId == null || positionId.trim().isEmpty()) {
                    throw new IllegalStateException("Position ID is required for promotion decisions");
                }
                break;
            case INCREASE_SALARY:
                if (salaryPromotionId == null) {
                    throw new IllegalStateException("Salary promotion ID is required for salary increase decisions");
                }
                break;
            case SENIORITY_ALLOWANCE:
                if (seniorityAllowanceRuleId == null) {
                    throw new IllegalStateException("Seniority allowance rule ID is required for seniority allowance decisions");
                }
                break;
            // Other decision types don't have specific requirements
            case AWARD:
            case DISCIPLINE:
            case TERMINATION:
            default:
                break;
        }
    }

    /**
     * Build the DecisionRequest object
     * @return new DecisionRequest instance with all configured values
     * @throws IllegalStateException if required fields are missing or invalid
     */
    public DecisionRequest build() {
        validate();

        return new DecisionRequest(
                id,
                value,
                content,
                type,
                date,
                effectiveDate,
                userId,
                salaryPromotionId,
                positionId,
                seniorityAllowanceRuleId
        );
    }

    /**
     * Build without validation (use with caution)
     * @return new DecisionRequest instance without validation
     */
    public DecisionRequest buildUnsafe() {
        return new DecisionRequest(
                id,
                value,
                content,
                type,
                date,
                effectiveDate,
                userId,
                salaryPromotionId,
                positionId,
                seniorityAllowanceRuleId
        );
    }

    /**
     * Check if the builder has all required fields
     * @return true if all required fields are set, false otherwise
     */
    public boolean isValid() {
        try {
            validate();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /**
     * Get validation error message
     * @return error message if validation fails, null if valid
     */
    public String getValidationError() {
        try {
            validate();
            return null;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String toString() {
        return "DecisionRequestBuilder{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", userId=" + userId +
                ", salaryPromotionId=" + salaryPromotionId +
                ", positionId='" + positionId + '\'' +
                ", seniorityAllowanceRuleId=" + seniorityAllowanceRuleId +
                '}';
    }
}