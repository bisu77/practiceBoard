package com.practice.repository;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberDto> searchAll();
    Page<MemberDto> searchDynamic(MemberSearchCond memberSearchCond, Pageable pageable);
    Page<MemberDto> search(MemberSearchCond memberSearchCond, Pageable pageable);
    Page<MemberDto> searchCustomCountQuery(MemberSearchCond memberSearchCond, Pageable pageable);
}
