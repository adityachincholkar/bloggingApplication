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
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.Category;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryControlller {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto createCategory =  categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto categoryDto2 =  categoryService.updateCategory(categoryDto, categoryId ); 
        return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{cateogoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer cateogoryId){
        categoryService.deleteCategory(cateogoryId)
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);
    }
    //get
    @GetMapping("/{cateogoryId}")
    public ResponseEntity<CategoryDto> findCategory(@PathVariable Integer cateogoryId){
        CategoryDto foundcategory = categoryService.getCategory(cateogoryId);
        return new ResponseEntity<CategoryDto>(foundcategory,HttpStatus.OK);
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> findCategories(){
        List<CategoryDto> listOfCategories = categoryService.getCategories();
        return new ResponseEntity<List<CategoryDto>>(listOfCategories,HttpStatus.OK);
    }

}
