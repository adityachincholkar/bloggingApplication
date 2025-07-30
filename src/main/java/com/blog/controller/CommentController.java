package com.blog.controller;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("posts/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId
    ){
        CommentDto comment = commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commnetId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commnetId){
        commentService.deleteComment(commnetId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully" ,true)   ,HttpStatus.OK);
    }


}
