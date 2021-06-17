package com.practice.dto.cond;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSearchCond {
    private String userId;
    private String title;
    private String content;
}
