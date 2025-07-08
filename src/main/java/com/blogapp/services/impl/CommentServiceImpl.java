package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDTO;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.CommentRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post ID", postId));
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment =  commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
        commentRepo.delete(comment);
    }
}
