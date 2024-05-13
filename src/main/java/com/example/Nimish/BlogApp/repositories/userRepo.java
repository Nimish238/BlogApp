package com.example.Nimish.BlogApp.repositories;

import com.example.Nimish.BlogApp.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepo extends JpaRepository<user,Integer> {

    Optional<user> findByEmail(String email);

}
