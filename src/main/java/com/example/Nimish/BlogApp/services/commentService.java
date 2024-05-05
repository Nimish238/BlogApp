package com.example.Nimish.BlogApp.services;

import com.example.Nimish.BlogApp.entities.comment;
import com.example.Nimish.BlogApp.payloads.commentDto;

public interface commentService {

    commentDto createComment(commentDto commentDto,Integer postId,Integer id);

    void deleteComment(Integer commentId, Integer postId, Integer id);

}
