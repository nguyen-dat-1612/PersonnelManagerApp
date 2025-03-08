package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.UserModel;

public class LoginResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private TokenData data;

    public int getCode() {
        return code;
    }

    public TokenData getData() {
        return data;
    }

    public static class TokenData {
        @SerializedName("accessToken")
        private String accessToken;

        @SerializedName("refreshToken")
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
