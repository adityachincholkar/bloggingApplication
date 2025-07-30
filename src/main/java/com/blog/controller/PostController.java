package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.blog.config.AppConfig;
import com.blog.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    // create 
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDto createPost = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
    }

    // get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                        @RequestParam (value = "pageNumber" , defaultValue = AppConfig.PAGE_NUMBER, required = false)  Integer pageNumber,
                                                       @RequestParam (value = "pageSize" ,defaultValue = AppConfig.PAGE_SIZE ,required = false) Integer pageSize
    ){
        PostResponse posts = postService.getPostsByUser(userId,pageNumber,pageSize);
        
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber" ,defaultValue = AppConfig.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConfig.PAGE_SIZE ,required = false) Integer pageSize
    ){
        PostResponse posts = postService.getPostsByCategory(categoryId,pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    // get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNumber" , defaultValue = AppConfig.PAGE_NUMBER , required = false)  Integer pageNumber,
        @RequestParam (value = "pageSize" , defaultValue= AppConfig.PAGE_SIZE, required = false)Integer pageSize,
        @RequestParam (value = "sortby" , defaultValue = AppConfig.SORT_BY , required = false) String sortby,
        @RequestParam(value = "sortOrder" , defaultValue = AppConfig.SORT_DIR , required = false) String sortOrder
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

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>>searchTitleByKeyword (@PathVariable(value = "keyword") String keyword){
        List<PostDto> postDtos = postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        // Get the post
        PostDto postDto = postService.getPostById(postId);
        
        // Upload the image and get the new filename (with UUID)
        String newFileName = fileService.uploadImage(path, image);
        
        // Update the post with the new image name
        postDto.setImageName(newFileName);
        
        // Save the updated post
        PostDto updatedPost = postService.updatePost(postDto, postId);
        
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    // method to server files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException{
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
