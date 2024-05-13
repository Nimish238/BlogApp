package com.example.Nimish.BlogApp.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    public void setToken(String token) {
        this.token = token;

    }
}
