package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContractListResponse {
    @SerializedName("content")
    private List<ContractResponse> content;

    public List<ContractResponse> getContent() {
        return content;
    }

    public void setContent(List<ContractResponse> content) {
        this.content = content;
    }
}