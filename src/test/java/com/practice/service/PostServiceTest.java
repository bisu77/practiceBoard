package com.practice.service;

import com.practice.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {
    private PostService postService;

    @Autowired
    public PostServiceTest(PostService postService) {
        this.postService = postService;
    }

    @Test
    public void findByTitle() throws Exception{
        List<Post> postList = postService.findByTitle("안녕");
        assertThat(postList.size()).isEqualTo(0);

        List<Post> postLikeList = postService.findByTitleLike("안녕%");//@EntityGraph : Left Outer Join
        assertThat(postLikeList.size()).isEqualTo(50);

        List<Post> postLikeListFetch = postService.findByTitleLikeFetch("안녕");//fetch join : Inner Join
        assertThat(postLikeListFetch.size()).isEqualTo(50);

        System.out.println("postLikeList = " + postLikeList.get(0).toString());
        System.out.println("postLikeListFetch = " + postLikeListFetch.get(0).toString());
    }
}