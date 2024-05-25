package com.example.Nimish.BlogApp.payloads;

import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
;import java.util.Collection;


@Data
@Setter
public class JwtAuthResponse {

    private String token;

    private String userName;

    private Integer id;

//    private Collection<? extends GrantedAuthority> role;

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
}
