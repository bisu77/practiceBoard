package com.practice.repository;

import com.practice.dto.cond.PostSearchCond;
import com.practice.entity.Post;
import com.practice.repository.support.Querydsl4RepositorySupport;
import com.practice.repository.util.QueryUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.practice.entity.QPost.post;
import static com.practice.repository.util.StringUtils.*;

public class PostRepositoryImpl extends Querydsl4RepositorySupport implements PostRepositoryCustom{

    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public Page<Post> searchPostCond(Pageable pageable, PostSearchCond postSearchCond) {
        return applyPagination(pageable, jpaQueryFactory -> contentQuery(postSearchCond), jpaQueryFactory -> countQuery(postSearchCond));
    }

    @Override
    public Page<Post> searchPostCondQueryUtil(Pageable pageable, PostSearchCond postSearchCond) {

        List<Post> contents = contentQuery(postSearchCond)
                .orderBy(
                        QueryUtil.getOrderSpecifier(
                                pageable.getSort(), Post.class, "post"
                        ).stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = getCountQuery(postSearchCond);

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }


    private JPAQuery<Post> contentQuery(PostSearchCond postSearchCond){
        return selectFrom(post)
                .leftJoin(post.member)
                .where(
                        userIdStartsWith(postSearchCond.getUserId()),
                        titleContains(postSearchCond.getTitle()),
                        contentContains(postSearchCond.getContent())
                );
    }

    private JPAQuery<Post> countQuery(PostSearchCond postSearchCond){
        return hasText(postSearchCond.getUserId()) ? contentQuery(postSearchCond) : getCountQuery(postSearchCond);
    }

    private JPAQuery<Post> getCountQuery(PostSearchCond postSearchCond){
        return selectFrom(post)
                .where(
                        titleContains(postSearchCond.getTitle()),
                        contentContains(postSearchCond.getContent())
                );
    }

    private BooleanExpression userIdStartsWith(String userId){
        return hasText(userId) ? post.member.userId.startsWith(userId) : null;
    }

    private BooleanExpression titleContains(String title){
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression contentContains(String content){
        return hasText(content) ? post.content.contains(content) : null;
    }
}
