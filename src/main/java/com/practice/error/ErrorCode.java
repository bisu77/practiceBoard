package com.practice.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {
    //Common
    INVALID_INPUT_VALUE(400,"C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002","Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003","Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member
    USER_ID_DUPLICATION(400, "M001", "User Id is Duplication");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
