package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.services.fileService;
import com.example.Nimish.BlogApp.config.appConstants;
import com.example.Nimish.BlogApp.payloads.apiResponse;
import com.example.Nimish.BlogApp.payloads.postDto;
import com.example.Nimish.BlogApp.payloads.postResponse;
import com.example.Nimish.BlogApp.services.postService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Getter
@Setter
@NoArgsConstructor
public class postController {


    @Autowired
    private postService postService;

    @Autowired
    private fileService fileService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<postDto> createPost(
                                              @RequestParam("postData")String postData,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId,
                                              @RequestParam(value = "image",required = false)MultipartFile image
                                             )throws IOException
    {
            postDto convertedData=null;
            convertedData =  mapper.readValue(postData,postDto.class);
            postDto createPost = this.postService.createPost(convertedData, userId, categoryId,path,image);
            return new ResponseEntity<postDto>(createPost, HttpStatus.CREATED);
    }

    //upload image
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<postDto> uploadPostImage(
            @RequestParam(value = "image",required = false)MultipartFile image,
            @PathVariable Integer postId
    )throws IOException
    {
        postDto postDto = this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);

        postDto.setImageName(fileName);
        postDto updatePost = this.fileService.updatePostImage(postDto,postId);
        return new ResponseEntity<postDto>(updatePost,HttpStatus.OK);

    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<postDto> updatePost(@RequestBody postDto postDto,@PathVariable Integer postId){
        postDto updatePost = this.postService.updatePost(postDto ,postId);
        return new ResponseEntity<postDto>(updatePost,HttpStatus.OK);
    }



    //get By user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<postDto>> getPostsByUser(@PathVariable Integer userId){

        List<postDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<postDto>>(posts,HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<postDto>> getPostsByCategory(@PathVariable Integer categoryId){

        List<postDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<postDto>>(posts,HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<postResponse> getAllPost(
            @RequestParam(value ="pageNumber",defaultValue = appConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = appConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = appConstants.PAGE_SIZE,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = appConstants.SORT_DIR,required = false)String sortDir
    ){
        postResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<postResponse>(postResponse,HttpStatus.OK);
    }

    //get post details by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<postDto> getPostById(@PathVariable Integer postId){

        postDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<postDto>(postDto,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public apiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new apiResponse("Post is successfully deleted",true);
    }



    //search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<postDto>> searchPostByTitle(
            @PathVariable("keywords") String keywords
    ){

        List<postDto> result=this.postService.searchPosts(keywords);
        return new ResponseEntity<List<postDto>>(result,HttpStatus.OK);

    }



    //get image
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    )throws IOException{
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }



}
