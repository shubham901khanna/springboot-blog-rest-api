package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@Tag(
        name ="CRUD Rest API's for post resource"
)
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(
            summary = "Create post API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{Id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name = "Id") long Id) {
        PostDto postResponse = postService.updatePost(postDto, Id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "Id") long Id) {
        postService.deletePost(Id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }



}
