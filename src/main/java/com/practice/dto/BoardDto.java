package com.practice.dto;

import com.practice.entity.Board;
import lombok.Getter;

@Getter
public class BoardDto {
    private String name;
    private String description;

    public BoardDto(Board board){
        name = board.getName();
        description = board.getDescription();
    }
}
