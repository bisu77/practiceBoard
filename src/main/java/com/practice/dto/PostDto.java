package com.practice.dto;

import com.practice.entity.Post;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostDto {
    private Long memberId;
    private Long boardId;
    private String userId;
    private String userName;
    private String boardName;
    private String title;
    private String content;
    private Long viewCount;

    public PostDto(Post post){
        memberId = post.getMember().getId();
        boardId = post.getBoard().getId();
        userId = post.getMember().getUserId();
        userName = post.getMember().getName();
        boardName = post.getBoard().getName();
        title = post.getTitle();
        content = post.getContent();
        viewCount = post.getViewCount();
    }
}
