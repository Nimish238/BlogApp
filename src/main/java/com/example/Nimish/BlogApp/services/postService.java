package com.example.Nimish.BlogApp.services;

import com.example.Nimish.BlogApp.entities.post;
import com.example.Nimish.BlogApp.payloads.postDto;
import com.example.Nimish.BlogApp.payloads.postResponse;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface postService {

    //create
    postDto createPost(postDto postDto,Integer userId,Integer categoryId,String path, MultipartFile file)throws IOException;

    InputStream getResource(String path,String fileName)throws FileNotFoundException;
    //update
    postDto updatePost(postDto postDto , Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all post
    postResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    //get single post
    postDto getPostById(Integer postId);

    //get all post by category
    List<postDto> getPostsByCategory(Integer categoryId);

    //get all post by user
    List<postDto> getPostsByUser(Integer userId);

    //search posts
    List<postDto> searchPosts(String keyword);

//    String uploadImage(String path, MultipartFile file )throws IOException;
//    InputStream getResource(String path, String fileName)throws FileNotFoundException;


}
