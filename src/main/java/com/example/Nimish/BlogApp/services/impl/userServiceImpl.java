package com.example.Nimish.BlogApp.services.impl;

import com.example.Nimish.BlogApp.entities.user;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.payloads.userDto;
import com.example.Nimish.BlogApp.repositories.userRepo;
import com.example.Nimish.BlogApp.services.userService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService {


    @Autowired
    private userRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public userDto createUser(userDto userDto) {

        user user = this.dtoToUser(userDto);

        user savedUser = this.userRepo.save(user);

        return this.userToDto(savedUser);

    }

    @Override
    public userDto updateUser(userDto userDto, Integer userId) {

        user user = this.userRepo.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("user","id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        user updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }

    @Override
    public userDto getUserById(Integer userId) {

        user user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user" ,"id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<userDto> getAllUsers() {
        List<user> users = this.userRepo.findAll();
        List<userDto> userDos = users.stream().map(user->this.userToDto(user))
                .collect(Collectors.toList());
        return userDos;

    }

    @Override
    public void deleteUser(Integer userId) {

        user user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user" ,"id", userId));
        this.userRepo.delete(user);

    }

    public user dtoToUser(userDto userDto){

//        user user= new user();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());

        user user = this.modelMapper.map(userDto,user.class);

        return user;
    }

    public userDto userToDto(user user){

//        userDto userDto = new userDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        userDto userDto = this.modelMapper.map(user,userDto.class);

        return userDto;
    }
}
