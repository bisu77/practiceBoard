package com.practice.config.auth.dto;

import com.practice.entity.Member;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionMember implements Serializable {
    private String userId;
    private String name;

    public SessionMember(Member member) {
        this.userId = member.getUserId();
        this.name = member.getName();
    }
}
