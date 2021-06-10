package com.practice.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true, updatable = false)
    private String userId;
    @NotEmpty
    private String name;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(@NotEmpty String userId, @NotEmpty String name, Address address) {
        this.userId = userId;
        this.name = name;
        this.address = address;
    }

    public void memberUpdate(String name, Address address){
        this.name = name;
        this.address = address;
    }
}
