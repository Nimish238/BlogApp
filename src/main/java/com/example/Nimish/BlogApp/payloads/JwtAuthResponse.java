package com.example.Nimish.BlogApp.payloads;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


@Data
public class JwtAuthResponse {


    private String token;

    private String username;

    private Integer id;

    private String name;



    private Collection<? extends GrantedAuthority> role;


//    private userDto user;
//    public void setUser(userDto user){
//        this.user=user;
//    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public void setName(String name) {
        this.name= name;
    }
}
