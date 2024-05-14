package com.example.Nimish.BlogApp.services;

import com.example.Nimish.BlogApp.payloads.userDto;

import java.util.List;

public interface userService {

    userDto registerNewUser(userDto user);

    userDto createUser(userDto user);

    userDto updateUser(userDto user,Integer userId);

    userDto getUserById(Integer userId);

    List<userDto> getAllUsers();

    void deleteUser(Integer userId);
}
