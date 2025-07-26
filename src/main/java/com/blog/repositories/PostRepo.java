package com.blog.repositories;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo extends  JpaRepository<Post,Integer>{

    List<Post> findAllByUser(User user);
    List<Post> findByCategory(Category category);


}
 