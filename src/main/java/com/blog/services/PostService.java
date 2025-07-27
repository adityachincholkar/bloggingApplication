package com.blog.services;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer userId , Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);
    
    //delete
    void deletePost(Integer postId);

    //get all post

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortby,String sortOrder);

    //get single post

    PostDto getPostById(Integer postId);

    // get all post by category
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize);

    // get all posts by user
    PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize);

    // search posts
    List<PostDto> searchPosts(String keyword);




}
