package com.springboot.blog.controller;


import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/posts/{postid}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postid") long postid, @RequestBody CommentDto comment) {

        return new ResponseEntity<>(commentService.createComment(postid, comment), HttpStatus.CREATED);

    }


    @GetMapping("/posts/{postid}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(value = "postid") long postid) {

        return commentService.getCommentByPostId(postid);

    }


    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId) {

        CommentDto commentDto = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);

    }

}
