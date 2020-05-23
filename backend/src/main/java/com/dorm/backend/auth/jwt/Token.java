package com.dorm.backend.auth.jwt;

import java.util.Date;

public class Token {
    private String token;
    private Date expirationDate;

    public Token(String token, Date expireDate) {
        this.token = token;
        this.expirationDate = expireDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expireDate) {
        this.expirationDate = expireDate;
    }
}
