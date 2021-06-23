package com.practice.dto;

import com.practice.entity.Address;
import com.practice.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private String userId;
    private String name;
    private String street;
    private String detailAddress;
    private String zipcode;
    private List<PostDto> posts;

    /**
     *  Repository에서 기본 Entity(Member) 반환하기 때문에 @QueryProjection 필요 없음
     *  Controller에서 DTO변환하기
     * @param member
     */
    //@QueryProjection
    public MemberDto(Member member){
        userId = member.getUserId();
        name = member.getName();
        street = member.getAddress() == null ? null : member.getAddress().getStreet();
        detailAddress = member.getAddress() == null ? null : member.getAddress().getDetailAddress();
        zipcode = member.getAddress() == null ? null : member.getAddress().getZipcode();
        posts = member.getPosts().stream()
                    .map(post->new PostDto(post))
                    .collect(Collectors.toList());
    }

    public MemberDto(@NotEmpty String userId, @NotEmpty String name, String street, String detailAddress, String zipcode) {
        this.userId = userId;
        this.name = name;
        this.street = street;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }
}
