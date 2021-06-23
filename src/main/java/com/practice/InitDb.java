package com.practice;

import com.practice.entity.Address;
import com.practice.entity.Board;
import com.practice.entity.Member;
import com.practice.entity.Post;
import com.practice.repository.BoardRepository;
import com.practice.repository.MemberRepository;
import com.practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Transactional
public class InitDb {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @PostConstruct
    public void init(){
        String userId = null;
        String userName = null;
        String title = null;
        String content = null;

        Board createdBoard = createBoard("기본게시판", "기본 제공 게시판 입니다");

        for(int i=1;i<101;i++){
            userId = "test"+i;
            userName = "홍길동"+i;
            title = "제목 테스트 " + i;
            content = "내용 테스트 " + i;
            if(i%2==0){
                userId = "rudwns"+i;
                userName = "경준"+i;
                title = "안녕~~ " + i;
                content = "반갑다 JPA " + i;
            }
            Member createdMember = createMember(userId, userName, "12345", "금당로", "안알려줌");
            createPost(createdMember, createdBoard, title, content, 0L);
        }
    }

    private Member createMember(String userId, String name, String zipcode, String street, String detailAddress){
        Member member = new Member(userId, name, new Address(street, detailAddress, zipcode));

        return memberRepository.save(member);
    }

    private Board createBoard(String name, String description){
        Board board = new Board(name, description);
        return boardRepository.save(board);
    }

    private void createPost(Member member, Board board, String title, String content, Long viewCount){
        Post post = new Post(member, board, title, content, viewCount);
        postRepository.save(post);
    }
}
