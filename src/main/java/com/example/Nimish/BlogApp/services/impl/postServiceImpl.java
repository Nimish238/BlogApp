package com.example.Nimish.BlogApp.services.impl;

import com.example.Nimish.BlogApp.entities.category;
import com.example.Nimish.BlogApp.entities.post;
import com.example.Nimish.BlogApp.entities.user;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.payloads.postDto;
import com.example.Nimish.BlogApp.payloads.postResponse;
import com.example.Nimish.BlogApp.repositories.postRepo;
import com.example.Nimish.BlogApp.services.postService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Nimish.BlogApp.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class postServiceImpl implements postService {

    @Autowired
    private postRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private categoryRepo categoryRepo;

    @Autowired
    private userRepo userRepo;



    @Override
    public postDto createPost(postDto postDto, Integer userId, Integer categoryId,String path, MultipartFile file) throws IOException {

        user user = this.userRepo.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("user","user id",userId));

        category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() ->new ResourceNotFoundException("category","category id",categoryId));

        post post = this.modelMapper.map(postDto, post.class);

        if(file!=null){
            //file name
            String name=file.getOriginalFilename();
            //abc.png

            //random name generate file
            String randomID= UUID.randomUUID().toString();
            String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));

            //fullPath
            String filePath = path+ File.separator +fileName1;

            //create folder if not created
            File f=new File(path);
            if(!f.exists()){
                f.mkdir();
            }

            //file copy
            Files.copy(file.getInputStream(), Paths.get(filePath));

            post.setImageName(fileName1);
        }
        else {
            post.setImageName("defaultImage.png");
        }


        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost,postDto.class);
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);

        //db logic to return input stream
        return is;
    }



    @Override
    public postDto updatePost(postDto postDto, Integer postId) {

        post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post","post id",postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
//        post.setImageName(postDto.getImageName());

        post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost,postDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        post post = this.postRepo.findById(postId)
                .orElseThrow(() ->new ResourceNotFoundException("post","post id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public  postResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable p =  PageRequest.of(pageNumber,pageSize, sort);
        Page<post> pagePost= this.postRepo.findAll(p);
        List<post> allPost = pagePost.getContent();

//        List<post> allPost = this.postRepo.findAll();
        List<postDto> postDos = allPost.stream().map(post -> this.modelMapper.map(post,postDto.class))
                .collect(Collectors.toList());

        postResponse postResponse = new postResponse();
        postResponse.setContent(postDos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    @Override
    public postDto getPostById(Integer postId) {
        post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post","post id",postId));

        return this.modelMapper.map(post,postDto.class);
    }


    @Override
    public List<postDto> getPostsByCategory(Integer categoryId) {
        category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() ->new ResourceNotFoundException("category","category id",categoryId));
        List<post> posts = this.postRepo.findByCategory(cat);

        List<postDto> postDos = posts.stream().map(post ->this.modelMapper.map(post,postDto.class))
                .collect(Collectors.toList());
        return postDos;
    }

    @Override
    public List<postDto> getPostsByUser(Integer userId) {
        user user = this.userRepo.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("user","user id",userId));
        List<post> posts = this.postRepo.findByUser(user);

        List<postDto> postDos =   posts.stream().map(post ->this.modelMapper.map(post,postDto.class))
                .collect(Collectors.toList());
        return postDos;
    }

    @Override
    public List<postDto> searchPosts(String keyword) {
        List<post> posts = this.postRepo.findByTitleContaining(keyword);

       List<postDto> postDos= posts.stream().map((post) ->this.modelMapper.map(post, postDto.class)).collect(Collectors.toList());
        return postDos;
    }



}
