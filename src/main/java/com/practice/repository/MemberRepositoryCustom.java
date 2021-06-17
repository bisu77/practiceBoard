package com.practice.repository;

import com.practice.dto.cond.MemberSearchCond;
import com.practice.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> searchAll();
    Page<Member> searchQueryUtil(MemberSearchCond memberSearchCond, Pageable pageable);
    Page<Member> search(MemberSearchCond memberSearchCond, Pageable pageable);
    Page<Member> searchCustomCountQuery(MemberSearchCond memberSearchCond, Pageable pageable);
}
