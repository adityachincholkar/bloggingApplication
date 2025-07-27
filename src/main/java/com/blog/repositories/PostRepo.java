package com.blog.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.PostResponse;

public interface PostRepo extends  JpaRepository<Post,Integer>{

    Page<Post> findByUser(User user,Pageable pageable);
    Page<Post> findByCategory(Category category,Pageable pageable);


}
 