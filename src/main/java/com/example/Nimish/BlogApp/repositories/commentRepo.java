package com.example.Nimish.BlogApp.repositories;

import com.example.Nimish.BlogApp.entities.comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepo extends JpaRepository<comment,Integer> {
}
