package com.blogapp.controllers;

import com.blogapp.entities.Comment;
import com.blogapp.payloads.APIResponse;
import com.blogapp.payloads.CommentDTO;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment,@PathVariable Long postId){
        CommentDTO createdComment = commentService.createComment(comment, postId);
        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return  new ResponseEntity<>(new APIResponse("Comment Deleted Successfully", true), HttpStatus.OK);
    }

}
