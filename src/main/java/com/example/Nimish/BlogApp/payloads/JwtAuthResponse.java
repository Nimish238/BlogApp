package com.example.Nimish.BlogApp.payloads;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


@Data
public class JwtAuthResponse {

    private String token;

    private String userName;

    private Integer id;

    private Collection<? extends GrantedAuthority> role;

    public void setToken(String token) {
        this.token = token;

    }

    public void setUsername(String username) {
        this.userName = username;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setRole(Collection<? extends GrantedAuthority> role) {
        this.role = role;
    }
}
