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

    @GetMapping("/search")
    @ResponseBody
    public Page<MemberDto> searchMember(MemberSearchCond cond, Pageable pageable){//queryUtil 사용하여 동적 페이징 && order by
        return memberService.searchDynamic(cond, pageable).map(MemberDto::new);
    }

    @GetMapping("/search2")
    @ResponseBody
    public Page<MemberDto> searchMember2(MemberSearchCond cond, Pageable pageable){//QueryDsl4Supoort>pagination 사용하여 동적페이징 && order by
        return memberService.searchCustomCountQuery(cond, pageable).map(MemberDto::new);
    }

    @GetMapping("/{id}")
    public MemberDto searchDetailMember(@PathVariable Long id){
        return new MemberDto(memberService.findMemberDtoById(id));
    }

    @PostMapping("/join")
    public MemberDto join(@Valid @RequestBody final MemberRequest request){
        return new MemberDto(memberService.join(request.toEntity()));
    }

    @PutMapping("/{id}")
    public MemberDto updateMember(@PathVariable Long id, @RequestBody @Valid final MemberRequest request){
        return new MemberDto(memberService.updateMember(id, request.toDto()));
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return;
    }

    @Getter
    static class MemberRequest{
        @NotBlank
        private String userId;
        @NotBlank
        private String name;

        private Address address;

        public MemberDto toDto(){
            return new MemberDto(userId, name, address);
        }

        public Member toEntity(){
            return new Member(userId, name, address);
        }
    }

}
