package com.practice.repository.util;

import static org.springframework.util.StringUtils.hasText;

public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean hasNotText(String text){
        return !hasText(text);
    }
}
