package com.example.Nimish.BlogApp.services;


import com.example.Nimish.BlogApp.payloads.postDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public interface fileService {
        String uploadImage(String path, MultipartFile file )throws IOException;
        InputStream getResource(String path,String fileName)throws FileNotFoundException;
        //update
        postDto updatePostImage(postDto postDto , Integer postId);

}
