package com.practice.error.exception.member;

import com.practice.error.ErrorCode;
import com.practice.error.exception.BusinessException;

public class MemberDuplicateException extends BusinessException {
    public MemberDuplicateException(String message) {
        super(message, ErrorCode.USER_ID_DUPLICATION);
    }
}
