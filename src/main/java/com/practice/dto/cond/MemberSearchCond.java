package com.practice.dto.cond;

import lombok.Getter;

@Getter
public class MemberSearchCond {
    private String userId;
    private String name;

    public MemberSearchCond(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
