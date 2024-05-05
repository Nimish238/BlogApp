package com.example.Nimish.BlogApp.services.impl;

import com.example.Nimish.BlogApp.entities.comment;
import com.example.Nimish.BlogApp.entities.post;
import com.example.Nimish.BlogApp.entities.user;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.exceptions.IllegalArgumentException;
import com.example.Nimish.BlogApp.payloads.commentDto;
import com.example.Nimish.BlogApp.services.commentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Nimish.BlogApp.repositories.postRepo;
import com.example.Nimish.BlogApp.repositories.commentRepo;
import org.springframework.stereotype.Service;
import com.example.Nimish.BlogApp.repositories.userRepo;

@Service
public class commentServiceImpl implements commentService {

    @Autowired
    private  postRepo postRepo;

    @Autowired
    private commentRepo commentRepo;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public commentDto createComment(commentDto commentDto, Integer postId, Integer id) {

        post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","post id",postId));

        user user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user","id",id));

        comment comment=this.modelMapper.map(commentDto,comment.class);
        comment.setPost(post);
        comment.setUser(user);
        comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,commentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId,Integer postId,Integer id) {

        comment can = this.commentRepo.findById(commentId)
                .orElseThrow(() ->new ResourceNotFoundException("comment","comment id",commentId));


        if(can.getPost().getPostId().equals(postId) && can.getUser().getId().equals(id)){
            this.commentRepo.delete(can);
        }
        else{
            throw new IllegalArgumentException (postId,id);
        }


    }

}
