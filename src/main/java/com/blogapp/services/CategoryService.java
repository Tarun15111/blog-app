package com.blogapp.services;

import com.blogapp.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {
//    create
    CategoryDTO createCategory(CategoryDTO categoryDTO);
//    update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);
//    delete
    void deleteCategory(Long id);
//    get
    CategoryDTO getCategoryById(Long id);
//    get all
    List<CategoryDTO> getAllCategories();
}
