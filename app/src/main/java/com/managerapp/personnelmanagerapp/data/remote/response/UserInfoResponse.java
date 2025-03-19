package com.managerapp.personnelmanagerapp.data.remote.response;

import com.managerapp.personnelmanagerapp.domain.model.User;

public class UserInfoResponse {
    private String status;
    private User data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}



