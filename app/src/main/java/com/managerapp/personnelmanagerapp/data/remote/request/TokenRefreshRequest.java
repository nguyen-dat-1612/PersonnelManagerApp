package com.managerapp.personnelmanagerapp.data.remote.request;

import com.google.gson.annotations.SerializedName;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Objects;

public class TokenRefreshRequest implements Serializable {
    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("grant_type")
    private String grantType = "refresh_token";

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("scope")
    private String scope;

    // Constructor mặc định
    public TokenRefreshRequest() {}

    // Constructor cơ bản chỉ với refresh token
    @Inject
    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Constructor đầy đủ
    public TokenRefreshRequest(
            String refreshToken,
            String clientId,
            String clientSecret,
            String scope
    ) {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    // Getters
    public String getRefreshToken() {
        return refreshToken;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }

    // Setters
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    // Kiểm tra tính hợp lệ của request
    public boolean isValid() {
        return refreshToken != null && !refreshToken.isEmpty() &&
                (clientId == null || !clientId.isEmpty()) &&
                (clientSecret == null || !clientSecret.isEmpty());
    }

    // Equals và HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenRefreshRequest that = (TokenRefreshRequest) o;
        return Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(grantType, that.grantType) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(clientSecret, that.clientSecret) &&
                Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken, grantType, clientId, clientSecret, scope);
    }

    // ToString
    @Override
    public String toString() {
        return "TokenRefreshRequest{" +
                "refreshToken='" + refreshToken + '\'' +
                ", grantType='" + grantType + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + (clientSecret != null ? "[REDACTED]" : "null") + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }

    // Builder Pattern
    public static class Builder {
        private String refreshToken;
        private String grantType = "refresh_token";
        private String clientId;
        private String clientSecret;
        private String scope;

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder grantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public TokenRefreshRequest build() {
            return new TokenRefreshRequest(
                    refreshToken,
                    clientId,
                    clientSecret,
                    scope
            );
        }
    }
}
