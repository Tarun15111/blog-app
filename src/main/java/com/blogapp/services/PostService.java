package com.blogapp.services;

import com.blogapp.payloads.PostDTO;
import com.blogapp.payloads.PostResponse;
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
    PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

//    get by ID
    PostDTO getPostById(Long postId);

//    get posts by category
    List<PostDTO> getPostsByCategory(Long categoryId, Integer pageNo, Integer pageSize);

//    get all posts by user
    List<PostDTO> getPostsByUser(Long userId);

//    Search posts
    List<PostDTO> searchPosts(String keyword);
}
