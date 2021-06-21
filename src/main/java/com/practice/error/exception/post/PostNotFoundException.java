package com.practice.error.exception.post;

import com.practice.error.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException(Long id) {
        super(id + " is not found");
    }
}
