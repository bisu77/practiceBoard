package com.practice.service;

import com.practice.entity.Post;
import com.practice.repository.PostRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findByTitle(String title){
        return postRepository.findByTitle(title);
    }

    public List<Post> findByTitleLike(String title){
        return postRepository.findByTitleLike(title);
    }

    public List<Post> findByTitleLikeFetch(String title){
        return postRepository.findByTitleLikeFetch(title);
    }
}
