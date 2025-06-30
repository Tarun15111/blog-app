package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDTO;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category =  modelMapper.map(categoryDTO, Category.class);
        Category savedCategory =   categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", id));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long id) {
            Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", id));
            categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", id));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        return categoryDTOS;
    }
}
