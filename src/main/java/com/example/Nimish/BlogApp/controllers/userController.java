package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.payloads.apiResponse;
import com.example.Nimish.BlogApp.payloads.userDto;
import com.example.Nimish.BlogApp.services.userService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    // POST - CREATE user
    @PostMapping("/")
    public ResponseEntity<userDto> createUser(@Valid @RequestBody userDto userDto){
        userDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - UPDATE user
    @PutMapping("/{userId}")
    public ResponseEntity<userDto> updateUser(@Valid @RequestBody userDto userDto, @PathVariable("userId") Integer uId){
        userDto updatedUser= this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<apiResponse> deleteUser( @PathVariable("userId") Integer uId){
        this.userService.deleteUser(uId);
        return new ResponseEntity<>(new apiResponse("user deleted successfully",true),HttpStatus.OK);
    }

    //GET - get user
    @GetMapping("/")
    public ResponseEntity<List<userDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //GET -get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<userDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

}
