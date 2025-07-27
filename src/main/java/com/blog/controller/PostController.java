package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.PostService;

import jakarta.persistence.criteria.CriteriaBuilder.In;


@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    // create 
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDto createPost = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
    }

    // get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                        @RequestParam (value = "pageNumber" , defaultValue = "0" , required = false)  Integer pageNumber,
                                                       @RequestParam (value = "pageSize" ,defaultValue = "10" ,required = false) Integer pageSize
    ){
        PostResponse posts = postService.getPostsByUser(userId,pageNumber,pageSize);
        
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber" ,defaultValue = "0", required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "10" ,required = false) Integer pageSize
    ){
        PostResponse posts = postService.getPostsByCategory(categoryId,pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    // get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNumber" , defaultValue = "0" , required = false)  Integer pageNumber, 
        @RequestParam (value = "pageSize" , defaultValue= "10", required = false)Integer pageSize,
        @RequestParam (value = "sortby" , defaultValue = "postId" , required = false) String sortby,
        @RequestParam(value = "sortOrder" , defaultValue = "asc" , required = false) String sortOrder
        ){
        PostResponse posts = postService.getAllPost(pageNumber,pageSize,sortby,sortOrder);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    // get post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePostById(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully" , true);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId){
        PostDto pDto = postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(pDto,HttpStatus.OK);

    }
}
