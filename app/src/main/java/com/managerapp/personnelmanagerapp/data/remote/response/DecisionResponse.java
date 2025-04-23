package com.managerapp.personnelmanagerapp.data.remote.response;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DecisionResponse {
    private String id;
    private String attachment;
    private String content;
    private int value;
    private String type;
    private String date;
    private Signer signer;
    private User user;
    private SeniorityAllowanceRule seniorityAllowanceRule;
    private Position position;

    public String getType() {
        return type;
    }

    public String getDisplayType() {
        if (type == null) return "Không xác định";

        switch (type.toUpperCase()) {
            case "AWARD":
                return "Khen thưởng";
            case "DISCIPLINE":
                return "Kỷ luật";
            case "PROMOTION":
                return "Thăng chức";
            case "INCREASE_SALARY":
                return "Tăng lương";
            case "SENIORITY_ALLOWANCE":
                return "Phụ cấp thâm niên";
            case "TERMINATION":
                return "Chấm dứt hợp đồng";
            default:
                return "Không xác định";
        }
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

    public int getValue() {
        return value;
    }

    public String getDate() {
        if (date == null || date.isEmpty()) return "";

        try {
            // Parse chuỗi ISO date
            Instant instant = Instant.parse(date);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

            // Format lại theo định dạng mong muốn
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return formatter.format(zonedDateTime);

        } catch (DateTimeParseException e) {
            return date; // fallback nếu parse lỗi
        }
    }

    public Signer getSigner() {
        return signer;
    }

    public User getUser() {
        return user;
    }

    public SeniorityAllowanceRule getSeniorityAllowanceRule() {
        return seniorityAllowanceRule;
    }

    public Position getPosition() {
        return position;
    }

    public static class Signer {
        private long id;
        private String fullName;

        public String getFullName() {
            return fullName;
        }

        public long getId() {
            return id;
        }
    }

    public static class User {
        private long id;
        private String fullName;

        public long getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }
    }

    public static class SeniorityAllowanceRule {
        private long id;
        private int minService;
        private int seniorityPercentage;
        private int seniorityLeaveDay;
        private String effectiveDate;
        private String expiryDate;
        private String description;
        private Signer signer;

        public long getId() {
            return id;
        }

        public int getMinService() {
            return minService;
        }

        public int getSeniorityPercentage() {
            return seniorityPercentage;
        }

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public int getSeniorityLeaveDay() {
            return seniorityLeaveDay;
        }

        public String getDescription() {
            return description;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public Signer getSigner() {
            return signer;
        }
    }

    public static class Position {
        private String id;
        private String name;
        private String description;
        private Department department;
        private Role role;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getId() {
            return id;
        }

        public Department getDepartment() {
            return department;
        }

        public Role getRole() {
            return role;
        }

        public static class Department {
            private String id;
            private String name;
            private String description;

            public void setId(String id) {
                this.id = id;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }
        }

        public static class Role {
            private String id;
            private String description;

            public String getId() {
                return id;
            }

            public String getDescription() {
                return description;
            }

        }
    }

    @Override
    public String toString() {
        return "DecisionResponse{" +
                "id='" + id + '\'' +
                ", attachment='" + attachment + '\'' +
                ", content='" + content + '\'' +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", signer=" + signer +
                ", user=" + user +
                ", seniorityAllowanceRule=" + seniorityAllowanceRule +
                ", position=" + position +
                '}';
    }
}
