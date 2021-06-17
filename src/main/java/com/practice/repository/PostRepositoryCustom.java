package com.practice.repository;

import com.practice.dto.cond.PostSearchCond;
import com.practice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> searchPostCond(Pageable pageable, PostSearchCond postSearchCond);
    Page<Post> searchPostCondQueryUtil(Pageable pageable, PostSearchCond postSearchCond);
}
