package com.blog.services.impl;

import java.util.Date;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
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
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortby, String sortOrder) {
        Sort sort = null;
        if (sortOrder.equalsIgnoreCase("asc")){
            sort = Sort.by(sortby).ascending();
        }
        else {
            sort  = Sort.by(sortby).descending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

       Page<Post> pagePost=  postRepo.findAll(p);

       List<Post> posts  = pagePost.getContent();

       List<PostDto> postDtos = posts.stream().map((e)-> modelMapper.map(e, PostDto.class)).toList();

       PostResponse postResponse = new PostResponse();

       postResponse.setContent(postDtos);
       postResponse.setPageNumber(pagePost.getNumber());
       postResponse.setPageSize(pagePost.getSize());
       postResponse.setTotalElements(pagePost.getTotalElements());
       postResponse.setTotalPages(pagePost.getTotalPages());
       postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId)
                    .orElseThrow(()->new ResourceNotFoundException("Post", "postId" ,postId));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
        Category cat  = categoryRepo.findById(categoryId)
                                    .orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));
        // Ensure pageSize is at least 1
        int validPageSize = Math.max(1, pageSize);
        Pageable page = PageRequest.of(pageNumber, validPageSize);
        Page<Post> pagePosts = postRepo.findByCategory(cat, page);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map((e)->modelMapper.map(e, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLastPage(pagePosts.isLast());
        postResponse.setPageSize(pagePosts.getSize());

        

        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        User user = userRepo.findById(userId)
                            .orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = postRepo.findByUser(user,pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map((e)->modelMapper.map(e, PostDto.class)).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;

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
