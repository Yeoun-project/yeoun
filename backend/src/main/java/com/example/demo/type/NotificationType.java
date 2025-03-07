package com.example.demo.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    QUESTION_LIKE("QUESTION_LIKE"),
    COMMENT("COMMENT");

    private final String type;

}