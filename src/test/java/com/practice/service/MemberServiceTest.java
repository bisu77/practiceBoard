package com.practice.service;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import com.practice.error.exception.member.MemberDuplicateException;
import com.practice.error.exception.member.MemberNotFoundException;
import com.practice.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("querydsl테스트")
    public void querydslBasicSelect() throws Exception{
        List<MemberDto> basicSelect = memberRepository.searchAll();
        assertThat(basicSelect.size()).isEqualTo(100);
    }

    @Test
    @DisplayName("querydsl 동적페이징 쿼리(기본 카운트 쿼리 호출)")
    public void querydslDynamicPagingTest() throws Exception{
        MemberSearchCond memberSearchCond = new MemberSearchCond("rudwns", "경준");
        Sort idSortDesc = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(0,10, idSortDesc);

        Page<MemberDto> members = memberRepository.search(memberSearchCond, pageable);

        assertThat(members.getTotalPages()).isEqualTo(5);
        assertThat(members.getContent().size()).isEqualTo(10);

        for (MemberDto member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    @DisplayName("querydsl support 동적페이징 쿼리(사용자 정의 카운트 쿼리 호출)")
    public void querydslDynamicPagingCustomCount() throws Exception{
        MemberSearchCond memberSearchCond = new MemberSearchCond("rudwns", "경준");
        Sort idSortDesc = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(4,11, idSortDesc);

        Page<MemberDto> members = memberRepository.searchCustomCountQuery(memberSearchCond, pageable);

        assertThat(members.getTotalPages()).isEqualTo(5);
        assertThat(members.getContent().size()).isEqualTo(6);

        for (MemberDto member : members) {
            System.out.println("member = " + member);
        }
    }
    
    @Test
    @DisplayName("querydsl 동적페이징 Order by 쿼리(사용자 정의 카운트 쿼리 호출)")
    public void querydslDynamicPagingOrderByCustomCount() throws Exception{
        MemberSearchCond memberSearchCond = new MemberSearchCond("rudwns", "경준");
        Sort idSortDesc = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(4,11, idSortDesc);

        Page<MemberDto> members = memberRepository.searchDynamic(memberSearchCond, pageable);

        assertThat(members.getTotalPages()).isEqualTo(5);
        assertThat(members.getContent().size()).isEqualTo(6);

        for (MemberDto member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    @DisplayName("존재하지 않는 회원 상세조회")
    public void memberNotFoundExceptionTest() throws Exception{
        Assertions.assertThrows(MemberNotFoundException.class,()->memberService.findMemberById(300));
    }

    @Test
    @DisplayName("존재하는 회원 아이디 조회")
    public void findMemberByName() throws Exception{
        Assertions.assertThrows(MemberDuplicateException.class,() -> memberService.checkDuplicateUserId("rudwns2"));
    }
}