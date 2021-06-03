package com.practice.dto;

import com.practice.entity.Address;
import com.practice.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class MemberDto {
    private String userId;
    private String name;
    private Address address;
    private List<PostDto> posts;

    @QueryProjection
    public MemberDto(Member member){
        userId = member.getUserId();
        name = member.getName();
        address = member.getAddress();
        posts = member.getPosts().stream()
                    .map(post->new PostDto(post))
                    .collect(Collectors.toList());
    }
}
