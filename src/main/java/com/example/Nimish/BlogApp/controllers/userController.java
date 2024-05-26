package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.exceptions.apiException;
import com.example.Nimish.BlogApp.payloads.JwtAuthResponse;
import com.example.Nimish.BlogApp.payloads.apiResponse;
import com.example.Nimish.BlogApp.payloads.responseDto;
import com.example.Nimish.BlogApp.security.JwtTokenHelper;
import com.example.Nimish.BlogApp.payloads.userDto;
import com.example.Nimish.BlogApp.services.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    // POST - CREATE user
    @PostMapping("/createUser")
    public ResponseEntity<responseDto> createUser(@Valid @RequestBody userDto userDto){
        try{

            if(userService.checkUniqueEmail(userDto.getEmail())) {
                throw new Exception("Email already exists,try another one!");
            }

            userDto registeredUser= this.userService.createUser(userDto);

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDto.getEmail());
            String token = this.jwtTokenHelper.generateToken(userDetails);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setId(this.userService.getUserId(userDetails.getUsername()));
            response.setUsername(userDto.getEmail());
            response.setToken(token);
            response.setRole(userDetails.getAuthorities());


            return new ResponseEntity<responseDto>(new responseDto(response,"User created successfully!!",false), HttpStatus.CREATED);

        }catch(Exception e){
            throw new apiException(e.getMessage());
        }

    }

    //PUT - UPDATE user
    @PutMapping("/updateUser")
    public ResponseEntity<userDto> updateUser(@Valid @RequestBody userDto userDto,
                                                  HttpServletRequest request){
        try{
            String reqHeader = request.getHeader("Authorization");
            String reqToken = reqHeader.substring(7);

            String email = this.jwtTokenHelper.getUsernameFromToken(reqToken);

            if(!email.equals(userDto.getEmail()) && userDto.getEmail()!=null){
                throw new Exception("Can't modify");
            }

            userDto.setEmail(email);
            userDto updatedUser= this.userService.updateUser(userDto,this.userService.getUserId(email));
            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }catch(Exception e){
            throw new apiException(e.getMessage());
        }

    }

    //DELETE - delete user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<apiResponse> deleteUser( @PathVariable("userId") Integer uId){
        this.userService.deleteUser(uId);
        return new ResponseEntity<>(new apiResponse( "user deleted successfully" ,true),HttpStatus.OK);
    }

    //GET - get user
    @GetMapping("/getAllUsers/")
    public ResponseEntity<List<userDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //GET -get user by id
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<userDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }




}
