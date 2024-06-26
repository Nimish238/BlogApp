package com.example.Nimish.BlogApp.services.impl;

import com.example.Nimish.BlogApp.entities.post;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.payloads.postDto;
import com.example.Nimish.BlogApp.services.fileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.Nimish.BlogApp.repositories.postRepo;
import org.modelmapper.ModelMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class fileServiceImpl implements fileService {

    @Autowired
    private postRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

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

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);

        //db logic to return input stream
        return is;
    }

    @Override
    public postDto updatePostImage(postDto postDto, Integer postId) {
        post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post","post id",postId));


        post.setImageName(postDto.getImageName());

        post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost,postDto.class);
    }
}
