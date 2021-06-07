package com.practice.error.exception.member;

import com.practice.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException(Long id) {
        super(id + " is not found");
    }
}
