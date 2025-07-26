package com.blog.services.impl;

import java.util.Date;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId)
                            .orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id" , postId));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
       List<Post> posts =  postRepo.findAll();
       List<PostDto> postDtos = posts.stream().map((e)-> modelMapper.map(e, PostDto.class)).toList();
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId)
                    .orElseThrow(()->new ResourceNotFoundException("Post", "postId" ,postId));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat  = categoryRepo.findById(categoryId)
                                    .orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));
        List<Post> posts = postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((e)->modelMapper.map(e, PostDto.class)).toList();

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                            .orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));

        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((e)->modelMapper.map(e, PostDto.class)).toList();

        return postDtos;

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
       User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","User id", userId));

       Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId id", categoryId));
       
       Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

       Post newPost =  postRepo.save(post);

        return modelMapper.map(newPost, PostDto.class);
    }

    

}
