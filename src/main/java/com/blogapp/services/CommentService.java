package com.blogapp.services;

import com.blogapp.payloads.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Long postId);
    void deleteComment(Long commentId);
}
