package com.example.Nimish.BlogApp.repositories;

import com.example.Nimish.BlogApp.entities.category;
import com.example.Nimish.BlogApp.entities.post;
import com.example.Nimish.BlogApp.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface postRepo extends JpaRepository<post, Integer> {

    List<post> findByUser(user user);
    List<post> findByCategory(category category);
    List<post> findByTitleContaining(String title);

}
