package com.managerapp.personnelmanagerapp.data.remote.response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WorkLogResponse {
    private String type;
    private Contract contract;

    public String getType() {
        return type;
    }

    public Contract getContract() {
        return contract;
    }


    public static class Contract {
        private int id;
        private String startDate;
        private String endDate;
        private int basicSalary;
        private String clause;
        private String contractStatusEnum;
        private String contractTypeName;
        private Signer signer;
        private String positionName;
        private String jobGradeName;

        public int getId() {
            return id;
        }


        public String getStartDate() {
            LocalDate date = LocalDate.parse(startDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }


        public String getEndDate() {
            LocalDate date = LocalDate.parse(endDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }



        public String getDateRange() {
            return "🗓 Từ: " + getStartDate() + " đến " + getEndDate();
        }

        public String getSalaryRange() {
            return "💰 Lương cơ bản: " + getBasicSalary() + " VND";
        }

        public String getClauseRange() {
            return "📜 Điều khoản: " + getClause();
        }

        public String getContractStatus() {
            return "⏳ Trạng thái: " + getContractStatusEnum();
        }

        public String getContractType() {
            return "📄 Loại hợp đồng: " + getContractTypeName() + " (" + getJobGradeName() + ")";
        }
        public String getSignerName() {
            return "👤 Người kí: "+ getSigner().getFullName();
        }

        public int getBasicSalary() {
            return basicSalary;
        }

        public String getClause() {
            return clause;
        }


        public String getContractStatusEnum() {
            return contractStatusEnum;
        }


        public String getContractTypeName() {
            return contractTypeName;
        }

        public Signer getSigner() {
            return signer;
        }


        public String getPositionName() {
            return positionName;
        }


        public String getJobGradeName() {
            return jobGradeName;
        }

    }

    public static class Signer {
        private int id;
        private String fullName;

        public int getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }
    }
}
