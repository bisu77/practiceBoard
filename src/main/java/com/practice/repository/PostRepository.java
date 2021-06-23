package com.practice.repository;

import com.practice.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
    List<Post> findByTitle(String title);

    @EntityGraph(attributePaths = {"member","board"})
    List<Post> findByTitleLike(String title);

    @Query("select p from Post p " +
            "        join fetch p.member m " +
            "        join fetch p.board b " +
            "where p.title like %:title%")
    List<Post> findByTitleLikeFetch(@Param("title") String title);

    Optional<Post> findByIdAndMemberIdAndBoardId(Long id, Long memberId, Long boardId);
}
