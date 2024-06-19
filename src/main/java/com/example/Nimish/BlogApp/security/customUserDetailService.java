package com.example.Nimish.BlogApp.security;

import com.example.Nimish.BlogApp.entities.user;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.repositories.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class customUserDetailService implements UserDetailsService {

    @Autowired
    private userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
        user user= this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user","email"+ " :" +username,0));

        return user;
    }
}
