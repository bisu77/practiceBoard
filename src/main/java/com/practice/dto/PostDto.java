package com.practice.dto;

import com.practice.entity.Post;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostDto {
    private String userId;
    private String userName;
    private String boardName;
    private String title;
    private String content;
    private Long viewCount;

    public PostDto(Post post){
        userId = post.getMember().getUserId();
        userName = post.getMember().getName();
        boardName = post.getBoard().getName();
        title = post.getTitle();
        content = post.getContent();
        viewCount = post.getViewCount();
    }
}
