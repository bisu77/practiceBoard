package com.practice.entity;

import com.practice.dto.PostDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Post extends BaseEntity{
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;

    private String title;
    private String content;
    private Long viewCount;

    public Post(Member member, Board board, String title, String content, Long viewCount) {
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    public void addViewCount(){
        viewCount++;
    }

    public void update(PostDto postDto){
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
    }
}
