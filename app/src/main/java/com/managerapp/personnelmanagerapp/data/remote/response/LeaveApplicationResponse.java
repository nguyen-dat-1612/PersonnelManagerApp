package com.managerapp.personnelmanagerapp.data.remote.response;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class LeaveApplicationResponse {
    private long id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private FormStatusEnum formStatusEnum;
    private UserSummaryResponse user;
    private UserSummaryResponse signer;
    private String leaveTypeName;

    public long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public FormStatusEnum getFormStatusEnum() {
        return formStatusEnum;
    }

    public UserSummaryResponse getUser() {
        return user;
    }

    public UserSummaryResponse getSigner() {
        return signer;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }
}