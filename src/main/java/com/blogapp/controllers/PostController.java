package com.blogapp.controllers;

import com.blogapp.entities.Post;
import com.blogapp.payloads.APIResponse;
import com.blogapp.payloads.PostDTO;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ModelMapper modelMapper;

//    Create
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable Long userId,
                                              @PathVariable Long categoryId){
        return  ResponseEntity.ok(postService.createPost(postDTO, userId, categoryId));
    }
//    get post by user id
    @GetMapping("/user/{userId}")
    public  ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Long userId){
        List<PostDTO> postDTOS =  postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDTOS);
    }
//    get post by category id
    @GetMapping("/category/{categoryId}")
    public  ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Long categoryId){
        List<PostDTO> postDTOS =  postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDTOS);
    }
//    Get post by post id
    @GetMapping("/{postId}")
    public  ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
//    Get all post
    @GetMapping("/getAll")
    public ResponseEntity<List<PostDTO>> getAllPost(){
        return ResponseEntity.ok(postService.getAllPost());
    }

//    Delete post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok(new APIResponse("Post deleted Successfully", true));
    }

//    Update post
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id){
        return  ResponseEntity.ok(postService.updatePost(postDTO, id));
    }

}
