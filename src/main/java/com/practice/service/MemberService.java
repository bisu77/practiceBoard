package com.practice.service;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import com.practice.entity.Member;
import com.practice.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public List<MemberDto> findAll(){
        return memberRepository.searchAll();
    }

    public Page<MemberDto> search(MemberSearchCond cond, Pageable pageable){
        return memberRepository.search(cond, pageable);
    }

    public Page<MemberDto> searchDynamic(MemberSearchCond cond, Pageable pageable){
        return memberRepository.searchDynamic(cond, pageable);
    }

    public Page<MemberDto> searchCustomCountQuery(MemberSearchCond cond, Pageable pageable){
        return memberRepository.searchCustomCountQuery(cond, pageable);
    }
}
