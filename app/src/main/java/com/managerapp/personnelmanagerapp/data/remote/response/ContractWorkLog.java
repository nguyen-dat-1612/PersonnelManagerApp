package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class ContractWorkLog extends WorkLogResponse{
    @SerializedName("contract")
    public ContractResponse contractResponse;

    public ContractResponse getContractResponse() {
        return contractResponse;
    }
}
