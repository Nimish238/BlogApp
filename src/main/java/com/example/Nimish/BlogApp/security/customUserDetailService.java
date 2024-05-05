package com.example.Nimish.BlogApp.security;

import com.example.Nimish.BlogApp.entities.user;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.repositories.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class customUserDetailService implements UserDetailsService {

    @Autowired
    private userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //load user from database as username
        user user=  this.userRepo.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("user","name :"+username,0));

        return user;
    }
}
