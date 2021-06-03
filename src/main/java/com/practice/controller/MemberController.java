package com.practice.controller;

import com.practice.dto.MemberDto;
import com.practice.dto.cond.MemberSearchCond;
import com.practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
}
