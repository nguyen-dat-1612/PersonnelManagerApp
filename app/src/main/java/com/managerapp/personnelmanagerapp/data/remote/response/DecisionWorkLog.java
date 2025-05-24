package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class DecisionWorkLog extends WorkLogResponse {
    @SerializedName("decision")
    public DecisionResponse decisionResponse;

    public DecisionResponse getDecisionResponse() {
        return decisionResponse;
    }
}
