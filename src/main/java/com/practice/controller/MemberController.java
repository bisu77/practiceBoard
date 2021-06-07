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
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/search")
    @ResponseBody
    public Page<MemberDto> searchMember(MemberSearchCond cond, Pageable pageable){//queryUtil 사용하여 동적 페이징 && order by
        return memberService.searchDynamic(cond, pageable);
    }

    @GetMapping("/member/search2")
    @ResponseBody
    public Page<MemberDto> searchMember2(MemberSearchCond cond, Pageable pageable){//QueryDsl4Supoort>pagination 사용하여 동적페이징 && order by
        return memberService.searchCustomCountQuery(cond, pageable);
    }

    @GetMapping("/member/{id}")
    public MemberDto searchDetailMember(@PathVariable long id){
        return memberService.findMemberById(id);
    }

    @PostMapping("/member/join")
    public MemberDto join(@Valid @RequestBody final MemberRequest request){
        return memberService.join(request.toMember());
    }

    @Getter
    static class MemberRequest{
        @NotBlank
        private String userId;
        @NotBlank
        private String name;

        private Address address;

        public Member toMember(){
            return new Member(userId, name, address);
        }
    }

}
