package com.example.Nimish.BlogApp.services.impl;

import com.example.Nimish.BlogApp.config.appConstants;
import com.example.Nimish.BlogApp.entities.role;
import com.example.Nimish.BlogApp.entities.user;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.payloads.JwtAuthRequest;
import com.example.Nimish.BlogApp.payloads.userDto;
import com.example.Nimish.BlogApp.repositories.roleRepo;
import com.example.Nimish.BlogApp.repositories.userRepo;
import com.example.Nimish.BlogApp.services.userService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService {


    @Autowired
    private userRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private roleRepo roleRepo;

    @Override
    public userDto registerNewUser(userDto userDto) {

        user user = this.modelMapper.map(userDto,user.class);

        //encode password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //roles
        role role = this.roleRepo.findById(appConstants.ROLE_NORMAL).get();
        user.getRoles().add(role);


        user newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser,userDto.class);
    }

    @Override
    public userDto createUser(userDto userDto) {

//        user user = this.dtoToUser(userDto);

        user user = this.modelMapper.map(userDto,user.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        role role = this.roleRepo.findById(appConstants.ROLE_NORMAL).get();
        user.getRoles().add(role);


        user savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser,userDto.class);

    }

    @Override
    public userDto updateUser(userDto userDto, Integer userId) {

        user user = this.userRepo.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("user","id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

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

    @Override
    public boolean resetPassword(JwtAuthRequest request) {

        Optional<user> u = this.userRepo.findByEmail(request.getUsername());
        if(u.isPresent()){
            user usr = this.userRepo.findByEmail(request.getUsername()).orElseThrow(() ->new ResourceNotFoundException("user","username"+request.getUsername(),0));
            usr.setPassword(passwordEncoder.encode(request.getPassword()));
            this.userRepo.save(usr);
            return true;
        }
        return false;
    }

    public user dtoToUser(userDto userDto){

        user user = this.modelMapper.map(userDto,user.class);
        return user;
    }

    public userDto userToDto(user user){

        userDto userDto = this.modelMapper.map(user,userDto.class);

        return userDto;
    }

    public boolean checkUniqueEmail(String email){

        Optional<user> u = this.userRepo.findByEmail(email);
        return u.isPresent();
    }
}
