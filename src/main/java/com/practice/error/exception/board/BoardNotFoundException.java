package com.practice.error.exception.board;

import com.practice.error.exception.EntityNotFoundException;

public class BoardNotFoundException extends EntityNotFoundException {
    public BoardNotFoundException(Long id) {
        super(id + " is not found");
    }
}
