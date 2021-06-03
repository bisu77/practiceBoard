package com.practice.repository;

import com.practice.dto.MemberDto;
import com.practice.dto.QMemberDto;
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
    public List<MemberDto> searchAll(){
        return select(new QMemberDto(member))
                .from(member).fetch();
    }

    @Override
    public Page<MemberDto> searchDynamic(MemberSearchCond memberSearchCond, Pageable pageable){
        List<MemberDto> content = getContent(memberSearchCond, pageable);
        return PageableExecutionUtils.getPage(content, pageable, getCountJpaQuery(memberSearchCond)::fetchCount);
    }

    @Override
    public Page<MemberDto> search(MemberSearchCond memberSearchCond, Pageable pageable){
        return applyPagination(pageable, jpaQueryFactory -> getContentJpaQuery(memberSearchCond));
    }

    @Override
    public Page<MemberDto> searchCustomCountQuery(MemberSearchCond memberSearchCond, Pageable pageable){
        return applyPagination(pageable, jpaQueryFactory -> getContentJpaQuery(memberSearchCond), jpaQueryFactory -> getCountJpaQuery(memberSearchCond));
    }

    private List<MemberDto> getContent(MemberSearchCond memberSearchCond, Pageable pageable){
        return getContentJpaQuery(memberSearchCond)
                .orderBy(
                        QueryUtil.getOrderSpecifier(pageable.getSort(), Member.class)
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQuery<MemberDto> getContentJpaQuery(MemberSearchCond memberSearchCond){
        return select(new QMemberDto(member))
                .from(member)
                .where(
                        userIdEq(memberSearchCond.getUserId())
                        , nameEq(memberSearchCond.getName())
                );
    }

    private JPAQuery<MemberDto> getCountJpaQuery(MemberSearchCond memberSearchCond){
        return select(new QMemberDto(member))
                .from(member)
                .where(
                        userIdEq(memberSearchCond.getUserId())
                        , nameEq(memberSearchCond.getName())
                );
    }

    private BooleanExpression userIdEq(String userId){
        return hasText(userId) ? member.userId.startsWith(userId) : null;
    }

    private BooleanExpression nameEq(String name){
        return hasText(name) ? member.name.startsWith(name) : null;
    }
}
