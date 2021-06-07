package com.practice.service;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import com.practice.entity.Member;
import com.practice.error.exception.member.MemberDuplicateException;
import com.practice.error.exception.member.MemberNotFoundException;
import com.practice.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto join(Member member){
        checkDuplicateUserId(member.getUserId());
        return new MemberDto(memberRepository.save(member));
    }

    public List<MemberDto> findAll(){
        return memberRepository.searchAll();
    }

    public MemberDto findMemberById(long id){
        Optional<Member> findMember = memberRepository.findById(id);

        return new MemberDto(
                findMember.orElseThrow(
                        () -> new MemberNotFoundException(id)
                ));
    }

    public void checkDuplicateUserId(String userId){
        memberRepository.findByUserId(userId).ifPresent(member -> {
            throw new MemberDuplicateException(userId);
        });
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
