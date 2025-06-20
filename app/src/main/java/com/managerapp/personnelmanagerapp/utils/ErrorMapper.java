package com.managerapp.personnelmanagerapp.utils;

import android.content.Context;
import com.managerapp.personnelmanagerapp.R;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class ErrorMapper {

    private final Context context;
    private final Map<Integer, Integer> errorCodeToResIdMap;

    @Inject
    public ErrorMapper(@ApplicationContext Context context) {
        this.context = context.getApplicationContext();
        errorCodeToResIdMap = new HashMap<>();

        errorCodeToResIdMap.put(1000, R.string.error_unauthorized);
        errorCodeToResIdMap.put(1001, R.string.error_invalid_jwt);
        errorCodeToResIdMap.put(1002, R.string.error_username_not_found);
        errorCodeToResIdMap.put(1003, R.string.error_wrong_password);
        errorCodeToResIdMap.put(1004, R.string.error_account_locked);
        errorCodeToResIdMap.put(1005, R.string.error_degree_not_found);
        errorCodeToResIdMap.put(1006, R.string.error_role_not_found);
        errorCodeToResIdMap.put(1007, R.string.error_user_not_found);
        errorCodeToResIdMap.put(1008, R.string.error_password_not_match);
        errorCodeToResIdMap.put(1009, R.string.error_department_not_found);
        errorCodeToResIdMap.put(1010, R.string.error_position_not_found);
        errorCodeToResIdMap.put(1011, R.string.error_cannot_be_deleted);
        errorCodeToResIdMap.put(1012, R.string.error_contract_type_name_existed);
        errorCodeToResIdMap.put(1013, R.string.error_contract_type_not_found);
        errorCodeToResIdMap.put(1014, R.string.error_department_name_exist);
        errorCodeToResIdMap.put(1015, R.string.error_position_name_exists);
        errorCodeToResIdMap.put(1016, R.string.error_job_grade_name_exists);
        errorCodeToResIdMap.put(1017, R.string.error_job_grade_not_found);
        errorCodeToResIdMap.put(1018, R.string.error_feedback_not_found);
        errorCodeToResIdMap.put(1019, R.string.error_user_terminated);
        errorCodeToResIdMap.put(1020, R.string.error_notification_not_found);
        errorCodeToResIdMap.put(1021, R.string.error_reward_decision_not_found);
        errorCodeToResIdMap.put(1022, R.string.error_assignment_not_found);
        errorCodeToResIdMap.put(1023, R.string.error_discipline_decision_not_found);
        errorCodeToResIdMap.put(1024, R.string.error_leave_type_not_found);
        errorCodeToResIdMap.put(1025, R.string.error_leave_application_not_found);
        errorCodeToResIdMap.put(1026, R.string.error_leave_balance_not_found);
        errorCodeToResIdMap.put(1027, R.string.error_leave_day_not_found);
        errorCodeToResIdMap.put(1028, R.string.error_seniority_allowance_rule_not_found);
        errorCodeToResIdMap.put(1029, R.string.error_contract_not_found);
        errorCodeToResIdMap.put(1030, R.string.error_extend_contract);
        errorCodeToResIdMap.put(1031, R.string.error_contract_overlap);
        errorCodeToResIdMap.put(1032, R.string.error_email_not_found);
        errorCodeToResIdMap.put(1033, R.string.error_job_grade_id_exists);
        errorCodeToResIdMap.put(1034, R.string.error_position_id_exists);
        errorCodeToResIdMap.put(1035, R.string.error_department_id_exist);
        errorCodeToResIdMap.put(1036, R.string.error_contract_not_eligible_for_renewal);
        errorCodeToResIdMap.put(1037, R.string.error_invalid_decision_type);
        errorCodeToResIdMap.put(1038, R.string.error_decision_already_exists);
        errorCodeToResIdMap.put(1039, R.string.error_decision_not_found);
        errorCodeToResIdMap.put(1040, R.string.error_salary_promotion_not_found);
        errorCodeToResIdMap.put(1041, R.string.error_salary_not_found);
        errorCodeToResIdMap.put(1042, R.string.error_salary_already_exists);
        errorCodeToResIdMap.put(1043, R.string.error_invalid_contract_user);
        errorCodeToResIdMap.put(1044, R.string.error_contract_invalid_status);
        errorCodeToResIdMap.put(1045, R.string.error_promotion_already_processed);
        errorCodeToResIdMap.put(1046, R.string.error_contract_update_failed);
        errorCodeToResIdMap.put(1047, R.string.error_decision_creation_failed);
        errorCodeToResIdMap.put(1048, R.string.error_leave_type_already_exists);
        errorCodeToResIdMap.put(1049, R.string.error_form_status_invalid);
        errorCodeToResIdMap.put(1050, R.string.error_invalid_leave_application);
        errorCodeToResIdMap.put(1051, R.string.error_invalid_month);
        errorCodeToResIdMap.put(1052, R.string.error_month_year_leave_balance_exists);
        errorCodeToResIdMap.put(1053, R.string.error_leave_balance_exceeded);
        errorCodeToResIdMap.put(1054, R.string.error_right_signer);
        errorCodeToResIdMap.put(1055, R.string.error_signer_is_user);
        errorCodeToResIdMap.put(1056, R.string.error_email_existed);
    }
    static {
    }

    public String getMessage(int errorCode) {
        Integer resId = errorCodeToResIdMap.get(errorCode);
        if (resId != null) {
            return context.getString(resId);
        } else {
            return context.getString(R.string.error_unknown);
        }
    }
}
