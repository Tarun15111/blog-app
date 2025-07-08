package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostDTO;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));

        Post post =  modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", id));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ?
                    Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(p);
        List<Post> posts1 = pagePost.getContent();
        List<PostDTO> postDTOS = posts1.stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNo(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
        return modelMapper.map(post, PostDTO.class);
    }

//    Getting posts by category
//    Using pagination.
    @Override
    public List<PostDTO> getPostsByCategory(Long categoryId, Integer pageNo, Integer pageSize) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> page = postRepo.findByCategory(category, pageable);
        List<Post> posts = page.getContent();
        List<PostDTO> postDTOS = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class)).toList();
        return  postDTOS;
    }

    @Override
    public List<PostDTO> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDTO> postDTOS = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();
        return postDTOS;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDTO> postDTOS = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();
        return  postDTOS;
    }
}
