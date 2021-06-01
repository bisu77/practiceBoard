package com.practice.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
