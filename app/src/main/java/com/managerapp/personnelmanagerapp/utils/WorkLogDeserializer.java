package com.managerapp.personnelmanagerapp.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractWorkLog;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionWorkLog;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;

import java.lang.reflect.Type;

public class WorkLogDeserializer implements JsonDeserializer<WorkLogResponse> {
    @Override
    public WorkLogResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();

        switch (type) {
            case "CONTRACT_SIGN", "CONTRACT_RENEWAL":
                return context.deserialize(obj, ContractWorkLog.class);
            default:
                return context.deserialize(obj, DecisionWorkLog.class);
        }
    }

}