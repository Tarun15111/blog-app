package com.blogapp.controllers;

import com.blogapp.entities.Category;
import com.blogapp.payloads.APIResponse;
import com.blogapp.payloads.CategoryDTO;
import com.blogapp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
//    Create
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
            CategoryDTO categoryDTO1 = categoryService.createCategory(categoryDTO);
            return  ResponseEntity.ok(categoryDTO1);
    }
//    update
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        CategoryDTO categoryDTO1 = categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.ok(categoryDTO1);
    }
//    delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<APIResponse>(new APIResponse("Category deleted Successfully", true), HttpStatus.OK);
    }
//    get
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
//    get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
