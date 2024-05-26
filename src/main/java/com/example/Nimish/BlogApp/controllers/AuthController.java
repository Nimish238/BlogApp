package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.payloads.responseDto;
import com.example.Nimish.BlogApp.exceptions.apiException;
import com.example.Nimish.BlogApp.payloads.*;
import com.example.Nimish.BlogApp.security.JwtTokenHelper;
import com.example.Nimish.BlogApp.services.userService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private userService userService;

    @PostMapping("/login")
    public ResponseEntity<responseDto> createToken(@RequestBody JwtAuthRequest request) {

        try{
            this.authenticate(request.getUsername(),request.getPassword());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
            String token =  this.jwtTokenHelper.generateToken(userDetails);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setId(this.userService.getUserId(request.getUsername()));
            response.setUsername(request.getUsername());
            response.setToken(token);
            response.setRole(userDetails.getAuthorities());


            return new ResponseEntity<>(new responseDto(response,"Token generated successfully",false), HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(new responseDto(null,e.getMessage(),true),HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/register")
    public ResponseEntity<responseDto> registerUser(@Valid @RequestBody userDto userDto){

            try {
                if(userService.checkUniqueEmail(userDto.getEmail())) {
                   throw new Exception("Email already exists,try another one!");
                }

                userDto registeredUser = this.userService.registerNewUser(userDto);

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDto.getEmail());
                String token = this.jwtTokenHelper.generateToken(userDetails);

                JwtAuthResponse response = new JwtAuthResponse();
                response.setId(this.userService.getUserId(userDetails.getUsername()));
                response.setUsername(userDto.getEmail());
                response.setToken(token);
                response.setRole(userDetails.getAuthorities());


                return new ResponseEntity<responseDto>(new responseDto(response,"User created successfully!!",false),HttpStatus.CREATED);

            } catch (Exception e) {
                throw new apiException(e.getMessage());
            }

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<apiResponse>  resetPassword(@RequestBody JwtAuthRequest request){

        try{
            String response = this.userService.resetPassword(request)?
                    "Password changed successfully!!" :"Enter correct email address";
            return new ResponseEntity<>(new apiResponse(response,true),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new apiResponse(e.getMessage(),false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken =new
                UsernamePasswordAuthenticationToken(username,password);

        try{
            this.authenticationManager.authenticate(authenticationToken);
        }catch(BadCredentialsException e){
            throw new apiException("Invalid username or password");
        }

    }


}
