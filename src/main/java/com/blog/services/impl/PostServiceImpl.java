package com.blog.services.impl;

import java.util.Date;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {
        
    }

    @Override
    public List<Post> getAllPost() {
        return null;
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Integer categoryId) {
                return null;
    }

    @Override
    public List<Post> getPostsByUser(Integer userId) {
            return null;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return null;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
       User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","User id", userId));

       Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "categoryId id", categoryId));
       
       Post post = modelMapper.map(postDto, Post.class);
        post.setImgaeName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

       Post newPost =  postRepo.save(post);

        return modelMapper.map(newPost, PostDto.class);
    }

}
