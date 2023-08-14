package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid  @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = "Id", required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue =  "asc", required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "Id") long Id) {
        return ResponseEntity.ok(postService.getPostById(Id));
    }

    @PutMapping("/{Id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name = "Id") long Id) {
        PostDto postResponse = postService.updatePost(postDto, Id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "Id") long Id) {
        postService.deletePost(Id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }



}
