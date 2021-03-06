package com.practice.controller;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import com.practice.entity.Address;
import com.practice.entity.Member;
import com.practice.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public Page<MemberDto> findMembers(final Pageable pageable){
        return memberService.findAll(pageable).map(MemberDto::new);
    }

    @PostMapping
    public MemberDto join(@Valid @RequestBody final MemberRequest request){
        return new MemberDto(memberService.join(request.toEntity()));
    }

    @GetMapping("/search")
    public Page<MemberDto> searchMember(final MemberSearchCond cond, final Pageable pageable){//queryUtil 사용하여 동적 페이징 && order by
        return memberService.searchDynamic(cond, pageable).map(MemberDto::new);
    }

    @GetMapping("/search2")
    public Page<MemberDto> searchMember2(final MemberSearchCond cond, final Pageable pageable){//QueryDsl4Supoort>pagination 사용하여 동적페이징 && order by
        return memberService.searchCustomCountQuery(cond, pageable).map(MemberDto::new);
    }

    @GetMapping("/{id}")
    public MemberDto searchDetailMember(@PathVariable final Long id){
        return new MemberDto(memberService.findMemberDtoById(id));
    }

    @PutMapping("/{id}")
    public MemberDto updateMember(@PathVariable final Long id, @RequestBody @Valid final MemberRequest request){
        return new MemberDto(memberService.updateMember(id, request.toDto()));
    }

    @DeleteMapping("/{id}")
    public MemberDto deleteMember(@PathVariable final Long id){
        return new MemberDto(memberService.deleteMember(id));
    }

    @Getter
    static class MemberRequest{
        @NotBlank
        private String userId;
        @NotBlank
        private String name;

        private String street;
        private String detailAddress;
        private String zipcode;

        public MemberDto toDto(){
            return new MemberDto(userId, name, street, detailAddress, zipcode);
        }

        public Member toEntity(){
            return Member.builder()
                    .userId(userId)
                    .name(name)
                    .address(new Address(street, detailAddress, zipcode))
                    .build();
        }
    }
}
