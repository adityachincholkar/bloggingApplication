package com.blog.services.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        
        Category cat =  modelMapper.map(categoryDto, Category.class);
        Category addedCat =   categoryRepo.save(cat);

        
        return modelMapper.map(addedCat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryID) {

        Category cat = categoryRepo.findById(categoryID)
                                    .orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id" , categoryID));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updCat = categoryRepo.save(cat);

        return modelMapper.map(updCat, CategoryDto.class);
        
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
        .orElseThrow(()-> new ResourceNotFoundException("Category ","Not found ", categoryId));
       
        categoryRepo.delete(category);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = categoryRepo.findById(categoryId)
                                    .orElseThrow(()-> new ResourceNotFoundException("Category ", "Not found ", categoryId));
        return modelMapper.map(cat, CategoryDto.class);
        
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> cat = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = cat.stream()
                                       .map((c)->modelMapper.map(c, CategoryDto.class))
                                       .toList();
        return categoryDtos;
        
    }


}
