package com.practice.service;

import com.practice.dto.PostDto;
import com.practice.dto.cond.PostSearchCond;
import com.practice.entity.Board;
import com.practice.entity.Member;
import com.practice.entity.Post;
import com.practice.error.exception.board.BoardNotFoundException;
import com.practice.error.exception.member.MemberNotFoundException;
import com.practice.error.exception.post.PostNotFoundException;
import com.practice.repository.BoardRepository;
import com.practice.repository.MemberRepository;
import com.practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public List<Post> findByTitle(String title){
        return postRepository.findByTitle(title);
    }

    public List<Post> findByTitleLike(String title){
        return postRepository.findByTitleLike(title);
    }

    public List<Post> findByTitleLikeFetch(String title){
        return postRepository.findByTitleLikeFetch(title);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post write(PostDto postDto) {
        Member writeMember = memberRepository.findByIdAndUserId(postDto.getMemberId(), postDto.getUserId()).orElseThrow(
                () -> new MemberNotFoundException(postDto.getMemberId())
        );

        Board writeBoard = boardRepository.findById(postDto.getBoardId()).orElseThrow(
                () -> new BoardNotFoundException(postDto.getBoardId())
        );

        return postRepository.save(new Post(writeMember, writeBoard, postDto.getTitle(), postDto.getContent(), 0L));
    }

    public Page<Post> searchPosts(Pageable pageable, PostSearchCond postSearchCond) {
        return postRepository.searchPostCond(pageable, postSearchCond);
    }

    public Page<Post> searchPostsQueryUtil(Pageable pageable, PostSearchCond postSearchCond) {
        return postRepository.searchPostCondQueryUtil(pageable, postSearchCond);
    }

    public Post detailPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id)
        );

        post.addViewCount();

        return post;
    }

    public Post updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id)
        );

        memberRepository.findByIdAndUserId(postDto.getMemberId(), postDto.getUserId()).orElseThrow(
                () -> new MemberNotFoundException(postDto.getMemberId())
        );

        Board board = boardRepository.findById(postDto.getBoardId()).orElseThrow(
                () -> new BoardNotFoundException(postDto.getBoardId())
        );

        post.update(postDto, board);
        return postRepository.save(post);
    }

    public Post deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id)
        );

        post.updateDelete(true);
        return postRepository.save(post);
    }
}
