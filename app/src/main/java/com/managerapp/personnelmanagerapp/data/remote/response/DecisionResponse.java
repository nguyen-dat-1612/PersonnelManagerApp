package com.managerapp.personnelmanagerapp.data.remote.response;

public class DecisionResponse {
    private String id;
    private String attachment;
    private String content;
    private int value;
    private String date;
    private Signer signer;
    private User user;
    private SeniorityAllowanceRule seniorityAllowanceRule;
    private Position position;

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
        return date;
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
}
