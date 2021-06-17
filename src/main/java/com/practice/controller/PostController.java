package com.practice.controller;

import com.practice.dto.PostDto;
import com.practice.dto.cond.PostSearchCond;
import com.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Page<PostDto> findPosts(final Pageable pageable){
        return postService.findAll(pageable).map(PostDto::new);
    }

    @PostMapping
    public PostDto write(@RequestBody final PostDto postDto){
        return new PostDto(postService.write(postDto));
    }

    @GetMapping("/search")
    public Page<PostDto> searchPosts(final Pageable pageable, final PostSearchCond postSearchCond){
        return postService.searchPosts(pageable, postSearchCond).map(PostDto::new);
    }

    @GetMapping("/search/v2")
    public Page<PostDto> searchPostsQueryUtil(final Pageable pageable, final PostSearchCond postSearchCond){
        return postService.searchPostsQueryUtil(pageable, postSearchCond).map(PostDto::new);
    }

}
