package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.entities.comment;
import com.example.Nimish.BlogApp.payloads.commentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Nimish.BlogApp.services.commentService;
import com.example.Nimish.BlogApp.payloads.apiResponse;


@RestController
@RequestMapping("/api/")
public class commentController {

    @Autowired
    private commentService commentService;

    @PostMapping("/user/{id}/post/{postId}/comments")
    public ResponseEntity<commentDto> createComment(@RequestBody commentDto comment,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer id)
    {

        commentDto createComment= this.commentService.createComment(comment,postId,id);
        return new ResponseEntity<commentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}/post/{postId}/comments/{commentId}")
    public ResponseEntity<apiResponse> deleteComment(@PathVariable Integer commentId,
                                                     @PathVariable Integer postId,
                                                     @PathVariable Integer id
                                                     ){

        this.commentService.deleteComment(commentId,postId,id);
        return new ResponseEntity<apiResponse>(new apiResponse("Comment deleted successfully",true),HttpStatus.OK);

    }
}
