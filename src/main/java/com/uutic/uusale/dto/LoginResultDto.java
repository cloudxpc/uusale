package com.uutic.uusale.dto;

public class LoginResultDto {
    private String userType;
    private String token;

    public LoginResultDto(String userType, String token){
        this.userType = userType;
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
