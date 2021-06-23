package com.practice.service;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import com.practice.entity.Member;
import com.practice.error.exception.member.MemberDuplicateException;
import com.practice.error.exception.member.MemberNotFoundException;
import com.practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(Member member){
        checkDuplicateUserId(member.getUserId());
        return memberRepository.save(member);
    }

    public List<Member> findAll(){
        return memberRepository.searchAll();
    }

    public Page<Member> findAll(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    public Member findMemberDtoById(long id){
        Optional<Member> findMember = memberRepository.findById(id);

        return findMember.orElseThrow(
                        () -> new MemberNotFoundException(id)
                );
    }

    public Page<Member> search(MemberSearchCond cond, Pageable pageable){
        return memberRepository.search(cond, pageable);
    }

    public Page<Member> searchDynamic(MemberSearchCond cond, Pageable pageable){
        return memberRepository.searchQueryUtil(cond, pageable);
    }

    public Page<Member> searchCustomCountQuery(MemberSearchCond cond, Pageable pageable){
        return memberRepository.searchCustomCountQuery(cond, pageable);
    }

    public Member updateMember(Long id, MemberDto memberDto) {
        Member findMember = findMemberById(id);
        findMember.memberUpdate(memberDto.getName(), memberDto.getStreet(), memberDto.getDetailAddress(), memberDto.getZipcode());
        return findMember;
    }

    public void checkDuplicateUserId(String userId){
        memberRepository.findByUserId(userId).ifPresent(member -> {
            throw new MemberDuplicateException(userId);
        });
    }

    public Member deleteMember(Long id) {
        Member findMember = findMemberById(id);
        findMember.updateDelete(true);
        return findMember;
    }

    private Member findMemberById(long id){
        Optional<Member> findMember = memberRepository.findById(id);

        return findMember.orElseThrow(
                        () -> new MemberNotFoundException(id)
                );
    }
}
