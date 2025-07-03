package com.blogapp.services;

import com.blogapp.entities.Post;
import com.blogapp.payloads.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
//    Create
    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);

//    update
    PostDTO updatePost(PostDTO postDTO, Long id);

//    delete
    void deletePost(Long id);

//    get all posts
    List<PostDTO> getAllPost();

//    get by ID
    PostDTO getPostById(Long postId);

//    get posts by category
    List<PostDTO> getPostsByCategory(Long categoryId);

//    get all posts by user
    List<PostDTO> getPostsByUser(Long userId);

//    Search posts
    List<Post> searchPosts(String keyword);
}
