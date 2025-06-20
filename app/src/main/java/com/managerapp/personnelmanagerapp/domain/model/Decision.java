package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Objects;

public class Decision {
    private final String id;
    private final String attachment;
    private final String content;
    private final double value;
    private final DecisionEnum type;
    private final String date;
    private final UserSummary signer;
    private final UserSummary user;
    private final SeniorityAllowanceRule seniorityAllowanceRule;
    private final SalaryPromotion salaryPromotion;
    private final Position position;

    private Decision(Builder builder) {
        this.id = builder.id;
        this.attachment = builder.attachment;
        this.content = builder.content;
        this.value = builder.value;
        this.type = builder.type;
        this.date = builder.date;
        this.signer = builder.signer;
        this.user = builder.user;
        this.seniorityAllowanceRule = builder.seniorityAllowanceRule;
        this.salaryPromotion = builder.salaryPromotion;
        this.position = builder.position;
    }

    public String getId() {
        return id;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getContent() {
        return content;
    }

    public double getValue() {
        return value;
    }

    public DecisionEnum getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public UserSummary getSigner() {
        return signer;
    }

    public UserSummary getUser() {
        return user;
    }

    public SeniorityAllowanceRule getSeniorityAllowanceRule() {
        return seniorityAllowanceRule;
    }

    public SalaryPromotion getSalaryPromotion() {
        return salaryPromotion;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Decision)) return false;
        Decision decision = (Decision) o;
        return Double.compare(decision.value, value) == 0 &&
                Objects.equals(id, decision.id) &&
                Objects.equals(attachment, decision.attachment) &&
                Objects.equals(content, decision.content) &&
                type == decision.type &&
                Objects.equals(date, decision.date) &&
                Objects.equals(signer, decision.signer) &&
                Objects.equals(user, decision.user) &&
                Objects.equals(seniorityAllowanceRule, decision.seniorityAllowanceRule) &&
                Objects.equals(salaryPromotion, decision.salaryPromotion) &&
                Objects.equals(position, decision.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attachment, content, value, type, date, signer, user, seniorityAllowanceRule, salaryPromotion, position);
    }

    public static class Builder {
        private String id;
        private String attachment;
        private String content;
        private double value;
        private DecisionEnum type;
        private String date;
        private UserSummary signer;
        private UserSummary user;
        private SeniorityAllowanceRule seniorityAllowanceRule;
        private SalaryPromotion salaryPromotion;
        private Position position;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder attachment(String attachment) {
            this.attachment = attachment;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder value(double value) {
            this.value = value;
            return this;
        }

        public Builder type(DecisionEnum type) {
            this.type = type;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder signer(UserSummary signer) {
            this.signer = signer;
            return this;
        }

        public Builder user(UserSummary user) {
            this.user = user;
            return this;
        }

        public Builder seniorityAllowanceRule(SeniorityAllowanceRule seniorityAllowanceRule) {
            this.seniorityAllowanceRule = seniorityAllowanceRule;
            return this;
        }

        public Builder salaryPromotion(SalaryPromotion salaryPromotion) {
            this.salaryPromotion = salaryPromotion;
            return this;
        }

        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        public Decision build() {
            return new Decision(this);
        }
    }
}
