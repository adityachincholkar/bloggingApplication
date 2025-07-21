package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {

    //Create
    CategoryDto createCategory(CategoryDto categoryDto); 

    //Update
    CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryID);

    //Delete
    void deleteCategory(Integer categoryId);
    
    //get
    CategoryDto getCategory(Integer categoryId);

    //get All
    List<CategoryDto> getCategories();
    
}
