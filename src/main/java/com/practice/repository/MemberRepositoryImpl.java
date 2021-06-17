package com.practice.repository;

import com.practice.dto.cond.MemberSearchCond;
import com.practice.entity.Member;
import com.practice.repository.support.Querydsl4RepositorySupport;
import com.practice.repository.util.QueryUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.practice.entity.QMember.member;
import static org.springframework.util.StringUtils.hasText;

public class MemberRepositoryImpl extends Querydsl4RepositorySupport implements MemberRepositoryCustom{//MemberRepository + Impl 조합으로 Spring Boot에서 자동인식하여 bean 등록
    public MemberRepositoryImpl(){
        super(Member.class);
    }

    @Override
    public List<Member> searchAll(){
        return select(member)
                .from(member).fetch();
    }

    @Override
    public Page<Member> searchQueryUtil(MemberSearchCond memberSearchCond, Pageable pageable){
        List<Member> content = getContent(memberSearchCond, pageable);//custom QueryUtil 사용
        return PageableExecutionUtils.getPage(content, pageable, getCountJpaQuery(memberSearchCond)::fetchCount);
    }

    @Override
    public Page<Member> search(MemberSearchCond memberSearchCond, Pageable pageable){
        return applyPagination(pageable, jpaQueryFactory -> getContentJpaQuery(memberSearchCond));
    }

    @Override
    public Page<Member> searchCustomCountQuery(MemberSearchCond memberSearchCond, Pageable pageable){
        return applyPagination(pageable, jpaQueryFactory -> getContentJpaQuery(memberSearchCond), jpaQueryFactory -> getCountJpaQuery(memberSearchCond));
    }

    private List<Member> getContent(MemberSearchCond memberSearchCond, Pageable pageable){
        return getContentJpaQuery(memberSearchCond)
                .orderBy(
                        QueryUtil.getOrderSpecifier(pageable.getSort(), Member.class, "member1")
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQuery<Member> getContentJpaQuery(MemberSearchCond memberSearchCond){
        return selectFrom(member)
                .where(
                        userIdStartsWith(memberSearchCond.getUserId())
                        , nameStartsWith(memberSearchCond.getName())
                );
    }

    private JPAQuery<Member> getCountJpaQuery(MemberSearchCond memberSearchCond){
        return selectFrom(member)
                .where(
                        userIdStartsWith(memberSearchCond.getUserId())
                        , nameStartsWith(memberSearchCond.getName())
                );
    }

    private BooleanExpression userIdStartsWith(String userId){
        return hasText(userId) ? member.userId.startsWith(userId) : null;
    }

    private BooleanExpression nameStartsWith(String name){
        return hasText(name) ? member.name.startsWith(name) : null;
    }
}
