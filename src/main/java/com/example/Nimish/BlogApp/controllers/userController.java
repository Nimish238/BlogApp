package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.payloads.apiResponse;
import com.example.Nimish.BlogApp.payloads.responseDto;
import com.example.Nimish.BlogApp.payloads.userDto;
import com.example.Nimish.BlogApp.services.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.Nimish.BlogApp.security.JwtTokenHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    // POST - CREATE user
    @PostMapping("/createUser")
    public ResponseEntity<userDto> createUser(@Valid @RequestBody userDto userDto){
        userDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - UPDATE user
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<userDto> updateUser(@Valid @RequestBody userDto userDto,
                                              @PathVariable("userId") Integer uId){

        userDto updatedUser= this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
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
