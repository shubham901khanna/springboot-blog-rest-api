package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private  PostRepository postRepository;

    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;


    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));
        Post post = mapToEntity(postDto);
        categoryRepository.save(category);
        Post post1 = postRepository.save(post);
        PostDto postDto1 = mapToDto(post1);
        return postDto1;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);
        // get content from page
        List<Post> listofposts = posts.getContent();
        List<PostDto> content = listofposts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long Id) {
        Post post = postRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", Id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long Id) {
        // Get Id
        Post post = postRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", Id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long Id) {
        // Get Id
        Post post = postRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", Id));

        postRepository.delete(post);
    }


    private PostDto mapToDto(Post post) {
        // convert entity to DTO
        PostDto postDto = modelMapper.map(post,PostDto.class);
        /*PostDto postDto1 = new PostDto();
        postDto1.setId(post.getId());
        postDto1.setContent(post.getContent());
        postDto1.setDescription(post.getDescription());
        postDto1.setTitle(post.getTitle());*/
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        // convert DTO to entity
        Post post = modelMapper.map(postDto,Post.class);
       /* Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        return post;
    }


}
